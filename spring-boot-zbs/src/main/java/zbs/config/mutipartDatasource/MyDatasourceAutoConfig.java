package zbs.config.mutipartDatasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbaisen
 * @since 2021/1/12
 */
@Configuration
public class MyDatasourceAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public MyAbstractRoutingDataSource dataSource(){
        Map<Object, Object> dataSourceMap = createDataSourceMap();
        MyAbstractRoutingDataSource dataSource = new MyAbstractRoutingDataSource();
        dataSource.setDefaultTargetDataSource(dataSourceMap.get("db1"));//默认数据源
        dataSource.setTargetDataSources(dataSourceMap);//目标数据源map
        return dataSource;
    }

    //创建一个dataSourceMap用于测试
    public static Map<Object, Object> createDataSourceMap(){
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("db1",createDataSource(
                        "jdbc:mysql://localhost:3306/db1?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai",
                            "com.mysql.cj.jdbc.Driver","root","123"));
        dataSourceMap.put("db2",createDataSource(
                "jdbc:mysql://localhost:3306/db2?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai",
                "com.mysql.cj.jdbc.Driver","root","123"));

        return dataSourceMap;
    }
    //创建一个简单的HikariDataSource
    public static DataSource createDataSource(String url,String driver,String username,String password){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setDriverClassName(driver);
        config.setPassword(password);
        config.setUsername(username);
        return new HikariDataSource(config);
    }


}
