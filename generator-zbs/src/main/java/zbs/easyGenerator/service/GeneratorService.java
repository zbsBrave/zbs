package zbs.easyGenerator.service;

import zbs.easyGenerator.model.TableItem;

public interface GeneratorService {
    void generateZip(String[] tableNames, String zipPath);

    void generateZip(TableItem[] tableItems, String zipPath);
}
