package com.github.johnsonmoon.fastorm;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by johnsonmoon at 2018/4/11 16:59.
 */
public class QueryResult implements Iterable<LinkedHashMap<String, Object>> {
	private List<LinkedHashMap<String, Object>> resultMapList;

	public QueryResult() {
		resultMapList = new ArrayList<>();
	}

	public QueryResult(List<LinkedHashMap<String, Object>> resultMapList) {
		this.resultMapList = resultMapList;
	}

	/**
	 * Check the result is empty.
	 *
	 * @return true means result is empty.
	 */
	public boolean isEmpty() {
		return this.resultMapList == null || count() == 0;
	}

	/**
	 * Get result count.
	 *
	 * @return count of the result map list.
	 */
	public int count() {
		if (this.resultMapList == null)
			return 0;
		return this.resultMapList.size();
	}

	/**
	 * Get row data map at row ${row}
	 *
	 * @param row row number, begin from 0.
	 * @return {@link LinkedHashMap}
	 */
	public LinkedHashMap<String, Object> getRow(int row) {
		if (count() > row) {
			return resultMapList.get(row);
		}
		return null;
	}

	/**
	 * Get column count of row ${row}.
	 *
	 * @param row row number, begin from 0.
	 * @return column count of the row
	 */
	public int getColumnCount(int row) {
		if (count() > row) {
			return resultMapList.get(row).size();
		}
		return 0;
	}

	/**
	 * Get column object data at row ${row} column ${column}
	 *
	 * @param row    row number, begin from 0.
	 * @param column column number, begin from 0.
	 * @return data object, or null if not exist.
	 */
	public Object getColumn(int row, int column) {
		if (count() > row) {
			if (resultMapList.get(row).size() > column) {
				int index = 0;
				for (Map.Entry<String, Object> entry : resultMapList.get(row).entrySet()) {
					if (index == column) {
						return entry.getValue();
					}
					index++;
				}
			}
			return null;
		}
		return null;
	}

	/**
	 * Get column object data at row ${row} with column name ${columnName}
	 *
	 * @param row        row number, begin from 0.
	 * @param columnName name of the column
	 * @return data object, or null if not exist.
	 */
	public Object getColumn(int row, String columnName) {
		if (count() > row) {
			return resultMapList.get(row).get(columnName);
		}
		return null;
	}

	/**
	 * Get result map list.
	 */
	public List<LinkedHashMap<String, Object>> getResultMapList() {
		return resultMapList;
	}

	/**
	 * Set result map list.
	 */
	public void setResultMapList(List<LinkedHashMap<String, Object>> resultMapList) {
		this.resultMapList = resultMapList;
	}

	@Override
	public Iterator<LinkedHashMap<String, Object>> iterator() {
		return resultMapList.iterator();
	}

	@Override
	public void forEach(Consumer<? super LinkedHashMap<String, Object>> action) {
		resultMapList.forEach(action);
	}

	@Override
	public Spliterator<LinkedHashMap<String, Object>> spliterator() {
		return resultMapList.spliterator();
	}

	@Override
	public String toString() {
		return "QueryResult{" +
				"resultMapList=" + resultMapList +
				'}';
	}
}
