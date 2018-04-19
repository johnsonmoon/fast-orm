package com.github.johnsonmoon.fastorm.mapper.impl;

import java.lang.reflect.Field;
import java.util.*;

import com.github.johnsonmoon.fastorm.core.annotation.ForeignKey;
import com.github.johnsonmoon.fastorm.core.common.DatabaseType;
import com.github.johnsonmoon.fastorm.core.common.MapperException;
import com.github.johnsonmoon.fastorm.core.meta.ColumnMetaInfo;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;
import com.github.johnsonmoon.fastorm.core.util.AnnotationUtils;
import com.github.johnsonmoon.fastorm.core.util.ReflectionUtils;
import com.github.johnsonmoon.fastorm.core.util.StringUtils;
import com.github.johnsonmoon.fastorm.mapper.AbstractMapping;

public class SimpleMapping extends AbstractMapping {
	private static Map<String, TableMetaInfo> TABLE_META_INFO_CACHE = new HashMap<>();

	@Override
	public <T> TableMetaInfo getTableMetaInfo(Class<T> clazz) {
		if (clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
		String typeName = ReflectionUtils.getClassNameEntire(clazz);
		if (TABLE_META_INFO_CACHE.containsKey(typeName)) {
			return TABLE_META_INFO_CACHE.get(typeName);
		} else {
			TableMetaInfo tableMetaInfo = new TableMetaInfo();
			tableMetaInfo.setTableName(ReflectionUtils.getClassNameShort(clazz));
			tableMetaInfo.setClassName(ReflectionUtils.getClassNameEntire(clazz));
			List<ColumnMetaInfo> columnMetaInfoList = new ArrayList<>();
			for (Field field : ReflectionUtils.getFieldsUnStaticUnFinal(clazz)) {
				ColumnMetaInfo columnMetaInfo = new ColumnMetaInfo();
				columnMetaInfo.setFieldName(field.getName());
				columnMetaInfo.setFieldType(ReflectionUtils.getClassNameEntire(field.getType()));
				columnMetaInfo.setColumnName(field.getName());
				columnMetaInfo.setType(getColumnDatabaseStorageTypeByFieldType(field));
				if (AnnotationUtils.hasAnnotationId(field)) {
					columnMetaInfo.setIdColumn(true);
				} else if (StringUtils.containsAny(field.getName(),
						"id", "i_d", "ID", "I_D")) {
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
				columnMetaInfoList.add(columnMetaInfo);
			}
			tableMetaInfo.setColumnMetaInfoList(columnMetaInfoList);
			TABLE_META_INFO_CACHE.put(typeName, tableMetaInfo);
			return tableMetaInfo;
		}
	}

	private static String getColumnDatabaseStorageTypeByFieldType(Field field) {
		// TODO: 2018/4/19 according to different databases, change storage types
		String storageType;
		Class<?> fieldType = field.getType();
		if (fieldType.getName().contains("byte") || fieldType == Byte.class) {
			storageType = "char(1)";
		} else if (fieldType.getName().contains("boolean") || fieldType == Boolean.class) {
			storageType = "integer";
		} else if (fieldType.getName().contains("short") || fieldType == Short.class) {
			storageType = "integer";
		} else if (fieldType.getName().contains("int") || fieldType == Integer.class) {
			storageType = "integer";
		} else if (fieldType.getName().contains("long") || fieldType == Long.class) {
			storageType = "bigint";
		} else if (fieldType.getName().contains("float") || fieldType == Float.class) {
			storageType = "float";
		} else if (fieldType.getName().contains("double") || fieldType == Double.class) {
			storageType = "float";
		} else if (fieldType.getName().contains("char") || fieldType == Character.class) {
			storageType = "char(1)";
		} else if (fieldType == String.class) {
			storageType = "varchar(255)";
		} else if (fieldType == Date.class) {
			storageType = "datetime";
		} else {
			if (DatabaseType.CURRENT_DATABASE == DatabaseType.MYSQL
					|| DatabaseType.CURRENT_DATABASE == DatabaseType.SQLITE) {
				storageType = "text";
			} else {
				storageType = "varchar(5000)";
			}
		}
		return storageType;
	}
}
