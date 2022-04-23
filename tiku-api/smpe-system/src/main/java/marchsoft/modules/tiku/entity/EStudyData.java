package marchsoft.modules.tiku.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import marchsoft.base.BasicModel;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学习资料表
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode//(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="EStudyData对象", description="学习资料表")
public class EStudyData extends BasicModel<EStudyData> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "安全类别id")
    private Long safeTypeId;

    @ApiModelProperty(value = "封面图链接")
    private String coverPicLink;

    @ApiModelProperty(value = "封面视频链接")
    private String videoPath;

    @ApiModelProperty(value = "关键词")
    private String keywords;

    @ApiModelProperty(value = "学习资料格式类型(0：文字类型，1：图片类型，2：视频类型)")
    private Integer dataType;

    @ApiModelProperty(value = "学习资料来源(0:题库导入1:手动添加)")
    private Integer dataSource;

    @ApiModelProperty(value = "是否推荐(0:不推荐，1：推荐)")
    @TableField("is_introduce")
    private Boolean introduce;

    @ApiModelProperty(value = "点赞数")
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
