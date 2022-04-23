package marchsoft.modules.tiku.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import marchsoft.modules.tiku.entity.ERepository;
import marchsoft.modules.tiku.entity.EStudent;
import marchsoft.base.BasicMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
* <p>
* 学生基本信息表 Mapper 接口
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Component
public interface EStudentMapper extends BasicMapper<EStudent> {
    //  条件查询
    @Select("SELECT * FROM e_student ${ew.customSqlSegment}")
    IPage<EStudent> getAll(IPage<EStudent> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<EStudent> criteria);
}
