package marchsoft.modules.tiku.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import marchsoft.modules.tiku.entity.EStudyData;
import marchsoft.base.BasicMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
* <p>
* 学习资料表 Mapper 接口
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Component
public interface EStudyDataMapper extends BasicMapper<EStudyData> {


    // 条件查询
    @Select("select * from e_study_data ${ew.customSqlSegment}")
    IPage<EStudyData> selectStudyData(IPage<EStudyData> page,@Param(Constants.WRAPPER) LambdaQueryWrapper<EStudyData> criteria);

}
