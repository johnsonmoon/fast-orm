package com.github.johnsonmoon.fastorm.core.common;

import com.github.johnsonmoon.fastorm.core.sql.Criteria;
import com.github.johnsonmoon.fastorm.core.sql.Insert;
import com.github.johnsonmoon.fastorm.core.sql.Query;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by johnsonmoon at 2018/4/3 13:49.
 */
public class JdbcConnectorTest {
	@Before
	public void setUp() {
		JdbcConnector.createInstance("com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://127.0.0.1:3306/ebtest?serverTimezone=UTC",
				"user", "root",
				"password", "Root_123",
				"characterEncoding", "UTF-8",
				"useSSL", "true",
				"useUnicode", "true");
	}

	@Test
	public void testInsert() {
		String Acc_ID = "dawd1aw3d251a351d";
		Insert insert = Insert.insertInto("accounts")
				.fields("Acc_ID", "Acc_name", "Acc_pwd", "Acc_sex", "Acc_loc", "Acc_lvl",
						"Acc_ryb", "Acc_shop", "Acc_atn", "Acc_atnd", "Acc_pub", "Acc_no", "Acc_name2", "Acc_tel", "Acc_addTime")
				.values(Acc_ID, "xuyh", "xuyhtest", "M", "westLake", 1, 0, false, 0, 0, 0, "20131541520", "yhx", "157014767",
						"2018-04-03 00:00:00");
		System.out.println(insert.getSql());
		System.out.println(JdbcConnector.getInstance().update(insert.getSql()));
		Insert insert2 = Insert.insertInto("address")
				.fields("Add_ID", "Add_info", "Acc_ID", "Consign", "Con_tel", "Add_addTime")
				.values("dacawfcawc", "ddwadwa", Acc_ID, "dawdawd", "157156485", "2018-04-03 00:00:00");
		System.out.println(insert2.getSql());
		System.out.println(JdbcConnector.getInstance().update(insert2.getSql()));
	}

	@Test
	public void testQuery() {
		Query query = Query.selectAll().from("accounts as ac", "address as ad")
				.addWhere(Criteria.where("1").is(1).and("ac.Acc_ID").equalsField("ad.Acc_ID"));
		System.out.println(query.getSql());
		System.out.println(JdbcConnector.getInstance().query(query.getSql()));
	}

	@Test
	public void testQueryLeftJoin() {
		Query query = Query.selectAll().from("accounts as ac")
				.leftJoin("address as ad")
				.on("ac.Acc_ID", "ad.Acc_ID")
				.addWhere(Criteria.where("1").is(1).and("ac.Acc_ID").equalsField("ad.Acc_ID"));
		System.out.println(query.getSql());
		System.out.println(JdbcConnector.getInstance().query(query.getSql()));
	}
}
