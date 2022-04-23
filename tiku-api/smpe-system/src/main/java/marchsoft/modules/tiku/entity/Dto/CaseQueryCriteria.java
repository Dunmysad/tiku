package marchsoft.modules.tiku.entity.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaseQueryCriteria {

    @ApiModelProperty(value = "案例id")
    private Long id;

    @ApiModelProperty(value = "案例标题")
    private String title;

    @ApiModelProperty(value = "案例内容")
    private String content;

    @ApiModelProperty(value = "安全分类id")
    private Long safeType;

    @ApiModelProperty(value = "关键词")
    private String keywords;

    @ApiModelProperty(value = "案例图片路径")
    private String picPath;
}
