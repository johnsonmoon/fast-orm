package com.github.johnsonmoon.fastorm.mapper;

import com.github.johnsonmoon.fastorm.QueryResult;
import com.github.johnsonmoon.fastorm.core.sql.Delete;
import com.github.johnsonmoon.fastorm.core.sql.Insert;
import com.github.johnsonmoon.fastorm.core.sql.Query;
import com.github.johnsonmoon.fastorm.core.sql.Update;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Object-relation mapping functions define.
 * <p>
 * Created by johnsonmoon at 2018/4/8 10:31.
 */
public interface Mapping {
	/**
	 * Get table meta information by type information.
	 *
	 * @param clazz type
	 * @return {@link TableMetaInfo}
	 */
	<T> TableMetaInfo getTableMetaInfo(Class<T> clazz);

	/**
	 * Convert query result into object defined by type ${clazz}.
	 *
	 * @param queryResult query result {@link QueryResult}
	 * @param clazz       type information
	 * @return converted object list
	 */
	<T> List<T> convert(QueryResult queryResult, Class<T> clazz);

	/**
	 * Convert result map list into object defined by type ${clazz}.
	 *
	 * @param result query result map list
	 * @param clazz  type information
	 * @return converted object list
	 */
	<T> List<T> convert(List<LinkedHashMap<String, Object>> result, Class<T> clazz);

	/**
	 * Generate create table sentence.
	 *
	 * @param clazz object type
	 * @return create sql sentence
	 */
	<T> String createTable(Class<T> clazz);

	/**
	 * Generate create index sentence.
	 *
	 * @param clazz object type
	 * @return create index sentence list
	 */
	<T> List<String> createIndex(Class<T> clazz);

	/**
	 * Generate insert object.
	 *
	 * @param t     insert object instance
	 * @param clazz object type
	 * @return {@link Insert}
	 */
	<T> Insert insert(T t, Class<T> clazz);

	/**
	 * Generate update object.
	 *
	 * @param t     update object instance
	 * @param clazz object type
	 * @return {@link Update}
	 */
	<T> Update update(T t, Class<T> clazz);

	/**
	 * Generate query object.
	 *
	 * @param t     query object instance
	 * @param clazz object type
	 * @return {@link Query}
	 */
	<T> Query query(T t, Class<T> clazz);

	/**
	 * Generate delete object.
	 *
	 * @param t     delete object instance
	 * @param clazz object type
	 * @return {@link Delete}
	 */
	<T> Delete delete(T t, Class<T> clazz);
}
