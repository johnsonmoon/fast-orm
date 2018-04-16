package com.github.johnsonmoon.fastorm;

import com.github.johnsonmoon.fastorm.core.common.DatabaseType;
import com.github.johnsonmoon.fastorm.core.common.JdbcConnector;
import com.github.johnsonmoon.fastorm.core.sql.*;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;
import com.github.johnsonmoon.fastorm.mapper.MappingFactory;

import java.util.LinkedHashMap;
import java.util.List;

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
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> T queryOne(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> List<T> queryList(Query query, Class<T> entityClass) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> List<T> queryList(T t, Class<T> entityClass) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> List<T> queryList(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> T queryById(Object id, Class<T> entityClass) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> T queryById(Object id, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public long count(Query query) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> long count(T t, Class<T> entityClass) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> long count(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public int insert(Insert insert) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int insert(T t, Class<T> entityClass) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int insert(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int insert(List<T> t, Class<T> entityClass) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int insert(List<T> t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public int update(Update update) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public int updateFirst(Update update) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int update(T t, Class<T> entityClass) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int update(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int updateFirst(T t, Class<T> entityClass) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int updateFirst(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public int delete(Query query) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public int deleteFirst(Query query) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int delete(T t, Class<T> entityClass) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int delete(T t, Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return 0;
	}

	@Override
	public <T> int deleteFirst(T t, Class<T> entityClass) {
		// TODO: 2018/4/12
		return 0;
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
