package marchsoft.modules.tiku.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import marchsoft.modules.tiku.entity.Dto.ERepositoryQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.RepositoryDto;
import marchsoft.modules.tiku.entity.Dto.RepositoryExcel;
import marchsoft.modules.tiku.entity.ERepository;
import marchsoft.base.IBasicService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 题库 服务类
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
public interface IERepositoryService extends IBasicService<ERepository> {


    /**
     *  根据条件查询题
     * @param page
     * @return
     */
    IPage<ERepository> getAll(IPage<ERepository> page, ERepositoryQueryCriteria criteria);

    /**
     *  新增题
     * @param eRepository
     */
    void addERepository(ERepository eRepository);

    /**
     * 更新题
     * @param eRepository
     */
    void updateERepository(ERepository eRepository);

    /**
     * 删除题
      * @param ids
     */
    void deleteById(Set<Long> ids);

    /**
     *  根据criteria条件和isQuery查询
     * @param criteria
     * @param isQuery
     * @return
     */
    List<RepositoryDto> queryAll(ERepositoryQueryCriteria criteria, boolean isQuery);


    /**
     * 下载题库模版
     * @param isQuery
     * @return
     */
    List<RepositoryDto> query(boolean isQuery);

    /**
     * 导出queryAll的数据
     * @param queryAll
     * @param response
     * @throws IOException
     */
    void download(List<RepositoryDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     *  下载模版
     * @param query
     * @param response
     * @throws IOException
     */
    void downloadTemplate(List<RepositoryDto> query, HttpServletResponse response) throws IOException;
    /**
     *
     * @param cachedDataList
     */
    void addExcel(List<RepositoryExcel> cachedDataList);

}
