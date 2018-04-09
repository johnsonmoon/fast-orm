package com.github.johnsonmoon.fastorm.mapper;

import com.github.johnsonmoon.fastorm.core.annotation.Column;
import com.github.johnsonmoon.fastorm.core.annotation.ForeignKey;
import com.github.johnsonmoon.fastorm.core.annotation.Table;
import com.github.johnsonmoon.fastorm.core.sql.Insert;
import com.github.johnsonmoon.fastorm.core.util.RandomUtils;
import com.github.johnsonmoon.fastorm.mapper.common.ColumnMetaInfo;
import com.github.johnsonmoon.fastorm.mapper.common.MapperException;
import com.github.johnsonmoon.fastorm.mapper.common.TableMetaInfo;
import com.github.johnsonmoon.fastorm.mapper.util.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object - Relation mapping with annotations.
 * <p>
 * <pre>
 *     Annotations:
 *     {@link com.github.johnsonmoon.fastorm.core.annotation.Table}
 *     {@link com.github.johnsonmoon.fastorm.core.annotation.Column}
 *     {@link com.github.johnsonmoon.fastorm.core.annotation.Id}
 *     {@link com.github.johnsonmoon.fastorm.core.annotation.Indexed}
 *     {@link com.github.johnsonmoon.fastorm.core.annotation.PrimaryKey}
 *     {@link com.github.johnsonmoon.fastorm.core.annotation.ForeignKey}
 * </pre>
 * Created by johnsonmoon at 2018/4/8 10:14.
 */
public class AnnotationMapping implements Mapping {
	private static Map<String, TableMetaInfo> TABLE_META_INFO_CACHE = new HashMap<>();

	/**
	 * Get table meta info from class info
	 *
	 * @param clazz type info with annotations.
	 * @return {@link TableMetaInfo}
	 */
	public static TableMetaInfo getTableMetaInfo(Class<?> clazz) {
		if (!AnnotationUtils.hasAnnotationTable(clazz))
			throw new MapperException(
					String.format("Unsupported operation: clazz %s has no annotation @Table declared.", clazz.getName()));
		String typeName = ReflectionUtils.getClassNameEntire(clazz);
		if (TABLE_META_INFO_CACHE.containsKey(typeName)) {
			return TABLE_META_INFO_CACHE.get(typeName);
		} else {
			TableMetaInfo tableMetaInfo = new TableMetaInfo();
			Table table = AnnotationUtils.getAnnotationTable(clazz);
			tableMetaInfo.setClassName(typeName);
			tableMetaInfo.setTableName(table.name());
			tableMetaInfo.setTableSettings(table.settings());
			for (Field field : ReflectionUtils.getFieldsUnStaticUnFinal(clazz)) {
				if (!AnnotationUtils.hasAnnotationColumn(field)) {
					continue;
				}
				ColumnMetaInfo columnMetaInfo = new ColumnMetaInfo();
				columnMetaInfo.setFieldName(field.getName());
				columnMetaInfo.setFieldType(ReflectionUtils.getFieldTypeNameEntire(field));
				Column column = AnnotationUtils.getAnnotationColumn(field);
				columnMetaInfo.setColumnName(column.name());
				columnMetaInfo.setType(column.type());
				columnMetaInfo.setNotNull(column.notNull());
				columnMetaInfo.setDefaultValue(column.defaultValue());
				columnMetaInfo.setAutoIncrement(column.autoIncrement());
				if (AnnotationUtils.hasAnnotationId(field)) {
					columnMetaInfo.setIdColumn(true);
				}
				if (AnnotationUtils.hasAnnotationIndexed(field)) {
					columnMetaInfo.setIndexedColumn(true);
				}
				if (AnnotationUtils.hasAnnotationPrimaryKey(field)) {
					columnMetaInfo.setPrimaryKey(true);
				}
				if (AnnotationUtils.hasAnnotationForeignKey(field)) {
					ForeignKey foreignKey = AnnotationUtils.getAnnotationForeginKey(field);
					columnMetaInfo.setForeignKey(true);
					columnMetaInfo.setForeignKeyReferences(foreignKey.references());
				}
				tableMetaInfo.addColumnMetaInfo(columnMetaInfo);
			}
			TABLE_META_INFO_CACHE.put(typeName, tableMetaInfo);
			return tableMetaInfo;
		}
	}

