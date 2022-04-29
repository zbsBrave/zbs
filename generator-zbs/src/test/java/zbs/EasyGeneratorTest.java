package zbs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import zbs.easyGenerator.model.TableItem;
import zbs.easyGenerator.service.GeneratorService;

import javax.annotation.Resource;

/**
 * @author zbs
 * @since 2021/3/21
 */
@ActiveProfiles("example")
@SpringBootTest
public class EasyGeneratorTest {
    @Resource
    private GeneratorService generatorService;

    @Test
    public void test() {
        String[] tableNames = {"vip_rebuy","vip_rebuy_price"};
        generatorService.generateZip(tableNames, "C:\\Users\\zbs\\Documents\\code.zip");
    }

    @Test
    public void test2() {
        TableItem a = TableItem.newBuilder()
                .tableName("user")
                .dynamicPathVariable("User", "user")
                .build();
        System.out.println(a);
//        generatorService.generateZip(new TableItem[]{
//                TableItem.newBuilder()
//                        .tableName("user")
//                        .dynamicPathVariable("User", "user")
//                        .build(),
//                new TableItem("usera")
//        }, "C:\\Users\\zbs\\Documents\\code.zip");
    }
}
