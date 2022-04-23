package marchsoft.modules.tiku.entity.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description：
 *
 * @author mfei
 * Date: 2022/3/31 21:22
 **/
@Data
public class SafeTypeQueryCriteria {

    private Integer id;

    @ApiModelProperty(value = "分类名称")
    private String name;

    private String username;

    private Boolean isShow;

    @ApiModelProperty(value = "分类代码")
    private String rooterPath;
    @ApiModelProperty(value = "分类简称")
    private String forShort;
    @ApiModelProperty(value = "分类创建时间")
    private Date createTime;
}
