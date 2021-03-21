package zbs.easyGenerator.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zbs.easyGenerator.model.TableDO;
import zbs.easyGenerator.model.TableItem;
import zbs.easyGenerator.model.TemplateContext;
import zbs.easyGenerator.service.GeneratorService;
import zbs.easyGenerator.service.TableService;
import zbs.easyGenerator.util.SpringContextUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zbs
 * @since 2021/3/21
 */
@Service
@Log4j2
public class GeneratorServiceImpl implements GeneratorService {
    static {
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
    }

    @Value("${generator.template.base-path:}")
    private String templateBasePath;

    @Value("${generator.template.output-paths:}")
    private String templateOutputPaths;

    @Value("${generator.datasource.type:mysql}")
    private String datasourceType;

    @Override
    public void generateZip(String[] tableNames, String zipPath) {
        TableItem[] tableItems = new TableItem[tableNames.length];
        for (int i = 0; i < tableNames.length; i++) {
            tableItems[i] = new TableItem(tableNames[i]);
        }
        generateZip(tableItems, zipPath);
    }

    @Override
    public void generateZip(TableItem[] tableItems, String zipPath) {
        TableService tableService = SpringContextUtils.getBean(datasourceType, TableService.class);
        try (FileOutputStream fos = new FileOutputStream(zipPath)) {
            try (ZipOutputStream zos = new ZipOutputStream(fos)) {
                for (TableItem item : tableItems) {
                    TableDO table = tableService.getTable(item.getTableName());
                    if (table == null) {
                        log.warn("表{} 信息查询失败", item.getTableName());
                        continue;
                    }
                    generatorCode(TemplateContext.newBuilder()
                            .templateVariables(item.getTemplateVariables())
                            .table(table)
                            .dynamicPathVariables(item.getDynamicPathVariables())
                            .build(), zos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generatorCode(TemplateContext context, ZipOutputStream zos) {
        VelocityContext velocityContext = new VelocityContext(context.toMap());
        Map<String, String> outputPathMap = parseTemplateOutputPaths(context);
        for (Map.Entry<String, String> entry : outputPathMap.entrySet()) {
            Template template = Velocity.getTemplate(entry.getKey(), "UTF-8");
            try (StringWriter writer = new StringWriter()) {
                template.merge(velocityContext, writer);
                zos.putNextEntry(new ZipEntry(entry.getValue()));
                IOUtils.write(writer.toString(), zos, "UTF-8");

                zos.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, String> parseTemplateOutputPaths(TemplateContext context) {
        String[] rows = templateOutputPaths.split("\n");
        Map<String, String> outputPathMap = new HashMap<>();
        for (String row : rows) {
            int index = row.indexOf(":");
            if (index == -1) {
                continue;
            }
            String fileName = row.substring(0, index).trim();
            try {
                String path = replace(row.substring(index + 1).trim(), context.getDynamicPathVariables());
                outputPathMap.put(templateBasePath + "/" + fileName, path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outputPathMap;
    }

    private static String replace(String pattern, Map<String, String> context) throws Exception {
        char[] patternChars = pattern.toCharArray();
        StringBuilder valueBuffer = new StringBuilder();
        StringBuilder variableNameBuffer = null;
        boolean inVariable = false;
        for (int i = 0; i < patternChars.length; ++i) {
            if (!inVariable && patternChars[i] == '{') {
                inVariable = true;
                variableNameBuffer = new StringBuilder();
                continue;
            }
            if (inVariable && patternChars[i] == '}') {
                inVariable = false;
                String variable = context.get(variableNameBuffer.toString());
                valueBuffer.append(variable == null ? "null" : variable);
                variableNameBuffer = null;
                continue;
            }
            if (patternChars[i] == '\\' && ++i == patternChars.length) {
                throw new Exception("转义符 '\\' 后缺少字符");
            }
            StringBuilder activeBuffer = inVariable ? variableNameBuffer : valueBuffer;
            activeBuffer.append(patternChars[i]);
        }
        if (variableNameBuffer != null) {
            throw new Exception("结尾缺少 '}' ");
        }
        return valueBuffer.toString();
    }
}
