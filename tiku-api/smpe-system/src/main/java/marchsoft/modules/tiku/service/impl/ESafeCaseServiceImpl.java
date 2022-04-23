package marchsoft.modules.tiku.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.system.entity.Dept;
import marchsoft.modules.system.entity.dto.DeptDTO;
import marchsoft.modules.system.entity.dto.DeptQueryCriteria;
import marchsoft.modules.system.service.mapstruct.DeptMapStruct;
import marchsoft.modules.tiku.entity.Dto.CaseDto;
import marchsoft.modules.tiku.entity.Dto.CaseQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.safeCase.SafeCaseNameDto;
import marchsoft.modules.tiku.entity.ESafeCase;
import marchsoft.modules.tiku.mapper.ESafeCaseMapper;
import marchsoft.modules.tiku.service.IESafeCaseService;
import marchsoft.base.BasicServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import marchsoft.modules.tiku.service.mapstruct.CaseMapStruct;
import marchsoft.utils.FileUtils;
import marchsoft.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* <p>
* 安全案例 服务实现类
* </p>
*
* @author mfei
* @since 2022-03-29
*/
/**
 * <p>
 * 安全案例 服务实现类
 * </p>
 *
 * @author qisiyan
 * @since 2022-03-29
 */
@Service
@RequiredArgsConstructor
public class ESafeCaseServiceImpl extends BasicServiceImpl<ESafeCaseMapper, ESafeCase> implements IESafeCaseService {

    private final ESafeCaseMapper eSafeCaseMapper;
    private final CaseMapStruct caseMapStruct;

    private LambdaQueryWrapper<ESafeCase> buildCaseQueryCriteria (CaseQueryCriteria criteria){
        LambdaQueryWrapper<ESafeCase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(criteria.getKeywords())){
            lambdaQueryWrapper.like(ESafeCase::getKeywords, criteria.getKeywords());
        }
        if (ObjectUtil.isNotNull(criteria.getSafeType())){
            lambdaQueryWrapper.eq(ESafeCase::getSafeType, criteria.getSafeType());
        }
        if (StrUtil.isNotBlank(criteria.getTitle())){
            lambdaQueryWrapper.like(ESafeCase::getTitle, criteria.getTitle());
        }

