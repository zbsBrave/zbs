package zbs.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import zbs.mapper.UserMapper;
import zbs.model.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zbs
 * @since 2021/3/21
 */
public class PageExampleService {
    @Resource
    private UserMapper userMapper;

    public void getByPage(){

        int startPage = 1;
        int pageSize = 2;
        Page<Object> page = PageHelper.startPage(startPage, pageSize);
        List<User> all = userMapper.findAll();
        long total = page.getTotal();

    }
}
