package com.github.johnsonmoon.fastorm.core.sql;

import org.junit.Test;

/**
 * Created by xuyh at 2017/9/22 11:42.
 */
public class QueryTest {
    @Test
    public void test() {
        Query query = Query.selectAll().from("table1");
        String sql = query.getSql();
        System.out.println(sql);

        Query query1 = Query.selectDistinct("name as A", "address as B", "phoneNumber as C", "email as B")
                .from("student as s").innerJoin("teacher as t").addWhere(Criteria.where("name").is("johnson"));
        String sql1 = query1.getSql();
        System.out.println(sql1);

        Query query2 = Query.selectAll().from("student").leftJoin("teacher")
                .addWhere(Criteria.where("student.teacherId").equalsField("teacher.id"))
                .addOrderBy(Order.orderBy("student.name").direction_ASC());
        String sql2 = query2.getSql();
        System.out.println(sql2);

        Query query3 = Query.select("name", "id", "phoneNumber").from("student")
                .addWhere(Criteria.where("age").notBetween(10, 12))
                .addOrderBy(Order.orderBy("name").direction_DESC());
        System.out.println(query3.getSql());

        Query query5 = Query.select("name").from("temp_student").addWhere(Criteria.where("score").gte(80));
        Query query4 = Query.selectAll().from("student").addWhere(Criteria.where("name")
                .in(query5));
        System.out.println(query4.getSql());

        System.out.println(
                Query.select("COUNT(DISTINCT name)").from("student").getSql());

        System.out.println(
                Query.select("stu_id", "SUM(score) AS sum_score").from("student_score").addGroupBy("stu_id").getSql());
    }

    @Test
    public void testComplicatedQueryCondition() {
        Query query = Query.selectDistinct("t.tenant_id", "tt.tenant_name",
                "tt.language", "t.user_id", "t.username", "t.realname",
                "t.contacts", "t.email", "t.mobile", "t.office", "t.qq",
                "t.weixin", "t.site", "t.description", "t.add_time",
                "t.skin", "t.removed", "t.user_no", "t.image_path",
                "d.name departName", "du.depart_id departId", "d.path");
        query.from("user t");
        query.leftJoin("sys_depart_user du").on("t.user_id", "du.user_id");
        query.leftJoin("sys_depart d").on("du.depart_id", "d.id");
        query.leftJoin("tenant tt").on("tt.tenant_id", "t.tenant_id");
        query.addWhere(Criteria.where(1).is(1)
                .and("t.removed").isNull()
                .and("t.user_id").is("concat('%', 'name','%')")
                .and("t.user_id").is("dadwadawda")
                .and("t.tenant_id").is("dadawdawdwad")
                .and("du.lead").is("dwadwad"));
        query.setOrder(Order.orderBy("t.add_time"));
        System.out.println(query.getSql());
    }
}
