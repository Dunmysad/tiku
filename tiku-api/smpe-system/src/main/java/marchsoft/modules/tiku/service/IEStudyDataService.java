package marchsoft.modules.tiku.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import marchsoft.modules.tiku.entity.Dto.CaseDto;
import marchsoft.modules.tiku.entity.Dto.CaseQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.StudyDataDto;
import marchsoft.modules.tiku.entity.Dto.StudyDataQueryCriteria;
import marchsoft.modules.tiku.entity.EStudyData;
import marchsoft.base.IBasicService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 学习资料表 服务类
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
public interface IEStudyDataService extends IBasicService<EStudyData> {


    /**
     *功能描述:
     *  根据条件查询资料
     * @param page page
     * @return IPage
     * @author zhoubo
     * @date 2022/4/11
    */
    IPage<EStudyData> selectStudyData(IPage<EStudyData> page, StudyDataQueryCriteria criteria);


    /**
     *功能描述:
     *  增加学习资料
     * @param eStudyData eStudyData
     * @author zhoubo
     * @date 2022/4/11
    */
    void addStudyData(EStudyData eStudyData);


    /**
     *功能描述:
     *  修改资料信息
     * @param eStudyData eStudyData
     * @author zhoubo
     * @date 2022/4/11
    */
    void updateStudyData(EStudyData eStudyData);


    /**
     *功能描述:
     *  删除资料
     * @param ids ids
     * @author zhoubo
     * @date 2022/4/11
    */
    void deleteStudyData(Set<Long> ids);

    /**
     * 功能描述：导出queryAll的数据
     *
     * @param queryAll: 待到处数据
     * @param response: 服务器响应对象
     * @throws IOException io异常
     */
    void download(List<StudyDataDto> queryAll, HttpServletResponse response) throws IOException;
    /**
     * Description:
     * 根据criteria条件和isQuery查询
     * todo isQuery的作用
     *
     * @param criteria: Dept查询条件
     * @param isQuery:  是否判断当前用户数据权限
     * @return java.util.List<marchsoft.modules.system.entity.dto.DeptDTO>
     * @author zmm
     * @date 2020/11/26 15:45
     **/
    List<StudyDataDto> queryAll(StudyDataQueryCriteria criteria, Boolean isQuery);
}
