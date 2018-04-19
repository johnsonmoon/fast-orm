package com.github.johnsonmoon.fastorm;

import com.github.johnsonmoon.fastorm.core.common.QueryResult;
import com.github.johnsonmoon.fastorm.core.sql.Delete;
import com.github.johnsonmoon.fastorm.core.sql.Insert;
import com.github.johnsonmoon.fastorm.core.sql.Query;
import com.github.johnsonmoon.fastorm.core.sql.Update;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;

import java.util.List;

/**
 * Interface that specifies a basic set of MongoDB operations. Implemented by {@link OrmTemplate}. Not often used.
 * <p>
 * Created by johnsonmoon at 2018/4/11 10:19.
 */
public interface OrmOperations {
	/**
	 * Get table meta information.
	 *
	 * @param entityClass  class that determines the structure of the table
	 * @return {@link TableMetaInfo}
	 */
	TableMetaInfo getTableMetaInfo(Class<?> entityClass);

	/**
	 * Check to see if a table with a name indicated by the entity class exists.
	 *
	 * @param entityClass class that determines the name of the table
	 * @return true if a table with the given name is found, false otherwise.
	 */
	boolean tableExists(Class<?> entityClass);

	/**
	 * Check to see if a table with a given name exists.
	 *
	 * @param tableName name of the table
	 * @return true if a table with the given name is found, false otherwise.
	 */
	boolean tableExists(String tableName);

	/**
	 * Drop the table with the name indicated by the entity class.
	 *
	 * @param entityClass class that determines the table to drop/delete.
	 * @return true/false
	 */
	boolean dropTable(Class<?> entityClass);

	/**
	 * Drop the table with the given name.
	 *
	 * @param tableName name of the table
	 * @return true/false
	 */
	boolean dropTable(String tableName);

	/**
	 * Create a table with a name based on the provided entity class.
	 *
	 * @param entityClass class that determines the table to create
	 * @return true/false
	 */
	boolean createTable(Class<?> entityClass);

	/**
	 * Create a table with a name based on the provided meta data of table.
	 *
	 * @param tableMetaInfo meta data of table
	 * @return true/false
	 */
	boolean createTable(TableMetaInfo tableMetaInfo);

	/**
	 * Show indexes information of a table with a name based on the provided class.
	 *
	 * @param entityClass class that determines the indexes.
	 * @return {@link QueryResult}
	 */
	QueryResult showIndexes(Class<?> entityClass);

	/**
	 * Show indexes information of a table with tableName.
	 *
	 * @param tableName name of the table
	 * @return {@link QueryResult}
	 */
	QueryResult showIndexes(String tableName);

	/**
	 * Drop indexes with a table name based on the provided entity class.
	 *
	 * @param entityClass class that determines the indexes.
	 * @return true/false
	 */
	boolean dropIndexes(Class<?> entityClass);

	/**
	 * Drop indexes with a table name based on the provided meta info of table.
	 *
	 * @param tableMetaInfo meta info of table
	 * @return true/false
	 */
	boolean dropIndexes(TableMetaInfo tableMetaInfo);

	/**
	 * Drop indexes with a table name and indexes names.
	 *
	 * @param tableName  name of table
	 * @param indexNames names of indexes
	 * @return true/false
	 */
	boolean dropIndexes(String tableName, List<String> indexNames);

	/**
	 * Drop index with a table name and index name.
	 *
	 * @param tableName name of a table
	 * @param indexName name of an index
	 * @return true/false
	 */
	boolean dropIndex(String tableName, String indexName);

	/**
	 * Create indexes with a table name based on the provided entity class.
	 *
	 * @param entityClass class that determines the indexes.
	 * @return true/false
	 */
	boolean createIndexes(Class<?> entityClass);

	/**
	 * Create indexes with a table name based on the provided meta info of table.
	 *
	 * @param tableMetaInfo meta info of table
	 * @return true/false
	 */
	boolean createIndexes(TableMetaInfo tableMetaInfo);

	/**
	 * Create indexes with table of name tableName and columnNames.
	 *
	 * @param tableName   name of the table
	 * @param columnNames names of indexes
	 * @return true/false
	 */
	boolean createIndexes(String tableName, List<String> columnNames);

