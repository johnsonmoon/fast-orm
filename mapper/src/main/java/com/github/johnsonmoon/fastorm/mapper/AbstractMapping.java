package com.github.johnsonmoon.fastorm.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.johnsonmoon.fastorm.core.common.MapperException;
import com.github.johnsonmoon.fastorm.core.common.ObjectConverter;
import com.github.johnsonmoon.fastorm.core.common.QueryResult;
import com.github.johnsonmoon.fastorm.core.meta.ColumnMetaInfo;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;
import com.github.johnsonmoon.fastorm.core.sql.CreateIndex;
import com.github.johnsonmoon.fastorm.core.sql.CreateTable;
import com.github.johnsonmoon.fastorm.core.sql.Criteria;
import com.github.johnsonmoon.fastorm.core.sql.Delete;
import com.github.johnsonmoon.fastorm.core.sql.Insert;
import com.github.johnsonmoon.fastorm.core.sql.Query;
import com.github.johnsonmoon.fastorm.core.sql.Update;
import com.github.johnsonmoon.fastorm.core.util.AnnotationUtils;
import com.github.johnsonmoon.fastorm.core.util.CollectionUtils;
import com.github.johnsonmoon.fastorm.core.util.RandomUtils;
import com.github.johnsonmoon.fastorm.core.util.ReflectionUtils;
import com.github.johnsonmoon.fastorm.core.util.ValueUtils;

public abstract class AbstractMapping implements Mapping {
	@Override
	public abstract <T> TableMetaInfo getTableMetaInfo(Class<T> clazz);

