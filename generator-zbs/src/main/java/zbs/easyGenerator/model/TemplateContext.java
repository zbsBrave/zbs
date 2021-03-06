package zbs.easyGenerator.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import zbs.easyGenerator.constant.KeyConstant;
import zbs.easyGenerator.util.ConfigUtils;
import zbs.easyGenerator.util.SpringContextUtils;
import zbs.easyGenerator.util.TemplateUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zbs
 * @since 2021/3/21
 */
public class TemplateContext {
    private static final Map<String, Object> systemVariables = new HashMap<>();

    private final Map<String, String> dynamicPathVariables = new HashMap<>();
    private Map<String, Object> templateVariables;
    private TableDO table;

    static {
        Properties properties = System.getProperties();
        systemVariables.put("config", ConfigUtils.class);
        systemVariables.put("utils", TemplateUtils.class);

        systemVariables.put("username", System.getenv("USERNAME"));
        systemVariables.put("computerName", System.getenv("COMPUTERNAME"));
        systemVariables.put("osName", properties.getProperty("os.name"));
        systemVariables.put("osArch", properties.getProperty("os.arch"));
        systemVariables.put("osVersion", properties.getProperty("os.version"));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static void setSystemVariable(String key, Object value) {
        systemVariables.put(key, value);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("system", systemVariables);

        map.put("dynamicPath", dynamicPathVariables);
        map.put("template", templateVariables);
        map.put("table", table);
        return map;
    }

    public TableDO getTable() {
        return table;
    }

    public void setTable(TableDO table) {
        this.table = table;
    }

    public Map<String, Object> getSystemVariables() {
        return systemVariables;
    }

    public Map<String, String> getDynamicPathVariables() {
        return dynamicPathVariables;
    }

    public Map<String, Object> getTemplateVariables() {
        return templateVariables;
    }

    public void setTemplateVariables(Map<String, Object> templateVariables) {
        this.templateVariables = templateVariables;
    }


    public static class Builder {

        private Environment environment = SpringContextUtils.getBean(Environment.class);

        private TemplateContext context = new TemplateContext();

        private Builder() {
            String packageName = environment.getProperty("generator.package", "pkg");
            context.getDynamicPathVariables().put(KeyConstant.PACKAGE_PATH, packageName.replace(".", "/"));
        }

        public Builder table(TableDO table) {
            if (table != null) {
                context.setTable(table);
                context.getDynamicPathVariables().put(KeyConstant.CLASS_NAME, table.getClassName());
                context.getDynamicPathVariables().put(KeyConstant.LOWERCASE_CLASS_NAME, table.getLowercaseClassName());
            }
            return this;
        }

        public Builder dynamicPathVariables(Map<String, String> variables) {
            if (variables != null) {
                Map<String, String> cloneVariables = new HashMap<>();
                for (Map.Entry<String, String> entry : variables.entrySet()) {
                    cloneVariables.put(entry.getKey(), entry.getValue());
                }
                String className = cloneVariables.get(KeyConstant.CLASS_NAME);
                String lowercaseClassName = cloneVariables.get(KeyConstant.LOWERCASE_CLASS_NAME);
                if (StringUtils.isNotBlank(className)) {
                    cloneVariables.put(KeyConstant.LOWERCASE_CLASS_NAME, StringUtils.uncapitalize(className));
                } else if (StringUtils.isNotBlank(lowercaseClassName)) {
                    cloneVariables.put(KeyConstant.CLASS_NAME,
                            lowercaseClassName.substring(0, 1).toUpperCase() + lowercaseClassName.substring(1));
                }
                for (Map.Entry<String, String> entry : cloneVariables.entrySet()) {
                    context.getDynamicPathVariables().put(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public Builder templateVariables(Map<String, Object> variables) {
            context.setTemplateVariables(variables);
            return this;
        }

        public TemplateContext build() {
            return context;
        }
    }
}
