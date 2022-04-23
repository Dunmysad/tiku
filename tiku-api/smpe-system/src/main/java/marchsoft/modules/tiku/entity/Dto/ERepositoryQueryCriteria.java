package marchsoft.modules.tiku.entity.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ERepositoryQueryCriteria {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "安全分类id")
    private Long safeTypeId;

    @ApiModelProperty(value = "题型(1:单选 2:多选 3:判断)")
    private Integer quesType;

    @ApiModelProperty(value = "题干")
    private String quesContent;

    private Date StartTime;

    private Date EndTime;


}
