package com.github.johnsonmoon.fastorm.mapper;

import com.github.johnsonmoon.fastorm.mapper.impl.AnnotationMapping;
import com.github.johnsonmoon.fastorm.mapper.impl.SimpleMapping;
import com.github.johnsonmoon.fastorm.mapper.impl.XMLMapping;

/**
 * Created by johnsonmoon at 2018/4/16 17:01.
 */
public abstract class MappingFactory {
	/**
	 * Get mapping with declared class clazz.
	 *
	 * @param clazz declared class.
	 * @return {@link Mapping}
	 */
	public static Mapping getMapping(Class<?> clazz) {
		//TODO
		//scan classpath or (file directory), find if there exist xml mapping files, if does, doing with {@link XMLMapping}
		//if not, and entity class contains declared annotations (like {@link Table}) doing with {@link AnnotationMapping}
		//otherwise, doing with {@link SimpleMapping}
		if (XMLMappingFileDeclared(clazz)) {
			return new XMLMapping();
		} else if (annotationMappingConditionDeclared(clazz)) {
			return new AnnotationMapping();
		} else {
			return new SimpleMapping();
		}
	}

	private static boolean XMLMappingFileDeclared(Class<?> clazz) {
		//TODO
		return false;
	}

	private static boolean annotationMappingConditionDeclared(Class<?> clazz) {
		//TODO
		return true;
	}
}
