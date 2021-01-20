package zbs.config.mutipartDatasource;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Primary;

/**
 * @author zhangbaisen
 * @since 2021/1/12
 */
@Data
@TableName("user")
public class UserEntity {
    private Integer id;
    private String name;
    private Integer age;
}
