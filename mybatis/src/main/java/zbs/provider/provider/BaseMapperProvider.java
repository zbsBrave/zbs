package zbs.provider.provider;

import org.apache.ibatis.jdbc.SQL;
import zbs.provider.annotation.CusId;
import zbs.provider.annotation.CusTable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zbs
 * @since 2021/3/20
 */
public class BaseMapperProvider {
    protected Map<String, Class<?>> entityClassMap = new ConcurrentHashMap<String, Class<?>>();
    protected Class<?> mapperClass;

    public String getById(final Class resultType) {
        final String idName = getIdName(resultType);
        final String tableName = getTableName(resultType);
        return new SQL() {{
            SELECT("*").FROM(tableName).WHERE(idName + "  = #{id} ");
        }}.toString();
    }


    public static String getTableName(Class cls) {
        final CusTable table = (CusTable) cls.getAnnotation(CusTable.class);
        return (table == null || "".equals(table.value())) ? cls.getSimpleName() : table.value();
    }


    public static String getIdName(Class cls) {
        Field[] fields = cls.getDeclaredFields();
        Field field = fields[0];
        for (Field f : fields) {
            if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers())) {
                field = f;
                break;
            }
        }
        final CusId id = field.getAnnotation(CusId.class);
        return (id == null || "".equals(id.value())) ? field.getName() : id.value();
    }


}
