package marchsoft.modules.tiku.entity.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentQueryCriteria {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id" ,type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "学生准考证号")
    private String admissionNum;

    @ApiModelProperty(value = "学生身份证号")
    private String idCard;

    @ApiModelProperty(value = "学生学号")
    private String studentNum;

    @ApiModelProperty(value = "学校名称")
    private Long universityId;

    @ApiModelProperty(value = "学院名称")
    private String college;

    @ApiModelProperty(value = "入学年度")
    private String enrollmentYear;
}
