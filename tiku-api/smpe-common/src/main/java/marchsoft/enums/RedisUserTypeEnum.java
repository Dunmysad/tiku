package marchsoft.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisUserTypeEnum {

    ADMIN("admin-"),

    STUDENT("student-");


    private final String type;

}
