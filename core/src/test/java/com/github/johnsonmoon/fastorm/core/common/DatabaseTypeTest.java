package com.github.johnsonmoon.fastorm.core.common;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by johnsonmoon at 2018/4/16 16:17.
 */
public class DatabaseTypeTest {
	@Before
	public void setUp() {
		//        JdbcConnector.createInstance("com.mysql.cj.jdbc.Driver",
		//                "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC",
		//                "user", "root",
		//                "password", "Root_123",
		//                "characterEncoding", "UTF-8",
		//                "useSSL", "true",
		//                "useUnicode", "true");

		JdbcConnector.createInstance("org.sqlite.JDBC",
				"jdbc:sqlite:D:\\sqlite3\\databases\\test.db");
	}

	@Test
	public void testTableExists() {
		//TODO oracle, db2, informix, postgresql, sqlserver
		String sql = DatabaseType.getTableExistsSqlSentence("car");
		List<LinkedHashMap<String, Object>> result = JdbcConnector.getInstance().query(sql);
		System.out.println(result);
	}
}
