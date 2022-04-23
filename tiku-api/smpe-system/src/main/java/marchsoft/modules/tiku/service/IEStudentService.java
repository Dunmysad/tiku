package marchsoft.modules.tiku.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import marchsoft.modules.tiku.entity.Dto.*;
import marchsoft.modules.tiku.entity.EStudent;
import marchsoft.base.IBasicService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 学生基本信息表 服务类
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
public interface IEStudentService extends IBasicService<EStudent> {



    /**
     *功能描述:
     *  根据条件查询学生信息
     * @param page page
     * @return IPage
     * @author cwb
     * @date 2022/4/13
     */
    IPage<EStudent> getAll(IPage<EStudent> page, StudentQueryCriteria criteria);

    /**
     *功能描述:
     *  增加学生资料
     * @param eStudent eStudent
     * @author cwb
     * @date 2022/4/13
     */
    void addStudent(EStudent eStudent);

    /**
     *功能描述:
     *  修改学生信息
     * @param eStudent eStudent
     * @author cwb
     * @date 2022/4/13
     */
    void updateStudent(EStudent eStudent);

    /**
     *功能描述:
     *  删除学生信息
     * @param ids ids
     * @author cwb
     * @date 2022/4/13
     */
    void deleteStudentById(Set<Long> ids);


    Long findStudentIdByStudentNumAndDeptId(String username,Long deptId);

    /**
     * Description:
     * 导出queryAll的数据
     *
     * @param queryAll: 待到处数据
     * @param response: 服务器响应对象
     * @throws IOException io异常
     * @author zmm
     * @date 2020/11/26 15:45
     **/
    void download(List<StudentDto> queryAll, HttpServletResponse response) throws IOException;


    /**
     * Description:
     * 根据criteria条件和isQuery查询
     * todo isQuery的作用
     *
     * @param criteria : Dept查询条件
     * @param isQuery :  是否判断当前用户数据权限
     * @return java.util.List<marchsoft.modules.system.entity.dto.DeptDTO>
     * @author yx
     * @date 2022/4/14 15:45
     **/
    List<StudentDto> queryAll(StudentQueryCriteria criteria, Boolean isQuery);

    /**
     *
     * @param cachedDataList
     */
    void addExcel(List<StudentExcel> cachedDataList);

    /**
     *  下载模版
     * @param query
     * @param response
     * @throws IOException
     */
    void downloadTemplate(List<StudentDto> query, HttpServletResponse response) throws IOException;
    /**
     * 下载题库模版
     * @param isQuery
     * @return
     */
    List<StudentDto> query(boolean isQuery);
}
