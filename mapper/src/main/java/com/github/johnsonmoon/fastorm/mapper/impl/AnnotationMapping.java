package com.github.johnsonmoon.fastorm.mapper.impl;

import com.github.johnsonmoon.fastorm.core.annotation.Column;
import com.github.johnsonmoon.fastorm.core.annotation.ForeignKey;
import com.github.johnsonmoon.fastorm.core.annotation.Table;
import com.github.johnsonmoon.fastorm.core.util.*;
import com.github.johnsonmoon.fastorm.core.meta.ColumnMetaInfo;
import com.github.johnsonmoon.fastorm.core.common.MapperException;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;
import com.github.johnsonmoon.fastorm.mapper.AbstractMapping;

import java.lang.reflect.Field;
import java.util.*;

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
public class AnnotationMapping extends AbstractMapping {
	private static Map<String, TableMetaInfo> TABLE_META_INFO_CACHE = new HashMap<>();

	@Override
	public <T> TableMetaInfo getTableMetaInfo(Class<T> clazz) {
		if (clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
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
}
