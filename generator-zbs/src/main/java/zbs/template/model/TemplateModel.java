package zbs.template.model;

import java.util.Map;

/**
 * 模板抽象类
 *  模板路径
 *  模板数据
 * @author zhangbaisen
 * @since 2021/1/8
 */
public interface TemplateModel {
    String getTemplatePath();
    Map<String,Object> getTemplateMap();
}
