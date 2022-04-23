package marchsoft.modules.tiku.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import marchsoft.modules.tiku.entity.Dto.SlideshowDto;
import marchsoft.modules.tiku.entity.Dto.SlideshowQueryCriteria;
import marchsoft.modules.tiku.entity.ESlideshow;
import marchsoft.base.IBasicService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
public interface IESlideshowService extends IBasicService<ESlideshow> {


    /**
     *功能描述:
     *   获取所有的轮播图
     * @return List
     * @author mfei
     * @date 2022/3/29
    */
    List<ESlideshow> getAllSlide();

    /**
     *功能描述: 根据条件查询轮播图
     *
     * @param page buildPage
     * @author mfei
     * @date 2022/3/30
    */
    IPage<ESlideshow> getAll(IPage<ESlideshow> page);


    /**
     *功能描述: 新增轮播图
     *
     * @param eSlideshow eSlideshow
     * @author mfei
     * @date 2022/3/30
    */
    void addSlideshow(ESlideshow eSlideshow);

    /**
     *功能描述: 修改轮播图
     *
     * @param eSlideshow eSlideshow
     * @author mfei
     * @date 2022/3/30
    */
    void updateEslideshow(ESlideshow eSlideshow);

    /**
     *功能描述: 删除轮播图
     *
     * @param ids ids
     * @author mfei
     * @date 2022/3/30
    */
    void deleteById(Set<Long> ids);


}
