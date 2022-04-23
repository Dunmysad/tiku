package marchsoft.modules.tiku.entity.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhoubo
 * @Date: 2022/4/19 8:41
 * @Version 1.0
 */
@Data
public class SlideshowDto implements Serializable {

    private Long id;

    @ApiModelProperty(value = "轮播图标题")
    private String title;

    @ApiModelProperty(value = "学习资料id")
    private Integer studyDataId;

    @ApiModelProperty(value = "图片排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
