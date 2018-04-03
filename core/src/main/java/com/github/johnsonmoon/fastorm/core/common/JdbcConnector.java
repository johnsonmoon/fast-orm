package com.github.johnsonmoon.fastorm.core.common;

import java.sql.*;
import java.util.*;

/**
 * Created by johnsonmoon at 2018/3/30 17:45.
 */
public class JdbcConnector {
	private static JdbcConnector jdbcConnector;

	/**
	 * Get instance.
	 *
	 * @return jdbcConnector instance
	 */
	public static JdbcConnector getInstance() {
		return jdbcConnector;
	}

	/**
	 * Create.
	 *
	 * @param jdbcDriverClassName jdbc driver class name {like: com.mysql.jdbc.Driver}
	 * @param databaseUrl         database url {like: jdbc:mysql://127.0.0.1:3306/test/serverTimezone=UTC}
	 * @param user                user name for database login
	 * @param password            password for database login
	 * @return jdbcConnector instance
	 */
	public static JdbcConnector createInstance(String jdbcDriverClassName, String databaseUrl, String user,
			String password) {
		if (jdbcConnector == null) {
			jdbcConnector = new JdbcConnector();
			jdbcConnector.databaseUrl = databaseUrl;
			Properties properties = new Properties();
			properties.put("user", user);
			properties.put("password", password);
			jdbcConnector.connectionProperties = properties;
			try {
				Class.forName(jdbcDriverClassName).newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return jdbcConnector;
	}

	/**
	 * Create.
	 *
	 * @param jdbcDriverClassName  jdbc driver class name {like: com.mysql.jdbc.Driver}
	 * @param databaseUrl          database url {like: jdbc:mysql://127.0.0.1:3306/test/serverTimezone=UTC}
	 * @param connectionProperties properties for database connecting {like: "user", "testUser", "password", "testPassword",
	 *                             "characterEncoding", "UTF-8", "useSSL", "true", "useUnicode", "true"}
	 *                             properties must be even number.
	 * @return jdbcConnector instance
	 */
	public static JdbcConnector createInstance(String jdbcDriverClassName, String databaseUrl,
			String... connectionProperties) {
		if (jdbcConnector == null) {
			jdbcConnector = new JdbcConnector();
			jdbcConnector.databaseUrl = databaseUrl;
			Properties properties = new Properties();
			if (connectionProperties.length % 2 != 0) {
				throw new RuntimeException("Connection properties must has even numbers.");
			}
			for (int i = 0; i < connectionProperties.length - 1; i += 2) {
				properties.setProperty(connectionProperties[i], connectionProperties[i + 1]);
			}
			jdbcConnector.connectionProperties = properties;
			try {
				Class.forName(jdbcDriverClassName).newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return jdbcConnector;
	}

	private Properties connectionProperties = new Properties();
	private String databaseUrl = "jdbc:mysql://127.0.0.1:3306/test/serverTimezone=UTC";
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	private JdbcConnector() {
	}

	/**
	 * Query operation.
	 *
	 * @param sql sql sentence
	 * @return result set map list
	 */
	public List<Map<String, Object>> query(String sql) {
		List<Map<String, Object>> resultMapList = new ArrayList<>();
		try {
			connection = getConnection();
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = statement.executeQuery(sql);
			int columnCount = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					Map<String, Object> map = new LinkedHashMap<>();
					String columnName = resultSet.getMetaData().getColumnName(i);
					Object columnValue = resultSet.getObject(i);
					map.put(columnName, columnValue);
					resultMapList.add(map);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close();
		}
		return resultMapList;
	}

	/**
	 * Update or insert operation.
	 *
	 * @param sql update or insert sql sentence
	 * @return modified row count
	 */
	public int update(String sql) {
		int result;
		try {
			connection = getConnection();
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			result = statement.executeUpdate(sql);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close();
		}
		return result;
	}

	private Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(databaseUrl, connectionProperties);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		if (conn == null) {
			throw new RuntimeException("Failed to get jdbc connection.");
		}
		return conn;
	}

	private void close() {
		try {
			if ((resultSet != null) && (!resultSet.isClosed())) {
				resultSet.close();
			}
			if ((statement != null) && (!statement.isClosed())) {
				statement.close();
			}
			if ((connection != null) && (!connection.isClosed())) {
				connection.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
