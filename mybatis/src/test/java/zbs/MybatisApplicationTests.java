package zbs;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import zbs.mapper.UserMapper;
import zbs.model.User;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class MybatisApplicationTests {
    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        //userMapper.findAll().forEach(System.out::println);

        System.out.println(userMapper.getById(User.class, 1L));
        //System.out.println(user.toString());
    }

    @Test
    void page(){
        int startPage = 2;
        int pageSize = 2;
        Page<Object> page = PageHelper.startPage(startPage, pageSize);
        System.out.println("-----------");
        userMapper.findAll().forEach(System.out::println);
        long total = page.getTotal();
        System.out.println(total);

    }

}
