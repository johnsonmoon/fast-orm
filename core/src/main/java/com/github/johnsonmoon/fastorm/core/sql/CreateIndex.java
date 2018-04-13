package com.github.johnsonmoon.fastorm.core.sql;

import com.github.johnsonmoon.fastorm.core.common.MapperException;
import com.github.johnsonmoon.fastorm.core.meta.ColumnMetaInfo;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;
import com.github.johnsonmoon.fastorm.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmoon at 2018/4/13 14:24.
 */
public class CreateIndex {
    /**
     * Generate create indexes sql sentence list by given table meta information.
     *
     * @param tableMetaInfo meta information of table {@link TableMetaInfo}
     * @return sql sentence list
     */
    public static List<String> getSql(TableMetaInfo tableMetaInfo) {
        if (tableMetaInfo == null)
            throw new MapperException("Unsupported operation: table meta information must not be null.");
        List<String> sentenceList = new ArrayList<>();
        for (ColumnMetaInfo columnMetaInfo : tableMetaInfo.getColumnMetaInfoList()) {
            if (columnMetaInfo.isIndexedColumn()) {
                sentenceList.add("CREATE INDEX index_" + columnMetaInfo.getColumnName()
                        + " ON " + StringUtils.getSureName(tableMetaInfo.getTableName())
                        + " (" + StringUtils.getSureName(columnMetaInfo.getColumnName()) + ");");
            }
        }
        return sentenceList;
    }

    /**
     * Generate create indexes sql sentence list by given table name and column name list
     *
     * @param tableName      name of table
     * @param columnNameList name list of columns
     * @return sql sentence list
     */
    public static List<String> getSql(String tableName, List<String> columnNameList) {
        if (tableName == null || tableName.isEmpty()
                || columnNameList == null || columnNameList.isEmpty())
            throw new MapperException("Unsupported operation: tableName or columnNameList must not be null.");
        List<String> sentenceList = new ArrayList<>();
        for (String columnName : columnNameList) {
            sentenceList.add("CREATE INDEX index_" + columnName
                    + " ON " + StringUtils.getSureName(tableName)
                    + " (" + StringUtils.getSureName(columnName) + ");");
        }
        return sentenceList;
    }

    /**
     * Generate create indexes sql sentence by given table name and column name
     *
     * @param tableName  name of table
     * @param columnName name of column
     * @return sql sentence
     */
    public static String getSql(String tableName, String columnName) {
        if (tableName == null || tableName.isEmpty()
                || columnName == null || columnName.isEmpty())
            throw new MapperException("Unsupported operation: tableName or columnName must not be null.");
        return "CREATE INDEX index_" + columnName
                + " ON " + StringUtils.getSureName(tableName)
                + " (" + StringUtils.getSureName(columnName) + ");";
    }
}
