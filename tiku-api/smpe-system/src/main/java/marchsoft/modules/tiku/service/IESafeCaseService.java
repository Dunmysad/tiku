package marchsoft.modules.tiku.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import marchsoft.modules.system.entity.dto.DeptDTO;
import marchsoft.modules.system.entity.dto.DeptQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.CaseDto;
import marchsoft.modules.tiku.entity.Dto.CaseQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.safeCase.SafeCaseNameDto;
import marchsoft.modules.tiku.entity.ESafeCase;
import marchsoft.base.IBasicService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 安全案例 服务类
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
public interface IESafeCaseService extends IBasicService<ESafeCase> {



    /**
     *功能描述:
     *  根据条件查询资料
     * @param page page
     * @return IPage
     * @author zhangmiaomiao
     * @date 2022/4/14
     */
    IPage<SafeCaseNameDto> selectSafeCase(IPage<ESafeCase> page, CaseQueryCriteria criteria);
    /**
     * 新增案例
     * @param eSafeCase
     */
    void insert(ESafeCase eSafeCase);

    void updateSafeCase(ESafeCase eSafeCase);

    /**
     *功能描述:
     *  删除资料
     * @param ids ids
     * @author zhangmiaomiao
     * @date 2022/4/14
     */
    void deleteSafeCase(Set<Long> ids);

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
    void download(List<CaseDto> queryAll, HttpServletResponse response) throws IOException;
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
    List<CaseDto> queryAll(CaseQueryCriteria criteria, Boolean isQuery);


}