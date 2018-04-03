package com.github.johnsonmoon.fastorm.core.sql;

/**
 * 查询
 * <p>
 * Created by xuyh at 2017/9/20 15:09.
 */
public class Query {
	private String sql;
	private Criteria criteria;
	private Order order;
	private String groupBy;

	private Query() {
		sql = "SELECT ";
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
		if (order != null)
			result += (order.getOrder() + " ");
		if (groupBy != null)
			result += ("GROUP BY " + groupBy + " ");
		return result.trim();
	}

	/**
	 * 查询所有字段
	 *
	 * @return {@link Query}
	 */
	public static Query selectAll() {
		Query query = new Query();
		query.sql += "* ";
		return query;
	}

	/**
	 * 查询部分字段
	 * <pre>
	 *  参数fields可以是"field"格式,也可以是"field as alias格式"
	 *
	 *  举例
	 *  1. select("name", ""email);
	 *
	 *  2. select("name as A", "email as B");
	 *
	 * </pre>
	 *
	 * @param fields 需要查询的字段
	 * @return {@link Query}
	 */
	public static Query select(String... fields) {
		if (fields == null || fields.length == 0)
			return new Query();
		//这里的fields可以是单独fields,也可以是fields as alias
		Query query = new Query();
		StringBuilder fieldsStr = new StringBuilder();
		for (String field : fields) {
			fieldsStr.append(field);
			fieldsStr.append(", ");
		}
		String fieldsS = fieldsStr.substring(0, fieldsStr.length() - 2);
		query.sql += (fieldsS + " ");
		return query;
	}

	/**
	 * 查询唯一不同的值
	 * <p>
	 * <pre>
	 *  参数fields可以是"field"格式,也可以是"field as alias格式"
	 *
	 *  举例
	 *  1. select("name", ""email);
	 *
	 *  2. select("name as A", "email as B");
	 * </pre>
	 *
	 * @param fields 需要查询的字段
	 * @return {@link Query}
	 */
	public static Query selectDistinct(String... fields) {
		if (fields == null || fields.length == 0)
			return new Query();
		Query query = new Query();
		StringBuilder fieldsStr = new StringBuilder();
		for (String field : fields) {
			fieldsStr.append(field);
			fieldsStr.append(", ");
		}
		String field = fieldsStr.substring(0, fieldsStr.length() - 2);
		query.sql += ("DISTINCT " + field + " ");
		return query;
	}

	/**
	 * 从表中查询
	 * <pre>
	 *  参数tables可以是"table"格式,也可以是"table as alias格式,也可以是"table alias格式"
	 *
	 *  举例：
	 *  1. from("table1", ""table2);
	 *
	 *  2. from("table1 as t1", "table2 as t2");
	 *
	 *  3. from("table t");
	 * </pre>
	 *
	 * @param tables 需要查询的表
	 * @return {@link Query}
	 */
	public Query from(String... tables) {
		if (tables == null || tables.length == 0)
			return this;
		StringBuilder tableStr = new StringBuilder();
		for (String table : tables) {
			tableStr.append(table);
			tableStr.append(", ");
		}
		String table = tableStr.substring(0, tableStr.length() - 2);
		this.sql += ("FROM " + table + " ");
		return this;
	}

	/**
	 * 从表中查询
	 * <pre>
	 *  参数table可以是"table"格式,也可以是"table as alias格式,也可以是"table alias格式"
	 *
	 *  举例：
	 *  1. from("table");
	 *
	 *  2. from("table as t");
	 *
	 *  3. from("table t");
	 * </pre>
	 *
	 * @param table table name
	 * @return {@link Query}
	 */
	public Query from(String table) {
		if (table == null || table.isEmpty())
			return this;
		this.sql += ("FROM " + table + " ");
		return this;
	}

