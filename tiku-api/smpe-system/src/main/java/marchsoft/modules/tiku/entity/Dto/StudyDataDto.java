package marchsoft.modules.tiku.entity.Dto;

import io.swagger.annotations.ApiModelProperty;
import marchsoft.base.BaseDTO;

import java.io.Serializable;
import lombok.Data;

/**
 * 功能描述:
 * $ $
 *
 * @author mfei
 * @date $
 **/
@Data
public class StudyDataDto extends BaseDTO implements Serializable {
    @ApiModelProperty(value = "资料题目")
    private String title;

    @ApiModelProperty(value = "关键字")
    private String keywords;

    @ApiModelProperty(value = "安全类别")
    private Integer safeTypeId;

    @ApiModelProperty(value = "资料类型")
    private Integer dataType;

    @ApiModelProperty(value = "是否推荐")
    private Boolean isIntroduce;


}
