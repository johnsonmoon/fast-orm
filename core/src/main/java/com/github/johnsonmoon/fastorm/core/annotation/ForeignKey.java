package com.github.johnsonmoon.fastorm.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by johnsonmoon at 2018/4/8 11:39.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {
	/**
	 * foreign key references.
	 * <p>
	 * <pre>
	 *     example:
	 *     "Table(_id)"
	 * </pre>
	 *
	 * @return String
	 */
	String references();
}
