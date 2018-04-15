package com.github.johnsonmoon.fastorm.core.sql;

import com.github.johnsonmoon.fastorm.core.util.StringUtils;
import com.github.johnsonmoon.fastorm.core.util.ValueUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 插入数据
 * <p>
 * Created by xuyh at 2017/9/22 16:40.
 */
public class Insert {
    private String insertInto;
    private List<String> fields;
    private List<Object> values;
    private String sql;

    public Insert() {
        this.sql = "INSERT INTO ";
    }

    /**
     * 获取最终的sql语句
     *
     * @return sql sentence
     */
    public String getSql() {
        sql += (StringUtils.getSureName(insertInto) + " ");
        if (fields != null && fields.size() != 0) {
            StringBuilder fieldStr = new StringBuilder();
            for (String field : fields) {
                fieldStr.append(StringUtils.getSureName(field));
                fieldStr.append(", ");
            }
            String field = fieldStr.substring(0, fieldStr.length() - 2);
            this.sql += ("( " + field + " ) ");
        }
        if (values != null && values.size() != 0) {
            StringBuilder valueStr = new StringBuilder();
            for (Object value : values) {
                value = ValueUtils.formatValue(value);
                if (value instanceof CharSequence) {
                    valueStr.append("'");
                    valueStr.append(value);
                    valueStr.append("', ");
                } else {
                    valueStr.append(String.valueOf(value));
                    valueStr.append(", ");
                }
            }
            String value = valueStr.substring(0, valueStr.length() - 2);
            this.sql += ("VALUES (" + value + ") ");
        }
        return sql.trim();
    }

    /**
     * 插入数据的表格
     *
     * @param table 表格名称
     * @return {@link Insert}
     */
    public static Insert insertInto(String table) {
        Insert insert = new Insert();
        insert.insertInto = table;
        return insert;
    }

    /**
     * 指定插入数据的字段
     *
     * @param fields 字段
     * @return {@link Insert}
     */
    public Insert fields(String... fields) {
        this.fields = Arrays.asList(fields);
        return this;
    }

    /**
     * 插入的数据
     *
     * @param values 数据
     * @return {@link Insert}
     */
    public Insert values(Object... values) {
        this.values = Arrays.asList(values);
        return this;
    }

    /**
     * Set insert into table name.
     *
     * @param table name of table
     */
    public Insert setInsertInto(String table) {
        this.insertInto = table;
        return this;
    }

    /**
     * Set inserted fields.
     *
     * @param fields inserted fields
     */
    public Insert setFields(List<String> fields) {
        this.fields = fields;
        return this;
    }

    /**
     * Set inserted values.
     *
     * @param values object values
     */
    public Insert setValues(List<Object> values) {
        this.values = values;
        return this;
    }

    @Override
    public String toString() {
        return "Insert{" +
                "insertInto='" + insertInto + '\'' +
                ", fields=" + fields +
                ", values=" + values +
                ", sql='" + sql + '\'' +
                '}';
    }
}
