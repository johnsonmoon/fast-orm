package com.github.johnsonmoon.fastorm.mapper;

import com.github.johnsonmoon.fastorm.core.common.QueryResult;
import com.github.johnsonmoon.fastorm.core.common.JdbcConnector;
import com.github.johnsonmoon.fastorm.core.sql.Delete;
import com.github.johnsonmoon.fastorm.core.sql.Insert;
import com.github.johnsonmoon.fastorm.core.sql.Query;
import com.github.johnsonmoon.fastorm.core.sql.Update;
import com.github.johnsonmoon.fastorm.entity.Car;
import com.github.johnsonmoon.fastorm.core.util.RandomUtils;
import com.github.johnsonmoon.fastorm.entity.Order;
import com.github.johnsonmoon.fastorm.entity.Student;
import com.github.johnsonmoon.fastorm.entity.StudentOrder;
import com.github.johnsonmoon.fastorm.mapper.impl.AnnotationMapping;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by johnsonmoon at 2018/4/8 17:44.
 */
public class AnnotationMappingTest {
	@Before
	public void setUp() {
		JdbcConnector.createInstance("com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC",
				"user", "root",
				"password", "Root_123",
				"characterEncoding", "UTF-8",
				"useSSL", "true",
				"useUnicode", "true");

		//		JdbcConnector.createInstance("org.sqlite.JDBC",
		//				"jdbc:sqlite:D:\\sqlite3\\databases\\test.db");
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
		System.out.println(mapping.createTable(Car.class));
		System.out.println(JdbcConnector.getInstance().execute(mapping.createTable(Car.class)));
		System.out.println("\r\n\r\n");
	}

	@Test
	public void testCreateIndex() {
		Mapping mapping = new AnnotationMapping();
		List<String> indexes = new ArrayList<>();
		indexes.addAll(mapping.createIndex(Student.class));
		indexes.addAll(mapping.createIndex(Order.class));
		indexes.addAll(mapping.createIndex(StudentOrder.class));
		indexes.addAll(mapping.createIndex(Car.class));
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
			String sql = mapping.insert(student, Student.class).getSql();
			System.out.println(sql);
			System.out.println(JdbcConnector.getInstance().update(sql));

			Order order = new Order();
			order.setId(RandomUtils.getRandomString(20));
			order.setReceiver("Johnsonmoon" + RandomUtils.getRandomNumberString(2));
			order.setAddress("johnsonmoonAddress" + RandomUtils.getRandomString(5));
			String sql2 = mapping.insert(order, Order.class).getSql();
			System.out.println(sql2);
			System.out.println(JdbcConnector.getInstance().update(sql2));

			StudentOrder studentOrder = new StudentOrder();
			studentOrder.setStudentId(student.getId());
			studentOrder.setOrderId(order.getId());
			String sql3 = mapping.insert(studentOrder, StudentOrder.class).getSql();
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
			if (JdbcConnector.getInstance().update(mapping.insert(student, Student.class).getSql()) > 0) {
				Student studentUp = new Student();
				studentUp.setId(student.getId());
				studentUp.setName("JohnsonMoon_" + RandomUtils.getRandomNumberString(2));
				String updateSql = mapping.update(studentUp, Student.class).getSql();
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
			if (JdbcConnector.getInstance().update(mapping.insert(student, Student.class).getSql()) > 0) {
				Student studentSelect = new Student();
				studentSelect.setId(student.getId());
				studentSelect.setName(student.getName());
				String selectSql = mapping.query(studentSelect, Student.class).getSql();
				System.out.println(selectSql);
				System.out.println(JdbcConnector.getInstance().query(selectSql));
			}
		}
	}

