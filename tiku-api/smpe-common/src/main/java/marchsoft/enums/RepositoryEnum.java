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
