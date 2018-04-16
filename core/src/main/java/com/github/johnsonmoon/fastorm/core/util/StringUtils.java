package com.github.johnsonmoon.fastorm.core.util;

import com.github.johnsonmoon.fastorm.core.common.DatabaseType;

import java.util.regex.Pattern;

/**
 * Created by johnsonmoon at 2018/4/8 16:56.
 */
public class StringUtils {
	private static final Pattern SURENAME_PATTERN_POION = Pattern.compile("^.*\\..*$");

	/**
	 * Get name with `` format (Avoid key words in database) [sqlite : ""]
	 *
	 * <pre>
	 *     example:  [`` condition etc.]
	 *      name --> `name`
	 *      name as n --> `name` as n
	 *      student s --> `student` s
	 *      order.language --> `order`.`language`
	 *      COUNT(*) AS count --> COUNT(*) AS count [keep it]
	 * </pre>
	 *
	 * @param name name
	 * @return `name`
	 */
	public static String getSureName(String name) {
		//"COUNT(DISTINCT a) as count"  --->  keep it
		if (name.contains("(") && name.contains(")")) {
			return name;
		}
		//"`order`.`language`"  --->  keep it
		if (name.contains(DatabaseType.getKeywordAvoidChar())) {
			return name;
		}
		//"order.name as n" or "order.name AS n" ---> "`order`.`name` as n"
		if (name.contains(".")
				&& name.contains(" ")
				&& (name.contains("AS") || name.contains("as"))) {
			String[] array = name.split(" ");
			if (array.length > 0) {
				return name.replace(array[0], getPointSureName(array[0]));
			}
		}
		//"order.name n" ---> "`order`.`name` n"
		if (name.contains(".")
				&& name.contains(" ")) {
			String[] array = name.split(" ");
			if (array.length > 0) {
				return name.replace(array[0], getPointSureName(array[0]));
			}
		}
		//"order as o" or "order AS o" ---> "`order` as o" "`order` AS o"
		if (name.contains(" ")
				&& (name.contains("AS") || name.contains("as"))) {
			String[] array = name.split(" ");
			if (array.length > 0) {
				return name.replace(array[0], String.format(DatabaseType.getKeywordAvoidFormatStr(), array[0]));
			}
		}
		//"order o"
		if (name.contains(" ")) {
			String[] array = name.split(" ");
			if (array.length > 0) {
				return name.replace(array[0], String.format(DatabaseType.getKeywordAvoidFormatStr(), array[0]));
			}
		}
		//"order.name" ---> "`order`.`name`"
		if (SURENAME_PATTERN_POION.matcher(name).matches()) {
			return getPointSureName(name);
		}
		//"order" ---> "`order`"
		return String.format(DatabaseType.getKeywordAvoidFormatStr(), name);
	}

	private static String getPointSureName(String name) {
		if (!SURENAME_PATTERN_POION.matcher(name).matches()) {
			return name;
		}
		String[] array = name.split("\\.");
		return String.format(DatabaseType.getKeywordAvoidFormatStr(), array[0])
				+ "." + String.format(DatabaseType.getKeywordAvoidFormatStr(), array[1]);
	}

	/**
	 * Get name with ``(``) format (Avoid key words in database)
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
		return String.format(DatabaseType.getKeywordAvoidFormatStr(), table) + "("
				+ String.format(DatabaseType.getKeywordAvoidFormatStr(), field) + ")";
	}

	/**
	 * Target str contains any search str.
	 *
	 * @param target target str
	 * @param search search str array
	 * @return true/false
	 */
	public static boolean containsAny(String target, String... search) {
		for (String s : search) {
			if (target.contains(s)) {
				return true;
			}
		}
		return false;
	}
}