	@Override
	public <T> List<T> convert(QueryResult queryResult, Class<T> clazz) {
		if (queryResult == null || queryResult.isEmpty()) {
			return null;
		}
		if (clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
		TableMetaInfo tableMetaInfo = this.getTableMetaInfo(clazz);
		if (tableMetaInfo == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate table meta info from class %s.", clazz.getName()));
		return ObjectConverter.convert(queryResult.getResultMapList(), clazz, tableMetaInfo);
	}

	@Override
	public <T> List<T> convert(List<LinkedHashMap<String, Object>> result, Class<T> clazz) {
		if (result == null || result.isEmpty())
			return null;
		if (clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
		TableMetaInfo tableMetaInfo = this.getTableMetaInfo(clazz);
		if (tableMetaInfo == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate table meta info from class %s.", clazz.getName()));
		return ObjectConverter.convert(result, clazz, tableMetaInfo);
	}

	@Override
	public <T> String createTable(Class<T> clazz) {
		if (clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
		TableMetaInfo tableMetaInfo = this.getTableMetaInfo(clazz);
		if (tableMetaInfo == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate table meta info from class %s.", clazz.getName()));
		return CreateTable.getSql(tableMetaInfo);
	}

	@Override
	public <T> List<String> createIndex(Class<T> clazz) {
		if (clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
		TableMetaInfo tableMetaInfo = this.getTableMetaInfo(clazz);
		if (tableMetaInfo == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate table meta info from class %s.", clazz.getName()));
		return CreateIndex.getSql(tableMetaInfo);
	}

	@Override
	public <T> Insert insert(T t, Class<T> clazz) {
		if (t == null || clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
		TableMetaInfo tableMetaInfo = this.getTableMetaInfo(clazz);
		if (tableMetaInfo == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate table meta info from class %s.", clazz.getName()));
		List<String> columns = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		for (ColumnMetaInfo columnMetaInfo : tableMetaInfo.getColumnMetaInfoList()) {
			if (columnMetaInfo.isIdColumn()) {
				if (columnMetaInfo.isAutoIncrement()) {
					continue;
				}
				Object value = ReflectionUtils.getFieldValue(t, columnMetaInfo.getFieldName());
				if (value == null) {
					value = RandomUtils.getRandomNumberString(ValueUtils.getLengthOfColumnType(columnMetaInfo.getType()));
				}
				columns.add(columnMetaInfo.getColumnName());
				values.add(value);
			} else if (columnMetaInfo.isNotNull()) {
				Object value = ReflectionUtils.getFieldValue(t, columnMetaInfo.getFieldName());
				if (value == null)
					throw new MapperException(
							String.format("Unsupported operation: field %s value must not be null.", columnMetaInfo.getFieldName()));
				columns.add(columnMetaInfo.getColumnName());
				values.add(value);
			} else if (!columnMetaInfo.isNotNull() && !columnMetaInfo.getDefaultValue().isEmpty()) {
				Object value = ReflectionUtils.getFieldValue(t, columnMetaInfo.getFieldName());
				if (value == null)
					value = ValueUtils.parseValue(columnMetaInfo.getDefaultValue(),
							ReflectionUtils.getClassByName(columnMetaInfo.getFieldType()));
				columns.add(columnMetaInfo.getColumnName());
				values.add(value);
			} else {
				Object value = ReflectionUtils.getFieldValue(t, columnMetaInfo.getFieldName());
				if (value == null)
					continue;
				columns.add(columnMetaInfo.getColumnName());
				values.add(value);
			}
		}
		return Insert.insertInto(tableMetaInfo.getTableName())
				.fields(CollectionUtils.strListToArray(columns))
				.values(CollectionUtils.objListToArray(values));
	}

	@Override
	public <T> Update update(T t, Class<T> clazz) {
		if (t == null || clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
		TableMetaInfo tableMetaInfo = this.getTableMetaInfo(clazz);
		if (tableMetaInfo == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate table meta info from class %s.", clazz.getName()));
		List<String> whereKeys = new ArrayList<>();
		List<Object> whereValues = new ArrayList<>();
		ColumnField idColumn = getIdColumn(tableMetaInfo.getColumnMetaInfoList());
		List<ColumnField> primaryKeyColumnList = getPrimaryKeyColumns(tableMetaInfo.getColumnMetaInfoList());
		if (idColumn == null && primaryKeyColumnList.isEmpty()) {
			throw new MapperException(String.format(
					"Unsupported operation: can not locate @Id field or @PrimaryKey fields from class %s.", clazz.getName()));
		}
		if (idColumn != null) {
			whereKeys.add(idColumn.columnName);
			whereValues.add(checkNotNull(ReflectionUtils.getFieldValue(t, idColumn.fieldName)));
		} else {
			for (ColumnField columnField : primaryKeyColumnList) {
				whereKeys.add(columnField.columnName);
				whereValues.add(checkNotNull(ReflectionUtils.getFieldValue(t, columnField.fieldName)));
			}
		}
		List<String> updateKeys = new ArrayList<>();
		List<Object> updateValues = new ArrayList<>();
		for (ColumnMetaInfo columnMetaInfo : tableMetaInfo.getColumnMetaInfoList()) {
			if (!columnMetaInfo.isIdColumn() && !columnMetaInfo.isPrimaryKey()) {
				Object value = ReflectionUtils.getFieldValue(t, columnMetaInfo.getFieldName());
				if (value != null) {
					updateKeys.add(columnMetaInfo.getColumnName());
					updateValues.add(value);
				}
			}
		}
		if (updateKeys.isEmpty()) {
			throw new MapperException("Unsupported operation: updating field value count is 0, no updating operated");
		}
		Criteria criteria = Criteria.where(whereKeys.get(0)).is(whereValues.get(0));
		for (int w = 1; w < whereKeys.size(); w++) {
			criteria.and(whereKeys.get(w)).is(whereValues.get(w));
		}
		Update update = Update.update(tableMetaInfo.getTableName()).addWhere(criteria);
		for (int u = 0; u < updateKeys.size(); u++) {
			update.set(updateKeys.get(u), updateValues.get(u));
		}
		return update;
	}

	private Object checkNotNull(Object value) {
		if (value == null)
			throw new MapperException("Unsupported operation: @Id field or @PrimaryKey fields value must not be null.");
		return value;
	}

	private ColumnField getIdColumn(List<ColumnMetaInfo> columnMetaInfoList) {
		for (ColumnMetaInfo columnMetaInfo : columnMetaInfoList) {
			if (columnMetaInfo.isIdColumn()) {
				return new ColumnField(columnMetaInfo.getColumnName(), columnMetaInfo.getFieldName());
			}
		}
		return null;
	}

	private List<ColumnField> getPrimaryKeyColumns(List<ColumnMetaInfo> columnMetaInfoList) {
		List<ColumnField> names = new ArrayList<>();
		for (ColumnMetaInfo columnMetaInfo : columnMetaInfoList) {
			if (columnMetaInfo.isPrimaryKey()) {
				names.add(new ColumnField(columnMetaInfo.getColumnName(), columnMetaInfo.getFieldName()));
			}
		}
		return names;
	}

	private class ColumnField {
		private String columnName;
		private String fieldName;

		private ColumnField(String columnName, String fieldName) {
			this.columnName = columnName;
			this.fieldName = fieldName;
		}
	}

	@Override
	public <T> Query query(T t, Class<T> clazz) {
		if (t == null || clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
		TableMetaInfo tableMetaInfo = this.getTableMetaInfo(clazz);
		if (tableMetaInfo == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate table meta info from class %s.", clazz.getName()));
		List<String> whereKeys = new ArrayList<>();
		List<Object> whereValues = new ArrayList<>();
		for (ColumnMetaInfo columnMetaInfo : tableMetaInfo.getColumnMetaInfoList()) {
			Object value = ReflectionUtils.getFieldValue(t, columnMetaInfo.getFieldName());
			if (value != null && !ValueUtils.equalsZero(value)) {
				whereKeys.add(columnMetaInfo.getColumnName());
				whereValues.add(value);
			}
		}
		Query query = Query.selectAll().from(tableMetaInfo.getTableName());
		if (whereKeys.isEmpty()) {
			return query;
		} else {
			Criteria criteria = Criteria.where(whereKeys.get(0)).is(whereValues.get(0));
			for (int w = 1; w < whereKeys.size(); w++) {
				criteria.and(whereKeys.get(w)).is(whereValues.get(w));
			}
			return query.addWhere(criteria);
		}
	}

	@Override
	public <T> Delete delete(T t, Class<T> clazz) {
		if (t == null || clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
		TableMetaInfo tableMetaInfo = this.getTableMetaInfo(clazz);
		if (tableMetaInfo == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate table meta info from class %s.", clazz.getName()));
		Query query = this.query(t, clazz);
		if (query == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate criteria from object %s class %s.", t,
							clazz.getName()));
		Criteria criteria = query.getCriteria();
		return Delete.deleteFrom(tableMetaInfo.getTableName()).addWhere(criteria);
	}
}
