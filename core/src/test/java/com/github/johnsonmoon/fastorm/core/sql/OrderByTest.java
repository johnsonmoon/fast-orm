package com.github.johnsonmoon.fastorm.core.sql;

import org.junit.Test;

/**
 * Created by xuyh at 2017/9/22 15:34.
 */
public class OrderByTest {
	@Test
	public void test() {
		System.out.println(Order.orderBy("name", "phoneNumber").direction_DESC().getOrder());
	}

	@Test
	public void testSingleOrderField(){
		System.out.println(Order.orderBy("name").direction_DESC().getOrder());
	}
}