        lambdaQueryWrapper.eq(ESafeCase::getDeleted, 0);
        return lambdaQueryWrapper;
    }

    // 进行条件查询
    private QueryWrapper<ESafeCase> buildSafeCaseCriteria(CaseQueryCriteria criteria) {
        QueryWrapper<ESafeCase> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(criteria.getId())){
            queryWrapper.eq("sc.id",criteria.getId());
        }
        if (ObjectUtil.isNotNull(criteria.getTitle())){
            // 使用like  进行模糊查询
            queryWrapper.like("sc.title",criteria.getTitle());
        }
        if (ObjectUtil.isNotNull(criteria.getContent())){
            queryWrapper.eq("sc.content",criteria.getContent());
        }
        if (ObjectUtil.isNotNull(criteria.getSafeType())){
            queryWrapper.eq("sc.safe_type",criteria.getSafeType());
        }
        if (ObjectUtil.isNotNull(criteria.getPicPath())){
            queryWrapper.eq("sc.pic_path",criteria.getPicPath());
        }
        if (ObjectUtil.isNotNull(criteria.getKeywords())){
            // 使用like 进行模糊查询
            queryWrapper.like("sc.keywords",criteria.getKeywords());
        }
        queryWrapper.eq("sc.is_deleted",0);

        return queryWrapper;
    }

    //进行条件查询
    @Override
    public IPage<SafeCaseNameDto> selectSafeCase(IPage<ESafeCase> page, CaseQueryCriteria criteria) {
        QueryWrapper<ESafeCase> queryWrapper = buildSafeCaseCriteria(criteria);
        IPage<SafeCaseNameDto> eSafeCaseIPage = eSafeCaseMapper.selectSafeCase(page, queryWrapper);
        return eSafeCaseIPage;
    }


    @Override
    public void insert(ESafeCase eSafeCase) {

        //  判断增加的id是否为空
        if (ObjectUtil.isNotNull(eSafeCase.getId())) {
            ESafeCase eSafeCase1 = eSafeCaseMapper.selectById(eSafeCase.getId());
            if (ObjectUtil.isNull(eSafeCase1)){
                throw new BadRequestException(ResultEnum.CASE_DATA_IS_EXIST);
            }
        }

        // 判断案例库中是否已经存在该id
        LambdaQueryWrapper<ESafeCase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ESafeCase::getId,eSafeCase.getId());
        ESafeCase eSafeCase1 = eSafeCase.selectOne(queryWrapper);

        // 判断案例库中是否已经存在该内容（标题：title)
        LambdaQueryWrapper<ESafeCase> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(ESafeCase::getTitle,eSafeCase.getTitle());
        ESafeCase eSafeCase2 = eSafeCase.selectOne(queryWrapper1);
        // 判断
        if (ObjectUtil.isNotNull(eSafeCase1)) {
            throw new BadRequestException(ResultEnum.CASE_SHOW_DATA_IS_EXIST);
        }else if (ObjectUtil.isNotNull(eSafeCase2)) {
            throw new BadRequestException(ResultEnum.CASE_TiTLE_IS_EXIST);
        }


        int insert = eSafeCaseMapper.insert(eSafeCase);
        if (insert < 1) {
            throw new BadRequestException(ResultEnum.INSERT_OPERATION_FAIL);
        }
    }

    @Override
    // @CacheEvict(key = "'name'",allEntries = true)
    public void updateSafeCase(ESafeCase eSafeCase) {
        // 通过id进行查询信息
        ESafeCase oldESafeCase = eSafeCaseMapper.selectById(eSafeCase.getId());

        if (ObjectUtil.isNotNull(eSafeCase.getId()) && !eSafeCase.getId().equals(oldESafeCase.getId())) {
            ESafeCase eSafeCase1 = eSafeCaseMapper.selectById(eSafeCase.getId());
            if(ObjectUtil.isNull(eSafeCase1)){
                throw new BadRequestException(ResultEnum.CASE_SHOW_DATA_IS_EXIST);
            }

        }
//        // 判断id是否重复
//        LambdaQueryWrapper<ESafeCase> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(ESafeCase::getId,eSafeCase.getId());
//        ESafeCase eSafeCase2 = eSafeCaseMapper.selectOne(wrapper);
//        if (ObjectUtil.isNotNull(eSafeCase2)){
//            throw new BadRequestException(ResultEnum.CASE_SHOW_DATA_IS_EXIST);
//        }

        // 判断内容（标题：title)是否重复
        LambdaQueryWrapper<ESafeCase> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(ESafeCase::getTitle,eSafeCase.getTitle());
        ESafeCase eSafeCase3 = eSafeCase.selectOne(wrapper1);
        if (ObjectUtil.isNotNull(eSafeCase3)){
            throw new BadRequestException(ResultEnum.CASE_TiTLE_IS_EXIST);
            //
        }



        int i = eSafeCaseMapper.updateById(eSafeCase);
        if (i < 1){
            throw new BadRequestException(ResultEnum.INSERT_OPERATION_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSafeCase(Set<Long> ids) {
        for(Long id : ids){
            eSafeCaseMapper.deleteById(id);
        }
    }

    /**
     * Description:
     * 导出queryAll的数据
     *
     * @param caseDtos: 待到处数据
     * @param response: 服务器响应对象
     * @throws IOException io异常
     * @author liuxingxing
     * @date 2020/11/26 15:45
     **/
    @Override
    public void download(List<CaseDto> caseDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CaseDto caseDto : caseDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("案例标题", caseDto.getTitle());
            map.put("案例内容", caseDto.getContent());
            map.put("安全分类id", caseDto.getSafeType());
            map.put("案例封面图片", caseDto.getPicPath());
            map.put("关键词", caseDto.getKeywords());
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
    public List<CaseDto> queryAll(CaseQueryCriteria criteria, Boolean isQuery) {
        List<ESafeCase> cases = this.eSafeCaseMapper.selectList(analysisQueryCriteria(criteria));
        return caseMapStruct.toDto(cases);
    }

    private LambdaQueryWrapper<ESafeCase> analysisQueryCriteria(CaseQueryCriteria criteria) {
        LambdaQueryWrapper<ESafeCase> wrapper = new LambdaQueryWrapper<>();


        return wrapper;
    }

}


