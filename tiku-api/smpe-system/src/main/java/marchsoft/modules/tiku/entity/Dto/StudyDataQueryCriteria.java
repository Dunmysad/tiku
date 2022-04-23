package marchsoft.modules.tiku.entity.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhoubo
 * @Date: 2022/4/13 10:58
 * @Version 1.0
 */
@Data
public class StudyDataQueryCriteria {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "关键字")
    private String keywords;

    @ApiModelProperty(value = "案例类别")
    private Integer safeTypeId;

    @ApiModelProperty(value = "资源类型")
    private Integer dataType;

    @ApiModelProperty(value = "是否推荐")
    private Boolean isIntroduce;
}
