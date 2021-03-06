package zbs.easyGenerator.service;

import org.springframework.beans.factory.annotation.Value;
import zbs.easyGenerator.model.ColumnDO;
import zbs.easyGenerator.model.TableDO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author zbs
 * @since 2021/3/21
 */
public abstract class BaseTableService implements TableService{
    private final Map<String, TypeHandler> typeHandlerMap = new HashMap<>();

    @Value("${generator.datasource.url}")
    private String url;

    @Value("${generator.datasource.username}")
    private String username;

    @Value("${generator.datasource.password}")
    private String password;

    protected abstract String getDriverClassName();

    protected abstract String analysisDataType(ColumnDO column);

    @Override
    public TableDO getTable(String tableName) throws Exception {
        try (Connection connection = getConnection()) {
            TableDO table = getMetaDataTable(connection, tableName);
            if (table == null) {
                return null;
            }
            List<ColumnDO> columns = listMetaDataColumn(connection, tableName);
            table.setColumns(columns);
            return table;
        }
    }

    protected void addTypeHandler(String dataType, Class clazz) {
        this.typeHandlerMap.put(dataType, column -> clazz);
    }

    protected void addTypeHandler(String dataType, TypeHandler handler) {
        this.typeHandlerMap.put(dataType, handler);
    }

    protected Class getTypeMappingOrDefault(String dataType, ColumnDO column, Class clazz) {
        TypeHandler handler = this.typeHandlerMap.get(dataType);
        if (handler == null) {
            return clazz;
        }
        return handler.handle(column);
    }


    private TableDO getMetaDataTable(Connection connection, String tableName) throws SQLException {
        ResultSet resultSet = connection.getMetaData().getTables(
                connection.getCatalog(),
                connection.getSchema(),
                tableName,
                null);
        if (resultSet.next()) {
            if (!Objects.equals(tableName, resultSet.getString("TABLE_NAME"))) {
                return null;
            }
            TableDO table = new TableDO();
            table.setTableName(tableName);
            table.setTableType(resultSet.getString("TABLE_TYPE"));
            table.setTableComment(resultSet.getString("REMARKS"));
            return table;
        }
        return null;
    }

    private List<ColumnDO> listMetaDataColumn(Connection connection, String tableName) throws SQLException {
        ResultSet resultSet = connection.getMetaData().getColumns(connection.getCatalog(),
                connection.getSchema(),
                tableName,
                null);
        List<ColumnDO> columns = new ArrayList<>();
        while (resultSet.next()) {
            if (!Objects.equals(tableName, resultSet.getString("TABLE_NAME"))) {
                continue;
            }

            ColumnDO column = new ColumnDO();
            column.setTableName(tableName);
            column.setColumnName(resultSet.getString("COLUMN_NAME"));
            column.setDataType(resultSet.getString("TYPE_NAME"));
            column.setColumnSize(resultSet.getInt("COLUMN_SIZE"));
            column.setDecimalDigits(resultSet.getInt("DECIMAL_DIGITS"));
            column.setColumnComment(resultSet.getString("REMARKS"));

            String nullAble = resultSet.getString("IS_NULLABLE");
            if (nullAble != null) {
                column.setNullAble("YES".equals(nullAble));
            }
            String autoIncrement = resultSet.getString("IS_AUTOINCREMENT");
            if (autoIncrement != null) {
                column.setAutoIncrement("YES".equals(autoIncrement));
            }

            column.setAttributeType(analysisDataType(column));

            columns.add(column);
        }
        return columns;
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(getDriverClassName());
        return DriverManager.getConnection(url, username, password);
    }

    @FunctionalInterface
    protected interface TypeHandler {
        Class handle(ColumnDO column);
    }
}
