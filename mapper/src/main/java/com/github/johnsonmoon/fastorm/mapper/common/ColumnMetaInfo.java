package com.github.johnsonmoon.fastorm.mapper.common;

/**
 * Created by johnsonmoon at 2018/4/8 14:07.
 */
public class ColumnMetaInfo {
	public static final String NOT_NULL = "NOT NULL";
	public static final String DEFAULT = "DEFAULT";
	public static final String AUTO_INCREMENT = "AUTO_INCREMENT";
	public static final String CONSTRAINT = "CONSTRAINT";
	public static final String PRIMARY_KEY = "PRIMARY KEY";
	public static final String FOREIGN_KEY = "FOREIGN KEY";
	public static final String REFERENCES = "REFERENCES";

	private boolean idColumn = false;
	private boolean indexedColumn = false;
	private String columnName = "";
	private String type = "";
	private boolean notNull = false;
	private String defaultValue = "";
	private boolean autoIncrement = false;
	private boolean primaryKey = false;
	private boolean foreignKey = false;
	private String foreignKeyReferences = "";

	public ColumnMetaInfo() {
	}

	public ColumnMetaInfo(String columnName, String type) {
		this.columnName = columnName;
		this.type = type;
	}

	public boolean isIdColumn() {
		return idColumn;
	}

	public void setIdColumn(boolean idColumn) {
		this.idColumn = idColumn;
	}

	public boolean isIndexedColumn() {
		return indexedColumn;
	}

	public void setIndexedColumn(boolean indexedColumn) {
		this.indexedColumn = indexedColumn;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String getForeignKeyReferences() {
		return foreignKeyReferences;
	}

	public void setForeignKeyReferences(String foreignKeyReferences) {
		this.foreignKeyReferences = foreignKeyReferences;
	}

	@Override
	public String toString() {
		return "ColumnMetaInfo{" +
				"columnName='" + columnName + '\'' +
				", type='" + type + '\'' +
				", notNull=" + notNull +
				", defaultValue='" + defaultValue + '\'' +
				", primaryKey=" + primaryKey +
				", foreignKey=" + foreignKey +
				", foreignKeyReferences='" + foreignKeyReferences + '\'' +
				'}';
	}
}
