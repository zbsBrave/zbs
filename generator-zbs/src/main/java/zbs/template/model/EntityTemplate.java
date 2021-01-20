package zbs.template.model;

import java.util.Map;

/**
 * @author zhangbaisen
 * @since 2021/1/8
 */
public class EntityTemplate implements TemplateModel{
    private String templatePath;
    private Map<String,Object> templateMap;

    public String getTemplatePath() {
        return this.templatePath;
    }

    public Map<String, Object> getTemplateMap() {
        return this.templateMap;
    }
}
