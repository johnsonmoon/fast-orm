package com.github.johnsonmoon.fastorm.core.sql;

import com.github.johnsonmoon.fastorm.core.util.StringUtils;

/**
 * Created by xuyh at 2017/9/22 18:28.
 */
public class Delete {
	private String deleteFrom;
	private String sql;
	private Criteria criteria;

	public Delete() {
		this.sql = "DELETE FROM ";
	}

	/**
	 * 获取最终的sql语句
	 *
	 * @return sql sentence
	 */
	public String getSql() {
		String result = sql;
		result += (StringUtils.getSureName(deleteFrom) + " ");
		if (criteria != null)
			result += (criteria.getCriteria() + " ");
		return result.trim();
	}

	/**
	 * 删除
	 *
	 * @param table 表名
	 * @return {@link Delete}
	 */
	public static Delete deleteFrom(String table) {
		Delete delete = new Delete();
		delete.deleteFrom = table;
		return delete;
	}

	/**
	 * 添加查询条件
	 *
	 * @param criteria 查询条件
	 * @return {@link Delete}
	 */
	public Delete addWhere(Criteria criteria) {
		this.criteria = criteria;
		return this;
	}

	/**
	 * Set delete table from.
	 *
	 * @param table table name which is to be deleted data.
	 */
	public Delete setDeleteFrom(String table) {
		this.deleteFrom = table;
		return this;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public Delete setCriteria(Criteria criteria) {
		this.criteria = criteria;
		return this;
	}

	public String getDeleteFrom() {
		return deleteFrom;
	}

	@Override
	public String toString() {
		return "Delete{" +
				"deleteFrom='" + deleteFrom + '\'' +
				", sql='" + sql + '\'' +
				", criteria=" + criteria +
				'}';
	}
}
