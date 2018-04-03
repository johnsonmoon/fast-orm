package com.github.johnsonmoon.fastorm.core.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * 更新
 * <p>
 * Created by xuyh at 2017/9/20 15:12.
 */
public class Update {
	private String sql;
	private Map<String, Object> set;
	private Criteria criteria;

	private Update() {
		this.sql = "UPDATE ";
		this.set = new HashMap<>();
	}

	/**
	 * 获取最终的sql语句
	 *
	 * @return sql sentence
	 */
	public String getSql() {
		String result = sql;
		if (!set.isEmpty()) {
			StringBuilder setStr = new StringBuilder("SET ");
			for (Map.Entry<String, Object> entry : set.entrySet()) {
				Object value = entry.getValue();
				if (value instanceof CharSequence) {
					setStr.append(entry.getKey());
					setStr.append(" = '");
					setStr.append(value);
					setStr.append("', ");
				} else {
					setStr.append(entry.getKey());
					setStr.append(" = ");
					setStr.append(value);
					setStr.append(", ");
				}
			}
			String setStrS = setStr.substring(0, setStr.length() - 2);
			result += (setStrS + " ");
		}
		if (criteria != null)
			result += (criteria.getCriteria() + " ");
		return result.trim();
	}

	/**
	 * 更新
	 *
	 * @param table table name
	 * @return {@link Update}
	 */
	public static Update update(String table) {
		Update update = new Update();
		update.sql += (table + " ");
		return update;
	}

	/**
	 * 设置值
	 *
	 * @param field field name
	 * @param value field value to be set
	 * @return {@link Update}
	 */
	public Update set(String field, Object value) {
		this.set.put(field, value);
		return this;
	}

	/**
	 * 添加查询条件
	 *
	 * @param criteria 查询条件
	 * @return {@link Update}
	 */
	public Update addWhere(Criteria criteria) {
		this.criteria = criteria;
		return this;
	}

	@Override
	public String toString() {
		return "Update [sql=" + sql + ", set=" + set + ", criteria=" + criteria + "]";
	}
}
