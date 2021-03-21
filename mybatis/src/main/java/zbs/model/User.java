package zbs.model;

import lombok.Data;
import zbs.provider.annotation.CusId;

/**
 * @author zbs
 * @since 2021/3/20
 */
@Data
public class User {
    @CusId
    private long id;
    private String name;
    private int age;
    private String email;
}
