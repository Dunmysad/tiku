package marchsoft.modules.tiku.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import marchsoft.base.BasicServiceImpl;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.tiku.entity.Dto.StudentDto;
import marchsoft.modules.tiku.entity.Dto.StudentExcel;
import marchsoft.modules.tiku.entity.Dto.StudentQueryCriteria;
import marchsoft.modules.tiku.entity.EStudent;
import marchsoft.modules.tiku.mapper.EStudentMapper;
import marchsoft.modules.tiku.service.IEStudentService;
import marchsoft.modules.tiku.service.mapstruct.StudentMapStruct;
import marchsoft.utils.FileUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import sun.jvm.hotspot.debugger.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* <p>
* 学生基本信息表 服务实现类
* </p>
*
* @author mfei
* @since 2022-03-29
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "Studentshow")
public class EStudentServiceImpl extends BasicServiceImpl<EStudentMapper, EStudent> implements IEStudentService {

    private final StudentMapStruct studentMapStruct;
    private StudentQueryCriteria studentQueryCriteria;

    // 进行查询条件判断
    private LambdaQueryWrapper<EStudent> buildStudentQueryCriteria(StudentQueryCriteria criteria){
        LambdaQueryWrapper<EStudent> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(criteria.getId())){
            queryWrapper.eq(EStudent::getId,criteria.getId());
        }
        if (ObjectUtil.isNotNull(criteria.getName())){
            queryWrapper.eq(EStudent::getName,criteria.getName());
        }
        if (ObjectUtil.isNotNull(criteria.getAdmissionNum())){
            queryWrapper.eq(EStudent::getAdmissionNum,criteria.getAdmissionNum());
        }
        if (ObjectUtil.isNotNull(criteria.getIdCard())){
            queryWrapper.eq(EStudent::getIdCard,criteria.getIdCard());
        }
        if (ObjectUtil.isNotNull(criteria.getStudentNum())){
            queryWrapper.eq(EStudent::getStudentNum,criteria.getStudentNum());
        }
        if (ObjectUtil.isNotNull(criteria.getUniversityId())){
            queryWrapper.eq(EStudent::getUniversityId,criteria.getUniversityId());
        }
        if (ObjectUtil.isNotNull(criteria.getCollege())){
            queryWrapper.eq(EStudent::getCollege,criteria.getCollege());
        }
        if (ObjectUtil.isNotNull(criteria.getEnrollmentYear())){
            queryWrapper.eq(EStudent::getEnrollmentYear,criteria.getEnrollmentYear());
        }

        queryWrapper.eq(EStudent::getDeleted,0);

