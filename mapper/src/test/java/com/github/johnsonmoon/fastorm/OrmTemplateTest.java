package com.github.johnsonmoon.fastorm;

import com.github.johnsonmoon.fastorm.core.common.QueryResult;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;
import com.github.johnsonmoon.fastorm.core.sql.*;
import com.github.johnsonmoon.fastorm.core.util.RandomUtils;
import com.github.johnsonmoon.fastorm.entity.Car;
import com.github.johnsonmoon.fastorm.entity.Order;
import com.github.johnsonmoon.fastorm.entity.Student;
import com.github.johnsonmoon.fastorm.entity.StudentOrder;
import com.github.johnsonmoon.fastorm.mapper.MappingFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by johnsonmoon at 2018/4/16 17:06.
 */
public class OrmTemplateTest {
	private OrmTemplate template;

	@Before
	public void setUp() {
		OrmFactory ormFactory = new OrmFactory();
		//		ormFactory.init("com.mysql.cj.jdbc.Driver",
		//				"jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC",
		//				"user,root,password, Root_123, characterEncoding, UTF-8, useSSL, true, useUnicode, true");
		ormFactory.init("org.sqlite.JDBC",
				"jdbc:sqlite:D:\\sqlite3\\databases\\test.db");
		template = new OrmTemplate(ormFactory);

		if (!template.tableExists(Student.class)) {
			Assert.assertTrue(template.createTable(Student.class));
			Assert.assertTrue(template.createIndexes(Student.class));
		}
		if (!template.tableExists(Order.class)) {
			Assert.assertTrue(template.createTable(Order.class));
			Assert.assertTrue(template.createIndexes(MappingFactory.getMapping(Order.class).getTableMetaInfo(Order.class)));
		}
		if (!template.tableExists(StudentOrder.class)) {
			Assert.assertTrue(template.createTable(StudentOrder.class));
			Assert.assertTrue(template.createIndexes("student_order", Arrays.asList("student_id", "order_id")));
		}
		if (!template.tableExists(Car.class)) {
			Assert.assertTrue(template.createTable(MappingFactory.getMapping(Car.class).getTableMetaInfo(Car.class)));
			Assert.assertTrue(template.createIndexes(Car.class));
		}

		if (!template.tableExists("car_temp")) {
			TableMetaInfo tableMetaInfo = template.getTableMetaInfo(Car.class);
			tableMetaInfo.setTableName("car_temp");
			template.createTable(tableMetaInfo);
		}
	}

	@Test
	public void dropTableTestClass() {
		Assert.assertTrue(template.dropTable(Car.class));
	}

	@Test
	public void dropTableTestString() {
		Assert.assertTrue(template.dropTable("car"));
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

	@Test
	public void testInsertTAndQueryOneT() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setSequenceNumbers(Arrays.asList("1234586", "16541654", "415641"));
		carSave.setPrice(320000);
		carSave.setCreateDate(new Date());
		carSave.setProperties(Collections.singletonMap("color", "WHITE"));
		int inserted = template.insert(carSave, Car.class);
		Assert.assertNotEquals(0, inserted);
		System.out.println(inserted);
		Car carQ = new Car();
		carQ.setId(carSave.getId());
		Car carResult = template.queryOne(carQ, Car.class);
		Assert.assertNotNull(carResult);
		System.out.println(carResult);
	}

	@Test
	public void testQueryListQuery() {
		Query query = Query.selectAll().from("car");
		List<Car> cars = template.queryList(query, Car.class);
		Assert.assertNotNull(cars);
		System.out.println(cars);
	}

	@Test
	public void testQueryListT() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setSequenceNumbers(Arrays.asList("1234586", "16541654", "415641"));
		carSave.setPrice(320000);
		carSave.setCreateDate(new Date());
		carSave.setProperties(Collections.singletonMap("color", "WHITE"));
		template.insert(carSave, Car.class);

