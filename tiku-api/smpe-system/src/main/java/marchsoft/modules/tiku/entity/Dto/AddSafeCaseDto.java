package marchsoft.modules.tiku.entity.Dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddSafeCaseDto {
    @ApiModelProperty(value = "案例标题")
    @NotBlank(message = "案例标题为空")
    private String title;

    @NotBlank(message = "案例内容为空")
    @ApiModelProperty(value = "content")
    private String content;

    @NotBlank(message = "案例封面图片为空")
    @ApiModelProperty(value = "picPath")
    private String picPath;

    @NotBlank(message = "关键词为空")
    @ApiModelProperty(value = "keywords")
    private String keywords;

    @NotBlank(message = "安全分类ID为空")
    @ApiModelProperty(value = "safeType")
    private Long safeType;

    @ApiModelProperty(value = "tags")
    @NotNull(message = "获赞量为空")
    private Integer tags;

    @ApiModelProperty(value = "是否显示：显示（1），隐藏（0）")
    private Boolean isShow;

}