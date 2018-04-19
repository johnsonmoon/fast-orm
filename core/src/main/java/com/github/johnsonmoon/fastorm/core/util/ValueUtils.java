package com.github.johnsonmoon.fastorm.core.util;

import java.util.Date;

/**
 * Created by johnsonmoon at 2018/4/9 10:51.
 */
public class ValueUtils {
	/**
	 * Parse string instance into given type object.
	 * <p>
	 * <pre>
	 *     object itself   <--- Integer, Float, Byte, Double, Boolean, Character, Long, Short, String, Date
	 *     json format string  <--- Other type
	 * </pre>
	 *
	 * @param value String instance to be parsed
	 * @param clazz object type String instance to be parsed
	 * @param <T>   type
	 * @return object parsed
	 */
	public static <T> Object parseValue(Object value, Class<T> clazz) {
		if (value == null)
			return null;

		if (clazz == null)
			return null;

		if (clazz == Float.class
				|| clazz == Byte.class
				|| clazz == Double.class
				|| clazz == Boolean.class
				|| clazz == Long.class
				|| clazz == Short.class
				|| clazz == String.class) {
			return value;
		}

		if (clazz == Date.class) {
			if (value instanceof Date) {
				return value;
			}
			if (value instanceof Long) {
				return DateUtils.parseDateTime((Long) value);
			}
			return DateUtils.parseDateTime(String.valueOf(value));
		}

		if (clazz == Character.class) {
			char[] chars = String.valueOf(value).toCharArray();
			if (chars.length == 0) {
				return null;
			}
			return chars[0];
		}

		return JsonUtils.JsonStr2Obj(String.valueOf(value), clazz);
	}

	/**
	 * Format object to String instance.
	 * <p>
	 * <pre>
	 *     Integer, Float, Byte, Double, Boolean, Character, Long, Short ---> object
	 *     String, Date ---> String instance
	 *     Other type ---> json format String instance
	 * </pre>
	 *
	 * @param object object to be format
	 * @return formatted String instance
	 */
	public static Object formatValue(Object object) {
		if (object == null)
			return "";
		if (object instanceof Integer
				|| object instanceof Float
				|| object instanceof Byte
				|| object instanceof Double
				|| object instanceof Boolean
				|| object instanceof Character
				|| object instanceof Long
				|| object instanceof Short
				|| object instanceof String)
			return object;
		if (object instanceof Date) {
			return DateUtils.formatDateTime((Date) object);
		}
		return JsonUtils.obj2JsonStr(object);
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

	/**
	 * Whether given value equals 0.
	 *
	 * @param value given value.
	 * @return true means equals 0.
	 */
	public static boolean equalsZero(Object value) {
		if (value instanceof Integer) {
			return (Integer) value == 0;
		}
		if (value instanceof Float) {
			return (Float) value == 0F;
		}
		if (value instanceof Short) {
			return (Short) value == 0;
		}
		if (value instanceof Double) {
			return (Double) value == 0;
		}
		return false;
	}
}
