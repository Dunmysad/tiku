package marchsoft.modules.tiku.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import marchsoft.base.BasicServiceImpl;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.entity.Dto.ESafeTypeDto;
import marchsoft.modules.tiku.entity.Dto.SlideshowDto;
import marchsoft.modules.tiku.entity.Dto.SlideshowQueryCriteria;
import marchsoft.modules.tiku.entity.ESlideshow;
import marchsoft.modules.tiku.entity.EStudyData;
import marchsoft.modules.tiku.mapper.ESlideshowMapper;
import marchsoft.modules.tiku.mapper.EStudyDataMapper;
import marchsoft.modules.tiku.service.IESlideshowService;
import marchsoft.modules.tiku.service.mapstruct.SlideshowMapStruct;
import marchsoft.utils.RedisUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
* <p>
* 轮播图 服务实现类
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "slidenshow")
public class ESlideshowServiceImpl extends BasicServiceImpl<ESlideshowMapper, ESlideshow> implements IESlideshowService {

    private final ESlideshowMapper eSlideshowMapper;
    private final EStudyDataMapper eStudyDataMapper;
    private final SlideshowMapStruct slideshowMapStruct;

    private final RedisUtils redisUtils;

    @Override
    @Cacheable(key = "'all:alls'")
    public List<ESlideshow> getAllSlide() {
        return eSlideshowMapper.selectList(null);
    }

    @Override
    @Cacheable(key = "'all:'  + #page.current + #p0.size ")
    public IPage<ESlideshow> getAll(IPage<ESlideshow> page) {
        redisUtils.set("404key","404value",1, TimeUnit.DAYS);
        IPage<ESlideshow> eSlideshowIPage = eSlideshowMapper.getAllPage(page);
        return eSlideshowIPage;
    }

    @Override
    @CacheEvict(key = "'all'",allEntries = true)
    public void addSlideshow(ESlideshow eSlideshow) {
        //步骤一： 如果学习资料id不为空，判断数据库中是否有该学习资料
        if (ObjectUtil.isNotNull(eSlideshow.getStudyDataId())) {
            EStudyData eStudyData = eStudyDataMapper.selectById(eSlideshow.getStudyDataId());
            if(ObjectUtil.isNull(eStudyData)) {
                throw new BadRequestException(ResultEnum.STUDY_DATA_NOT_EXIST);
            }
        }

        // 学习资料id为空或者数据库中有该学习资料
        //步骤二：判断前端传过来的顺序和轮播图的中学习资料id是否与数据库中已有的重复
        LambdaQueryWrapper<ESlideshow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ESlideshow::getSort,eSlideshow.getSort());
        ESlideshow eSlideshow1 = eSlideshowMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNotNull(eSlideshow1)) {
            throw new BadRequestException(ResultEnum.SLIDE_SHOW_IS_EXIST);
        }
        // 判断存在学习资料id的情况下，学习资料id在轮播图中是否重复
        if (ObjectUtil.isNotNull(eSlideshow.getStudyDataId())) {
            LambdaQueryWrapper<ESlideshow> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ESlideshow::getStudyDataId,eSlideshow.getStudyDataId());
            ESlideshow eSlideshow2 = eSlideshowMapper.selectOne(wrapper);
            if (ObjectUtil.isNotNull(eSlideshow2)) {
                throw new BadRequestException(ResultEnum.SLIDE_SHOW_STUDY_DATA_IS_EXIST);
            }
        }
        // 新增轮播图
        int i = eSlideshowMapper.insert(eSlideshow);
        if(i<1) {
            throw new BadRequestException(ResultEnum.INSERT_OPERATION_FAIL);
        }

    }

    @Override
    @CacheEvict(key = "'all'",allEntries = true)
    public void updateEslideshow(ESlideshow eSlideshow) {
        // 通过id查询当前的轮播图信息
        ESlideshow oldESlideshow = eSlideshowMapper.selectById(eSlideshow.getId());
        // 前端传过来的值studyDataId不为空，；并且和旧值不相同
        if (ObjectUtil.isNotNull(eSlideshow.getStudyDataId()) && !eSlideshow.getStudyDataId().equals(oldESlideshow.getStudyDataId())) {
            EStudyData eStudyData = eStudyDataMapper.selectById(eSlideshow.getStudyDataId());
            if(ObjectUtil.isNull(eStudyData)) {
                throw new BadRequestException(ResultEnum.STUDY_DATA_NOT_EXIST);
            }
            // 判断studyDataId是否重复
            LambdaQueryWrapper<ESlideshow> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ESlideshow::getStudyDataId,eSlideshow.getStudyDataId());
            ESlideshow eSlideshow2 = eSlideshowMapper.selectOne(wrapper);
            if (ObjectUtil.isNotNull(eSlideshow2)) {
                throw new BadRequestException(ResultEnum.SLIDE_SHOW_STUDY_DATA_IS_EXIST);
            }
        }

        if (!oldESlideshow.getSort().equals(eSlideshow.getSort())) {
            LambdaQueryWrapper<ESlideshow> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ESlideshow::getSort,eSlideshow.getSort());
            ESlideshow eSlideshow1 = eSlideshowMapper.selectOne(queryWrapper);
            if (ObjectUtil.isNotNull(eSlideshow1)) {
                throw new BadRequestException(ResultEnum.SLIDE_SHOW_IS_EXIST);
            }
        }

        // 新增轮播图
        int i = eSlideshowMapper.updateById(eSlideshow);
        if(i<1) {
            throw new BadRequestException(ResultEnum.INSERT_OPERATION_FAIL);
        }


    }

    @Override
    @CacheEvict(key = "'all'",allEntries = true)
    public void deleteById(Set<Long> ids) {
        for (Long id : ids) {
            eSlideshowMapper.deleteById(id);
        }
    }


    private LambdaQueryWrapper<ESlideshow> analysisQueryCriteria(SlideshowQueryCriteria criteria){
        LambdaQueryWrapper<ESlideshow> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }


}

