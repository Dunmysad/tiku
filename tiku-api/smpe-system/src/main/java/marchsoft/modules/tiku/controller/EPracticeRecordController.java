package marchsoft.modules.tiku.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marchsoft.modules.tiku.entity.ERepository;
import marchsoft.modules.tiku.service.IEPracticeRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
* <p>
* 题库练习记录 前端控制器
* </p>
* @author mfei
* @since 2022-03-29
*/
@RequiredArgsConstructor
@RestController
@Slf4j
@Api(tags = "题库练习记录模块")
@RequestMapping("/api/ePracticeRecord")
public class EPracticeRecordController {

    private final IEPracticeRecordService ePracticeRecordService;



}
