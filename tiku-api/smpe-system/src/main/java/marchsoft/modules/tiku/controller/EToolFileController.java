package marchsoft.modules.tiku.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.service.IEToolFileService;
import marchsoft.response.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
* <p>
* 本地存储 前端控制器
* </p>
* @author mfei
* @since 2022-03-29
*/
@RequiredArgsConstructor
@RestController
@Slf4j
@Api(tags = "安全教育：文件上传模块")
@RequestMapping("/api/EToolFile")
public class EToolFileController {
    private final IEToolFileService eToolFileService;



    @ApiOperation(value = "文件上传（任意类型文件）", notes = " \n author：RenShiWei 2020/11/28")
    @ApiImplicitParam(name = "file", value = "文件")
    @PostMapping
    public Result<Object> uploadFile(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()){
//            log.info(StrUtil.format("【上传附件/api/EToolFile】操作人id：{},文件不存在", SecurityUtils.getCurrentUserId()));
            throw new BadRequestException("文件为空");
        }

        return Result.success(eToolFileService.upload(file));
    }


}
