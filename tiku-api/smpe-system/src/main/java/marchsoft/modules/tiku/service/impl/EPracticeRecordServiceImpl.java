package marchsoft.modules.tiku.service.impl;

import lombok.RequiredArgsConstructor;
import marchsoft.modules.tiku.entity.EPracticeRecord;
import marchsoft.modules.tiku.mapper.EPracticeRecordMapper;
import marchsoft.modules.tiku.service.IEPracticeRecordService;
import marchsoft.base.BasicServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
* 题库练习记录 服务实现类
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Service
@RequiredArgsConstructor
public class EPracticeRecordServiceImpl extends BasicServiceImpl<EPracticeRecordMapper, EPracticeRecord> implements IEPracticeRecordService {

    private final EPracticeRecordMapper ePracticeRecordMapper;

}

