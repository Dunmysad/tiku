package marchsoft.modules.tiku.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marchsoft.annotation.rest.AnonymousGetMapping;
import marchsoft.annotation.rest.AnonymousPostMapping;
import marchsoft.base.PageVO;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.entity.Dto.ERepositoryQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.RepositoryDto;
import marchsoft.modules.tiku.entity.Dto.RepositoryExcel;
import marchsoft.modules.tiku.entity.ERepository;
import marchsoft.modules.tiku.listener.RespositoryListener;
import marchsoft.modules.tiku.service.IERepositoryService;
import marchsoft.response.Result;
import marchsoft.utils.SecurityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
* <p>
* 题库 前端控制器
* </p>
* @author mfei
* @since 2022-03-29
*/
@RequiredArgsConstructor
@RestController
@Slf4j
@Api(tags = "题库模块")
@RequestMapping("/api/eRepository")
public class ERepositoryController {
    private final IERepositoryService eRepositoryService;


    @ApiOperation("获取题库中的全部题")
    @AnonymousGetMapping
    public Result<Object> getAllRepository(PageVO pageVO, ERepositoryQueryCriteria criteria){
        IPage<ERepository> all = eRepositoryService.getAll(pageVO.buildPage(), criteria);
        return Result.success(all);
    }

    @ApiOperation("新增题")
    @PostMapping
    public Result<Object> addRepository(@RequestBody ERepository eRepository){
       eRepositoryService.addERepository(eRepository);
       return Result.success();
    }

    @ApiOperation("修改题")
    @PutMapping
    public Result<Object> update(@RequestBody ERepository eRepository){
        eRepositoryService.updateERepository(eRepository);
        return Result.success();
    }


    @ApiOperation("删除题")
    @DeleteMapping
    public Result<Void> delete(@RequestBody Set<Long> ids){
       if (CollectionUtil.isEmpty(ids)){
           throw new BadRequestException(ResultEnum.PARAM_IS_BLANK);
       }
       eRepositoryService.deleteById(ids);
       return Result.success();
    }


    @ApiOperation("导出题库数据")
    @GetMapping("/download")
    public void download(HttpServletResponse response, ERepositoryQueryCriteria criteria) throws Exception{
        eRepositoryService.download(eRepositoryService.queryAll(criteria,false), response);
    }


    @AnonymousPostMapping("/import")
    @ResponseBody
    public Result<Object> importLoad(@RequestPart MultipartFile file) throws Exception {
        // easyExcel解析
        EasyExcel.read(file.getInputStream(), RepositoryExcel.class,new RespositoryListener(eRepositoryService)).sheet().doRead();
        return Result.success();
    }

    @ApiOperation("下载模版")
    @AnonymousGetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException{
        eRepositoryService.downloadTemplate(eRepositoryService.query(false), response);
    }
}


