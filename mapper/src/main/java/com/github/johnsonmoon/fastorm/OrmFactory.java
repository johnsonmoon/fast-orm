package com.github.johnsonmoon.fastorm;

import com.github.johnsonmoon.fastorm.core.common.JdbcConnector;

/**
 * Created by johnsonmoon at 2018/4/16 16:27.
 */
public interface OrmFactory {
	/**
	 * Init db factory.
	 *
	 * @param jdbcDriverClassName  jdbc driver class name {like: com.mysql.jdbc.Driver}
	 * @param databaseUrl          database url {like: jdbc:mysql://127.0.0.1:3306/test/serverTimezone=UTC}
	 * @param connectionProperties properties for database connecting
	 * <pre>
	 * example:
	 * "user,testUser,password,testPassword,characterEncoding,UTF-8,useSSL,true,useUnicode,true"
	 * </pre>
	 * properties param count must be even number, split by "," character.
	 */
	void init(String jdbcDriverClassName, String databaseUrl,
			String connectionProperties);

	/**
	 * Init db factory.
	 *
	 * @param jdbcDriverClassName  jdbc driver class name {like: com.mysql.jdbc.Driver}
	 * @param databaseUrl          database url {like: jdbc:mysql://127.0.0.1:3306/test/serverTimezone=UTC}
	 * @param connectionProperties properties for database connecting
	 * <pre>
	 * example:
	 * "user", "testUser",
	 * "password", "testPassword",
	 * "characterEncoding", "UTF-8",
	 * "useSSL", "true",
	 * "useUnicode", "true"
	 * </pre>
	 * properties param count must be even number.
	 */
	void init(String jdbcDriverClassName, String databaseUrl,
			String... connectionProperties);

	/**
	 * Get jdbcConnector instance.
	 *
	 * @return {@link JdbcConnector}
	 */
	JdbcConnector getJdbcConnector();
}