		Car car = new Car();
		List<Car> cars = template.queryList(car, Car.class);
		Assert.assertNotNull(cars);
		System.out.println(cars);
	}

	@Test
	public void testQueryById() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setSequenceNumbers(Arrays.asList("1234586", "16541654", "415641"));
		carSave.setPrice(320000);
		carSave.setCreateDate(new Date());
		carSave.setProperties(Collections.singletonMap("color", "WHITE"));
		int inserted = template.insert(carSave, Car.class);
		Assert.assertNotEquals(0, inserted);
		System.out.println(inserted);

		Car carR = template.queryById(carSave.getId(), Car.class);
		Assert.assertNotNull(carR);
		System.out.println(carR);
	}

	@Test
	public void testCount() {
		List<Car> carList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Car car = new Car();
			car.setId(RandomUtils.getRandomString(20));
			car.setCreateDate(new Date());
			car.setPrice(150000 + i + 1);
			car.setProperties(Collections.singletonMap("color", "WHITE"));
			car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));
			carList.add(car);
		}
		System.out.println(template.insert(carList, Car.class));

		Query query = Query.selectAll()
				.from("car")
				.addWhere(Criteria.where("price").isNot(150000));
		long count = template.count(query);
		Assert.assertNotEquals(0L, count);
		System.out.println(count);
	}

	@Test
	public void testCountT() {
		Car car = new Car();
		car.setPrice(150000);
		long count = template.count(car, Car.class);
		Assert.assertNotEquals(0L, count);
		System.out.println(count);
	}

	@Test
	public void testInsertInsert() {
		Insert insert = Insert.insertInto("car").values(
				RandomUtils.getRandomString(20),
				20000,
				new Date(),
				Arrays.asList("dawdwa", "dawfaa"),
				Collections.singletonMap("color", "WHITE"));
		int aff = template.insert(insert);
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testInsertList() {
		List<Car> cars = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Car car = new Car();
			car.setId(RandomUtils.getRandomString(20));
			car.setPrice(150000);
			car.setCreateDate(new Date());
			car.setSequenceNumbers(Arrays.asList(RandomUtils.getRandomString(15), RandomUtils.getRandomString(15)));
			car.setProperties(Collections.singletonMap("color", "BLACK"));
			cars.add(car);
		}
		int aff = template.insert(cars, Car.class);
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testUpdateUpdate() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setSequenceNumbers(Arrays.asList("1234586", "16541654", "415641"));
		carSave.setPrice(320000);
		carSave.setCreateDate(new Date());
		carSave.setProperties(Collections.singletonMap("color", "WHITE"));
		int inserted = template.insert(carSave, Car.class);
		Assert.assertNotEquals(0, inserted);
		System.out.println(inserted);

		Update update = Update.update("car")
				.addWhere(Criteria.where("price").isNot(150000))
				.set("price", 501200);
		int aff = template.update(update);
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testUpdateFirstUpdate() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setPrice(36000);
		carSave.setCreateDate(new Date());
		template.insert(carSave, Car.class);

		Update update = Update.update("car")
				.set("price", 1230000)
				.addWhere(Criteria.where("price").is(36000));
		int aff = template.updateFirst(update);
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testUpdateTMulti() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setPrice(36000);
		carSave.setCreateDate(new Date());
		template.insert(carSave, Car.class);

		Car car = new Car();
		car.setId(carSave.getId());
		car.setPrice(150000);
		int aff = template.update(car, Car.class);
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testUpdateFirstT() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setPrice(36000);
		carSave.setCreateDate(new Date());
		template.insert(carSave, Car.class);

		Car car = new Car();
		car.setId(carSave.getId());
		car.setPrice(502232);
		int aff = template.updateFirst(car, Car.class);
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testDeleteDelete() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setPrice(36000);
		carSave.setCreateDate(new Date());
		template.insert(carSave, Car.class);

		Delete delete = Delete.deleteFrom("car")
				.addWhere(Criteria.where("create_date").is(carSave.getCreateDate()));
		int aff = template.delete(delete);
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testDeleteFirstDelete() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setPrice(36000);
		carSave.setCreateDate(new Date());
		template.insert(carSave, Car.class);

		Delete delete = Delete.deleteFrom("car")
				.addWhere(Criteria.where("price").isNot(150000));
		int aff = template.deleteFirst(delete);
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testDeleteT() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setPrice(150000);
		carSave.setCreateDate(new Date());
		template.insert(carSave, Car.class);

		Car car = new Car();
		car.setId(carSave.getId());
		int aff = template.delete(car, Car.class);
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testDeleteFirstT() {
		Car carSave = new Car();
		carSave.setId(RandomUtils.getRandomString(20));
		carSave.setPrice(150000);
		carSave.setCreateDate(new Date());
		template.insert(carSave, Car.class);

		Car car = new Car();
		car.setPrice(150000);
		int aff = template.deleteFirst(car, Car.class);
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testInsertAnotherTable() {
		Car car = new Car();
		car.setId(RandomUtils.getRandomString(20));
		car.setCreateDate(new Date());
		car.setPrice(150000);
		car.setProperties(Collections.singletonMap("color", "WHITE"));
		car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));

		int aff = template.insert(car, Car.class, "car_temp");
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testInsertListAnotherTable() {
		List<Car> carList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Car car = new Car();
			car.setId(RandomUtils.getRandomString(20));
			car.setCreateDate(new Date());
			car.setPrice(150000 + i);
			car.setProperties(Collections.singletonMap("color", "WHITE"));
			car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));
			carList.add(car);
		}
		int aff = template.insert(carList, Car.class, "car_temp");
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testUpdateAnotherTable() {
		Car car = new Car();
		car.setId(RandomUtils.getRandomString(20));
		car.setCreateDate(new Date());
		car.setPrice(150000);
		car.setProperties(Collections.singletonMap("color", "WHITE"));
		car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));

		System.out.println(template.insert(car, Car.class, "car_temp"));
		Car carUp = new Car();
		carUp.setId(car.getId());
		carUp.setPrice(1024589);
		int aff = template.update(carUp, Car.class, "car_temp");
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testUpdateFirstAnotherTable() {
		Car car = new Car();
		car.setId(RandomUtils.getRandomString(20));
		car.setCreateDate(new Date());
		car.setPrice(150000);
		car.setProperties(Collections.singletonMap("color", "WHITE"));
		car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));

		System.out.println(template.insert(car, Car.class, "car_temp"));

		Car carUp = new Car();
		carUp.setId(car.getId());
		carUp.setPrice(1024589);
		int aff = template.updateFirst(carUp, Car.class, "car_temp");
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testDeleteAnotherTable() {
		Car car = new Car();
		car.setId(RandomUtils.getRandomString(20));
		car.setCreateDate(new Date());
		car.setPrice(150000);
		car.setProperties(Collections.singletonMap("color", "WHITE"));
		car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));

		System.out.println(template.insert(car, Car.class, "car_temp"));

		Car carDel = new Car();
		carDel.setPrice(150000);
		int aff = template.delete(carDel, Car.class, "car_temp");
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testDeleteFirstAnotherTable() {
		List<Car> carList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Car car = new Car();
			car.setId(RandomUtils.getRandomString(20));
			car.setCreateDate(new Date());
			car.setPrice(150000);
			car.setProperties(Collections.singletonMap("color", "WHITE"));
			car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));
			carList.add(car);
		}
		System.out.println(template.insert(carList, Car.class, "car_temp"));

		Car carDel = new Car();
		carDel.setPrice(150000);
		int aff = template.deleteFirst(carDel, Car.class, "car_temp");
		Assert.assertNotEquals(0, aff);
		System.out.println(aff);
	}

	@Test
	public void testCountAnotherTable() {
		List<Car> carList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Car car = new Car();
			car.setId(RandomUtils.getRandomString(20));
			car.setCreateDate(new Date());
			car.setPrice(160000 + i);
			car.setProperties(Collections.singletonMap("color", "WHITE"));
			car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));
			carList.add(car);
		}
		System.out.println(template.insert(carList, Car.class, "car_temp"));

		Car carQ = new Car();
		carQ.setPrice(160000);
		long count = template.count(carQ, Car.class, "car_temp");
		Assert.assertNotEquals(0, count);
		System.out.println(count);
	}

	@Test
	public void testQueryByIdAnotherTable() {
		Car car = new Car();
		car.setId(RandomUtils.getRandomString(20));
		car.setCreateDate(new Date());
		car.setPrice(150000);
		car.setProperties(Collections.singletonMap("color", "WHITE"));
		car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));

		System.out.println(template.insert(car, Car.class, "car_temp"));

		Car carQ = template.queryById(car.getId(), Car.class, "car_temp");
		Assert.assertNotNull(carQ);
		System.out.println(carQ);
	}

	@Test
	public void testQueryListAnotherTable() {
		List<Car> carList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Car car = new Car();
			car.setId(RandomUtils.getRandomString(20));
			car.setCreateDate(new Date());
			car.setPrice(235210);
			car.setProperties(Collections.singletonMap("color", "WHITE"));
			car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));
			carList.add(car);
		}
		System.out.println(template.insert(carList, Car.class, "car_temp"));

		Car carQ = new Car();
		carQ.setPrice(235210);
		List<Car> cars = template.queryList(carQ, Car.class, "car_temp");
		Assert.assertNotNull(cars);
		System.out.println(cars);
	}

	@Test
	public void testQueryOneAnotherTable() {
		Car car = new Car();
		car.setId(RandomUtils.getRandomString(20));
		car.setCreateDate(new Date());
		car.setPrice(10032);
		car.setProperties(Collections.singletonMap("color", "WHITE"));
		car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));

		System.out.println(template.insert(car, Car.class, "car_temp"));

		Car carQ = new Car();
		carQ.setPrice(10032);
		Car car1 = template.queryOne(carQ, Car.class, "car_temp");
		Assert.assertNotNull(car1);
		System.out.println(car1);
	}

	@Test
	public void testQueryAllAnotherTable() {
		List<Car> carList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Car car = new Car();
			car.setId(RandomUtils.getRandomString(20));
			car.setCreateDate(new Date());
			car.setPrice(1000000);
			car.setProperties(Collections.singletonMap("color", "WHITE"));
			car.setSequenceNumbers(Arrays.asList("123123132456465", "7897897946", "415618487548"));
			carList.add(car);
		}
		System.out.println(template.insert(carList, Car.class, "car_temp"));

		List<Car> cars = template.queryAll(Car.class, "car_temp");
		Assert.assertNotNull(cars);
		System.out.println(cars);
	}

	@Test
	public void testShowIndexes() {
		QueryResult result = template.showIndexes(Student.class);
		Assert.assertNotNull(result);
		System.out.println(result);
		QueryResult result1 = template.showIndexes("student");
		Assert.assertNotNull(result1);
		System.out.println(result1);
	}

	@Test
	public void testDropIndexes() {
		Assert.assertTrue(template.dropIndexes(Student.class));
		template.dropTable("student_order");
		template.dropTable("student");
	}
}