	/**
	 * Execute sql command.
	 *
	 * @param sqlCommand sql command sentence
	 * @return true if the first result is a ResultSet object; false if it is an update count or there are no results
	 */
	boolean executeCommand(String sqlCommand);

	/**
	 * Query for result from the query object.
	 *
	 * @param query {@link Query}
	 * @return {@link QueryResult}
	 */
	QueryResult query(Query query);

	/**
	 * Query all results from the table name based on the provided entity class.
	 *
	 * @param entityClass class that determines the table
	 * @return the converted object list
	 */
	<T> List<T> queryAll(Class<T> entityClass);

	/**
	 * Query all results from the table name ${tableName}, convert result into the given
	 * type based on the provided entity class.
	 *
	 * @param entityClass class that determines type to convert results.
	 * @param tableName   name of the table
	 * @return the converted object list
	 */
	<T> List<T> queryAll(Class<T> entityClass, String tableName);

	/**
	 * Query one result from the query object, convert result into the given
	 * type based on the provided entity class.
	 *
	 * @param query       {@link Query}
	 * @param entityClass class that determines type to convert result.
	 * @return the converted object
	 */
	<T> T queryOne(Query query, Class<T> entityClass);

	/**
	 * Query one result from the ${t} object condition, convert result into the given
	 * type based on the provided entity class.
	 *
	 * @param t           query condition object
	 * @param entityClass class that determines type to convert result.
	 * @return the converted object
	 */
	<T> T queryOne(T t, Class<T> entityClass);

	/**
	 * Query one result by the ${t} object condition from the
	 * table name ${tableName}, convert result into the given
	 * type based on the provided entity class.
	 *
	 * @param t           query condition object
	 * @param entityClass class that determines type to convert result.
	 * @param tableName   name of the table
	 * @return the converted object
	 */
	<T> T queryOne(T t, Class<T> entityClass, String tableName);

	/**
	 * Query result list from the query object, convert result into the given
	 * type based on the provided entity class.
	 *
	 * @param query       {@link Query}
	 * @param entityClass class that determines type to convert result.
	 * @return the converted object list
	 */
	<T> List<T> queryList(Query query, Class<T> entityClass);

	/**
	 * Query result list from the ${t} object condition, convert result into the given
	 * type based on the provided entity class.
	 *
	 * @param t           query condition object
	 * @param entityClass class that determines type to convert result.
	 * @return the converted object list
	 */
	<T> List<T> queryList(T t, Class<T> entityClass);

	/**
	 * Query result list by the ${t} object condition from the
	 * table name ${tableName}, convert result into the given
	 * type based on the provided entity class.
	 *
	 * @param t           query condition object
	 * @param entityClass class that determines type to convert result.
	 * @param tableName   name of the table
	 * @return the converted object list
	 */
	<T> List<T> queryList(T t, Class<T> entityClass, String tableName);

	/**
	 * Query result by id based on
	 * {@link com.github.johnsonmoon.fastorm.core.annotation.Id}
	 * annotation declared in given entity class, convert result into the given
	 * type based on the provided entity class.
	 *
	 * @param id          id
	 * @param entityClass class that determines type to convert result.
	 * @return the converted object
	 */
	<T> T queryById(Object id, Class<T> entityClass);

	/**
	 * Query result by id based on
	 * {@link com.github.johnsonmoon.fastorm.core.annotation.Id}
	 * annotation declared in given entity class
	 * from table name of ${tableName}, convert result into the given
	 * type based on the provided entity class.
	 *
	 * @param id          id
	 * @param entityClass class that determines type to convert result.
	 * @return the converted object
	 */
	<T> T queryById(Object id, Class<T> entityClass, String tableName);

	/**
	 * Query result count by the query object.
	 *
	 * @param query {@link Query}
	 * @return result count
	 */
	long count(Query query);

	/**
	 * Query result count from the ${t} object condition,
	 * from the table name based on the provided entity class.
	 *
	 * @param t           query condition object
	 * @param entityClass class that determines the table name.
	 * @return result count
	 */
	<T> long count(T t, Class<T> entityClass);

	/**
	 * Query result count from the ${t} object condition,
	 * from the table name ${tableName}.
	 *
	 * @param t           query condition object
	 * @param entityClass class that determines the table name.
	 * @param tableName   name of the table
	 * @return result count
	 */
	<T> long count(T t, Class<T> entityClass, String tableName);

