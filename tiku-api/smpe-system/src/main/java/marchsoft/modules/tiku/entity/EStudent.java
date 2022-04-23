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
 * 学生基本信息表
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="EStudent对象", description="学生基本信息表")
public class EStudent extends BasicModel<EStudent> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联user表")
    private Long userId;

    @ApiModelProperty(value = "高校id（关联user表部门id）")
    private Long universityId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "学院")
    private String college;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "学号(?)")
    private String studentNum;

    @ApiModelProperty(value = "准考证号")
    private String admissionNum;

    @ApiModelProperty(value = "入学年份")
    private String enrollmentYear;


    @ApiModelProperty(value = "性别   0：男，1：女，默认0")
    private Boolean sex;

    @ApiModelProperty(value = "身份   0：测试用户，1：学生，2：老师")
    private Integer identity;


    @ApiModelProperty(value = "密码")
    private String password;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
