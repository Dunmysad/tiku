package marchsoft.modules.tiku.service.mapstruct;

import marchsoft.base.BaseMapStruct;
import marchsoft.modules.tiku.entity.Dto.SlideshowDto;
import marchsoft.modules.tiku.entity.ESlideshow;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @Author: zhoubo
 * @Date: 2022/4/19 8:39
 * @Version 1.0
 */


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SlideshowMapStruct extends BaseMapStruct<SlideshowDto, ESlideshow> {

}
