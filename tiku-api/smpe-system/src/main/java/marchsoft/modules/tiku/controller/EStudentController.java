package marchsoft.modules.tiku.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marchsoft.annotation.rest.AnonymousGetMapping;
import marchsoft.annotation.rest.AnonymousPostMapping;
import marchsoft.base.PageVO;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.entity.Dto.CaseQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.StudentExcel;
import marchsoft.modules.tiku.entity.Dto.StudentQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.StudyDataQueryCriteria;
import marchsoft.modules.tiku.entity.EStudent;
import marchsoft.modules.tiku.entity.EStudyData;
import marchsoft.modules.tiku.listener.StudentListener;
import marchsoft.modules.tiku.service.IEStudentService;
import marchsoft.response.Result;
import marchsoft.utils.SecurityUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.Object;
import java.util.Set;


/**
* <p>
* 学生基本信息表 前端控制器
* </p>
* @author mfei
* @since 2022-03-29
*/
@RequiredArgsConstructor
@RestController
@Slf4j
@Api(tags = "学生基本信息表模块")
@RequestMapping("/api/eStudent")
public class EStudentController {
    private final IEStudentService eStudentService;

    //分页查询
    @ApiOperation("根据条件获取学生信息")
    @AnonymousGetMapping
    public Result<Object> getAll(PageVO pageVO, StudentQueryCriteria criteria){
        IPage<EStudent> all = eStudentService.getAll(pageVO.buildPage(),criteria);
        return Result.success(all);
    }


    @ApiOperation("增加学生信息")
    @PostMapping
    //@PreAuthorize("@smpe.check('student:add')")
    public Result<Object> addStudent(@RequestBody EStudent eStudent){
        eStudentService.addStudent(eStudent);
        return Result.success();
    }


    @ApiOperation("修改学生信息")
    @PutMapping
    public Result<Object> updateStudent(@RequestBody EStudent eStudent){

        eStudentService.updateStudent(eStudent);
        return Result.success();
    }


    @ApiOperation("删除学生信息")
    @DeleteMapping
    //@PreAuthorize("@smpe.check('student:del')")
    public Result<Void> deleteStudyData(@RequestBody Set<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            log.error(StrUtil.format("【删除学生失败】学生ID不能为空:操作人id:{},删除学生id:{}",SecurityUtils.getCurrentUserId()));
            throw new BadRequestException(ResultEnum.PARAM_IS_BLANK);
        }
        eStudentService.deleteStudentById(ids);
        return Result.success();
    }

    @ApiOperation("导出学生数据")
    @GetMapping("/download")
    @PreAuthorize("@smpe.check('dept:list')")
    public void download(HttpServletResponse response, StudentQueryCriteria criteria) throws Exception {
        log.info(StrUtil.format("【导出学生数据 /api/eSafeCase/download】操作人id：{}，案例查询条件 criteria：{}", SecurityUtils.getCurrentUserId(),
                criteria));
        eStudentService.download(eStudentService.queryAll(criteria, false), response);
    }

    @ApiOperation("导入学生数据")
    @AnonymousPostMapping("/import")
    @ResponseBody
    public Result<Object> importLoad(@RequestPart MultipartFile file) throws Exception {
        // easyExcel解析
        EasyExcel.read(file.getInputStream(), StudentExcel.class,new StudentListener(eStudentService)).sheet().doRead();
        return Result.success();
    }

    @ApiOperation("下载模版")
    @AnonymousGetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        eStudentService.downloadTemplate(eStudentService.query(false), response);
    }
}
