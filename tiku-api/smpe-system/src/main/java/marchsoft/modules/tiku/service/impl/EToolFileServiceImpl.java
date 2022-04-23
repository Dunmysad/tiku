package marchsoft.modules.tiku.service.impl;

import lombok.RequiredArgsConstructor;
import marchsoft.base.BasicServiceImpl;
import marchsoft.config.bean.FileProperties;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.entity.EToolFile;
import marchsoft.modules.tiku.entity.vo.FileMessage;
import marchsoft.modules.tiku.mapper.EToolFileMapper;
import marchsoft.modules.tiku.service.IEToolFileService;
import marchsoft.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
* <p>
* 本地存储 服务实现类
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Service
@RequiredArgsConstructor
public class EToolFileServiceImpl extends BasicServiceImpl<EToolFileMapper, EToolFile> implements IEToolFileService {

    private final EToolFileMapper eToolFileMapper;
    private final FileProperties fileProperties;

    @Override
    public FileMessage upload(MultipartFile file) {
        // 返回给前端的数据
        FileMessage fileMessage = new FileMessage();
        // 文件资源表的实体
        EToolFile toolFile = new EToolFile();

        String uuid = FileUtils.getUUID();
        FileUtils.upload(file,fileProperties.getPath(),uuid);


        String originalFilename = file.getOriginalFilename();
        toolFile.setRealName(originalFilename);
        String extensionName = FileUtils.getExtensionName(Objects.requireNonNull(originalFilename));
        toolFile.setSuffix(extensionName);
        toolFile.setSize(FileUtils.getSize(file.getSize()));
        toolFile.setPath(fileProperties.getPath()+uuid+"."+extensionName);
        toolFile.setName(uuid+"."+extensionName);
        toolFile.setUuid(uuid);

        boolean save = this.save(toolFile);
        if (!save) {
            throw new BadRequestException("文件保存失败");
        }

        fileMessage.setId(toolFile.getId()).setName(toolFile.getName()).setPath(toolFile.getPath());

        return fileMessage;
    }
}

