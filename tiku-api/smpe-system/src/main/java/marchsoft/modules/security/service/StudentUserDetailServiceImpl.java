package marchsoft.modules.security.service;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import marchsoft.enums.RedisUserTypeEnum;
import marchsoft.enums.ResultEnum;
import marchsoft.exception.BadRequestException;
import marchsoft.modules.security.config.bean.LoginProperties;
import marchsoft.modules.security.entity.dto.JwtUserDto;
import marchsoft.modules.system.entity.dto.UserDTO;
import marchsoft.modules.system.service.IDataService;
import marchsoft.modules.system.service.IRoleService;
import marchsoft.modules.tiku.entity.EStudent;
import marchsoft.modules.tiku.service.IEStudentService;
import marchsoft.modules.tiku.service.mapstruct.StudentMapStruct;
import marchsoft.utils.RedisUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description：
 *
 * @author mfei
 * Date: 2022/4/6 0:06
 **/
@RequiredArgsConstructor
@Component("studentUserDetailsService")
public class StudentUserDetailServiceImpl implements UserDetailsService {

    private final IEStudentService studentService;

    private final IRoleService roleService;

    private final IDataService dataService;

    private final LoginProperties loginProperties;

    private final StudentMapStruct studentMapStruct;

    private final RedisUtils redisUtils;

    public void setEnableCache(boolean enableCache) {
        this.loginProperties.setCacheEnable(enableCache);
    }

    /**
     * description:
     * modify @RenShiWei 2020/11/21 description:修改为根据id生成token，改变身份认证策略，根据id查询
     *
     * @param username 查询参数 实质为user_id
     * @return /
     * @author RenShiWei
     * Date: 2020/11/21 21:24
     */
    @Override
    public JwtUserDto loadUserByUsername(String username) {
        boolean searchDb = true;
        JwtUserDto jwtUserDto = null;
        if (loginProperties.isCacheEnable() && redisUtils.hasKey(RedisUserTypeEnum.STUDENT.getType().concat(username))) {
            jwtUserDto = (JwtUserDto) SerializationUtils.deserialize((byte[]) redisUtils.get(RedisUserTypeEnum.STUDENT.getType().concat(username)));
            searchDb = false;
        }

        if (searchDb) {
            UserDTO userDto;
            EStudent student = studentService.getById(Long.parseLong(username));
            userDto = studentMapStruct.toUserDto(student);

            if (ObjectUtil.isEmpty(userDto)) {
                throw new BadRequestException(ResultEnum.USER_NOT_EXIST);
            } else {
//                if (! userDto.getEnabled()) {
//                    throw new BadRequestException(ResultEnum.COUNT_NOT_ENABLE);
//                }
                HashSet<String> permissions = new HashSet<>();
                permissions.add("student");
                List<GrantedAuthority> studentUserAuthor = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                userDto.setEnabled(true);
                jwtUserDto = new JwtUserDto(
                        userDto,
                        null,
                        studentUserAuthor
                );
                redisUtils.set(RedisUserTypeEnum.STUDENT.getType().concat(username), SerializationUtils.serialize(jwtUserDto));
            }
        }
        return jwtUserDto;
    }
}