	@Test
	public void testDelete() {
		Mapping mapping = new AnnotationMapping();
		for (int i = 0; i < 10; i++) {
			Student student = new Student();
			student.setId(RandomUtils.getRandomString(20));
			student.setName("johnson_moon_" + RandomUtils.getRandomNumberString(3));
			student.setEmail(student.getName() + "@123.com");
			student.setPhone("123123456");
			student.setAddress(RandomUtils.getRandomString(50));
			if (JdbcConnector.getInstance().update(mapping.insert(student, Student.class).getSql()) > 0) {
				Student studentDelete = new Student();
				studentDelete.setName(student.getName());
				studentDelete.setAddress(student.getAddress());
				String deleteSql = mapping.delete(studentDelete, Student.class).getSql();
				System.out.println(deleteSql);
				System.out.println(JdbcConnector.getInstance().update(deleteSql));
			}
		}
	}

	@Test
	public void testConvert() {
		Mapping mapping = new AnnotationMapping();
		Query query = Query.selectAll().from(mapping.getTableMetaInfo(Student.class).getTableName());
		List<Student> students = mapping.convert(JdbcConnector.getInstance().query(query.getSql()), Student.class);
		students.forEach(System.out::println);
		System.out.println("--------------------------");
		System.out.println("--------------------------");
		System.out.println("--------------------------");
		List<Student> students2 = mapping.convert(new QueryResult(JdbcConnector.getInstance().query(query.getSql())),
				Student.class);
		students2.forEach(System.out::println);
	}

	@Test
	public void testInsertCar() {
		Mapping mapping = new AnnotationMapping();
		for (int i = 0; i < 10; i++) {
			Car car = new Car();
			car.setId(RandomUtils.getRandomString(20));
			car.setPrice(15_0000);
			car.setCreateDate(new Date());
			car.setSequenceNumbers(Arrays.asList(RandomUtils.getRandomString(20), RandomUtils.getRandomString(20),
					RandomUtils.getRandomString(20)));
			Map<String, Object> props = new HashMap<>();
			props.put("location", "China");
			props.put("displacement", "1." + RandomUtils.getRandomNumberString(1));
			props.put("color", "BLACK");
			props.put("airbag_number", RandomUtils.getRandomNumberString(1));
			car.setProperties(props);
			String sql = mapping.insert(car, Car.class).getSql();
			System.out.println(sql);
			System.out.println(JdbcConnector.getInstance().update(sql));
		}
	}

	@Test
	public void testValueType() {
		Mapping mapping = new AnnotationMapping();
		Car car = new Car();
		car.setId(RandomUtils.getRandomString(20));
		car.setPrice(15_0000);
		car.setCreateDate(new Date());
		car.setSequenceNumbers(Arrays.asList(RandomUtils.getRandomString(20), RandomUtils.getRandomString(20),
				RandomUtils.getRandomString(20)));
		Map<String, Object> props = new HashMap<>();
		props.put("location", "China");
		props.put("displacement", "1." + RandomUtils.getRandomNumberString(1));
		props.put("color", "BLACK");
		props.put("airbag_number", RandomUtils.getRandomNumberString(1));
		car.setProperties(props);

		// Insert
		Insert insert = mapping.insert(car, Car.class);
		System.out.println(JdbcConnector.getInstance().update(insert.getSql()));

		// Update
		Car carUp = new Car();
		carUp.setId(car.getId());
		carUp.setPrice(16_3000);
		Update update = mapping.update(carUp, Car.class);
		System.out.println(JdbcConnector.getInstance().update(update.getSql()));

		// Query
		Car carQuery = new Car();
		carQuery.setPrice(16_3000);
		carQuery.setSequenceNumbers(car.getSequenceNumbers());
		Query query = mapping.query(carQuery, Car.class);
		System.out.println(mapping.convert(JdbcConnector.getInstance().query(query.getSql()), Car.class));

		// Delete
		Car carDelete = new Car();
		carDelete.setSequenceNumbers(car.getSequenceNumbers());
		Delete delete = mapping.delete(carDelete, Car.class);
		System.out.println(JdbcConnector.getInstance().update(delete.getSql()));
	}
}
