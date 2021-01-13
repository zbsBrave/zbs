package zbs.config.mutipartDatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author zhangbaisen
 * @since 2021/1/12
 */
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {
    public static String key = "db1";

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("key:"+key+"======================================");
        return key;
    }

}
