package marchsoft.modules.tiku.service.mapstruct;

import marchsoft.modules.tiku.entity.Dto.AddSafeTypeDto;
import marchsoft.modules.tiku.entity.Dto.ESafeTypeDto;
import marchsoft.modules.tiku.entity.ESafeType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SaftTypeMapStruct {

    /**
     *功能描述: 实体类转Dto
     *
     * @param eSafeTypeList eSafeTypeList
     * @return List
     * @author mfei
     * @date 2022/4/1
    */
    List<ESafeTypeDto> entityToDto(List<ESafeType> eSafeTypeList);


    ESafeType addDtoToEntity(AddSafeTypeDto safeTypeDto);
}
