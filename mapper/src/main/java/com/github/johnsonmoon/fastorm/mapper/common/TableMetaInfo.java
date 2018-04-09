package com.github.johnsonmoon.fastorm.mapper.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnsonmoon at 2018/4/8 14:06.
 */
public class TableMetaInfo {
	private String className;//java class name (entire)
	private String tableName;//database table name
	private String tableSettings;
	private List<ColumnMetaInfo> columnMetaInfoList;

	public TableMetaInfo() {
		this.columnMetaInfoList = new ArrayList<>();
	}

	public TableMetaInfo(String tableName) {
		this.tableName = tableName;
	}

	public TableMetaInfo(String tableName, String tableSettings) {
		this.tableName = tableName;
		this.tableSettings = tableSettings;
	}

	public TableMetaInfo(String tableName, List<ColumnMetaInfo> columnMetaInfoList) {
		this.tableName = tableName;
		this.columnMetaInfoList = columnMetaInfoList;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableSettings() {
		return tableSettings;
	}

	public void setTableSettings(String tableSettings) {
		this.tableSettings = tableSettings;
	}

	public List<ColumnMetaInfo> getColumnMetaInfoList() {
		return columnMetaInfoList;
	}

	public void setColumnMetaInfoList(List<ColumnMetaInfo> columnMetaInfoList) {
		this.columnMetaInfoList = columnMetaInfoList;
	}

	public void addColumnMetaInfo(ColumnMetaInfo columnMetaInfo) {
		if (this.columnMetaInfoList == null)
			this.columnMetaInfoList = new ArrayList<>();
		this.columnMetaInfoList.add(columnMetaInfo);
	}

	public void removeColumnMetaInfo(ColumnMetaInfo columnMetaInfo) {
		if (this.columnMetaInfoList == null)
			return;
		if (this.columnMetaInfoList.contains(columnMetaInfo))
			columnMetaInfoList.remove(columnMetaInfo);
	}

	@Override
	public String toString() {
		return "TableMetaInfo{" +
				"className='" + className + '\'' +
				", tableName='" + tableName + '\'' +
				", tableSettings='" + tableSettings + '\'' +
				", columnMetaInfoList=" + columnMetaInfoList +
				'}';
	}
}
