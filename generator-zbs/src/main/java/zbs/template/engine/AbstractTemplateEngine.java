package zbs.template.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * 模板引擎抽象类
 * @author zhangbaisen
 * @since 2021/1/8
 */
public abstract class AbstractTemplateEngine {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 模板引擎初始化
     */
    public abstract AbstractTemplateEngine init();

    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     * @throws Exception 异常
     */
    public abstract void writer(Map<String, Object> objectMap, String templatePath, File outputFile) throws Exception ;


}
