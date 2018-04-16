package com.github.johnsonmoon.fastorm.core.common;

/**
 * Created by johnsonmoon at 2018/4/13 9:57.
 */
public class DatabaseType {
	public static int CURRENT_DATABASE = 3;//default mysql

	/**
	 * Database type code.
	 */
	public static final int ORACLE = 0;
	public static final int DB2 = 1;
	public static final int SQLSERVER = 2;
	public static final int MYSQL = 3;
	public static final int INFORMIX = 4;
	public static final int POSTGRESQL = 5;
	public static final int SQLITE = 6;

	/**
	 * Set current database type by given jdbc driver class name.
	 *
	 * @param jdbcDriverClassName jdbc driver class name
	 */
	public static void setCurrentDatabase(String jdbcDriverClassName) {
		if (jdbcDriverClassName.contains("oracle")) {
			CURRENT_DATABASE = ORACLE;
			return;
		}
		if (jdbcDriverClassName.contains("ibm") || jdbcDriverClassName.contains("db2")) {
			CURRENT_DATABASE = DB2;
			return;
		}
		if (jdbcDriverClassName.contains("net.sourceforge") || jdbcDriverClassName.contains("jtds")) {
			CURRENT_DATABASE = SQLSERVER;
			return;
		}
		if (jdbcDriverClassName.contains("mysql")) {
			CURRENT_DATABASE = MYSQL;
			return;
		}
		if (jdbcDriverClassName.contains("informix")) {
			CURRENT_DATABASE = INFORMIX;
			return;
		}
		if (jdbcDriverClassName.contains("postgresql")) {
			CURRENT_DATABASE = POSTGRESQL;
			return;
		}
		if (jdbcDriverClassName.contains("sqlite")) {
			CURRENT_DATABASE = SQLITE;
		}
	}

	/**
	 * Avoid keyword conflict.
	 * <p>
	 * TODO test
	 */
	private static final String KEYWORD_AVOID_ORACLE_FORMAT_STR = "\"%s\"";
	private static final String KEYWORD_AVOID_DB2_FORMAT_STR = "\"%s\"";
	private static final String KEYWORD_AVOID_SQLSERVER_FORMAT_STR = "[%s]";
	private static final String KEYWORD_AVOID_MYSQL_FORMAT_STR = "`%s`";
	private static final String KEYWORD_AVOID_INFORMIX_FORMAT_STR = "[%s]";
	private static final String KEYWORD_AVOID_POSTGRESQL_FORMAT_STR = "\"%s\"";
	private static final String KEYWORD_AVOID_SQLITE_FORMAT_STR = "\"%s\"";

	/**
	 * Avoid keyword Character.
	 * <p>
	 * TODO test
	 */
	private static final String KEYWORD_AVOID_ORACLE_CHAR = "\"";
	private static final String KEYWORD_AVOID_DB2_CHAR = "\"";
	private static final String KEYWORD_AVOID_SQLSERVER_CHAR = "[";
	private static final String KEYWORD_AVOID_MYSQL_CHAR = "`";
	private static final String KEYWORD_AVOID_INFORMIX_CHAR = "[";
	private static final String KEYWORD_AVOID_POSTGRESQL_CHAR = "\"";
	private static final String KEYWORD_AVOID_SQLITE_CHAR = "\"";

	/**
	 * Get keyword avoid string format by current database type.
	 *
	 * @return keyword avoid format string
	 * <pre>
	 * {@link DatabaseType#KEYWORD_AVOID_ORACLE_FORMAT_STR}
	 * {@link DatabaseType#KEYWORD_AVOID_DB2_FORMAT_STR}
	 * {@link DatabaseType#KEYWORD_AVOID_SQLSERVER_FORMAT_STR}
	 * {@link DatabaseType#KEYWORD_AVOID_MYSQL_FORMAT_STR}
	 * {@link DatabaseType#KEYWORD_AVOID_INFORMIX_FORMAT_STR}
	 * {@link DatabaseType#KEYWORD_AVOID_POSTGRESQL_FORMAT_STR}
	 * {@link DatabaseType#KEYWORD_AVOID_SQLITE_FORMAT_STR}
	 * </pre>
	 */
	public static String getKeywordAvoidFormatStr() {
		switch (CURRENT_DATABASE) {
		case ORACLE:
			return KEYWORD_AVOID_ORACLE_FORMAT_STR;
		case DB2:
			return KEYWORD_AVOID_DB2_FORMAT_STR;
		case SQLSERVER:
			return KEYWORD_AVOID_SQLSERVER_FORMAT_STR;
		case MYSQL:
			return KEYWORD_AVOID_MYSQL_FORMAT_STR;
		case INFORMIX:
			return KEYWORD_AVOID_INFORMIX_FORMAT_STR;
		case POSTGRESQL:
			return KEYWORD_AVOID_POSTGRESQL_FORMAT_STR;
		case SQLITE:
			return KEYWORD_AVOID_SQLITE_FORMAT_STR;
		default:
			return KEYWORD_AVOID_MYSQL_FORMAT_STR;
		}
	}

	/**
	 * Get keyword avoid string basic character.
	 *
	 * @return keyword avoid string basic character.
	 * <pre>
	 *     {@link DatabaseType#KEYWORD_AVOID_ORACLE_CHAR}
	 *     {@link DatabaseType#KEYWORD_AVOID_DB2_CHAR}
	 *     {@link DatabaseType#KEYWORD_AVOID_SQLITE_CHAR}
	 *     {@link DatabaseType#KEYWORD_AVOID_MYSQL_CHAR}
	 *     {@link DatabaseType#KEYWORD_AVOID_INFORMIX_CHAR}
	 *     {@link DatabaseType#KEYWORD_AVOID_POSTGRESQL_CHAR}
	 *     {@link DatabaseType#KEYWORD_AVOID_SQLITE_CHAR}
	 * </pre>
	 */
	public static String getKeywordAvoidChar() {
		switch (CURRENT_DATABASE) {
		case ORACLE:
			return KEYWORD_AVOID_ORACLE_CHAR;
		case DB2:
			return KEYWORD_AVOID_DB2_CHAR;
		case SQLSERVER:
			return KEYWORD_AVOID_SQLSERVER_CHAR;
		case MYSQL:
			return KEYWORD_AVOID_MYSQL_CHAR;
		case INFORMIX:
			return KEYWORD_AVOID_INFORMIX_CHAR;
		case POSTGRESQL:
			return KEYWORD_AVOID_POSTGRESQL_CHAR;
		case SQLITE:
			return KEYWORD_AVOID_SQLITE_CHAR;
		default:
			return KEYWORD_AVOID_MYSQL_CHAR;
		}
	}

	/**
	 * Get table exists query sql sentence.
	 *
	 * @param tableName name of table
	 */
	public static String getTableExistsSqlSentence(String tableName) {
		switch (CURRENT_DATABASE) {
		case ORACLE:
			return "select * from user_tables where table_name = '" + tableName + "'";
		case DB2:
			return "select * from syscat.tables where tabname = upper('" + tableName + "')";
		case SQLSERVER:
			return "select * from sys.tables where name = '" + tableName + "' and type = 'u'";
		case MYSQL:
			return "SHOW TABLES LIKE '" + tableName + "'";
		case INFORMIX:
			return "select * from systables where tabname ='" + tableName + "'and tabtype = 'T'";
		case POSTGRESQL:
			return "select * from information_schema.tables where table_schema='public' and table_type='BASE TABLE' and table_name='"
					+ tableName + "'";
		case SQLITE:
			return "SELECT * FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
		default:
			return "SHOW TABLES LIKE '" + tableName + "'";
		}
	}
}
