package com.github.johnsonmoon.fastorm.mapper;

import com.github.johnsonmoon.fastorm.core.common.QueryResult;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;
import com.github.johnsonmoon.fastorm.core.util.RandomUtils;
import com.github.johnsonmoon.fastorm.entity.Van;
import com.github.johnsonmoon.fastorm.mapper.impl.SimpleMapping;
import org.junit.Before;

import com.github.johnsonmoon.fastorm.core.common.JdbcConnector;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by johnsonmoon at 2018/4/19 14:30.
 */
public class SimpleMappingTest {
	private Mapping mapping = new SimpleMapping();

	@Before
	public void setUp() {
		JdbcConnector.createInstance("com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC",
				"user", "root",
				"password", "Root_123",
				"characterEncoding", "UTF-8",
				"useSSL", "true",
				"useUnicode", "true");

		//				JdbcConnector.createInstance("org.sqlite.JDBC",
		//						"jdbc:sqlite:D:\\sqlite3\\databases\\test.db");
	}

	@Test
	public void testCreateTable() {
		TableMetaInfo tableMetaInfo = mapping.getTableMetaInfo(Van.class);
		System.out.println(tableMetaInfo);
		String sql = mapping.createTable(Van.class);
		System.out.println(sql);
		System.out.println(!JdbcConnector.getInstance().execute(sql));
	}

	@Test
	public void testInsertValue() {
		Van van = new Van();
		van.setId(RandomUtils.getRandomString(20));
		van.setCreateDate(new Date());
		van.setImported(true);
		van.setOrigin("US");
		van.setPrice(250000);
		van.setProperties(Collections.singletonMap("color", "BLACK"));
		van.setSeatCount(7);
		van.setSequenceNumbers(Arrays.asList("14120005369", "1011245668", "1236665011012"));

		String sql = mapping.insert(van, Van.class).getSql();
		System.out.println(sql);
		System.out.println(JdbcConnector.getInstance().update(sql));
	}

	@Test
	public void testQueryAndConvert() {
		Van van = new Van();
		van.setId(RandomUtils.getRandomString(20));
		van.setCreateDate(new Date());
		van.setImported(true);
		van.setOrigin("US");
		van.setPrice(250000);
		van.setProperties(Collections.singletonMap("color", "BLACK"));
		van.setSeatCount(7);
		van.setSequenceNumbers(Arrays.asList("14120005369", "1011245668", "1236665011012"));
		String insertSql = mapping.insert(van, Van.class).getSql();
		System.out.println(insertSql);
		System.out.println(JdbcConnector.getInstance().update(insertSql));

		Van vanQuery = new Van();
		vanQuery.setImported(true);
		String querySql = mapping.query(vanQuery, Van.class).getSql();
		System.out.println(querySql);
		QueryResult queryResult = new QueryResult(JdbcConnector.getInstance().query(querySql));
		System.out.println(queryResult);

		List<Van> vanList = mapping.convert(queryResult, Van.class);
		System.out.println(vanList);
	}
}
