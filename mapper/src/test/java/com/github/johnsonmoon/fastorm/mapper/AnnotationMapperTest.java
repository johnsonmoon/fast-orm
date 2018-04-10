package com.github.johnsonmoon.fastorm.mapper;

import com.github.johnsonmoon.fastorm.core.common.JdbcConnector;
import com.github.johnsonmoon.fastorm.core.util.RandomUtils;
import com.github.johnsonmoon.fastorm.entity.Order;
import com.github.johnsonmoon.fastorm.entity.Student;
import com.github.johnsonmoon.fastorm.entity.StudentOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

	@Test
	public void testCreateIndex() {
		Mapping mapping = new AnnotationMapping();
		List<String> indexes = new ArrayList<>();
		indexes.addAll(mapping.createIndex(Student.class));
		indexes.addAll(mapping.createIndex(Order.class));
		indexes.addAll(mapping.createIndex(StudentOrder.class));
		indexes.forEach(System.out::println);
		indexes.forEach(JdbcConnector.getInstance()::execute);
	}

	@Test
	public void testInsert() {
		Mapping mapping = new AnnotationMapping();
		for (int i = 0; i < 10; i++) {
			Student student = new Student();
			student.setId(RandomUtils.getRandomString(20));
			student.setName("johnsonmoon" + RandomUtils.getRandomNumberString(5));
			student.setEmail(student.getName() + "@123.com");
			student.setPhone("123123456");
			student.setAddress(RandomUtils.getRandomString(50));
			String sql = mapping.insert(student, Student.class);
			System.out.println(sql);
			System.out.println(JdbcConnector.getInstance().update(sql));

			Order order = new Order();
			order.setId(RandomUtils.getRandomString(20));
			order.setReceiver("Johnsonmoon" + RandomUtils.getRandomNumberString(2));
			order.setAddress("johnsonmoonAddress" + RandomUtils.getRandomString(5));
			String sql2 = mapping.insert(order, Order.class);
			System.out.println(sql2);
			System.out.println(JdbcConnector.getInstance().update(sql2));

			StudentOrder studentOrder = new StudentOrder();
			studentOrder.setStudentId(student.getId());
			studentOrder.setOrderId(order.getId());
			String sql3 = mapping.insert(studentOrder, StudentOrder.class);
			System.out.println(sql3);
			System.out.println(JdbcConnector.getInstance().update(sql3));
		}
	}

	@Test
	public void testUpdate() {
		Mapping mapping = new AnnotationMapping();
		for (int i = 0; i < 10; i++) {
			Student student = new Student();
			student.setId(RandomUtils.getRandomString(20));
			student.setName("johnsonmoon" + RandomUtils.getRandomNumberString(5));
			student.setEmail(student.getName() + "@123.com");
			student.setPhone("123123456");
			student.setAddress(RandomUtils.getRandomString(50));
			if (JdbcConnector.getInstance().update(mapping.insert(student, Student.class)) > 0) {
				Student studentUp = new Student();
				studentUp.setId(student.getId());
				studentUp.setName("JohnsonMoon_" + RandomUtils.getRandomNumberString(2));
				String updateSql = mapping.update(studentUp, Student.class);
				System.out.println(updateSql);
				System.out.println(JdbcConnector.getInstance().update(updateSql));
			}
		}
	}

	@Test
	public void testSelect() {
		Mapping mapping = new AnnotationMapping();
		for (int i = 0; i < 10; i++) {
			Student student = new Student();
			student.setId(RandomUtils.getRandomString(20));
			student.setName("johnson_moon_" + RandomUtils.getRandomNumberString(3));
			student.setEmail(student.getName() + "@123.com");
			student.setPhone("123123456");
			student.setAddress(RandomUtils.getRandomString(50));
			if (JdbcConnector.getInstance().update(mapping.insert(student, Student.class)) > 0) {
				Student studentSelect = new Student();
				studentSelect.setId(student.getId());
				studentSelect.setName(student.getName());
				String selectSql = mapping.select(studentSelect, Student.class);
				System.out.println(selectSql);
				System.out.println(JdbcConnector.getInstance().query(selectSql));
			}
		}
	}
}
