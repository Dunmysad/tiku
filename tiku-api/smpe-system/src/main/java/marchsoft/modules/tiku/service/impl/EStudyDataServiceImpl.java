package marchsoft.modules.tiku.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.RequiredArgsConstructor;
import marchsoft.base.BaseMapStruct;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.entity.Dto.CaseDto;
import marchsoft.modules.tiku.entity.Dto.CaseQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.StudyDataDto;
import marchsoft.modules.tiku.entity.Dto.StudyDataQueryCriteria;
import marchsoft.modules.tiku.entity.ESafeCase;
import marchsoft.modules.tiku.entity.EStudyData;
import marchsoft.modules.tiku.mapper.EStudyDataMapper;
import marchsoft.modules.tiku.service.IEStudyDataService;
import marchsoft.base.BasicServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marchsoft.modules.tiku.service.mapstruct.DataMapStruct;
import marchsoft.utils.FileUtils;
import marchsoft.utils.RedisUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* <p>
* 学习资料表 服务实现类
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Service
@RequiredArgsConstructor
public class EStudyDataServiceImpl extends BasicServiceImpl<EStudyDataMapper, EStudyData> implements IEStudyDataService {

    private final EStudyDataMapper eStudyDataMapper;
    private final DataMapStruct dataMapStruct;

    // 进行条件查询
    private LambdaQueryWrapper<EStudyData> buildStudyDataCriteria(StudyDataQueryCriteria criteria) {
        LambdaQueryWrapper<EStudyData> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(criteria.getId())){
            queryWrapper.eq(EStudyData::getId,criteria.getId());
        }
        if (ObjectUtil.isNotNull(criteria.getKeywords())){
            // 使用like查询
            queryWrapper.like(EStudyData::getKeywords,criteria.getKeywords());
        }
        if (ObjectUtil.isNotNull(criteria.getDataType())){
            queryWrapper.eq(EStudyData::getDataType,criteria.getDataType());
        }
        if (ObjectUtil.isNotNull(criteria.getIsIntroduce())){
            queryWrapper.eq(EStudyData::getIntroduce,criteria.getIsIntroduce());
        }
        if (ObjectUtil.isNotNull(criteria.getSafeTypeId())){
            queryWrapper.eq(EStudyData::getSafeTypeId,criteria.getSafeTypeId());
        }
        if (ObjectUtil.isNotNull(criteria.getTitle())){
            // 使用like查询
            queryWrapper.like(EStudyData::getTitle,criteria.getTitle());
        }
        queryWrapper.eq(EStudyData::getDeleted,0);

        return queryWrapper;
    }



    // 进行条件查询
    @Override
    public IPage<EStudyData> selectStudyData(IPage<EStudyData> page, StudyDataQueryCriteria criteria) {
        IPage<EStudyData> eStudyDataIPage = eStudyDataMapper.selectStudyData(page, buildStudyDataCriteria(criteria));
        return eStudyDataIPage;
    }


    // 增加资料数据
    @Override
    public void addStudyData(EStudyData eStudyData) {
        //   一、判断增加的id是否为空
        if (ObjectUtil.isNotNull(eStudyData.getId())) {
            EStudyData eStudyData1 = eStudyDataMapper.selectById(eStudyData.getId());
            if (ObjectUtil.isNull(eStudyData1)){
                throw new BadRequestException(ResultEnum.STUDY_DATA_NOT_EXIST);
            }
        }

        // 判断资料库中是否已经存在该数据   根据文章标题进行判断
        LambdaQueryWrapper<EStudyData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EStudyData::getTitle,eStudyData.getTitle());
        EStudyData eStudyData1 = eStudyData.selectOne(queryWrapper);

            // 判断
            if (ObjectUtil.isNotNull(eStudyData1)) {
                throw new BadRequestException(ResultEnum.STUDY_DATA_HAD_EXIST);
            }

            int i = eStudyDataMapper.insert(eStudyData);
            if (i<1){
                throw new BadRequestException(ResultEnum.INSERT_OPERATION_FAIL);
            }
        }


 // 修改资料的数据
    @Override
    public void updateStudyData(EStudyData eStudyData) {
        // 通过id进行查询信息
        EStudyData oldEStudyData = eStudyDataMapper.selectById(eStudyData.getId());

        if (ObjectUtil.isNotNull(eStudyData.getId()) && !eStudyData.getId().equals(oldEStudyData.getId())) {
            EStudyData eStudyData1 = eStudyDataMapper.selectById(eStudyData.getId());
            if(ObjectUtil.isNull(eStudyData1)){
                throw new BadRequestException(ResultEnum.STUDY_DATA_NOT_EXIST);
            }
            // 判断id是否重复
            LambdaQueryWrapper<EStudyData> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(EStudyData::getId,eStudyData.getId());
            EStudyData eStudyData2 = eStudyDataMapper.selectOne(wrapper);
            if (ObjectUtil.isNotNull(eStudyData2)){
                throw new BadRequestException(ResultEnum.SLIDE_SHOW_STUDY_DATA_IS_EXIST);
            }
        }


        int i = eStudyDataMapper.updateById(eStudyData);
        if (i < 1){
            throw new BadRequestException(ResultEnum.INSERT_OPERATION_FAIL);
        }

    }

    @Override
    public void deleteStudyData(Set<Long> ids) {
        for(Long id : ids){
            eStudyDataMapper.deleteById(id);
        }
    }
    //导出学习资料
    @Override
    public void download(List<StudyDataDto> studyDataDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudyDataDto studyDataDto : studyDataDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("文章标题", studyDataDto.getTitle());
            map.put("案例类别", studyDataDto.getSafeTypeId());
            map.put("关键词", studyDataDto.getKeywords());
            map.put("资源类型", studyDataDto.getDataType());
            map.put("是否推荐", studyDataDto.getIsIntroduce());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }
    /**
     * Description:
     * 根据criteria条件和isQuery查询
     * todo isQuery的作用
     *
     * @param criteria: Dept查询条件
     * @param isQuery:  是否判断当前用户数据权限
     * @return java.util.List<marchsoft.modules.system.entity.dto.DeptDTO>
     * @author liuxingxing
     * @date 2020/11/26 15:45
     **/
    @Override
    public List<StudyDataDto> queryAll(StudyDataQueryCriteria criteria, Boolean isQuery) {
        List<EStudyData> datas = this.eStudyDataMapper.selectList(analysisQueryCriteria(criteria));

        return dataMapStruct.toDto(datas);
    }

    private LambdaQueryWrapper<EStudyData> analysisQueryCriteria(StudyDataQueryCriteria criteria) {
        LambdaQueryWrapper<EStudyData> wrapper = new LambdaQueryWrapper<>();


        return wrapper;
    }
}

