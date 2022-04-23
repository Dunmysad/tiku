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
import marchsoft.modules.system.entity.dto.DeptQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.CaseQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.safeCase.SafeCaseNameDto;
import marchsoft.modules.tiku.entity.ESafeCase;
import marchsoft.modules.tiku.service.IESafeCaseService;
import marchsoft.response.Result;
import marchsoft.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * 安全案例 前端控制器
 * </p>
 * @author qisiyan
 * @since 2022-03-29
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@Api(tags = "安全案例模块")
@RequestMapping("/api/eSafeCase")
public class ESafeCaseController {
    private final IESafeCaseService eSafeCaseService;

    /**
     * 根据条件进行筛选查询
     * */

    @ApiOperation("根据条件获取案例信息")
    @AnonymousGetMapping()
    public Result<Object> getAll(PageVO pageVO, CaseQueryCriteria criteria) {
        IPage<SafeCaseNameDto> all = eSafeCaseService.selectSafeCase(pageVO.buildPage(),criteria);
        return Result.success(all);
    }

    @ApiOperation(value = "新增案例信息",notes = "zmm")
    @PostMapping
    public Result<Object> insert(@RequestBody  ESafeCase eSafeCase) {

        eSafeCaseService.insert(eSafeCase);
        return Result.success();
    }

    @ApiOperation(value = "更新案例信息",notes = "zmm")
    @PutMapping
    public Result<Object> update(@RequestBody ESafeCase safeCase) {
        eSafeCaseService.updateSafeCase(safeCase);
        return Result.success();
    }


    @ApiOperation("根据ID删除案例信息")
    @DeleteMapping
    public Result<Void> deleteSafeCase(@RequestBody Set<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new BadRequestException(ResultEnum.PARAM_IS_BLANK);
        }
        eSafeCaseService.deleteSafeCase(ids);
        return Result.success();
    }



    /**
     * 案例导出
     * */

    @ApiOperation("导出案例数据")
    @GetMapping("/download")
    @PreAuthorize("@smpe.check('dept:list')")
    public void download(HttpServletResponse response, CaseQueryCriteria criteria) throws Exception {
        log.info(StrUtil.format("【导出案例数据 /api/eSafeCase/download】操作人id：{}，案例查询条件 criteria：{}", SecurityUtils.getCurrentUserId(),
                criteria));
        eSafeCaseService.download(eSafeCaseService.queryAll(criteria, false), response);
    }

    /**
     * 前台展示样式
     * @param pageVO
     * @param criteria
     * @return
     */
    @ApiOperation("front-all")
    @AnonymousGetMapping("/front-all")
    public Result<Object> frontAll(PageVO pageVO, CaseQueryCriteria criteria) {
        IPage<SafeCaseNameDto> all = eSafeCaseService.selectSafeCase(pageVO.buildPage(),criteria);
        return Result.success(all);
    }
    @PostMapping("/front/read")
    public Result<Object> read(PageVO pageVO, CaseQueryCriteria criteria) {
        IPage<SafeCaseNameDto> all = eSafeCaseService.selectSafeCase(pageVO.buildPage(),criteria);
        return Result.success(all);
    }
}

