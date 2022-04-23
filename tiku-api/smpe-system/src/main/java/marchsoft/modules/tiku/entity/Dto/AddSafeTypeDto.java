package marchsoft.modules.tiku.entity.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description：
 *
 * @author mfei
 * Date: 2022/4/4 8:55
 **/
@Data
public class AddSafeTypeDto {


    @ApiModelProperty(value = "分类名称")
    @NotBlank(message = "分类名称为空")
    private String name;

    @NotBlank(message = "logo为空")
    @ApiModelProperty(value = "logo")
    private String picPath;

    @NotBlank(message = "路由路径为空")
    @ApiModelProperty(value = "路由路径")
    private String rooterPath;

    @NotBlank(message = "题库图标路径为空")
    @ApiModelProperty(value = "题库图标路径")
    private String repositoryPath;

    @NotBlank(message = "分类名简称为空")
    @ApiModelProperty(value = "分类名简称")
    private String forShort;

    @ApiModelProperty(value = "图标序号")
    @NotNull(message = "图标序号为空")
    private Integer seqNum;

    @ApiModelProperty(value = "是否显示：显示（1），隐藏（0）")
    private Boolean isShow;


}
