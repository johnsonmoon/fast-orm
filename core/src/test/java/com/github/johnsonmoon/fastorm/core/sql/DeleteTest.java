package com.github.johnsonmoon.fastorm.core.sql;

import org.junit.Test;

/**
 * Created by xuyh at 2017/9/22 18:33.
 */
public class DeleteTest {
	@Test
	public void test() {
		System.out.println(Delete.deleteFrom("student").getSql());
		System.out.println(Delete.deleteFrom("student").addWhere(Criteria.where("name").is("Johnson")).getSql());
	}
}
