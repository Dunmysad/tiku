package marchsoft.modules.tiku.entity.Dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RepositoryExcel {

    @ExcelProperty("题干")
    private String quesContent;

    @ExcelProperty("题目类型")
    private String quesTypeName;

    @ExcelProperty("安全类别")
    private String safeTypeName;

    @ExcelProperty("选项")
    private String options;

    @ExcelProperty("答案")
    private String answer;

}
