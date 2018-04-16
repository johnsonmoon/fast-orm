package com.github.johnsonmoon.fastorm;

import org.junit.Test;

/**
 * Created by johnsonmoon at 2018/4/16 16:54.
 */
public class OrmFactoryTest {
	@Test
	public void testInit() {
		OrmFactory ormFactory = new OrmFactorySimpleImpl();
		ormFactory.init("com.mysql.jdbc.Driver",
				"jdbc:mysql://127.0.0.1:3306/test/serverTimezone=UTC",
				"user,testUser ,password, testPassword, characterEncoding, UTF-8,useSSL, true,useUnicode,true");
	}
}
