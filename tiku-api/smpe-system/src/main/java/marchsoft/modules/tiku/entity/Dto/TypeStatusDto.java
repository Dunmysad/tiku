package marchsoft.modules.tiku.entity.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description：
 *
 * @author mfei
 * Date: 2022/4/4 10:05
 **/
@Data
public class TypeStatusDto {

    @NotNull
    private Long safeTypeId;

    @NotNull
    private Boolean isShow;


}
