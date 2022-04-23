package marchsoft.modules.tiku.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import marchsoft.base.BasicMapper;
import marchsoft.modules.tiku.entity.Dto.TypeStatusDto;
import marchsoft.modules.tiku.entity.ESafeType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* <p>
* 安全类别 Mapper 接口
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Component
public interface ESafeTypeMapper extends BasicMapper<ESafeType> {
    @Update("UPDATE e_safe_type SET is_show = #{isShow} WHERE id = #{safeTypeId}")
    int updateShow(TypeStatusDto typeStatusDto);

    @Select("SELECT * FROM e_safe_type ${ew.customSqlSegment}")
    IPage<ESafeType> getAll(IPage<ESafeType> page, @Param(Constants.WRAPPER)LambdaQueryWrapper<ESafeType> criteria);

    @Select("SELECT * FROM e_safe_type")
    List<ESafeType> selectAll();
}
