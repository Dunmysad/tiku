package marchsoft.modules.tiku.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marchsoft.annotation.rest.AnonymousGetMapping;
import marchsoft.base.PageVO;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.entity.Dto.CaseQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.StudyDataQueryCriteria;
import marchsoft.modules.tiku.entity.EStudyData;
import marchsoft.modules.tiku.service.IEStudyDataService;
import marchsoft.response.Result;
import marchsoft.utils.SecurityUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
* <p>
* 学习资料表 前端控制器
* </p>
* @author mfei
* @since 2022-03-29
*/
@RequiredArgsConstructor
@RestController
@Slf4j
@Api(tags = "学习资料表模块")
@RequestMapping("/api/eStudyData")
public class EStudyDataController {
    private final IEStudyDataService eStudyDataService;

    // 根据条件进行筛选查询
    @ApiOperation("根据条件获取资料信息")
    @AnonymousGetMapping()
    public Result<Object> getAll(PageVO pageVO, StudyDataQueryCriteria criteria){
        IPage<EStudyData> all = eStudyDataService.selectStudyData(pageVO.buildPage(),criteria);
        return Result.success(all);
    }


    @ApiOperation("增加学习资料")
    @PostMapping
    public Result<Object> addStudyData(@RequestBody EStudyData eStudyData){
        eStudyDataService.addStudyData(eStudyData);
        return Result.success();
    }


    @ApiOperation("修改学习资料")
    @PutMapping
    public Result<Object> updateStudyData(@RequestBody EStudyData eStudyData){

        log.info("【修改资料数据 /api/eStudyData】  操作人id： {} ，被修改的轮播图id：{}",SecurityUtils.getCurrentUserId(),
                eStudyData.getId());
        eStudyDataService.updateStudyData(eStudyData);
        return Result.success();
    }

    @ApiOperation("根据id删除资料信息")
    @DeleteMapping
    public Result<Void> deleteStudyData(@RequestBody Set<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new BadRequestException(ResultEnum.PARAM_IS_BLANK);
        }
        eStudyDataService.deleteStudyData(ids);
        return Result.success();
    }
    /**
     * 案例导出
     * */

    @ApiOperation("导出案例数据")
    @GetMapping("/download")
    @PreAuthorize("@smpe.check('dept:list')")
    public void download(HttpServletResponse response, StudyDataQueryCriteria criteria) throws Exception {
        log.info(StrUtil.format("【导出案例数据 /api/eStudyData/download】操作人id：{}，案例查询条件 criteria：{}", SecurityUtils.getCurrentUserId(),
                criteria));
        eStudyDataService.download(eStudyDataService.queryAll(criteria, false), response);
    }

    /**
     * 前台展示样式
     * @param pageVO
     * @param criteria
     * @return
     */
    @ApiOperation("前台数据获取")
    @GetMapping("/front/daily")
    public Result<Object> daily(PageVO pageVO, StudyDataQueryCriteria criteria){
        IPage<EStudyData> all = eStudyDataService.selectStudyData(pageVO.buildPage(),criteria);
        return Result.success(all);
    }
    @GetMapping("/front")
    public Result<Object> front(PageVO pageVO, StudyDataQueryCriteria criteria){
        IPage<EStudyData> all = eStudyDataService.selectStudyData(pageVO.buildPage(),criteria);
        return Result.success(all.getRecords().get(0));
    }
    @GetMapping("/front-all")
    public Result<Object> frontAll(PageVO pageVO, StudyDataQueryCriteria criteria){
        IPage<EStudyData> all = eStudyDataService.selectStudyData(pageVO.buildPage(),criteria);
        return Result.success(all);
    }
}
