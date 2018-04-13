package com.github.johnsonmoon.fastorm.core.sql;

import org.junit.Test;

import java.util.Date;

/**
 * Created by xuyh at 2017/9/22 17:14.
 */
public class InsertTest {
    @Test
    public void test() {
        Insert insert = Insert.insertInto("student").values("Johnson", "12345678", "123456789", "123456@qq.com", 12, new Date());
        System.out.println(insert.getSql());

        Insert insert1 = Insert.insertInto("student").fields("name", "email", "level", "date").values("Johnson", "123456@qq.com",
                12, new Date());
        System.out.println(insert1.getSql());
    }

    @Test
    public void testInsertIntoOtherTable() {
        Insert insert = new Insert();
        insert.fields("name", "email", "level", "date").values("Johnson", "123456@qq.com", 12, new Date());
        insert.setInsertInto("abc");
        System.out.println(insert.getSql());
    }
}
