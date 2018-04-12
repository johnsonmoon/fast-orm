package com.github.johnsonmoon.fastorm;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by johnsonmoon at 2018/4/12 11:11.
 */
public class QueryResultTest {
	private QueryResult queryResult;

	@Before
	public void setUp() {
		List<LinkedHashMap<String, Object>> mapList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			for (int j = 0; j < 10; j++) {
				map.put(i + "00" + j, i + "00" + j);
			}
			mapList.add(map);
		}
		queryResult = new QueryResult(mapList);
	}

	@Test
	public void testColumnOrder() {
		for (int row = 0; row < queryResult.count(); row++) {
			for (int column = 0; column < queryResult.getColumnCount(row); column++) {
				System.out.print(column + " : " + queryResult.getColumn(row, column));
				System.out.print("  ");
			}
			System.out.println("\r\n-------------------------------\r\n");
		}
	}

	@Test
	public void testGetColumnStr() {
		for (int row = 0; row < queryResult.count(); row++) {
			for (int j = 0; j < 10; j++) {
				System.out.println(queryResult.getColumn(row, row + "00" + j));
			}
		}
	}

	@Test
	public void testIterator() {
		Iterator<LinkedHashMap<String, Object>> iterator = queryResult.iterator();
		Map<String, Object> map;
		while (iterator.hasNext()) {
			map = iterator.next();
			System.out.println(map);
		}
	}

	@Test
	public void testForEach() {
		queryResult.forEach(System.out::println);
	}
}
