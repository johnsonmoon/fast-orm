package com.github.johnsonmoon.fastorm;

import com.github.johnsonmoon.fastorm.core.common.JdbcConnector;

/**
 * Simple implementation of {@link OrmFactory}.
 * <p>
 * Created by johnsonmoon at 2018/4/16 16:47.
 */
public class OrmFactorySimpleImpl implements OrmFactory {
	@Override
	public void init(String jdbcDriverClassName, String databaseUrl, String connectionProperties) {
		String[] array = connectionProperties.split(",");
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].trim();
		}
		JdbcConnector.createInstance(jdbcDriverClassName, databaseUrl, array);
	}

	@Override
	public void init(String jdbcDriverClassName, String databaseUrl, String... connectionProperties) {
		JdbcConnector.createInstance(jdbcDriverClassName, databaseUrl, connectionProperties);
	}

	@Override
	public JdbcConnector getJdbcConnector() {
		return JdbcConnector.getInstance();
	}
}
