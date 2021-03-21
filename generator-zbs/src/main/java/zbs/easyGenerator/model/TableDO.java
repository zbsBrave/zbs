package zbs.easyGenerator.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import java.util.List;

/**
 * 存放表名, 对于的类名, 以及列信息
 * @author zbs
 * @since 2021/3/21
 */
@Data
public class TableDO {
    private String tableName;
    private String tableType;
    private String tableComment;
    private List<ColumnDO> columns;

    private String className;
    private String lowercaseClassName;

    public void setTableName(String tableName) {
        this.tableName = tableName;
        if (this.tableName != null) {
            this.className = WordUtils.capitalizeFully(tableName.toLowerCase(), new char[]{'_'})
                    .replace("_", "");
            this.lowercaseClassName = StringUtils.uncapitalize(this.className);
        }
    }
}
