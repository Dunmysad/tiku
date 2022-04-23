package marchsoft.modules.tiku.entity.Dto;

import lombok.Data;
import marchsoft.base.BaseDTO;

import java.io.Serializable;

@Data
public class CaseDto extends BaseDTO implements Serializable {

    private String title;

    private String content;

    private Long safeType;

    private String picPath;

    private String keywords;
}
