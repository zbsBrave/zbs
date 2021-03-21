package zbs.mapper;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import zbs.model.User;
import zbs.provider.mapper.BaseMapper;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user")
    @ResultType(User.class)
    List<User> findAll();
}
