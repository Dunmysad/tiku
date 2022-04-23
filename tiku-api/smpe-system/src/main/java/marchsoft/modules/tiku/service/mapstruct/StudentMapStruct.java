package marchsoft.modules.tiku.service.mapstruct;

import marchsoft.modules.system.entity.dto.UserDTO;
import marchsoft.modules.tiku.entity.Dto.StudentDto;
import marchsoft.modules.tiku.entity.Dto.StudentExcel;
import marchsoft.modules.tiku.entity.EStudent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapStruct {


    UserDTO toUserDto(EStudent student);

    List<StudentDto> toDto(List<EStudent> cases);
    List<EStudent> excel(List<StudentExcel> cachedDataList);
}
