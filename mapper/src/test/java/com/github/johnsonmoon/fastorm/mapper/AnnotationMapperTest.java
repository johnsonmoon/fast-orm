package com.github.johnsonmoon.fastorm.mapper;

import com.github.johnsonmoon.fastorm.core.common.JdbcConnector;
import com.github.johnsonmoon.fastorm.entity.Order;
import com.github.johnsonmoon.fastorm.entity.Student;
import com.github.johnsonmoon.fastorm.entity.StudentOrder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by johnsonmoon at 2018/4/8 17:44.
 */
public class AnnotationMapperTest {
	@Before
	public void setUp() {
		JdbcConnector.createInstance("com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC",
				"user", "root",
				"password", "Root_123",
				"characterEncoding", "UTF-8",
				"useSSL", "true",
				"useUnicode", "true");
	}

	@Test
	public void testCreateTable() {
		Mapping mapping = new AnnotationMapping();
		System.out.println(mapping.createTable(Student.class));
		System.out.println(JdbcConnector.getInstance().execute(mapping.createTable(Student.class)));
		System.out.println("\r\n\r\n");
		System.out.println(mapping.createTable(Order.class));
		System.out.println(JdbcConnector.getInstance().execute(mapping.createTable(Order.class)));
		System.out.println("\r\n\r\n");
		System.out.println(mapping.createTable(StudentOrder.class));
		System.out.println(JdbcConnector.getInstance().execute(mapping.createTable(StudentOrder.class)));
		System.out.println("\r\n\r\n");
	}
}
