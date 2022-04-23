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
import marchsoft.modules.tiku.entity.Dto.SlideshowQueryCriteria;
import marchsoft.modules.tiku.entity.ESlideshow;
import marchsoft.modules.tiku.service.IESlideshowService;
import marchsoft.response.Result;
import marchsoft.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
* <p>
* 轮播图 前端控制器
* </p>
* @author mfei
* @since 2022-03-29
*/
@RequiredArgsConstructor
@RestController
@Slf4j
@Api(tags = "轮播图模块")
@RequestMapping("/api/eSlideshow")
public class ESlideshowController {
    private final IESlideshowService eSlideshowService;

    @ApiOperation("获取全部的轮播图")
    @GetMapping("/test")
    public Result<Object> test() {
        Long userId = SecurityUtils.getCurrentUserId();
        return Result.success(userId);
    }

    @ApiOperation("获取全部的轮播图")
    @AnonymousGetMapping
    public Result<Object> getAllSlide() {
        List<ESlideshow> allSlide = eSlideshowService.getAllSlide();
        return Result.success(allSlide);

    }

    @ApiOperation("根据条件获取轮播图信息")
    @AnonymousGetMapping("/all")
    public Result<Object> getAll(PageVO pageVO) {
        log.info("获取轮播图信息");
        IPage<ESlideshow> all = eSlideshowService.getAll(pageVO.buildPage());
        return Result.success(all);
    }

    @ApiOperation("新增轮播图")
    @PostMapping
    public Result<Object> addSlideshow(@RequestBody ESlideshow eSlideshow) {
        eSlideshowService.addSlideshow(eSlideshow);
        return Result.success();
    }


    @ApiOperation("修改轮播图")
    @PutMapping
    public Result<Object> update(@RequestBody ESlideshow eSlideshow){
        log.info("【修改轮播图 /api/eSlideshow】操作人id：{}，被修改轮播图id：{}", SecurityUtils.getCurrentUserId(),
                eSlideshow.getId());
        eSlideshowService.updateEslideshow(eSlideshow);
        return Result.success();
    }

    @ApiOperation("删除轮播图")
    @DeleteMapping
    public Result<Void> delete(@RequestBody Set<Long> ids){
        if (CollectionUtil.isEmpty(ids)) {
            String name = "12";
            log.info("获取到的id集合：{},名称：{}",ids,name);
            log.error(StrUtil.format("【删除轮播图失败】轮播图ID不能为空。操作人id：{}，删除轮播图id：{}", SecurityUtils.getCurrentUserId(), ids));
            throw new BadRequestException(ResultEnum.PARAM_IS_BLANK);
        }
        eSlideshowService.deleteById(ids);
        return Result.success();
    }



}
