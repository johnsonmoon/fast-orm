package com.github.johnsonmoon.fastorm.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Table
 * <p>
 * Created by johnsonmoon at 2018/4/8 10:03.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	/**
	 * Table name.
	 *
	 * @return String
	 */
	String name();

	/**
	 * Table settings. default value is "".
	 * <p>
	 * <pre>
	 *     example:
	 *      "engine=InnoDB AUTO_INCREMENT=1 CHARSET=UTF8" for mysql
	 * </pre>
	 *
	 * @return String
	 */
	String settings() default "";
}
