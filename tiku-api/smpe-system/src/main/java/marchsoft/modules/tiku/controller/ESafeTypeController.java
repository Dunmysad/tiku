package marchsoft.modules.tiku.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marchsoft.annotation.rest.AnonymousGetMapping;
import marchsoft.base.PageVO;
import marchsoft.modules.tiku.entity.Dto.AddSafeTypeDto;
import marchsoft.modules.tiku.entity.Dto.ESafeTypeDto;
import marchsoft.modules.tiku.entity.Dto.SafeTypeQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.TypeStatusDto;
import marchsoft.modules.tiku.entity.ESafeType;
import marchsoft.modules.tiku.service.IESafeTypeService;
import marchsoft.response.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;


/**
* <p>
* 安全类别 前端控制器
* </p>
* @author mfei
* @since 2022-03-29
*/
@RequiredArgsConstructor
@RestController
@Slf4j
@Api(tags = "安全类别模块")
@RequestMapping("/api/eSafeType")
public class ESafeTypeController {
    private final IESafeTypeService eSafeTypeService;


    @ApiOperation("分页条件获取分类数据")
    @AnonymousGetMapping
    public Result<IPage<ESafeType>> getAll(SafeTypeQueryCriteria criteria, PageVO pageVO) {

        IPage<ESafeType> page = eSafeTypeService.selectSafeType(criteria,pageVO);
        return Result.success(page);
    }

    @ApiOperation("获取所有的分类名称")
    @AnonymousGetMapping("/allName")
    public Result<Object> queryAllName() {
        List<ESafeTypeDto> eSafeTypes = eSafeTypeService.queryAllName();

        return Result.success(eSafeTypes);
    }


    @ApiOperation(value = "新增分类信息",notes = "zhaoendong")
    @PostMapping
    public Result<Object> insert(@RequestBody @Valid AddSafeTypeDto eSafeType) {

        eSafeTypeService.insert(eSafeType);
        return Result.success();
    }

    @ApiOperation(value = "更新分类信息",notes = "zhaoendong")
    @PutMapping
    public Result<Object> update(@RequestBody ESafeType safeType) {
        eSafeTypeService.updateSafeType(safeType);
        return Result.success();
    }


    @ApiOperation(value = "更新是否展示字段")
    @PutMapping("/update/state")
    public Result<Object> updateShow(@RequestBody @Valid TypeStatusDto typeStatusDto) {
        eSafeTypeService.updateShow(typeStatusDto);

        return Result.success();
    }

    @ApiOperation(value = "删除分类信息，允许批量删除",notes = "zhaoendong")
    @DeleteMapping
    public Result<Object> del(@RequestBody Set<Long> ids) {
        eSafeTypeService.del(ids);

        return Result.success();
    }

    /**
     * 前台展示样式
     * @param criteria
     * @param pageVO
     * @return
     */
    @ApiOperation("eSafeType/front-all")
    @AnonymousGetMapping("/front-all")
    public Result<List<ESafeType>> frontAll(SafeTypeQueryCriteria criteria, PageVO pageVO) {
        pageVO.setSize(-1);
        pageVO.setCurrent(-1);
        criteria.setIsShow(false);
        IPage<ESafeType> page = eSafeTypeService.selectSafeType(criteria,pageVO);
        return Result.success(page.getRecords());
    }

    @ApiOperation("导出分类数据")
    @GetMapping("/download")
    @PreAuthorize("@smpe.check('dept:list')")
    public void download(HttpServletResponse response,SafeTypeQueryCriteria criteria)throws Exception {
        eSafeTypeService.download(eSafeTypeService.queryAll(criteria,false),response);
    }


}
