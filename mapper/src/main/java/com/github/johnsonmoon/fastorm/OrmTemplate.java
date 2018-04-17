package com.github.johnsonmoon.fastorm;

import com.github.johnsonmoon.fastorm.core.common.DatabaseType;
import com.github.johnsonmoon.fastorm.core.common.JdbcConnector;
import com.github.johnsonmoon.fastorm.core.common.MapperException;
import com.github.johnsonmoon.fastorm.core.meta.ColumnMetaInfo;
import com.github.johnsonmoon.fastorm.core.sql.*;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;
import com.github.johnsonmoon.fastorm.mapper.MappingFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Primary implementation of {@link OrmOperations}.
 * <p>
 * Created by johnsonmoon at 2018/4/11 10:13.
 */
public class OrmTemplate implements OrmOperations {
	private JdbcConnector jdbcConnector;

	public OrmTemplate(OrmFactory ormFactory) {
		this.jdbcConnector = ormFactory.getJdbcConnector();
	}

	@Override
	public boolean tableExists(Class<?> entityClass) {
		TableMetaInfo tableMetaInfo = MappingFactory.getMapping(entityClass).getTableMetaInfo(entityClass);
		QueryResult result = convert(
				jdbcConnector.query(DatabaseType.getTableExistsSqlSentence(tableMetaInfo.getTableName())));
		return result.count() > 0;
	}

	@Override
	public boolean tableExists(String tableName) {
		QueryResult result = convert(jdbcConnector.query(DatabaseType.getTableExistsSqlSentence(tableName)));
		return result.count() > 0;
	}

