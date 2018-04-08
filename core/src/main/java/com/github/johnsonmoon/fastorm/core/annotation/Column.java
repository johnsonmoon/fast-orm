package com.github.johnsonmoon.fastorm.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Column of table.
 * <p>
 * Created by johnsonmoon at 2018/4/8 10:00.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * column name.
	 *
	 * @return String
	 */
	String name();

	/**
	 * data type of database storage.
	 * <p>
	 * <pre>
	 *     examples:
	 *      "integer(size)"
	 *      "int(size)"
	 *      "smallint(size)"
	 *      "tinyint(size)"
	 *      "decimal(size,d)"
	 *      "numeric(size,d)"
	 *      "char(size)"
	 *      "varchar(size)"
	 *      "date(yyyy-MM-dd)"
	 *      etc.
	 *
	 *     default:
	 *      "varchar(50)"
	 * </pre>
	 *
	 * @return String
	 */
	String type() default "varchar(50)";

	/**
	 * column value can not be null. default value false, means column value can be null.
	 *
	 * @return Boolean
	 */
	boolean notNull() default false;

	/**
	 * column default value. default value is "NULL", means set column value NULL while insert field value is null.
	 * <p>
	 * <pre>
	 *     {@link Column#notNull()}
	 *     if notNull is true, defaultValue is invalid.
	 * </pre>
	 *
	 * @return String
	 */
	String defaultValue() default "NULL";

	/**
	 * column value is able to increase automatically. default value is false.
	 *
	 * @return Boolean
	 */
	boolean autoIncrement() default false;
}
