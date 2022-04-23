package marchsoft.modules.tiku.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import marchsoft.modules.tiku.entity.ESafeType;
import marchsoft.modules.tiku.entity.ESlideshow;
import marchsoft.base.BasicMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
* <p>
* 轮播图 Mapper 接口
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Component
public interface ESlideshowMapper extends BasicMapper<ESlideshow> {

    @Select("SELECT * FROM e_slideshow WHERE is_deleted = 0")
    IPage<ESlideshow> getAllPage(IPage<ESlideshow> page);



}
