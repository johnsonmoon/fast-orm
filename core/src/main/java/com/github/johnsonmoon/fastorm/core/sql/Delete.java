package com.github.johnsonmoon.fastorm.core.sql;

/**
 * Created by xuyh at 2017/9/22 18:28.
 */
public class Delete {
	private String sql;
	private Criteria criteria;

	private Delete() {
		this.sql = "DELETE FROM ";
	}

	/**
	 * 获取最终的sql语句
	 *
	 * @return sql sentence
	 */
	public String getSql() {
		String result = sql;
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
		delete.sql += (table + " ");
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

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public String toString() {
		return "Delete [sql=" + sql + ", criteria=" + criteria + "]";
	}
}
