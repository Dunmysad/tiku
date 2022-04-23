package marchsoft.modules.tiku.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Description：
 *
 * @author mfei
 * Date: 2022/3/29 21:36
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileMessage {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "文件别名")
    private String name;

    @ApiModelProperty(value = "路径")
    private String path;
}
