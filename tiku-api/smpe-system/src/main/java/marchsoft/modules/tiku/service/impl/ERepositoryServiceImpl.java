package marchsoft.modules.tiku.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import marchsoft.base.BasicServiceImpl;
import marchsoft.enums.RepositoryEnum;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.entity.Dto.ERepositoryQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.RepositoryDto;
import marchsoft.modules.tiku.entity.Dto.RepositoryExcel;
import marchsoft.modules.tiku.entity.ERepository;
import marchsoft.modules.tiku.entity.ESafeType;
import marchsoft.modules.tiku.mapper.ERepositoryMapper;
import marchsoft.modules.tiku.mapper.ESafeTypeMapper;
import marchsoft.modules.tiku.service.IERepositoryService;
import marchsoft.modules.tiku.service.mapstruct.RepositoryMapStruct;
import marchsoft.utils.FileUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
* 题库 服务实现类
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "repository")
public class ERepositoryServiceImpl extends BasicServiceImpl<ERepositoryMapper, ERepository> implements IERepositoryService {

    private final ERepositoryMapper eRepositoryMapper;
    private final RepositoryMapStruct repositoryMapStruct;
    private final ESafeTypeMapper eSafeTypeMapper;

    /**
     * 更新数据之前的判断
     * @param criteria
     * @return
     */
    private LambdaQueryWrapper<ERepository> buildERepositoryCriteria(ERepositoryQueryCriteria criteria){
        LambdaQueryWrapper<ERepository> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(criteria.getId())) {
            queryWrapper.eq(ERepository::getId,criteria.getId());
        }

        if (ObjectUtil.isNotNull(criteria.getSafeTypeId())) {
            queryWrapper.eq(ERepository::getSafeTypeId,criteria.getSafeTypeId());
        }

        if (StrUtil.isNotBlank(criteria.getQuesContent())) {
            //默认使用like匹配
            queryWrapper.like(ERepository::getQuesContent, criteria.getQuesContent());
        }

        if (ObjectUtil.isNotNull(criteria.getQuesType())) {
            queryWrapper.eq(ERepository::getQuesType, criteria.getQuesType());
        }
        if (ObjectUtil.isNotNull(criteria.getStartTime()) && ObjectUtil.isNotNull(criteria.getEndTime())) {
            queryWrapper.between(ERepository::getCreateTime, criteria.getStartTime(), criteria.getEndTime());
        }

