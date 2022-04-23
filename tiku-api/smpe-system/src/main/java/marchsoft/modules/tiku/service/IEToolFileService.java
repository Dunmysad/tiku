package marchsoft.modules.tiku.service;

import marchsoft.base.IBasicService;
import marchsoft.modules.tiku.entity.EToolFile;
import marchsoft.modules.tiku.entity.vo.FileMessage;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 本地存储 服务类
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
public interface IEToolFileService extends IBasicService<EToolFile> {

    FileMessage upload(MultipartFile file);
}
