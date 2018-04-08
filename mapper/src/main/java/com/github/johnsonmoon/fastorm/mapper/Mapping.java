package com.github.johnsonmoon.fastorm.mapper;

/**
 * Object-relation mapping functions define.
 * <p>
 * Created by johnsonmoon at 2018/4/8 10:31.
 */
public interface Mapping {
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
	 * @return create index sentence
	 */
	<T> String createIndex(Class<T> clazz);

	/**
	 * Generate insert sql sentence.
	 *
	 * @param t     insert object instance
	 * @param clazz object type
	 * @return insert sql sentence
	 */
	<T> String insert(T t, Class<T> clazz);

	/**
	 * Generate update sql sentence.
	 *
	 * @param t     update object instance
	 * @param clazz object type
	 * @return update sql sentence
	 */
	<T> String update(T t, Class<T> clazz);

	/**
	 * Generate select sql sentence.
	 *
	 * @param t     query object instance
	 * @param clazz object type
	 * @return select sql sentence
	 */
	<T> T select(T t, Class<T> clazz);
}
