package com.github.johnsonmoon.fastorm.core.common;

import com.github.johnsonmoon.fastorm.core.util.DateUtils;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				DatabaseType.setCurrentDatabase(jdbcDriverClassName);
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
				DatabaseType.setCurrentDatabase(jdbcDriverClassName);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return jdbcConnector;
	}

	private Properties connectionProperties = new Properties();
	private String databaseUrl = "jdbc:mysql://127.0.0.1:3306/test/serverTimezone=UTC";
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	private JdbcConnector() {
	}

	/**
	 * Substring pattern which sql sentence contains (DATETIME | true | false ) . Which is to be replaced.
	 */
	private static final Pattern REPLACEMENT_PATTERN = Pattern.compile(
			"('?[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d'?)" +
					"|true|false|TRUE|FALSE");
	private static final Pattern REPLACEMENT_PATTERN_DATETIME = Pattern.compile(
			"'?[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d'?");
	private static final Pattern REPLACEMENT_PATTERN_BOOLEAN = Pattern.compile("true|false|TRUE|FALSE");

	/**
	 * To parse date into a correct format with correct time zone, it is necessary to
	 * process sql sentence which contains 'yyyy-MM-dd HH:mm:ss' or yyyy-MM-dd HH:mm:ss
	 * strings.
	 * <p/>
	 * replace 'yyyy-MM-dd HH:mm:ss' or yyyy-MM-dd HH:mm:ss by a placeholder '?',
	 * using {@link java.sql.PreparedStatement#setTimestamp(int, Timestamp)} to
	 * set correct value of datetime.
	 * <p/>
	 * replace true/false into a correctly way, 
	 * using {@link PreparedStatement#setBoolean(int, boolean)} to set correct value of boolean.
	 */
	private PreparedStatement processSqlSentence(Connection connection, String sql) throws Exception {
		PreparedStatement preparedStatement;
		//process date type --> timestamp
		Matcher matcher = REPLACEMENT_PATTERN.matcher(sql);
		List<String> replaceParams = new ArrayList<>();
		while (matcher.find()) {
			String group = matcher.group();
			replaceParams.add(group);
		}
		if (!replaceParams.isEmpty()) {
			for (String param : replaceParams) {
				sql = sql.replaceFirst(param, "?");
			}
		}
		preparedStatement = connection.prepareStatement(sql);
		for (int i = 1; i <= replaceParams.size(); i++) {
			String valueStr = replaceParams.get(i - 1);
			if (valueStr.isEmpty()) {
				continue;
			}
			if (valueStr.contains("'")) {
				valueStr = valueStr.replace("'", "");
			}
			//Set data
			if (REPLACEMENT_PATTERN_DATETIME.matcher(valueStr).matches()) {
				long time = DateUtils.parseDateTime(valueStr).getTime();
				preparedStatement.setTimestamp(i, new Timestamp(time));
			} else if (REPLACEMENT_PATTERN_BOOLEAN.matcher(valueStr).matches()) {
				boolean bool = Boolean.parseBoolean(valueStr);
				preparedStatement.setBoolean(i, bool);
			}
		}
		return preparedStatement;
	}

	/**
	 * Query operation.
	 *
	 * @param sql sql sentence
	 * @return result set map list
	 */
	public List<LinkedHashMap<String, Object>> query(String sql) {
		List<LinkedHashMap<String, Object>> resultMapList = new ArrayList<>();
		try {
			connection = getConnection();
			statement = processSqlSentence(connection, sql);
			resultSet = statement.executeQuery();
			int columnCount = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = resultSet.getMetaData().getColumnName(i);
					Object columnValue = resultSet.getObject(i);
					map.put(columnName, columnValue);
				}
				resultMapList.add(map);
			}
		} catch (Exception e) {
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
			statement = processSqlSentence(connection, sql);
			result = statement.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close();
		}
		return result;
	}

	/**
	 * Execute sql command.
	 *
	 * @param sql sql sentence
	 * @return true if the first result is a ResultSet object; false if it is an update count or there are no results
	 */
	public boolean execute(String sql) {
		try {
			connection = getConnection();
			statement = processSqlSentence(connection, sql);
			return statement.execute();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close();
		}
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
