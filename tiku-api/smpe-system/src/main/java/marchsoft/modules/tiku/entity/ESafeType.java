package marchsoft.modules.tiku.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import marchsoft.base.BasicModel;

import java.io.Serializable;

/**
 * <p>
 * 安全类别
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ESafeType对象", description="安全类别")
public class ESafeType extends BasicModel<ESafeType> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "logo")
    private String picPath;

    @ApiModelProperty(value = "路由路径")
    private String rooterPath;

    @ApiModelProperty(value = "题库图标路径")
    private String repositoryPath;

    @ApiModelProperty(value = "分类名简称")
    private String forShort;

    @ApiModelProperty(value = "图标序号")
    private Integer seqNum;

    @ApiModelProperty(value = "是否显示：显示（1），隐藏（0）")
    private Boolean isShow;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
