package zbs.template.engine;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * Velocity 模板引擎
 * @author zhangbaisen
 * @since 2021/1/7
 */
public class VelocityTemplateEngine extends AbstractTemplateEngine{
    private static final String DOT_VM = ".vm";
    private VelocityEngine velocityEngine;

    @Override
    public AbstractTemplateEngine init() {
        if (null == velocityEngine) {
            Properties p = new Properties();
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, StringPool.EMPTY);
            p.setProperty(Velocity.ENCODING_DEFAULT, StandardCharsets.UTF_8.name());
            p.setProperty(Velocity.INPUT_ENCODING, StandardCharsets.UTF_8.name());
            p.setProperty("file.resource.loader.unicode", "true");
            velocityEngine = new VelocityEngine(p);
        }
        return this;
    }

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, File outputFile) throws Exception {
        Template template = velocityEngine.getTemplate(templatePath, StandardCharsets.UTF_8.name());
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(ow)) {

            template.merge(new VelocityContext(objectMap), writer);
        }
    }
}