        queryWrapper.eq(ERepository::getDeleted, 0);
        return queryWrapper;
    }


    /**
     * 查询题库中所有的题，当前端搜索框无参数信息传入时为查询全部题
     * @param page
     * @param criteria
     * @return
     */
    @Override
    public IPage<ERepository> getAll(IPage<ERepository> page, ERepositoryQueryCriteria criteria) {
        IPage<ERepository> eRepositoryIPage = eRepositoryMapper.getAll(page, buildERepositoryCriteria(criteria));
        return eRepositoryIPage;
    }

    /**
     * 新增题
     * @param eRepository
     */
    @Override
    public void addERepository(ERepository eRepository) {
        eRepository.setCreateTime(null);
        // 判断新增题是否与题库中的题重复
        if (StrUtil.isNotBlank(eRepository.getQuesContent())){
            LambdaQueryWrapper<ERepository> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ERepository::getQuesContent, eRepository.getQuesContent());
            ERepository one = this.getOne(wrapper);
            if (ObjectUtil.isNotNull(one)){
                throw new BadRequestException(ResultEnum.REPOSITORY_SHOW_ID_IS_EXIST);
            }
        }else{
            throw new BadRequestException("题干为空！");
        }

        int i = eRepositoryMapper.insert(eRepository);
    }

    /**
     * 更新题
     * @param eRepository
     */
    @Override
    public void updateERepository(ERepository eRepository) {
        if (StrUtil.isNotBlank(eRepository.getQuesContent())){
            // 获取旧值
            ERepository oldERpository = eRepositoryMapper.selectById(eRepository.getId());
            // 如果当前题干不为空 且 当前题干与旧题干不同
            if(ObjectUtil.isNotNull(eRepository.getQuesContent()) && !eRepository.getQuesContent().equals(oldERpository.getQuesContent())){
                // 获取题干信息 与 题库对比
                LambdaQueryWrapper<ERepository> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ERepository::getQuesContent, eRepository.getQuesContent());
                ERepository eRepository1 = eRepositoryMapper.selectOne(wrapper);
                if (ObjectUtil.isNotNull(eRepository1)){
                    throw new BadRequestException(ResultEnum.REPOSITORY_SHOW_ID_IS_EXIST);
                }
            }
        }
        int i = eRepositoryMapper.updateById(eRepository);
    }

    /**
     * 删除题
     * @param ids
     */
    @Override
    public void deleteById(Set<Long> ids) {
        for (Long id : ids){
            eRepositoryMapper.deleteById(id);
        }
    }

    /**
     * 根据criteria条件和isQuery查询
     * @param criteria
     * @param isQuery
     * @return
     */
    @Override
    public List<RepositoryDto> queryAll(ERepositoryQueryCriteria criteria, boolean isQuery) {
        List<ERepository> repositories = this.eRepositoryMapper.selectList(analysisQueryCriteria(criteria));
        return repositoryMapStruct.toDto(repositories);
    }

    /**
     * 下载题库模版
     * @param isQuery
     * @return
     */
    @Override
    public List<RepositoryDto> query(boolean isQuery) {
        return null;
    }

    private LambdaQueryWrapper<ERepository> analysisQueryCriteria(ERepositoryQueryCriteria criteria){
        LambdaQueryWrapper<ERepository> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    /**
     * 下载题库信息
     * @param repositoryDtos
     * @param response
     * @throws IOException
     */
    @Override
    public void download(List<RepositoryDto> repositoryDtos, HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = new ArrayList<>();
        List<ESafeType> eSafeTypes = eSafeTypeMapper.selectAll();
        Map<Long, String> collect = eSafeTypes.stream().collect(Collectors.toMap(ESafeType::getId, ESafeType::getName));

        for (RepositoryDto repositoryDto : repositoryDtos){
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("题干", repositoryDto.getQuesContent());
            map.put("题目类型", RepositoryEnum.findById(Long.valueOf(repositoryDto.getQuesType())).getName());
            map.put("安全类别", collect.get(repositoryDto.getSafeTypeId()));
            map.put("答案", repositoryDto.getAnswer());
            map.put("选项", repositoryDto.getOptions());
            list.add(map);

        }
        FileUtils.downloadExcel(list, response);
    }

    /**
     *  下载题库模版
     * @param query
     * @param response
     * @throws IOException
     */
    @Override
    public void downloadTemplate(List<RepositoryDto> query, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("题干", null);
        map.put("题目类型", null);
        map.put("安全类别", null);
        map.put("答案", null);
        map.put("选项", null);
        list.add(map);
        FileUtils.downloadExcel(list, response);
    }


    /**
     * 导入Excel表格
     * @param cachedDataList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addExcel(List<RepositoryExcel> cachedDataList) {
        List<ERepository> excel = repositoryMapStruct.excel(cachedDataList);
        List<ESafeType> eSafeTypes = eSafeTypeMapper.selectList(null);
        Map<String, Long> collect = eSafeTypes.stream().collect(Collectors.toMap(ESafeType::getName, ESafeType::getId));

        for (int i = 0; i < excel.size(); i++) {
            ERepository eRepository = excel.get(i);
            if (ObjectUtil.isNull(cachedDataList.get(i).getQuesTypeName())) {
                throw new BadRequestException(ResultEnum.INSERT_OPERATION_FAIL);
            }
            eRepository.setQuesType(Math.toIntExact(RepositoryEnum.findByName(cachedDataList.get(i).getQuesTypeName()).getId()));
            eRepository.setSafeTypeId(collect.get(cachedDataList.get(i).getSafeTypeName()));
            this.addERepository(eRepository);
        }
    }
}

