package marchsoft.modules.tiku.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import marchsoft.base.BasicServiceImpl;
import marchsoft.base.PageVO;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.entity.Dto.AddSafeTypeDto;
import marchsoft.modules.tiku.entity.Dto.ESafeTypeDto;
import marchsoft.modules.tiku.entity.Dto.SafeTypeQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.TypeStatusDto;
import marchsoft.modules.tiku.entity.ESafeType;
import marchsoft.modules.tiku.mapper.ESafeTypeMapper;
import marchsoft.modules.tiku.service.IESafeTypeService;
import marchsoft.modules.tiku.service.mapstruct.SaftTypeMapStruct;
import marchsoft.utils.FileUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* <p>
* 安全类别 服务实现类
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "type")
public class ESafeTypeServiceImpl extends BasicServiceImpl<ESafeTypeMapper, ESafeType> implements IESafeTypeService {

    private final ESafeTypeMapper eSafeTypeMapper;
    private final SaftTypeMapStruct saftTypeMapStruct;

    @Override
//    @Cacheable(key = "'name:'+ #criteria.name + #pageVO.size + #pageVO.current + #pageVO.orders")
    public IPage<ESafeType> selectSafeType(SafeTypeQueryCriteria criteria, PageVO pageVO) {
        LambdaQueryWrapper<ESafeType> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(criteria.getId())){
            queryWrapper.eq(ESafeType::getId, criteria.getId());
        }
        if (BooleanUtil.isFalse(criteria.getIsShow())){
            queryWrapper.eq(ESafeType::getIsShow, 1);
        }
        if (StrUtil.isNotBlank(criteria.getName())) {
            queryWrapper.like(ESafeType::getName,criteria.getName());
        }
        IPage<ESafeType> eSafeTypeIPage = eSafeTypeMapper.selectPage(pageVO.buildPage(),queryWrapper);
        return eSafeTypeIPage;
    }

    @Override
    @Cacheable(key = "'name:all'")
    public List<ESafeTypeDto> queryAllName() {
        // 状态为显示，没有被删除的
        // 方式一： 使用wrapper
        LambdaQueryWrapper<ESafeType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ESafeType::getIsShow,1);
        List<ESafeType> eSafeTypes = eSafeTypeMapper.selectList(queryWrapper);
        List<ESafeTypeDto> eSafeTypeDtos = saftTypeMapStruct.entityToDto(eSafeTypes);

        // 方式二：手写sql
//        eSafeTypeMapper.queryAllName();
        return eSafeTypeDtos;
    }

    @Override
    @CacheEvict(key = "'name'",allEntries = true)
    public void insert(AddSafeTypeDto safeTypeDto) {
        // 判断分类排序字段是否重复
        LambdaQueryWrapper<ESafeType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ESafeType::getSeqNum,safeTypeDto.getSeqNum());
        ESafeType type = this.getOne(queryWrapper);
        if (ObjectUtil.isNotNull(type)) {
            // 根据排序字段查询已有的数据库，能得到一条数据，表示前端传过来的分类排序不符合要求
            throw new BadRequestException(ResultEnum.SORT_IS_EXIST);
        }
        ESafeType safeType = saftTypeMapStruct.addDtoToEntity(safeTypeDto);
        int insert = eSafeTypeMapper.insert(safeType);
        if (insert < 1) {
            throw new BadRequestException(ResultEnum.INSERT_OPERATION_FAIL);
        }
    }

    @Override
    @CacheEvict(key = "'name'",allEntries = true)
    public void updateSafeType(ESafeType safeType) {
        if (ObjectUtil.isNull(safeType.getId())) {
            throw new BadRequestException(ResultEnum.BAD_REQUEST);
        }
        ESafeType byId = this.getById(safeType.getId());
        if (!byId.getSeqNum().equals(safeType.getSeqNum())) {
            // 更新修改了排序字段
            // 判断分类排序字段是否重复
            LambdaQueryWrapper<ESafeType> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ESafeType::getSeqNum,safeType.getSeqNum());
            ESafeType type = this.getOne(queryWrapper);
            if (ObjectUtil.isNotNull(type)) {
                // 根据排序字段查询已有的数据库，能得到一条数据，表示前端传过来的分类排序不符合要求
                throw new BadRequestException(ResultEnum.SORT_IS_EXIST);
            }
        }

        eSafeTypeMapper.updateById(safeType);
    }

    @Override
    @CacheEvict(key = "'name'",allEntries = true)
    public void updateShow(TypeStatusDto typeStatusDto) {
        eSafeTypeMapper.updateShow(typeStatusDto);
    }

    @Override
    @CacheEvict(key = "'name'",allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void del(Set<Long> ids) {
        this.removeByIds(ids);
    }


    /**
     *功能描述:
     *   导出queryAll数据
     * @param queryAll queryAll
     * @param response response
     * @author zhoubo
     * @date 2022/4/15
     */
    @Override
    public void download(List<ESafeTypeDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String,Object>> list = new ArrayList<>();
        for (ESafeTypeDto eSafeTypeDto : queryAll){
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("分类名称",eSafeTypeDto.getName());
            map.put("分类简称",eSafeTypeDto.getForShort());
            map.put("分类代码",eSafeTypeDto.getRooterPath());
            map.put("创建时间",eSafeTypeDto.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list,response);
    }
    private LambdaQueryWrapper<ESafeType> analysisQueryCriteria(SafeTypeQueryCriteria criteria){
        LambdaQueryWrapper<ESafeType> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }
    /**
     *功能描述:
     * 查询条件
     * @param criteria criteria
     * @param isQuery isQuery
     * @return List
     * @author zhoubo
     * @date 2022/4/15
     */
    @Override
    public List<ESafeTypeDto> queryAll(SafeTypeQueryCriteria criteria, boolean isQuery) {
        List<ESafeType> types = this.eSafeTypeMapper.selectList(analysisQueryCriteria(criteria));
        return saftTypeMapStruct.entityToDto(types);
    }
}

