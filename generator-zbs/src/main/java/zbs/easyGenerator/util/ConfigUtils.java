package zbs.easyGenerator.util;

import org.springframework.core.env.Environment;

/**
 * @author zbs
 * @since 2021/3/21
 */
public class ConfigUtils {
    public static String get(String key) {
        Environment environment = SpringContextUtils.getBean(Environment.class);
        return environment.getProperty(key);
    }

    public static String getOrDefault(String key, String defaultValue) {
        Environment environment = SpringContextUtils.getBean(Environment.class);
        return environment.getProperty(key, defaultValue);
    }
}
