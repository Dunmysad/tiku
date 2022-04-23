package marchsoft.modules.tiku.entity.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：
 *
 * @author mfei
 * Date: 2022/4/1 14:24
 **/
@Data
public class ESafeTypeDto implements Serializable {

    private Long id;
    @ApiModelProperty(value = "分类名称")
    private String name;
    @ApiModelProperty(value = "分类代码")
    private String rooterPath;
    @ApiModelProperty(value = "分类简称")
    private String forShort;
    @ApiModelProperty(value = "分类创建时间")
    private Date createTime;

}
