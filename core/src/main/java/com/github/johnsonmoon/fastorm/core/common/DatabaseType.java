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
    public static final int SYBASE = 4;
    public static final int INFORMIX = 5;
    public static final int POSTGRESQL = 6;
    public static final int SQLITE = 7;

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
        if (jdbcDriverClassName.contains("sybase")) {
            CURRENT_DATABASE = SYBASE;
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
     * TODO modify oracle, db2, sqlserver, sybase, informix, postgresql
     */
    private static final String KEYWORD_AVOID_ORACLE = "\"%s\"";
    private static final String KEYWORD_AVOID_DB2 = "\"%s\"";
    private static final String KEYWORD_AVOID_SQLSERVER = "\"%s\"";
    private static final String KEYWORD_AVOID_MYSQL = "`%s`";
    private static final String KEYWORD_AVOID_SYBASE = "\"%s\"";
    private static final String KEYWORD_AVOID_INFORMIX = "\"%s\"";
    private static final String KEYWORD_AVOID_POSTGRESQL = "\"%s\"";
    private static final String KEYWORD_AVOID_SQLITE = "\"%s\"";

    /**
     * Get keyword avoid string format by current database type.
     *
     * @return keyword avoid format string
     * <pre>
     * {@link DatabaseType#KEYWORD_AVOID_ORACLE}
     * {@link DatabaseType#KEYWORD_AVOID_DB2}
     * {@link DatabaseType#KEYWORD_AVOID_SQLSERVER}
     * {@link DatabaseType#KEYWORD_AVOID_MYSQL}
     * {@link DatabaseType#KEYWORD_AVOID_SYBASE}
     * {@link DatabaseType#KEYWORD_AVOID_INFORMIX}
     * {@link DatabaseType#KEYWORD_AVOID_POSTGRESQL}
     * {@link DatabaseType#KEYWORD_AVOID_SQLITE}
     * </pre>
     */
    public static String getKeywordAvoid() {
        switch (CURRENT_DATABASE) {
            case ORACLE:
                return KEYWORD_AVOID_ORACLE;
            case DB2:
                return KEYWORD_AVOID_DB2;
            case SQLSERVER:
                return KEYWORD_AVOID_SQLSERVER;
            case MYSQL:
                return KEYWORD_AVOID_MYSQL;
            case SYBASE:
                return KEYWORD_AVOID_SYBASE;
            case INFORMIX:
                return KEYWORD_AVOID_INFORMIX;
            case POSTGRESQL:
                return KEYWORD_AVOID_POSTGRESQL;
            case SQLITE:
                return KEYWORD_AVOID_SQLITE;
            default:
                return KEYWORD_AVOID_MYSQL;
        }
    }
}