        return queryWrapper;
    }


    private final EStudentMapper eStudentMapper;


    // 条件查询
    @Override
    public IPage<EStudent> getAll(IPage<EStudent> page, StudentQueryCriteria criteria) {
        IPage<EStudent> eStudentIPage = eStudentMapper.getAll(page,buildStudentQueryCriteria(criteria));
        return eStudentIPage;
    }

    //增加学生信息
    @Override
    public void addStudent(EStudent eStudent) {
        // 判断id是否为空
        if(ObjectUtil.isNotNull(eStudent.getId())){
            EStudent eStudent1 = eStudentMapper.selectById(eStudent.getId());
            if ((ObjectUtil.isNull(eStudent1))){
                throw new BadRequestException(ResultEnum.STUDENT_ID_NOT_EXIST);
            }
        }
        // 通过身份证号判断学生信息是否存在  (idCard)
        LambdaQueryWrapper<EStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EStudent::getIdCard,eStudent.getIdCard());
        EStudent eStudent1 = eStudent.selectOne(queryWrapper);
        // 身份信息不存在 判断准考证号是否存在  (admissionNum)
        LambdaQueryWrapper<EStudent> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(EStudent::getAdmissionNum, eStudent.getAdmissionNum());
        EStudent eStudent2 = eStudentMapper.selectOne(queryWrapper1);
        //   身份信息与准考证号均不存在时  判断学生学号是否一致   (studentNum)
        LambdaQueryWrapper<EStudent> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(EStudent::getStudentNum,eStudent.getStudentNum());
        EStudent eStudent3 = eStudentMapper.selectOne(queryWrapper2);
        if (ObjectUtil.isNotNull(eStudent1)){
            throw new BadRequestException(ResultEnum.STUDENT_IDCARD_IS_EXIST);
        }else if (ObjectUtil.isNotNull(eStudent2)) {
            throw new BadRequestException(ResultEnum.STUDENT_ADMISSION_NUM_IS_EXIST);
        }else if (ObjectUtil.isNotNull(eStudent3)){
            throw new BadRequestException(ResultEnum.STUDENT_NUM_IS_EXIST);
        }

        int i = eStudentMapper.insert(eStudent);
        if (i < 1){
            throw new BadRequestException(ResultEnum.INSERT_OPERATION_FAIL);
        }

    }

    //修改学生信息
    @Override
    public void updateStudent(EStudent eStudent) {

        //通过学号查询当前的学生信息
        EStudent oldEstudent = eStudentMapper.selectById(eStudent.getId());

        //前端传过来的值Id不为空并且和旧值不同
        if(ObjectUtil.isNotNull(eStudent.getId()) && ! eStudent.getId().equals(oldEstudent.getId())){
            EStudent eStudent1 = eStudentMapper.selectById(eStudent.getId());
            if (ObjectUtil.isNull(eStudent1)){
                throw new BadRequestException(ResultEnum.STUDENT_ID_NOT_EXIST);
            }
        }

        //修改身份证号，判断修改后的身份证号是否重复
        if (!oldEstudent.getIdCard().equals(eStudent.getIdCard())){
            LambdaQueryWrapper<EStudent> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EStudent::getIdCard,eStudent.getIdCard());
            EStudent eStudent1 = eStudentMapper.selectOne(queryWrapper);
            if (ObjectUtil.isNotNull(eStudent1)){
                throw new BadRequestException(ResultEnum.STUDENT_IDCARD_IS_EXIST);
            }
        }

        //修改学号，判断修改后的学号是否重复
        if (!oldEstudent.getStudentNum().equals(eStudent.getStudentNum())){
            LambdaQueryWrapper<EStudent> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(EStudent::getStudentNum,eStudent.getStudentNum());
            EStudent eStudent2 = eStudentMapper.selectOne(queryWrapper1);
            if (ObjectUtil.isNotNull(eStudent2)){
                throw new BadRequestException(ResultEnum.STUDENT_NUM_IS_EXIST);
            }
        }

        //修改准考证号，判断修改后的准考证号是否重复
        if (!oldEstudent.getAdmissionNum().equals(eStudent.getAdmissionNum())){
            LambdaQueryWrapper<EStudent> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(EStudent::getAdmissionNum,eStudent.getAdmissionNum());
            EStudent eStudent3 = eStudentMapper.selectOne(queryWrapper2);
            if (ObjectUtil.isNotNull(eStudent3)){
                throw new BadRequestException(ResultEnum.STUDENT_ADMISSION_NUM_IS_EXIST);
            }
        }


        int i = eStudentMapper.updateById(eStudent);
        if (i < 1){
            throw new BadRequestException(ResultEnum.UPDATE_OPERATION_FAIL);
        }


    }


    //删除学生信息
    @Override
    public void deleteStudentById(Set<Long> ids) {
        for(Long id : ids){
            eStudentMapper.deleteById(id);
        }
    }


    @Override
    public Long findStudentIdByStudentNumAndDeptId(String username, Long deptId) {
        LambdaQueryWrapper<EStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EStudent::getStudentNum,username).eq(EStudent::getUniversityId,deptId);
        EStudent eStudent = this.getOne(queryWrapper);
        if (ObjectUtil.isNull(eStudent)) {
            throw new BadRequestException(ResultEnum.USER_NOT_EXIST);
        }

        return eStudent.getId();
    }


    @Override
    public void download(List<StudentDto> studentDos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudentDto studentDto : studentDos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("学生姓名", studentDto.getName());
            map.put("学生学号", studentDto.getStudentNum());
            map.put("学生身份证号", studentDto.getIdCard());
            map.put("学生准考证号", studentDto.getAdmissionNum());
            map.put("入学年度", studentDto.getEnrollmentYear());
            map.put("学院名称", studentDto.getCollege());
            map.put("学校名称（”7“表示”华南“，”8“表示华北）", studentDto.getUniversityId());
            map.put("专业",studentDto.getMajor());
            map.put("性别（”0“表示”男“，”1“表示”女“", studentDto.getSex()?"女":"男");
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }


    /**
     * Description:
     * 根据criteria条件和isQuery查询
     * todo isQuery的作用
     *
     * @param criteria : Dept查询条件
     * @param isQuery :  是否判断当前用户数据权限
     * @return java.util.List<marchsoft.modules.system.entity.dto.DeptDTO>
     * @author yx
     * @date 2020/11/26 15:45
     **/
    @Override
    public List<StudentDto> queryAll(StudentQueryCriteria criteria, Boolean isQuery) {
        List<EStudent> students = this.eStudentMapper.selectList(analysisQueryCriteria(criteria));
        return studentMapStruct.toDto(students);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addExcel(List<StudentExcel> cachedDataList) {
        List<EStudent> excel = studentMapStruct.excel(cachedDataList);
        for (EStudent eStudent : excel) {
            this.addStudent(eStudent);
        }
    }

    /**
     *  下载学生信息模板
     * @param query
     * @param response
     * @throws IOException
     */
    @Override
    public void downloadTemplate(List<StudentDto> query, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("学生姓名", null);
        map.put("学生学号", null);
        map.put("学生身份证号", null);
        map.put("学生准考证号", null);
        map.put("入学年度", null);
        map.put("学院名称", null);
        map.put("学校名称（”7“表示”华南“，”8“表示华北）", null);
        map.put("专业", null);
        map.put("性别（”0“表示”男“，”1“表示”女“）", null);
        list.add(map);
        FileUtils.downloadExcel(list, response);
    }

    @Override
    public List<StudentDto> query(boolean isQuery) {
        return null;
    }

    private LambdaQueryWrapper<EStudent> analysisQueryCriteria(StudentQueryCriteria criteria) {
        LambdaQueryWrapper<EStudent> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }


}

