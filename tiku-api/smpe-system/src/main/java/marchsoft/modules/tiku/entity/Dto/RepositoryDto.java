package marchsoft.modules.tiku.entity.Dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RepositoryDto {

    private Long safeTypeId;

    private Integer quesType;

    private String quesContent;

    private String options;

    private String answer;

}
