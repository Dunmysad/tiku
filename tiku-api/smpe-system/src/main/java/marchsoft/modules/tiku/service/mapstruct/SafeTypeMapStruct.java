package marchsoft.modules.tiku.service.mapstruct;

import marchsoft.base.BaseMapStruct;
import marchsoft.modules.tiku.entity.Dto.ESafeTypeDto;
import marchsoft.modules.tiku.entity.ESafeType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @Author: zhoubo
 * @Date: 2022/4/19 9:45
 * @Version 1.0
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SafeTypeMapStruct extends BaseMapStruct<ESafeTypeDto, ESafeType> {
}
