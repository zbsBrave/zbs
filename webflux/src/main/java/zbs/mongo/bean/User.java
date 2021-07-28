package zbs.mongo.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author zhangbaisen
 * @createTime 2021/4/30 13:48
 */
@Data
@Document
public class User {
    @Id
    private String id;
    
    /** 注解属性username为索引，并且不能重复 */
    @Indexed(unique = true) 
    private String name;
    
    private int sex;
    
    private String phone;
}
