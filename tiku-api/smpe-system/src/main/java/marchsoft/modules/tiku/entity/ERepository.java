package marchsoft.modules.tiku.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import marchsoft.base.BasicModel;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 题库
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ERepository对象", description="题库")
public class ERepository extends BasicModel<ERepository> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "安全分类id")
    private Long safeTypeId;

    @ApiModelProperty(value = "题型(1:单选 2:多选 3:判断)")
    private Integer quesType;

    @ApiModelProperty(value = "题干")
    private String quesContent;

    @ApiModelProperty(value = "选项")
    private String options;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "是否审核(0:未审核,1:已审核)")
    private Boolean isCheck;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