	/**
	 * Insert data by the insert object.
	 *
	 * @param insert {@link Insert}
	 * @return affected rows
	 */
	int insert(Insert insert);

	/**
	 * Insert data by the object into
	 * the table name based on the provided entity class.
	 *
	 * @param t           entity object instance
	 * @param entityClass class that determines the table
	 * @return affected rows
	 */
	<T> int insert(T t, Class<T> entityClass);

	/**
	 * Insert data by the object into
	 * the table name ${tableName}.
	 *
	 * @param t           entity object instance
	 * @param entityClass class that determines the table structure
	 * @param tableName   name of the table
	 * @return affected rows
	 */
	<T> int insert(T t, Class<T> entityClass, String tableName);

	/**
	 * Insert data by the object list into
	 * the table name based on the provided entity class.
	 *
	 * @param tList           entity object instance
	 * @param entityClass class that determines the table name
	 * @return affected rows
	 */
	<T> int insert(List<T> tList, Class<T> entityClass);

	/**
	 * Insert data by the object list into
	 * the table name ${tableName}.
	 *
	 * @param tList           entity object instance
	 * @param entityClass class that determines the table structure
	 * @param tableName   name of the table
	 * @return affected rows
	 */
	<T> int insert(List<T> tList, Class<T> entityClass, String tableName);

	/**
	 * Update multi by update object.
	 *
	 * @param update {@link Update}
	 * @return affected rows
	 */
	int update(Update update);

	/**
	 * Update first row by update object.
	 *
	 * @param update {@link Update}
	 * @return affected rows
	 */
	int updateFirst(Update update);

	/**
	 * Update multi by the ${t} object condition from
	 * the table name based on the provided entity class.
	 *
	 * @param t           update condition object
	 * @param entityClass class that determines the table name.
	 * @return affected rows
	 */
	<T> int update(T t, Class<T> entityClass);

	/**
	 * Update multi by the ${t} object condition from
	 * the table name ${tableName}.
	 *
	 * @param t           update condition object
	 * @param entityClass class that determines the table structure.
	 * @param tableName   name of the table
	 * @return affected rows
	 */
	<T> int update(T t, Class<T> entityClass, String tableName);

	/**
	 * Update first by the ${t} object condition from
	 * the table name based on the provided entity class.
	 *
	 * @param t           update condition object
	 * @param entityClass class that determines the table name.
	 * @return affected rows
	 */
	<T> int updateFirst(T t, Class<T> entityClass);

	/**
	 * Update first by the ${t} object condition from
	 * the table name ${tableName}.
	 *
	 * @param t           update condition object
	 * @param entityClass class that determines the table structure.
	 * @param tableName   name of the table
	 * @return affected rows
	 */
	<T> int updateFirst(T t, Class<T> entityClass, String tableName);

	/**
	 * Delete multi by query object.
	 *
	 * @param delete {@link Delete}
	 * @return affected rows
	 */
	int delete(Delete delete);

	/**
	 * Delete first by query object.
	 *
	 * @param delete {@link Delete}
	 * @return affected rows
	 */
	int deleteFirst(Delete delete);

	/**
	 * Delete multi by ${t} object condition from
	 * the table name based on the provided entity class.
	 *
	 * @param t           query condition object
	 * @param entityClass class that determines the table name.
	 * @return affected rows
	 */
	<T> int delete(T t, Class<T> entityClass);

	/**
	 * Delete multi by ${t} object condition from
	 * the table name ${tableName}.
	 *
	 * @param t           query condition object
	 * @param entityClass class that determines the table structure
	 * @param tableName   name of the table
	 * @return affected rows
	 */
	<T> int delete(T t, Class<T> entityClass, String tableName);

	/**
	 * Delete multi by ${t} object condition from
	 * the table name based on the provided entity class.
	 *
	 * @param t           query condition object
	 * @param entityClass class that determines the table name
	 * @return affected rows
	 */
	<T> int deleteFirst(T t, Class<T> entityClass);

	/**
	 * Delete multi by ${t} object condition from
	 * the table name ${tableName}.
	 *
	 * @param t           query condition object
	 * @param entityClass class that determines the table structure
	 * @param tableName   name of the table
	 * @return affected rows
	 */
	<T> int deleteFirst(T t, Class<T> entityClass, String tableName);
}
