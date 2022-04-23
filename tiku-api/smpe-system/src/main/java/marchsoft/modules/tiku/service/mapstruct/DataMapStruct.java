package marchsoft.modules.tiku.service.mapstruct;

import marchsoft.base.BaseMapStruct;
import marchsoft.modules.tiku.entity.Dto.CaseDto;
import marchsoft.modules.tiku.entity.Dto.StudyDataDto;
import marchsoft.modules.tiku.entity.ESafeCase;
import marchsoft.modules.tiku.entity.EStudyData;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DataMapStruct extends BaseMapStruct<StudyDataDto, EStudyData> {
}
