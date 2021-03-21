package zbs.provider.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import zbs.provider.provider.BaseMapperProvider;

import java.io.Serializable;

public interface BaseMapper<T> {
    @SelectProvider(type = BaseMapperProvider.class,method = "getById")
    T getById(Class<T> resultType, Serializable id);
}
