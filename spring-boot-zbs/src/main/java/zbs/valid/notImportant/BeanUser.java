package zbs.valid.notImportant;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * @author zhangbaisen
 * @since 2021/1/15
 */
@Data
public class BeanUser {
    public interface IdValid{}
    @NotNull(message = "id不能为null",groups = {IdValid.class, Default.class})
    private Integer id;
    @NotBlank(message = "name不能为null")
    private String name;
}
