package marchsoft.modules.tiku.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import marchsoft.modules.tiku.entity.Dto.CaseQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.safeCase.SafeCaseNameDto;
import marchsoft.modules.tiku.entity.ERepository;
import marchsoft.modules.tiku.entity.ESafeCase;
import marchsoft.base.BasicMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
* <p>
* 安全案例 Mapper 接口
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Component
public interface ESafeCaseMapper extends BasicMapper<ESafeCase> {

    @Select("SELECT sc.id, title, content, safe_type, sc.pic_path, keywords, tags, dislike, page_view, sc.create_time, NAME AS safe_name  " +
            "FROM e_safe_case sc LEFT JOIN e_safe_type st ON sc.safe_type = st.id ${ew.customSqlSegment}")
    IPage<SafeCaseNameDto> selectSafeCase(IPage<ESafeCase> page, @Param(Constants.WRAPPER) QueryWrapper<ESafeCase> criteria);

  /*  //条件查询
    @Select("SELECT * FROM e_safe_case ${ew.customSqlSegment}")
    IPage<ESafeCase> selectSafeCase(IPage<ESafeCase> page,@Param(Constants.WRAPPER) LambdaQueryWrapper<ESafeCase> criteria);
*/
    @Insert("insert into e_safe_case (title,keywords,safeType,picPath,content) value(#{title},#{keywords},#{safe_type},#{pic_path},#{content}")
    void addSafeCase(ESafeCase safeCase);
    @Update("update e_safe_case set title=#{title},keywords=#{keywords},safeType=#{safe_type},picPath=#{pic_path},content=#{content} where ")
    void updateEsafeCase(ESafeCase eSafeCase);
    @Delete("delete from e_safe_case where id = #{id}")
    void deleteById(Set<Long> ids);
}
