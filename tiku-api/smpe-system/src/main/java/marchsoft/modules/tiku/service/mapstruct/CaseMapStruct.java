package marchsoft.modules.tiku.service.mapstruct;

import marchsoft.base.BaseMapStruct;
import marchsoft.modules.system.entity.Dept;
import marchsoft.modules.system.entity.dto.DeptDTO;
import marchsoft.modules.tiku.entity.Dto.CaseDto;
import marchsoft.modules.tiku.entity.ESafeCase;
import org.apache.ibatis.annotations.Case;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CaseMapStruct extends BaseMapStruct<CaseDto, ESafeCase> {
}
