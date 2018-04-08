package com.github.johnsonmoon.fastorm.mapper.util;

/**
 * Created by johnsonmoon at 2018/4/8 16:56.
 */
public class StringUtils {
	private static final String sureName = "`%s`";

	/**
	 * Get name with `` format
	 * <p>
	 * <pre>
	 *     example:
	 *      name --> `name`
	 * </pre>
	 *
	 * @param name name
	 * @return `name`
	 */
	public static String getSureName(String name) {
		return String.format(sureName, name);
	}

	/**
	 * Get name with ``(``) format
	 * <pre>
	 *     example:
	 *      student(name) --> `student`(`name`)
	 * </pre>
	 *
	 * @param name name
	 * @return split sure name
	 */
	public static String getSplitSureName(String name) {
		if (!name.contains("(") || !name.contains(")")) {
			return name;
		}
		String table = name.substring(0, name.indexOf("(")).trim();
		String field = name.substring(name.indexOf("(") + 1, name.lastIndexOf(")")).trim();
		return String.format(sureName, table) + "(" + String.format(sureName, field) + ")";
	}
}