	@Override
	public boolean dropTable(Class<?> entityClass) {
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean dropTable(String tableName) {
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean createTable(Class<?> entityClass) {
		String sql = MappingFactory.getMapping(entityClass).createTable(entityClass);
		return !jdbcConnector.execute(sql);
	}

	@Override
	public boolean createTable(TableMetaInfo tableMetaInfo) {
		String sql = CreateTable.getSql(tableMetaInfo);
		return !jdbcConnector.execute(sql);
	}

	@Override
	public QueryResult showIndexes(Class<?> entityClass) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public QueryResult showIndexes(String tableName) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public boolean dropIndexes(Class<?> entityClass) {
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean dropIndexes(TableMetaInfo tableMetaInfo) {
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean dropIndexes(String tableName, List<String> indexNames) {
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean dropIndex(String tableName, String indexName) {
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean createIndexes(Class<?> entityClass) {
		List<String> sqls = MappingFactory.getMapping(entityClass).createIndex(entityClass);
		boolean flag = true;
		for (String sql : sqls) {
			flag = flag && !jdbcConnector.execute(sql);
		}
		return flag;
	}

	@Override
	public boolean createIndexes(TableMetaInfo tableMetaInfo) {
		List<String> sqls = CreateIndex.getSql(tableMetaInfo);
		boolean flag = true;
		for (String sql : sqls) {
			flag = flag && !jdbcConnector.execute(sql);
		}
		return flag;
	}

	@Override
	public boolean createIndexes(String tableName, List<String> columnNames) {
		List<String> sqls = CreateIndex.getSql(tableName, columnNames);
		boolean flag = true;
		for (String sql : sqls) {
			flag = flag && !jdbcConnector.execute(sql);
		}
		return flag;
	}

	@Override
	public boolean executeCommand(String sqlCommand) {
		return jdbcConnector.execute(sqlCommand);
	}

	@Override
	public QueryResult query(Query query) {
		return convert(jdbcConnector.query(query.getSql()));
	}

	@Override
	public <T> List<T> queryAll(Class<T> entityClass) {
		String tableName = MappingFactory.getMapping(entityClass).getTableMetaInfo(entityClass).getTableName();
		Query query = Query.selectAll().from(tableName);
		return MappingFactory.getMapping(entityClass).convert(jdbcConnector.query(query.getSql()), entityClass);
	}

	@Override
	public <T> List<T> queryAll(Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> T queryOne(Query query, Class<T> entityClass) {
		List<T> tList = MappingFactory.getMapping(entityClass).convert(jdbcConnector.query(query.getSql()), entityClass);
		return tList.isEmpty() ? null : tList.get(0);
	}

	@Override
	public <T> T queryOne(T t, Class<T> entityClass) {
		String sql = MappingFactory.getMapping(entityClass).query(t, entityClass).getSql();
		List<T> tList = MappingFactory.getMapping(entityClass).convert(jdbcConnector.query(sql), entityClass);
		return tList.isEmpty() ? null : tList.get(0);
	}

	@Override
	public <T> T queryOne(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> List<T> queryList(Query query, Class<T> entityClass) {
		String sql = query.getSql();
		return MappingFactory.getMapping(entityClass).convert(jdbcConnector.query(sql), entityClass);
	}

	@Override
	public <T> List<T> queryList(T t, Class<T> entityClass) {
		String sql = MappingFactory.getMapping(entityClass).query(t, entityClass).getSql();
		return MappingFactory.getMapping(entityClass).convert(jdbcConnector.query(sql), entityClass);
	}

	@Override
	public <T> List<T> queryList(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> T queryById(Object id, Class<T> entityClass) {
		TableMetaInfo tableMetaInfo = MappingFactory.getMapping(entityClass).getTableMetaInfo(entityClass);
		ColumnMetaInfo idColumn = getIdColumnMetaInfo(tableMetaInfo);
		if (idColumn == null)
			throw new MapperException(
					String.format("Unsupported operation: can not find id column(field) for class:[%s]", entityClass.getName()));
		Query query = Query.selectAll()
				.from(tableMetaInfo.getTableName())
				.addWhere(Criteria.where(idColumn.getColumnName()).is(id));
		List<T> tList = MappingFactory.getMapping(entityClass).convert(jdbcConnector.query(query.getSql()), entityClass);
		return (tList == null || tList.isEmpty()) ? null : tList.get(0);
	}

	private ColumnMetaInfo getIdColumnMetaInfo(TableMetaInfo tableMetaInfo) {
		for (ColumnMetaInfo columnMetaInfo : tableMetaInfo.getColumnMetaInfoList()) {
			if (columnMetaInfo.isIdColumn()) {
				return columnMetaInfo;
			}
		}
		return null;
	}

	@Override
	public <T> T queryById(Object id, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public long count(Query query) {
		if (query == null)
			return 0;
		query.setSelect("COUNT(*)");
		String sql = query.getSql();
		QueryResult queryResult = new QueryResult(jdbcConnector.query(sql));
		if (queryResult.count() == 0)
			return 0;
		return Long.parseLong(String.valueOf(queryResult.getColumn(0, 0)));
	}

	@Override
	public <T> long count(T t, Class<T> entityClass) {
		Query query = MappingFactory.getMapping(entityClass).query(t, entityClass);
		query.setSelect("COUNT(*)");
		String sql = query.getSql();
		QueryResult queryResult = new QueryResult(jdbcConnector.query(sql));
		if (queryResult.count() == 0)
			return 0;
		return Long.parseLong(String.valueOf(queryResult.getColumn(0, 0)));
	}

	@Override
	public <T> long count(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public int insert(Insert insert) {
		if (insert == null)
			return 0;
		String sql = insert.getSql();
		return jdbcConnector.update(sql);
	}

	@Override
	public <T> int insert(T t, Class<T> entityClass) {
		String sql = MappingFactory.getMapping(entityClass).insert(t, entityClass).getSql();
		return jdbcConnector.update(sql);
	}

	@Override
	public <T> int insert(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int insert(List<T> tList, Class<T> entityClass) {
		if (tList == null)
			return 0;
		int aff = 0;
		for (T t : tList) {
			String sql = MappingFactory.getMapping(entityClass).insert(t, entityClass).getSql();
			aff += jdbcConnector.update(sql);
		}
		return aff;
	}

	@Override
	public <T> int insert(List<T> tList, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public int update(Update update) {
		if (update == null)
			return 0;
		String sql = update.getSql();
		return jdbcConnector.update(sql);
	}

	@Override
	public int updateFirst(Update update) {
		String table = update.getUpdateTable();
		Criteria criteria = update.getCriteria();
		Query query = Query.selectAll().from(table).addWhere(criteria);
		QueryResult result = new QueryResult(jdbcConnector.query(query.getSql()));
		if (result.count() == 0)
			return 0;
		Criteria criteriaFirst = Criteria.where(1).is(1);
		for (Map.Entry<String, Object> entry : result.getRow(0).entrySet()) {
			if (entry.getValue() == null)
				continue;
			criteriaFirst.and(entry.getKey()).is(entry.getValue());
		}
		update.setCriteria(criteriaFirst);
		String sql = update.getSql();
		return jdbcConnector.update(sql);
	}

	@Override
	public <T> int update(T t, Class<T> entityClass) {
		Update update = MappingFactory.getMapping(entityClass).update(t, entityClass);
		if (update == null)
			return 0;
		String sql = update.getSql();
		return jdbcConnector.update(sql);
	}

	@Override
	public <T> int update(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int updateFirst(T t, Class<T> entityClass) {
		Update update = MappingFactory.getMapping(entityClass).update(t, entityClass);
		if (update == null)
			return 0;
		String table = update.getUpdateTable();
		Criteria criteria = update.getCriteria();
		Query query = Query.selectAll().from(table).addWhere(criteria);
		QueryResult result = new QueryResult(jdbcConnector.query(query.getSql()));
		if (result.count() == 0)
			return 0;
		Criteria criteriaFirst = Criteria.where(1).is(1);
		for (Map.Entry<String, Object> entry : result.getRow(0).entrySet()) {
			if (entry.getValue() == null)
				continue;
			criteriaFirst.and(entry.getKey()).is(entry.getValue());
		}
		update.setCriteria(criteriaFirst);
		String sql = update.getSql();
		return jdbcConnector.update(sql);
	}

	@Override
	public <T> int updateFirst(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public int delete(Delete delete) {
		if (delete == null)
			return 0;
		String sql = delete.getSql();
		return jdbcConnector.update(sql);
	}

	@Override
	public int deleteFirst(Delete delete) {
		String tableName = delete.getDeleteFrom();
		Criteria criteria = delete.getCriteria();
		Query query = Query.selectAll().from(tableName).addWhere(criteria);
		QueryResult result = new QueryResult(jdbcConnector.query(query.getSql()));
		if (result.count() == 0)
			return 0;
		Criteria criteriaFirst = Criteria.where(1).is(1);
		for (Map.Entry<String, Object> entry : result.getRow(0).entrySet()) {
			if (entry.getValue() == null)
				continue;
			criteriaFirst.and(entry.getKey()).is(entry.getValue());
		}
		delete.setCriteria(criteriaFirst);
		String sql = delete.getSql();
		return jdbcConnector.update(sql);
	}

	@Override
	public <T> int delete(T t, Class<T> entityClass) {
		Delete delete = MappingFactory.getMapping(entityClass).delete(t, entityClass);
		if (delete == null) {
			return 0;
		}
		String sql = delete.getSql();
		return jdbcConnector.update(sql);
	}

	@Override
	public <T> int delete(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int deleteFirst(T t, Class<T> entityClass) {
		Delete delete = MappingFactory.getMapping(entityClass).delete(t, entityClass);
		if (delete == null) {
			return 0;
		}
		String tableName = delete.getDeleteFrom();
		Criteria criteria = delete.getCriteria();
		Query query = Query.selectAll().from(tableName).addWhere(criteria);
		QueryResult result = new QueryResult(jdbcConnector.query(query.getSql()));
		if (result.count() == 0)
			return 0;
		Criteria criteriaFirst = Criteria.where(1).is(1);
		for (Map.Entry<String, Object> entry : result.getRow(0).entrySet()) {
			if (entry.getValue() == null)
				continue;
			criteriaFirst.and(entry.getKey()).is(entry.getValue());
		}
		delete.setCriteria(criteriaFirst);
		String sql = delete.getSql();
		return jdbcConnector.update(sql);
	}

	@Override
	public <T> int deleteFirst(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	private QueryResult convert(List<LinkedHashMap<String, Object>> mapList) {
		return new QueryResult(mapList);
	}
}
