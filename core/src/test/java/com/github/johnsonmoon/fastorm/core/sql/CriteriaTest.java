package com.github.johnsonmoon.fastorm.core.sql;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by xuyh at 2017/9/20 15:35.
 */
public class CriteriaTest {
	@Test
	public void testCriteriaGenerate() {
		Criteria criteria = Criteria.where("field1").in("select * from table").and("field2").notIn("select * from table");
		System.out.println(criteria.getCriteria());
		Assert.assertNotNull(criteria.getCriteria());
	}

	@Test
	public void testCriteria2() {
		Criteria criteria = Criteria.where("field1").in("dad", "dawd");
		System.out.println(criteria.getCriteria());
		Assert.assertNotNull(criteria);
	}

	@Test
	public void testCriteriaValue(){
		Criteria criteria = Criteria.where("address").equalsField("test.address").and("name").is(16003).and("od").lt(160).and("ld").lte(180)
				.and("td").gt(600).and("pd").gte(800)
				.or("lmk").between(10, 20)
				.or("lpl").notBetween(30, 50)
				.and("iks").in("da", "dw", "dd")
				.and("iop").in(Arrays.asList("dee", "pee", "dee"))
				.and("dd").notIn("da", "dw", "dd")
				.and("dc").notIn(Arrays.asList("dee", "pee", "dee"))
				.and("da").like("dawedawd")
				.and("da").notLike("dafdafa")
				.and("date").gte(new Date());
		System.out.println(criteria.getCriteria());
	}
}
