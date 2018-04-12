package com.github.johnsonmoon.fastorm.core.sql;

/**
 * 插入数据
 * <p>
 * Created by xuyh at 2017/9/22 16:40.
 */
public class Insert {
	private String sql;

	private Insert() {
		this.sql = "INSERT INTO ";
	}

	/**
	 * 获取最终的sql语句
	 *
	 * @return sql sentence
	 */
	public String getSql() {
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
		insert.sql += (table + " ");
		return insert;
	}

	/**
	 * 指定插入数据的字段
	 *
	 * @param fields 字段
	 * @return {@link Insert}
	 */
	public Insert fields(String... fields) {
		if (fields == null || fields.length == 0)
			return this;
		StringBuilder fieldStr = new StringBuilder();
		for (String field : fields) {
			fieldStr.append(field);
			fieldStr.append(", ");
		}
		String field = fieldStr.substring(0, fieldStr.length() - 2);
		this.sql += (" ( " + field + " ) ");
		return this;
	}

	/**
	 * 插入的数据
	 *
	 * @param values 数据
	 * @return {@link Insert}
	 */
	public Insert values(Object... values) {
		if (values == null || values.length == 0)
			return this;
		StringBuilder valueStr = new StringBuilder();
		for (Object value : values) {
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
		return this;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return "Insert [sql=" + sql + "]";
	}
}
