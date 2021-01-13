package zbs.config.mutipartDatasource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangbaisen
 * @since 2021/1/12
 */
@Mapper
//@DS("db2")
public interface UserMapper extends BaseMapper<UserEntity> {
}
