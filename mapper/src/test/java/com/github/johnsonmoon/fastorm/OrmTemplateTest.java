package com.github.johnsonmoon.fastorm;

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
        OrmFactory ormFactory = new OrmFactorySimpleImpl();
        ormFactory.init("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC",
                "user,root,password, Root_123, characterEncoding, UTF-8, useSSL, true, useUnicode, true");
        //        ormFactory.init("org.sqlite.JDBC",
        //                "jdbc:sqlite:D:\\sqlite3\\databases\\test.db");
        template = new OrmTemplate(ormFactory);
    }

    public void createTableTest() {
        Assert.assertTrue(template.createTable(Student.class));
        Assert.assertTrue(template.createTable(Order.class));
        Assert.assertTrue(template.createTable(StudentOrder.class));
        Assert.assertTrue(template.createTable(MappingFactory.getMapping(Car.class).getTableMetaInfo(Car.class)));
    }


    public void createIndexesTest() {
        Assert.assertTrue(template.createIndexes(Student.class));
        Assert.assertTrue(template.createIndexes(MappingFactory.getMapping(Order.class).getTableMetaInfo(Order.class)));
        Assert.assertTrue(template.createIndexes("student_order", Arrays.asList("student_id", "order_id")));
        Assert.assertTrue(template.createIndexes(Car.class));
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
        Query query = Query.selectAll()
                .from("car")
                .addWhere(Criteria.where("price").is(150000));
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
                "123123132132",
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
    public void testUpdateUpdate(){
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
    public void testUpdateFirstUpdate(){
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
    public void testUpdateTMulti(){
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
    public void testUpdateFirstT(){
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
    public void testDeleteDelete(){
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
    public void testDeleteFirstDelete(){
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
    public void testDeleteT(){
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
    public void testDeleteFirstT(){
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
}
