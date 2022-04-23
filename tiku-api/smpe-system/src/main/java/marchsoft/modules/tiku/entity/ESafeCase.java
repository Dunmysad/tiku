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
 * 安全案例
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ESafeCase对象", description="案例")
public class ESafeCase extends BasicModel<ESafeCase> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "案例标题")
    private String title;

    @ApiModelProperty(value = "案例内容")
    private String content;

    @ApiModelProperty(value = "安全分类id")
    private Long safeType;

    @ApiModelProperty(value = "案例封面图片")
    private String picPath;

    @ApiModelProperty(value = "关键词")
    private String keywords;

    @ApiModelProperty(value = "获赞量")
    private Integer tags;

    @ApiModelProperty(value = "踩踩数")
    private Integer dislike;

    @ApiModelProperty(value = "浏览量")
    private Integer pageView;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
