package com.github.johnsonmoon.fastorm.core.sql;

import com.github.johnsonmoon.fastorm.core.common.DatabaseType;
import com.github.johnsonmoon.fastorm.core.common.MapperException;
import com.github.johnsonmoon.fastorm.core.util.StringUtils;
import com.github.johnsonmoon.fastorm.core.meta.ColumnMetaInfo;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;

import java.util.List;

/**
 * Created by johnsonmoon at 2018/4/13 14:13.
 */
public class CreateTable {
	/**
	 * Generate create table sql sentence by given table meta information. {@link TableMetaInfo}
	 *
	 * @param tableMetaInfo {@link TableMetaInfo}
	 * @return create table sql sentence
	 */
	public static String getSql(TableMetaInfo tableMetaInfo) {
		if (tableMetaInfo == null)
			throw new MapperException("Unsupported operation: table meta information must not be null.");
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
		String tableSettings = tableMetaInfo.getTableSettings();
		builder.append(tableSettings == null? "":tableSettings);
		builder.append(";");
		return builder.toString();
	}

	private static String getPrimaryKeysSentence(List<ColumnMetaInfo> columnMetaInfoList) {
		StringBuilder keys = new StringBuilder();
		for (ColumnMetaInfo columnMetaInfo : columnMetaInfoList) {
			if (columnMetaInfo.isPrimaryKey()) {
				keys.append(StringUtils.getSureName(columnMetaInfo.getColumnName()));
				keys.append(", ");
			}
		}
		if (keys.length() == 0) {
			return "";
		}

		//No "CONSTRAINT" word situation
		//TODO different database support different sql sentences
		if (DatabaseType.CURRENT_DATABASE == DatabaseType.SQLITE
		/* || DatabaseType.CURRENT_DATABASE == DatabaseType.? */) {
			return ColumnMetaInfo.PRIMARY_KEY + " (" +
					keys.substring(0, keys.length() - 2) + ")";
		}

		return ColumnMetaInfo.CONSTRAINT + " " +
				ColumnMetaInfo.PRIMARY_KEY + " (" +
				keys.substring(0, keys.length() - 2) + ")";
	}

	private static String getForeignKeysSentence(List<ColumnMetaInfo> columnMetaInfoList) {
		StringBuilder keys = new StringBuilder();
		for (ColumnMetaInfo columnMetaInfo : columnMetaInfoList) {
			if (columnMetaInfo.isForeignKey()) {
				keys.append("  ");
				//No "CONSTRAINT" word and constraint name "fk_" situation
				//TODO different database support different sql sentences
				if (DatabaseType.CURRENT_DATABASE != DatabaseType.SQLITE
				/*&& DatabaseType.CURRENT_DATABASE != DatabaseType.?*/) {
					keys.append(ColumnMetaInfo.CONSTRAINT);
					keys.append(" ");
					keys.append("fk_");
					keys.append(columnMetaInfo.getColumnName());
					keys.append(" ");
				}
				keys.append(ColumnMetaInfo.FOREIGN_KEY);
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
}
