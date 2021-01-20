package zbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import zbs.config.mutipartDatasource.MyAbstractRoutingDataSource;
import zbs.config.mutipartDatasource.UserEntity;
import zbs.config.mutipartDatasource.UserMapper;
import java.util.List;

@SpringBootApplication
public class SpringBootMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext cac = SpringApplication.run(SpringBootMain.class, args);
        UserMapper mapper = cac.getBean(UserMapper.class);

        //这是key为默认的db1
        List<UserEntity> list = mapper.selectList(null);
        System.out.println(list);

        //改变key为db2
        MyAbstractRoutingDataSource.key="db2";
        System.out.println(mapper.selectList(null));
    }

}
