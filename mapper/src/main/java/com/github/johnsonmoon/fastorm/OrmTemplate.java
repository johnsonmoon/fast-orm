package com.github.johnsonmoon.fastorm;

import com.github.johnsonmoon.fastorm.core.sql.Insert;
import com.github.johnsonmoon.fastorm.core.sql.Query;
import com.github.johnsonmoon.fastorm.core.sql.Update;
import com.github.johnsonmoon.fastorm.mapper.meta.TableMetaInfo;

import java.util.List;

/**
 * Primary implementation of {@link OrmOperations}.
 * <p>
 * Created by johnsonmoon at 2018/4/11 10:13.
 */
public class OrmTemplate implements OrmOperations {
	@Override
	public boolean tableExists(Class<?> entityClass) {
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean tableExists(String tableName) {
		// TODO: 2018/4/12
		return false;
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
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean createTable(TableMetaInfo tableMetaInfo) {
		// TODO: 2018/4/12
		return false;
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
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean createIndexes(TableMetaInfo tableMetaInfo) {
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean createIndexes(String tableName, List<String> columnNames) {
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public boolean executeCommand(String sqlCommand) {
		// TODO: 2018/4/12
		return false;
	}

	@Override
	public QueryResult query(Query query) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> List<T> queryAll(Class<T> entityClass) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> List<T> queryAll(Class<T> entityClass, String tableName) {
		// TODO: 2018/4/12
		return null;
	}

	@Override
	public <T> T queryOne(Query query, Class<T> entityClass) {
		// TODO: 2018/4/12
		return null;
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
}
