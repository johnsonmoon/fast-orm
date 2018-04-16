package com.github.johnsonmoon.fastorm.mapper;

/**
 * Created by johnsonmoon at 2018/4/16 17:01.
 */
public class MappingFactory {
	/**
	 * Get mapping with declared class clazz.
	 *
	 * @param clazz declared class.
	 * @return {@link Mapping}
	 */
	public static Mapping getMapping(Class<?> clazz) {
		//TODO
		return new AnnotationMapping();
	}
}
