package com.github.johnsonmoon.fastorm;

import com.github.johnsonmoon.fastorm.core.sql.Query;
import com.github.johnsonmoon.fastorm.entity.Car;
import com.github.johnsonmoon.fastorm.entity.Order;
import com.github.johnsonmoon.fastorm.entity.Student;
import com.github.johnsonmoon.fastorm.entity.StudentOrder;
import com.github.johnsonmoon.fastorm.mapper.MappingFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by johnsonmoon at 2018/4/16 17:06.
 */
public class OrmTemplateTest {
	private OrmTemplate template;

	@Before
	public void setUp() {
		OrmFactory ormFactory = new OrmFactorySimpleImpl();
		ormFactory.init("com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC",
				"user,root,password, Root_123, characterEncoding, UTF-8, useSSL, true, useUnicode, true");
		//        ormFactory.init("org.sqlite.JDBC",
		//                "jdbc:sqlite:D:\\sqlite3\\databases\\test.db");
		template = new OrmTemplate(ormFactory);
	}

	@Test
	public void tableExistsTest() {
		boolean not = template.tableExists("K_MALL");
		Assert.assertFalse(not);
		boolean yes = template.tableExists("car");
		Assert.assertTrue(yes);
		boolean yes2 = template.tableExists(Car.class);
		Assert.assertTrue(yes2);
	}

	@Test
	public void createTableTest() {
		Assert.assertTrue(template.createTable(Student.class));
		Assert.assertTrue(template.createTable(Order.class));
		Assert.assertTrue(template.createTable(StudentOrder.class));
		Assert.assertTrue(template.createTable(MappingFactory.getMapping(Car.class).getTableMetaInfo(Car.class)));
	}

	@Test
	public void createIndexesTest() {
		Assert.assertTrue(template.createIndexes(Student.class));
		Assert.assertTrue(template.createIndexes(MappingFactory.getMapping(Order.class).getTableMetaInfo(Order.class)));
		Assert.assertTrue(template.createIndexes("student_order", Arrays.asList("student_id", "order_id")));
		Assert.assertTrue(template.createIndexes(Car.class));
	}

	@Test
	public void testQueryQuery() {
		Query query = Query.selectAll().from("car").limit(10);
		QueryResult queryResult = template.query(query);
		Assert.assertNotNull(queryResult);
		System.out.println(queryResult.count());
		System.out.println(queryResult);
	}

	@Test
	public void testQueryAllClass() {
		List<Car> cars = template.queryAll(Car.class);
		Assert.assertNotNull(cars);
		System.out.println(cars);
	}

	@Test
	public void testQueryOneQuery() {
		Car car = template.queryOne(Query.selectAll().from("car"), Car.class);
		Assert.assertNotNull(car);
		System.out.println(car);
	}
}
