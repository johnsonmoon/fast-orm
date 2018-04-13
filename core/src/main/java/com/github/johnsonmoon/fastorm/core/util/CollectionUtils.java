package com.github.johnsonmoon.fastorm.core.util;

import java.util.List;

/**
 * Created by johnsonmoon at 2018/4/9 10:35.
 */
public class CollectionUtils {
	/**
	 * string list to string array
	 *
	 * @param strings str list
	 * @return str array
	 */
	public static String[] strListToArray(List<String> strings) {
		String[] result = new String[strings.size()];
		for (int i = 0; i < strings.size(); i++) {
			result[i] = strings.get(i);
		}
		return result;
	}

	/**
	 * object list to object array
	 *
	 * @param objects obj list
	 * @return obj array
	 */
	public static Object[] objListToArray(List<Object> objects) {
		Object[] result = new Object[objects.size()];
		for (int i = 0; i < objects.size(); i++) {
			result[i] = objects.get(i);
		}
		return result;
	}
}
