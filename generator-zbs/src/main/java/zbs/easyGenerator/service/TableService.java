package zbs.easyGenerator.service;

import zbs.easyGenerator.model.TableDO;

/**
 * 获取table信息
 */
public interface TableService {
    TableDO getTable(String tableName) throws Exception;
}
