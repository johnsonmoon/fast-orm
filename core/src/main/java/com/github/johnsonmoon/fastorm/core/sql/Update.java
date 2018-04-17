package com.github.johnsonmoon.fastorm.core.sql;

import com.github.johnsonmoon.fastorm.core.util.StringUtils;
import com.github.johnsonmoon.fastorm.core.util.ValueUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 更新
 * <p>
 * Created by xuyh at 2017/9/20 15:12.
 */
public class Update {
	private String sql;
	private String updateTable;
	private Map<String, Object> set;
	private Criteria criteria;

	public Update() {
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
		result += (StringUtils.getSureName(updateTable) + " ");
		if (!set.isEmpty()) {
			StringBuilder setStr = new StringBuilder("SET ");
			for (Map.Entry<String, Object> entry : set.entrySet()) {
				String key = StringUtils.getSureName(entry.getKey());
				Object value = entry.getValue();
				value = ValueUtils.formatValue(value);
				if (value instanceof CharSequence) {
					setStr.append(key);
					setStr.append(" = '");
					setStr.append(value);
					setStr.append("', ");
				} else {
					setStr.append(key);
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
		update.updateTable = table;
		return update;
	}

	/**
	 * Set update table name;
	 *
	 * @param table name of table to update
	 */
	public Update setUpdateTable(String table) {
		this.updateTable = table;
		return this;
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

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map<String, Object> getSet() {
		return set;
	}

	public void setSet(Map<String, Object> set) {
		this.set = set;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public String getUpdateTable() {
		return updateTable;
	}

	@Override
	public String toString() {
		return "Update{" +
				"sql='" + sql + '\'' +
				", updateTable='" + updateTable + '\'' +
				", set=" + set +
				", criteria=" + criteria +
				'}';
	}
}
