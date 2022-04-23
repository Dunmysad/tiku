package marchsoft.modules.tiku.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import marchsoft.base.IBasicService;
import marchsoft.base.PageVO;
import marchsoft.modules.tiku.entity.Dto.AddSafeTypeDto;
import marchsoft.modules.tiku.entity.Dto.ESafeTypeDto;
import marchsoft.modules.tiku.entity.Dto.SafeTypeQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.TypeStatusDto;
import marchsoft.modules.tiku.entity.ESafeType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 安全类别 服务类
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
public interface IESafeTypeService extends IBasicService<ESafeType> {

    /**
     *功能描述: 获取分类信息
     *
     * @param pageVO buildPage
     * @return IPage
     * @author mfei
     * @date 2022/3/31
    */
    IPage<ESafeType> selectSafeType(SafeTypeQueryCriteria criteria, PageVO pageVO);


    /**
     *功能描述: 获取当前状态为显示的分类名称
     *
     * @return Result
     * @author mfei
     * @date 2022/4/1
    */
    List<ESafeTypeDto> queryAllName();

    /**
     *功能描述: 新增分类信息
     *
     * @param eSafeType eSafeType
     * @author mfei
     * @date 2022/4/4
    */
    void insert(AddSafeTypeDto eSafeType);

    /**
     *功能描述: 更新分类信息
     *
     * @param safeType safeType
     * @author mfei
     * @date 2022/4/4
    */
    void updateSafeType(ESafeType safeType);

    /**
     *功能描述: 通过id修改展示状态信息
     *
     * @param typeStatusDto typeStatusDto
     * @author mfei
     * @date 2022/4/4
    */
    void updateShow(TypeStatusDto typeStatusDto);

    /**
     *功能描述: 批量删除
     *
     * @param ids ids
     * @author mfei
     * @date 2022/4/4
    */
    void del(Set<Long> ids);


    /**
     *功能描述:
     *  根据criteria 条件 isQuery 进行查询
     * @param criteria criteria
     * @param isQuery isQuery
     * @return List
     * @author zhoubo
     * @date 2022/4/15
     */
    List<ESafeTypeDto> queryAll(SafeTypeQueryCriteria criteria,boolean isQuery);
    /**
     *功能描述:
     *   导出queryAll数据
     * @param queryAll queryAll
     * @param response response
     * @author zhoubo
     * @date 2022/4/15
     */
    void download(List<ESafeTypeDto> queryAll, HttpServletResponse response) throws IOException;
}