	@Override
	public <T> String createTable(Class<T> clazz) {
		if (!AnnotationUtils.hasAnnotationTable(clazz))
			throw new MapperException(
					String.format("Unsupported operation: clazz %s has no annotation @Table declared.", clazz.getName()));
		TableMetaInfo tableMetaInfo = getTableMetaInfo(clazz);
		if (tableMetaInfo == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate table meta info from class %s.", clazz.getName()));
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ");
		builder.append(StringUtils.getSureName(tableMetaInfo.getTableName()));
		builder.append("(\r\n");
		for (ColumnMetaInfo columnMetaInfo : tableMetaInfo.getColumnMetaInfoList()) {
			builder.append("  ");
			builder.append(StringUtils.getSureName(columnMetaInfo.getColumnName()));
			builder.append(" ");
			builder.append(columnMetaInfo.getType());
			builder.append(" ");
			if (columnMetaInfo.isNotNull()) {
				builder.append(ColumnMetaInfo.NOT_NULL);
				builder.append(" ");
			} else if (!columnMetaInfo.isNotNull() && !columnMetaInfo.getDefaultValue().isEmpty()) {
				builder.append(ColumnMetaInfo.DEFAULT);
				builder.append(" ");
				builder.append(columnMetaInfo.getDefaultValue());
				builder.append(" ");
			}
			if (columnMetaInfo.isAutoIncrement()) {
				builder.append(ColumnMetaInfo.AUTO_INCREMENT);
				builder.append(" ");
			}
			builder.append(", \r\n");
		}
		String primaryKeysSentence = getPrimaryKeysSentence(tableMetaInfo.getColumnMetaInfoList());
		String foreignKeysSentence = getForeignKeysSentence(tableMetaInfo.getColumnMetaInfoList());
		if (!primaryKeysSentence.isEmpty()) {
			builder.append("  ");
			builder.append(primaryKeysSentence);
			builder.append(", \r\n");
		}
		if (!foreignKeysSentence.isEmpty()) {
			builder.append(foreignKeysSentence);
			builder.append(", \r\n");
		}
		builder = new StringBuilder(builder.substring(0, builder.length() - 4));
		builder.append("\r\n) ");
		builder.append(tableMetaInfo.getTableSettings());
		builder.append(";");
		return builder.toString();
	}

	private String getPrimaryKeysSentence(List<ColumnMetaInfo> columnMetaInfoList) {
		StringBuilder keys = new StringBuilder();
		for (ColumnMetaInfo columnMetaInfo : columnMetaInfoList) {
			if (columnMetaInfo.isPrimaryKey()) {
				keys.append(StringUtils.getSureName(columnMetaInfo.getColumnName()));
				keys.append(", ");
			}
		}
		return keys.length() == 0 ? ""
				: ColumnMetaInfo.CONSTRAINT + " " +
						ColumnMetaInfo.PRIMARY_KEY + " (" +
						keys.substring(0, keys.length() - 2) + ")";
	}

	private String getForeignKeysSentence(List<ColumnMetaInfo> columnMetaInfoList) {
		StringBuilder keys = new StringBuilder();
		for (ColumnMetaInfo columnMetaInfo : columnMetaInfoList) {
			if (columnMetaInfo.isForeignKey()) {
				keys.append("  ");
				keys.append(ColumnMetaInfo.CONSTRAINT);
				keys.append(" ");
				keys.append(ColumnMetaInfo.FOREIGN_KEY);
				keys.append(" ");
				keys.append("fk_");
				keys.append(columnMetaInfo.getColumnName());
				keys.append(" ");
				keys.append("(");
				keys.append(StringUtils.getSureName(columnMetaInfo.getColumnName()));
				keys.append(") ");
				keys.append(ColumnMetaInfo.REFERENCES);
				keys.append(" ");
				keys.append(StringUtils.getSplitSureName(columnMetaInfo.getForeignKeyReferences()));
				keys.append(" , \r\n");
			}
		}
		return keys.length() == 0 ? "" : keys.substring(0, keys.length() - 5);
	}

	@Override
	public <T> List<String> createIndex(Class<T> clazz) {
		TableMetaInfo tableMetaInfo = getTableMetaInfo(clazz);
		if (tableMetaInfo == null)
			throw new MapperException(
					String.format("Unsupported operation: can not generate table meta info from class %s.", clazz.getName()));
		List<String> sentenceList = new ArrayList<>();
		for (ColumnMetaInfo columnMetaInfo : tableMetaInfo.getColumnMetaInfoList()) {
			if (columnMetaInfo.isIndexedColumn()) {
				sentenceList.add("CREATE INDEX index_" + columnMetaInfo.getColumnName()
						+ " ON " + StringUtils.getSureName(tableMetaInfo.getTableName())
						+ " (" + StringUtils.getSureName(columnMetaInfo.getColumnName()) + ");");
			}
		}
		return sentenceList;
	}

	@Override
	public <T> String insert(T t, Class<T> clazz) {
		TableMetaInfo tableMetaInfo = getTableMetaInfo(clazz);
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
				columns.add(StringUtils.getSureName(columnMetaInfo.getColumnName()));
				values.add(value);
			} else if (columnMetaInfo.isNotNull()) {
				Object value = ReflectionUtils.getFieldValue(t, columnMetaInfo.getFieldName());
				if (value == null)
					throw new MapperException(
							String.format("Unsupported operation: field %s value must not be null.", columnMetaInfo.getFieldName()));
				columns.add(StringUtils.getSureName(columnMetaInfo.getColumnName()));
				values.add(value);
			} else if (!columnMetaInfo.isNotNull() && !columnMetaInfo.getDefaultValue().isEmpty()) {
				Object value = ReflectionUtils.getFieldValue(t, columnMetaInfo.getFieldName());
				if (value == null)
					value = ValueUtils.upToObject(columnMetaInfo.getDefaultValue(),
							ReflectionUtils.getClassByName(columnMetaInfo.getFieldType()));
				columns.add(StringUtils.getSureName(columnMetaInfo.getColumnName()));
				values.add(value);
			} else {
				Object value = ReflectionUtils.getFieldValue(t, columnMetaInfo.getFieldName());
				if (value == null)
					continue;
				columns.add(StringUtils.getSureName(columnMetaInfo.getColumnName()));
				values.add(value);
			}
		}
		return Insert.insertInto(StringUtils.getSureName(tableMetaInfo.getTableName()))
				.fields(CollectionUtils.strListToArray(columns))
				.values(CollectionUtils.objListToArray(values)).getSql();
	}

	@Override
	public <T> String update(T t, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> T select(T t, Class<T> clazz) {
		return null;
	}
}
