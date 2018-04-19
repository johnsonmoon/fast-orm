package com.github.johnsonmoon.fastorm.core.meta;

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

	private String fieldName;//java class field name
	private String fieldType;//java class type of field(entire name)
	private boolean idColumn = false;
	private boolean indexedColumn = false;
	private String columnName = "";//database column name
	private String type = "";//database data type [example: char(5), varchar(20), integer, datetime, timestamp, longint, etc.]
	private boolean notNull = false;
	private String defaultValue = "";
	private boolean autoIncrement = false;
	private boolean primaryKey = false;
	private boolean foreignKey = false;
	private String foreignKeyReferences = "";

	public ColumnMetaInfo() {
	}

	public ColumnMetaInfo(String fieldName) {
		this.fieldName = fieldName;
	}

	public ColumnMetaInfo(String fieldName, String columnName, String type) {
		this.fieldName = fieldName;
		this.columnName = columnName;
		this.type = type;
	}

	/**
	 * java class field name
	 */
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
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

	/**
	 * Get data type of database storage.
	 * <p>
	 * <pre>
	 *     examples:
	 *      "integer(size)"
	 *      "int(size)"
	 *      "smallint(size)"
	 *      "tinyint(size)"
	 *      "decimal(size,d)"
	 *      "numeric(size,d)"
	 *      "char(size)"
	 *      "varchar(size)"
	 *      "date(yyyy-MM-dd)"
	 *      etc.
	 *
	 *     default:
	 *      "varchar(50)"
	 * </pre>
	 *
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set data type of database storage.
	 * <p>
	 * <pre>
	 *     examples:
	 *      "integer(size)"
	 *      "int(size)"
	 *      "smallint(size)"
	 *      "tinyint(size)"
	 *      "decimal(size,d)"
	 *      "numeric(size,d)"
	 *      "char(size)"
	 *      "varchar(size)"
	 *      "date(yyyy-MM-dd)"
	 *      etc.
	 *
	 *     default:
	 *      "varchar(50)"
	 * </pre>
	 */
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
				"fieldName='" + fieldName + '\'' +
				", idColumn=" + idColumn +
				", indexedColumn=" + indexedColumn +
				", columnName='" + columnName + '\'' +
				", type='" + type + '\'' +
				", notNull=" + notNull +
				", defaultValue='" + defaultValue + '\'' +
				", autoIncrement=" + autoIncrement +
				", primaryKey=" + primaryKey +
				", foreignKey=" + foreignKey +
				", foreignKeyReferences='" + foreignKeyReferences + '\'' +
				'}';
	}
}
