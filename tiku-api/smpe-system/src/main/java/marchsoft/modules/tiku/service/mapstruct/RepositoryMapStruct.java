package marchsoft.modules.tiku.service.mapstruct;

import marchsoft.base.BaseMapStruct;
import marchsoft.modules.tiku.entity.Dto.RepositoryDto;
import marchsoft.modules.tiku.entity.Dto.RepositoryExcel;
import marchsoft.modules.tiku.entity.ERepository;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RepositoryMapStruct extends BaseMapStruct<RepositoryDto, ERepository> {
    List<ERepository> excel(List<RepositoryExcel> cachedDataList);
}
