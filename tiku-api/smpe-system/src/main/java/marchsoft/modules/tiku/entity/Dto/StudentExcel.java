package marchsoft.modules.tiku.entity.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class StudentExcel {

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "学生准考证号")
    private String admissionNum;

    @ApiModelProperty(value = "学生身份证号")
    private String idCard;

    @ApiModelProperty(value = "学生学号")
    private String studentNum;

    @ApiModelProperty(value = "入学年度")
    private String enrollmentYear;

    @ApiModelProperty(value = "学院名称")
    private String college;

    @ApiModelProperty(value = "学校名称（”7“表示”华南“，”8“表示华北）")
    private Long universityId;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "性别（”0“表示”男“，”1“表示”女“")
    private Boolean sex;

}
