package zbs.easyGenerator.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * 存放列名, 数据库字段类型, 以及对应Java中的属性名和类型
 * @author zbs
 * @since 2021/3/21
 */
@Data
public class ColumnDO {
    private String tableName;
    private String columnName;
    private String dataType;
    private String columnComment;
    private Integer columnSize;
    private Integer decimalDigits;
    private boolean nullAble;
    private boolean autoIncrement;

    private String attributeName;
    private String uppercaseAttributeName;
    private String attributeType;

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        if (this.columnName != null) {
            this.uppercaseAttributeName = WordUtils.capitalizeFully(this.columnName.toLowerCase(), new char[]{'_'})
                    .replace("_", "");
            this.attributeName = StringUtils.uncapitalize(this.uppercaseAttributeName);
        }
    }
}