	/**
	 * INNER JOIN另外一张表
	 * <p>
	 * <pre>
	 *  参数table可以是"table"格式,也可以是"table as alias格式,也可以是"table alias格式"
	 *
	 *  举例：
	 *  1. innerJoin("table");
	 *
	 *  2. innerJoin("table as t");
	 *
	 *  3. innerJoin("table t");
	 * </pre>
	 *
	 * @param table table name
	 * @return {@link Query}
	 */
	public Query innerJoin(String table) {
		if (table == null || table.isEmpty())
			return this;
		this.sql += ("INNER JOIN " + table + " ");
		return this;
	}

	/**
	 * LEFT JOIN另外一张表
	 * <p>
	 * <pre>
	 *  参数table可以是"table"格式,也可以是"table as alias格式,也可以是"table alias格式"
	 *
	 *  举例：
	 *  1. leftJoin("table");
	 *
	 *  2. leftJoin("table as t");
	 *
	 *  3. leftJoin("table t");
	 * </pre>
	 *
	 * @param table table name
	 * @return {@link Query}
	 */
	public Query leftJoin(String table) {
		if (table == null || table.isEmpty())
			return this;
		this.sql += ("LEFT JOIN " + table + " ");
		return this;
	}

	/**
	 * RIGHT JOIN另外一张表
	 * <p>
	 * <pre>
	 *  参数table可以是"table"格式,也可以是"table as alias格式,也可以是"table alias格式"
	 *
	 *  举例：
	 *  1. rightJoin("table");
	 *
	 *  2. rightJoin("table as t");
	 *
	 *  3. rightJoin("table t");
	 * </pre>
	 *
	 * @param table table name
	 * @return {@link Query}
	 */
	public Query rightJoin(String table) {
		if (table == null || table.isEmpty())
			return this;
		this.sql += ("RIGHT JOIN " + table + " ");
		return this;
	}

	/**
	 * FULL JOIN另外一张表
	 * <p>
	 * <pre>
	 *  参数table可以是"table"格式,也可以是"table as alias格式,也可以是"table alias格式"
	 *
	 *  举例：
	 *  1. fullOuterJoin("table");
	 *
	 *  2. fullOuterJoin("table as t");
	 *
	 *  3. fullOuterJoin("table t");
	 * </pre>
	 *
	 * @param table table name
	 * @return {@link Query}
	 */
	public Query fullOuterJoin(String table) {
		if (table == null || table.isEmpty())
			return this;
		this.sql += ("FULL OUTER JOIN " + table + " ");
		return this;
	}

	/**
	 * 联表之后on操作
	 * <p>
	 * <pre>
	 *  select * from table1 t1 left join table2 on t1.name = t2.name where t1.like = 0
	 * </pre>
	 *
	 * @param fieldFormal 目标表字段
	 * @param fieldBehind 联表字段
	 * @return {@link Query}
	 */
	public Query on(String fieldFormal, String fieldBehind) {
		if (fieldFormal == null || fieldFormal.isEmpty()
				|| fieldBehind == null || fieldBehind.isEmpty())
			return this;
		this.sql += ("ON " + fieldFormal + " = " + fieldBehind + " ");
		return this;
	}

	/**
	 * 添加查询条件
	 *
	 * @param criteria 查询条件
	 * @return {@link Query}
	 */
	public Query addWhere(Criteria criteria) {
		this.criteria = criteria;
		return this;
	}

	/**
	 * 添加结果集排序
	 *
	 * @param order {@link Order}
	 * @return {@link Query}
	 */
	public Query addOrderBy(Order order) {
		this.order = order;
		return this;
	}

	/**
	 * 添加分组字段
	 *
	 * @param field group field
	 * @return {@link Query}
	 */
	public Query addGroupBy(String field) {
		this.groupBy = field;
		return this;
	}

	@Override
	public String toString() {
		return "Query [sql=" + sql + ", criteria=" + criteria + ", order=" + order + ", groupBy=" + groupBy + "]";
	}
}
