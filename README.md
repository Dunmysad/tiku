# 项目部署地址
- [后台](http://117.50.177.91)
    - 账号：admin
    - 密码：123456
- [前台](http://117.50.177.91:81)
    - 部门：华南分部
    - 账号：123456
    - 密码：123456

# 题库管理模块
## 框架支持 

- Springboot
- SpingSecurity
- mybatis-plus
- ......
## 功能说明
### 查询

- 使用`Ipage`分页查询
   - 前端`criteria`无参数传入时：分页查询题库中所有的题
   - 前端`criteria`传入参数：根据参数内容进行条件查询
### 新增

- 使用`LambdaQueryWrapper`接收前端传入的值
   - 新增之前查询数据库中是否有数据与插入数据冲突
### 修改

- 使用`LambdaQueryWrapper`接收传入值
   - 修改之前判断是否修改题干
   - 如果修改题干则需要判断题干是否已经在数据库中存在
### 删除

- 通过`id`删除
   - 通过`for`循环可进行批删除
### 题库模版下载

- 通过`easyExcel`对数据处理
   - 使用`LinkedHashMap`写入`Excel`标题信息
### 导入

- 使用`stream`流查询数据库中的`safetype`安全类别对应键值对，导入存储时转换
- 使用`RepositoryEnum`枚举类定义的`findByName`方法将文字转换成数据库中存储的数字进行储存（题型：单选、多选、判断）
### 导出

- 使用`LinkedHashMap`写入`Excel`信息
   - 使用`stream`流查询数据库中的`safetype`安全类别对应键值对，写入`Excel`时转换
   - 使用`RepositoryEnum`枚举类定义的`findById`方法将数据库获取的数字对应的文字说明写入`Excel`（题型：单选、多选、判断）
## 功能展示
### 查询
![查询.gif](https://cdn.nlark.com/yuque/0/2022/gif/27512376/1650417254139-89dc21f7-08c0-4b5d-bb66-0e47495ec81a.gif#clientId=u3500fd5b-099b-4&crop=0&crop=0&crop=1&crop=1&from=drop&id=qPuCN&margin=%5Bobject%20Object%5D&name=%E6%9F%A5%E8%AF%A2.gif&originHeight=896&originWidth=1665&originalType=binary&ratio=1&rotation=0&showTitle=false&size=2106937&status=done&style=none&taskId=u501e7285-d269-4a43-b324-c262e047a7a&title=)

### 新增
![新增.gif](https://cdn.nlark.com/yuque/0/2022/gif/27512376/1650417334969-f1bba498-ab30-499c-a6b3-6dba532796e9.gif#clientId=u3500fd5b-099b-4&crop=0&crop=0&crop=1&crop=1&from=drop&id=Praq8&margin=%5Bobject%20Object%5D&name=%E6%96%B0%E5%A2%9E.gif&originHeight=896&originWidth=1665&originalType=binary&ratio=1&rotation=0&showTitle=false&size=1125211&status=done&style=none&taskId=u17366687-1680-420e-8d1c-8596fb61d07&title=)
### 修改
![修改.gif](https://cdn.nlark.com/yuque/0/2022/gif/27512376/1650417387920-68167d22-5c85-4c64-a1a2-807841bbba46.gif#clientId=u3500fd5b-099b-4&crop=0&crop=0&crop=1&crop=1&from=drop&id=HCSwv&margin=%5Bobject%20Object%5D&name=%E4%BF%AE%E6%94%B9.gif&originHeight=896&originWidth=1665&originalType=binary&ratio=1&rotation=0&showTitle=false&size=886237&status=done&style=none&taskId=uf1dc61d1-e64b-4422-8c22-f53cbcf9a5d&title=)
### 删除
![删除.gif](https://cdn.nlark.com/yuque/0/2022/gif/27512376/1650417582055-9a42cead-1106-4440-b851-d641513b92b5.gif#clientId=u3500fd5b-099b-4&crop=0&crop=0&crop=1&crop=1&from=drop&id=CadUQ&margin=%5Bobject%20Object%5D&name=%E5%88%A0%E9%99%A4.gif&originHeight=896&originWidth=1665&originalType=binary&ratio=1&rotation=0&showTitle=false&size=1196988&status=done&style=none&taskId=u5cf894f7-1cb3-4cec-bfa3-67c07fa9055&title=)
### 批量导入模版下载
### ![下载批量导入模版.gif](https://cdn.nlark.com/yuque/0/2022/gif/27512376/1650418305561-d2633369-c6f6-4778-af62-7760da350c68.gif#clientId=u3500fd5b-099b-4&crop=0&crop=0&crop=1&crop=1&from=drop&id=ui5m9&margin=%5Bobject%20Object%5D&name=%E4%B8%8B%E8%BD%BD%E6%89%B9%E9%87%8F%E5%AF%BC%E5%85%A5%E6%A8%A1%E7%89%88.gif&originHeight=896&originWidth=1665&originalType=binary&ratio=1&rotation=0&showTitle=false&size=1987316&status=done&style=none&taskId=u66fc973e-f68a-4259-884f-7fcf05ab859&title=)
### 批量导入题库
![批量导入.gif](https://cdn.nlark.com/yuque/0/2022/gif/27512376/1650418419525-fdb14ee9-7c5a-4fc7-ac38-29022e231782.gif#clientId=u3500fd5b-099b-4&crop=0&crop=0&crop=1&crop=1&from=drop&id=E81l9&margin=%5Bobject%20Object%5D&name=%E6%89%B9%E9%87%8F%E5%AF%BC%E5%85%A5.gif&originHeight=896&originWidth=1665&originalType=binary&ratio=1&rotation=0&showTitle=false&size=2086717&status=done&style=none&taskId=u568f7603-a787-4bd3-babb-d1f6af0ccfd&title=)

### 导出
![Untitled.gif](https://cdn.nlark.com/yuque/0/2022/gif/27512376/1650416715180-24efca41-55d7-4215-9f44-3534d6f5807a.gif#clientId=u9e0e613a-2b23-4&crop=0&crop=0&crop=1&crop=1&from=drop&id=xwIsv&margin=%5Bobject%20Object%5D&name=Untitled.gif&originHeight=896&originWidth=1665&originalType=binary&ratio=1&rotation=0&showTitle=false&size=2262160&status=done&style=none&taskId=u94f338d8-5e5c-4bbb-a630-40de26257a9&title=)
## 项目结构
```bash
smpe-system/src/main/java/marchsoft/modules/tiku
├── controller
│   ├── EPracticeRecordController.java
│   ├── ERepositoryController.java
│   ├── ESafeCaseController.java
│   ├── ESafeTypeController.java
│   ├── ESlideshowController.java
│   ├── EStudentController.java
│   ├── EStudyDataController.java
│   └── EToolFileController.java
├── entity
│   ├── Dto
│   │   ├── AddSafeCaseDto.java
│   │   ├── AddSafeTypeDto.java
│   │   ├── CaseDto.java
│   │   ├── CaseQueryCriteria.java
│   │   ├── ERepositoryQueryCriteria.java
│   │   ├── ESafeTypeDto.java
│   │   ├── RepositoryDto.java
│   │   ├── RepositoryExcel.java
│   │   ├── SafeTypeQueryCriteria.java
│   │   ├── SlideshowDto.java
│   │   ├── SlideshowQueryCriteria.java
│   │   ├── StudentDto.java
│   │   ├── StudentExcel.java
│   │   ├── StudentQueryCriteria.java
│   │   ├── StudyDataDto.java
│   │   ├── StudyDataQueryCriteria.java
│   │   ├── TypeStatusDto.java
│   │   └── safeCase
│   │       └── SafeCaseNameDto.java
│   ├── EPracticeRecord.java
│   ├── ERepository.java
│   ├── ESafeCase.java
│   ├── ESafeType.java
│   ├── ESlideshow.java
│   ├── EStudent.java
│   ├── EStudyData.java
│   ├── EToolFile.java
│   └── vo
│       └── FileMessage.java
├── listener
│   ├── RespositoryListener.java
│   └── StudentListener.java
├── mapper
│   ├── EPracticeRecordMapper.java
│   ├── ERepositoryMapper.java
│   ├── ESafeCaseMapper.java
│   ├── ESafeTypeMapper.java
│   ├── ESlideshowMapper.java
│   ├── EStudentMapper.java
│   ├── EStudyDataMapper.java
│   └── EToolFileMapper.java
└── service
    ├── IEPracticeRecordService.java
    ├── IERepositoryService.java
    ├── IESafeCaseService.java
    ├── IESafeTypeService.java
    ├── IESlideshowService.java
    ├── IEStudentService.java
    ├── IEStudyDataService.java
    ├── IEToolFileService.java
    ├── impl
    │   ├── EPracticeRecordServiceImpl.java
    │   ├── ERepositoryServiceImpl.java
    │   ├── ESafeCaseServiceImpl.java
    │   ├── ESafeTypeServiceImpl.java
    │   ├── ESlideshowServiceImpl.java
    │   ├── EStudentServiceImpl.java
    │   ├── EStudyDataServiceImpl.java
    │   └── EToolFileServiceImpl.java
    └── mapstruct
        ├── CaseMapStruct.java
        ├── DataMapStruct.java
        ├── RepositoryMapStruct.java
        ├── SafeTypeMapStruct.java
        ├── SaftTypeMapStruct.java
        ├── SlideshowMapStruct.java
        └── StudentMapStruct.java

10 directories, 68 files
```
## 相关代码
```java
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

```
```java
package marchsoft.modules.tiku.entity.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ERepositoryQueryCriteria {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "安全分类id")
    private Long safeTypeId;

    @ApiModelProperty(value = "题型(1:单选 2:多选 3:判断)")
    private Integer quesType;

    @ApiModelProperty(value = "题干")
    private String quesContent;

    private Date StartTime;

    private Date EndTime;


}
```
```java
package marchsoft.modules.tiku.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import marchsoft.base.BasicModel;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 题库
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ERepository对象", description="题库")
public class ERepository extends BasicModel<ERepository> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "安全分类id")
    private Long safeTypeId;

    @ApiModelProperty(value = "题型(1:单选 2:多选 3:判断)")
    private Integer quesType;

    @ApiModelProperty(value = "题干")
    private String quesContent;

    @ApiModelProperty(value = "选项")
    private String options;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "是否审核(0:未审核,1:已审核)")
    private Boolean isCheck;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
```
```java
package marchsoft.modules.tiku.entity.Dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RepositoryExcel {

    @ExcelProperty("题干")
    private String quesContent;

    @ExcelProperty("题目类型")
    private String quesTypeName;

    @ExcelProperty("安全类别")
    private String safeTypeName;

    @ExcelProperty("选项")
    private String options;

    @ExcelProperty("答案")
    private String answer;

}
```
```java
package marchsoft.modules.tiku.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import marchsoft.modules.tiku.entity.Dto.RepositoryExcel;
import marchsoft.modules.tiku.service.IERepositoryService;
import marchsoft.modules.tiku.service.impl.ERepositoryServiceImpl;

import java.io.IOException;
import java.util.List;

@Slf4j
public class RespositoryListener implements ReadListener<RepositoryExcel> {


    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 2;
    /**
     * 缓存的数据
     */
    private List<RepositoryExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private RepositoryExcel repositoryExcel;


    private IERepositoryService ieRepositoryService;
    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param ieRepositoryService
     */
    public RespositoryListener(IERepositoryService ieRepositoryService) {
        this.ieRepositoryService = ieRepositoryService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(RepositoryExcel data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        System.out.println(cachedDataList.toString());
        ieRepositoryService.addExcel(cachedDataList);
        log.info("存储数据库成功！");
    }
}
```
```java
package marchsoft.modules.tiku.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import marchsoft.modules.tiku.entity.ERepository;
import marchsoft.base.BasicMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* <p>
* 题库 Mapper 接口
* </p>
*s
* @author mfei
* @since 2022-03-29
*/
@Component
public interface ERepositoryMapper extends BasicMapper<ERepository> {


    //  条件查询
    @Select("SELECT * FROM e_repository ${ew.customSqlSegment}")
    IPage<ERepository> getAll(IPage<ERepository> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<ERepository> criteria);

}
```
```java
package marchsoft.modules.tiku.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import marchsoft.base.BasicServiceImpl;
import marchsoft.enums.RepositoryEnum;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.entity.Dto.ERepositoryQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.RepositoryDto;
import marchsoft.modules.tiku.entity.Dto.RepositoryExcel;
import marchsoft.modules.tiku.entity.ERepository;
import marchsoft.modules.tiku.entity.ESafeType;
import marchsoft.modules.tiku.mapper.ERepositoryMapper;
import marchsoft.modules.tiku.mapper.ESafeTypeMapper;
import marchsoft.modules.tiku.service.IERepositoryService;
import marchsoft.modules.tiku.service.mapstruct.RepositoryMapStruct;
import marchsoft.utils.FileUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
* 题库 服务实现类
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "repository")
public class ERepositoryServiceImpl extends BasicServiceImpl<ERepositoryMapper, ERepository> implements IERepositoryService {

    private final ERepositoryMapper eRepositoryMapper;
    private final RepositoryMapStruct repositoryMapStruct;
    private final ESafeTypeMapper eSafeTypeMapper;

    /**
     * 更新数据之前的判断
     * @param criteria
     * @return
     */
    private LambdaQueryWrapper<ERepository> buildERepositoryCriteria(ERepositoryQueryCriteria criteria){
        LambdaQueryWrapper<ERepository> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(criteria.getId())) {
            queryWrapper.eq(ERepository::getId,criteria.getId());
        }

        if (ObjectUtil.isNotNull(criteria.getSafeTypeId())) {
            queryWrapper.eq(ERepository::getSafeTypeId,criteria.getSafeTypeId());
        }

        if (StrUtil.isNotBlank(criteria.getQuesContent())) {
            //默认使用like匹配
            queryWrapper.like(ERepository::getQuesContent, criteria.getQuesContent());
        }

        if (ObjectUtil.isNotNull(criteria.getQuesType())) {
            queryWrapper.eq(ERepository::getQuesType, criteria.getQuesType());
        }
        if (ObjectUtil.isNotNull(criteria.getStartTime()) && ObjectUtil.isNotNull(criteria.getEndTime())) {
            queryWrapper.between(ERepository::getCreateTime, criteria.getStartTime(), criteria.getEndTime());
        }

        queryWrapper.eq(ERepository::getDeleted, 0);
        return queryWrapper;
    }


    /**
     * 查询题库中所有的题，当前端搜索框无参数信息传入时为查询全部题
     * @param page
     * @param criteria
     * @return
     */
    @Override
    public IPage<ERepository> getAll(IPage<ERepository> page, ERepositoryQueryCriteria criteria) {
        IPage<ERepository> eRepositoryIPage = eRepositoryMapper.getAll(page, buildERepositoryCriteria(criteria));
        return eRepositoryIPage;
    }

    /**
     * 新增题
     * @param eRepository
     */
    @Override
    public void addERepository(ERepository eRepository) {
        eRepository.setCreateTime(null);
        // 判断新增题是否与题库中的题重复
        if (StrUtil.isNotBlank(eRepository.getQuesContent())){
            LambdaQueryWrapper<ERepository> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ERepository::getQuesContent, eRepository.getQuesContent());
            ERepository one = this.getOne(wrapper);
            if (ObjectUtil.isNotNull(one)){
                throw new BadRequestException(ResultEnum.REPOSITORY_SHOW_ID_IS_EXIST);
            }
        }else{
            throw new BadRequestException("题干为空！");
        }

        int i = eRepositoryMapper.insert(eRepository);
    }

    /**
     * 更新题
     * @param eRepository
     */
    @Override
    public void updateERepository(ERepository eRepository) {
        if (StrUtil.isNotBlank(eRepository.getQuesContent())){
            // 获取旧值
            ERepository oldERpository = eRepositoryMapper.selectById(eRepository.getId());
            // 如果当前题干不为空 且 当前题干与旧题干不同
            if(ObjectUtil.isNotNull(eRepository.getQuesContent()) && !eRepository.getQuesContent().equals(oldERpository.getQuesContent())){
                // 获取题干信息 与 题库对比
                LambdaQueryWrapper<ERepository> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ERepository::getQuesContent, eRepository.getQuesContent());
                ERepository eRepository1 = eRepositoryMapper.selectOne(wrapper);
                if (ObjectUtil.isNotNull(eRepository1)){
                    throw new BadRequestException(ResultEnum.REPOSITORY_SHOW_ID_IS_EXIST);
                }
            }
        }
        int i = eRepositoryMapper.updateById(eRepository);
    }

    /**
     * 删除题
     * @param ids
     */
    @Override
    public void deleteById(Set<Long> ids) {
        for (Long id : ids){
            eRepositoryMapper.deleteById(id);
        }
    }

    /**
     * 根据criteria条件和isQuery查询
     * @param criteria
     * @param isQuery
     * @return
     */
    @Override
    public List<RepositoryDto> queryAll(ERepositoryQueryCriteria criteria, boolean isQuery) {
        List<ERepository> repositories = this.eRepositoryMapper.selectList(analysisQueryCriteria(criteria));
        return repositoryMapStruct.toDto(repositories);
    }

    /**
     * 下载题库模版
     * @param isQuery
     * @return
     */
    @Override
    public List<RepositoryDto> query(boolean isQuery) {
        return null;
    }

    private LambdaQueryWrapper<ERepository> analysisQueryCriteria(ERepositoryQueryCriteria criteria){
        LambdaQueryWrapper<ERepository> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    /**
     * 下载题库信息
     * @param repositoryDtos
     * @param response
     * @throws IOException
     */
    @Override
    public void download(List<RepositoryDto> repositoryDtos, HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = new ArrayList<>();
        List<ESafeType> eSafeTypes = eSafeTypeMapper.selectAll();
        Map<Long, String> collect = eSafeTypes.stream().collect(Collectors.toMap(ESafeType::getId, ESafeType::getName));

        for (RepositoryDto repositoryDto : repositoryDtos){
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("题干", repositoryDto.getQuesContent());
            map.put("题目类型", RepositoryEnum.findById(Long.valueOf(repositoryDto.getQuesType())).getName());
            map.put("安全类别", collect.get(repositoryDto.getSafeTypeId()));
            map.put("答案", repositoryDto.getAnswer());
            map.put("选项", repositoryDto.getOptions());
            list.add(map);

        }
        FileUtils.downloadExcel(list, response);
    }

    /**
     *  下载题库模版
     * @param query
     * @param response
     * @throws IOException
     */
    @Override
    public void downloadTemplate(List<RepositoryDto> query, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("题干", null);
        map.put("题目类型", null);
        map.put("安全类别", null);
        map.put("答案", null);
        map.put("选项", null);
        list.add(map);
        FileUtils.downloadExcel(list, response);
    }


    /**
     * 导入Excel表格
     * @param cachedDataList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addExcel(List<RepositoryExcel> cachedDataList) {
        List<ERepository> excel = repositoryMapStruct.excel(cachedDataList);
        List<ESafeType> eSafeTypes = eSafeTypeMapper.selectList(null);
        Map<String, Long> collect = eSafeTypes.stream().collect(Collectors.toMap(ESafeType::getName, ESafeType::getId));

        for (int i = 0; i < excel.size(); i++) {
            ERepository eRepository = excel.get(i);
            if (ObjectUtil.isNull(cachedDataList.get(i).getQuesTypeName())) {
                throw new BadRequestException(ResultEnum.INSERT_OPERATION_FAIL);
            }
            eRepository.setQuesType(Math.toIntExact(RepositoryEnum.findByName(cachedDataList.get(i).getQuesTypeName()).getId()));
            eRepository.setSafeTypeId(collect.get(cachedDataList.get(i).getSafeTypeName()));
            this.addERepository(eRepository);
        }
    }
}
```
```java
package marchsoft.modules.tiku.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import marchsoft.modules.tiku.entity.Dto.ERepositoryQueryCriteria;
import marchsoft.modules.tiku.entity.Dto.RepositoryDto;
import marchsoft.modules.tiku.entity.Dto.RepositoryExcel;
import marchsoft.modules.tiku.entity.ERepository;
import marchsoft.base.IBasicService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 题库 服务类
 * </p>
 *
 * @author mfei
 * @since 2022-03-29
 */
public interface IERepositoryService extends IBasicService<ERepository> {


    /**
     *  根据条件查询题
     * @param page
     * @return
     */
    IPage<ERepository> getAll(IPage<ERepository> page, ERepositoryQueryCriteria criteria);

    /**
     *  新增题
     * @param eRepository
     */
    void addERepository(ERepository eRepository);

    /**
     * 更新题
     * @param eRepository
     */
    void updateERepository(ERepository eRepository);

    /**
     * 删除题
      * @param ids
     */
    void deleteById(Set<Long> ids);

    /**
     *  根据criteria条件和isQuery查询
     * @param criteria
     * @param isQuery
     * @return
     */
    List<RepositoryDto> queryAll(ERepositoryQueryCriteria criteria, boolean isQuery);


    /**
     * 下载题库模版
     * @param isQuery
     * @return
     */
    List<RepositoryDto> query(boolean isQuery);

    /**
     * 导出queryAll的数据
     * @param queryAll
     * @param response
     * @throws IOException
     */
    void download(List<RepositoryDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     *  下载模版
     * @param query
     * @param response
     * @throws IOException
     */
    void downloadTemplate(List<RepositoryDto> query, HttpServletResponse response) throws IOException;
    /**
     *
     * @param cachedDataList
     */
    void addExcel(List<RepositoryExcel> cachedDataList);

}
```
```java
package marchsoft.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum RepositoryEnum {


    DANXUANTI(1L,"单选题"),
    DUOXUANTI(2L,"多选题"),
    PANDUANTI(3L,"判断题"),
    NOTFIND(0L, "---"),
    ;



    private final Long id;
    private final String name;


    public static RepositoryEnum findByName(String name) {
        for (RepositoryEnum repositoryEnum : RepositoryEnum.values()) {
            if (name.equals(repositoryEnum.getName())){
                return repositoryEnum;
            }
        }
        return null;
    }

    public static RepositoryEnum findById(Long id) {
        for (RepositoryEnum repositoryEnum : RepositoryEnum.values()) {
           if (id.equals(repositoryEnum.getId())){
               return repositoryEnum;
           }
        }
        return null;
    }
}
```
```java
package marchsoft.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * description:统一响应信息枚举
 *
 * @author RenShiWei
 * Date: 2020/7/9 21:41
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultEnum {


    /**
     * 成功（默认返回状态码）
     */
    SUCCESS(0, "SUCCESS"),

    /**
     * 全局未知异常
     */
    SEVER_ERROR(500, "服务器异常,请重试"),

    /**
     * 请求失败（一般前端处理，不常用）
     */
    BAD_REQUEST(400, "请求失败"),

    /*
     * 登录、权限认证异常
     */
    LOGIN_EXPIRE(401, "未登录或当前登录状态过期"),
    IDENTITY_NOT_POW(403, "您的用户权限不足"),


    /*
       ====通用异常====
     */

    /*
        1001-1010 通用操作相关
     */
    OPERATION_FAIL(1001, "操作失败！"),
    SELECT_OPERATION_FAIL(1002, "查询操作失败！"),
    UPDATE_OPERATION_FAIL(1003, "更新操作失败！"),
    DELETE_OPERATION_FAIL(1004, "删除操作失败！"),
    INSERT_OPERATION_FAIL(1005, "新增操作失败！"),
    DATA_NOT_FOUND(1006, "查询数据不存在"),
    OPERATION_MIDDLE_FAIL(1007, "维护中间表失败！"),
    ALTER_DATA_NOT_EXIST(1008, "修改数据不存在！"),

    /*
        1011-1050 登录注册相关
     */
    LOGIN_FAIL(1011, "登录失败，账号或者密码错误"),
    LOGIN_FAIL_RELOGIN(1012, "登录失败，请重试"),
    LOGIN_FAIL_CODE(1013, "验证码错误"),
    NO_USER(1014, "用户不存在"),
    REGISTER_FAIL(1015, "注册失败，手机号已经存在"),
    NO_USER_PHONE(1016, "认证失败，手机号不存在"),
    PARAMS_NOT_NULL(1017, "请求参数不能为空"),
    LOGIN_INFO_NOT_FOUND(1018, "找不到当前登录的信息"),
    LOGIN_USER_INFO_NOT_FOUND(1019, "未能查到当前登录用户的信息"),
    COUNT_NOT_ENABLE(1020, "账号未激活"),

    /*
        1051-1070 短信业务相关
     */
    SMS_NOT_SEND(1051, "短信发送失败"),
    SMS_CODE_EXPIRE(1052, "短信验证码失效"),
    SMS_CODE_VERITY_FAIL(1053, "短信验证码验证失败"),

    /*
        1071-1100 文件、资源相关
     */
    FILE_OVERSTEP_SIZE(1071, "文件超出规定大小"),
    FILE_UPLOAD_FAIL(1072, "文件上传失败"),
    FILE_LOADING_FAIL(1073, "文件不存在，加载失败"),
    FILE_REQUEST_FAIL(1074, "文件类型不支持查看"),
    FILE_TYPE_IMAGE_FAIL(1075, "请上传图片类型的文件"),
    FILE_DOWNLOAD_FAIL_NOT_DATA(1076, "数据不存在，不能导出"),

    /*
        1101-1199 请求参数相关
     */
    PARAM_IS_INVALID(1101, "参数无效"),
    PARAM_IS_BLANK(1102, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1103, "参数类型错误"),
    PARAM_NOT_COMPLETE(1104, "参数缺失"),
    PARAM_VERIFY_FAILURE(1105, "参数验证失败"),
    ENTITY_FILED_EXIT(1106, "实体/属性/参数值已存在"),
    ENTITY_FILED_NOT_EXIT(1107, "实体/属性/参数值不存在"),

    /*
       框架
     */
    PERMISSION_DENIED(1120, "权限不足"),
    USER_USERNAME_EXIST(1121, "当前用户名已存在"),
    USER_EMAIL_EXIST(1122, "当前用户邮箱已存在"),
    USER_NOT_EXIST(1123, "用户不存在"),
    ROLE_NAME_EXIST(1124, "角色名已存在"),



    /*
        -----------业务相关（2xxx）------------
     */
    STUDY_DATA_NOT_EXIST(2001,"学习资料id不存在"),
    STUDY_DATA_HAD_EXIST(2009,"学习资料已经存在"),
    SLIDE_SHOW_IS_EXIST(2002,"轮播图的顺序不能重复"),
    SLIDE_SHOW_STUDY_DATA_IS_EXIST(2003,"该学习资料id已存在"),
    SORT_IS_EXIST(2004,"排序重复"),
    STUDY_DATA_IS_EXIST(2008,"学习资料的顺序不能重复"),


    /*
     学生信息相关
    */
    STUDENT_ID_NOT_EXIST(2010,"学生ID不存在"),
    STUDENT_IDCARD_IS_EXIST(2011,"身份证号已经存在"),
    STUDENT_NUM_IS_EXIST(2012,"学生学号已经存在"),
    STUDENT_ADMISSION_NUM_IS_EXIST(2013,"学生准考证号已经存在"),

     /*
        -------案例--------
     */
     CASE_SHOW_DATA_IS_EXIST(2015,"案例中的顺序不能重复"),
    CASE_TiTLE_IS_EXIST(2016,"案例已存在"),
    CASE_DATA_IS_EXIST(2017,"案例id已存在"),
    /*
        -------题库--------
     */
    REPOSITORY_DATA_NOT_EXIST(2005,"题库不存在"),
    REPOSITORY_SHOW_IS_EXIST(2006,"题库的顺序不能重复"),
    REPOSITORY_SHOW_ID_IS_EXIST(2007,"该题库id已存在"),
    REPOSITORY_QUESCONTENT_IS_EXIST(2008, "题库中已存在此题"),
    /*
        第三方相关（3xxx）
     */
    /*
        3001-3020 微信公众号
     */
    WX_GZH_ACCESS_TOKEN_FAIL(3001, "微信公众号JSSDK获取access_token失败"),
    WX_GZH_JS_API_TICKET_FAIL(3002, "微信公众号JSSDK获取jsapi_ticket失败"),
    WX_GZH_SIGN_FAIL(3003, "微信公众号JSSDK获取SIGN失败"),
    WX_CODE_EMPTY(3004, "微信wxCode为空"),
    WX_CODE_OUTTIME(3005, "微信wxCode失效或不正确请重新获取"),

    ;
    private int code;

    private String msg;

}
```
