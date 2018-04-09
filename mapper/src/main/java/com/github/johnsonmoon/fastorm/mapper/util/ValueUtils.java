package com.github.johnsonmoon.fastorm.mapper.util;

/**
 * Created by johnsonmoon at 2018/4/9 10:51.
 */
public class ValueUtils {
	/**
	 * Parse string instance into given type object.
	 * <p>
	 * <pre>
	 *     from String format   <--- Integer, Float, Byte, Double, Boolean, Character, Long, Short, String
	 * </pre>
	 *
	 * @param value String instance to be parsed
	 * @param clazz object type String instance to be parsed
	 * @param <T>   type
	 * @return object parsed
	 */
	public static <T> Object upToObject(String value, Class<T> clazz) {
		if (value == null)
			return null;

		if (clazz == null)
			return null;

		if (clazz == Float.class) {
			return Float.parseFloat(value);
		}

		if (clazz == Byte.class) {
			return Byte.parseByte(value);
		}

		if (clazz == Double.class) {
			return Double.parseDouble(value);
		}

		if (clazz == Boolean.class) {
			return Boolean.parseBoolean(value);
		}

		if (clazz == Character.class) {
			char[] chars = value.toCharArray();
			if (chars.length == 0) {
				return null;
			}
			return chars[0];
		}

		if (clazz == Long.class) {
			return Long.parseLong(value);
		}

		if (clazz == Short.class) {
			return Short.parseShort(value);
		}

		return value;
	}

	/**
	 * Get column value length by column type.
	 *
	 * @param type column value type
	 * @return length
	 */
	public static int getLengthOfColumnType(String type) {
		int result;
		if (type.contains("(") && type.contains(")")) {
			String raw = type.substring(type.indexOf("(") + 1, type.lastIndexOf(")")).trim();
			try {
				result = Integer.parseInt(raw);
			} catch (Exception e) {
				result = 0;
			}
		} else {
			result = 4;
		}
		return result;
	}
}
