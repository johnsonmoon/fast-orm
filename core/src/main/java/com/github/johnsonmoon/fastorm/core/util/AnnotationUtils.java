package com.github.johnsonmoon.fastorm.core.util;

import com.github.johnsonmoon.fastorm.core.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by johnsonmoon at 2018/4/8 10:16.
 */
public class AnnotationUtils {
	/**
	 * get declared annotations of the given class
	 *
	 * @param clazz given class
	 * @return annotations
	 */
	public static Annotation[] getClassAnnotations(Class<?> clazz) {
		return clazz.getAnnotations();
	}

	/**
	 * Whether the given class has annotation @Model
	 *
	 * @param clazz given class
	 * @return true/false
	 */
	public static boolean hasAnnotationTable(Class<?> clazz) {
		Annotation[] annotations = clazz.getAnnotations();
		if (annotations == null || annotations.length == 0)
			return false;
		for (Annotation annotation : annotations) {
			if (annotation instanceof Table)
				return true;
		}
		return false;
	}

	/**
	 * Get annotation @Model instance of the given class if exists.
	 *
	 * @param clazz given class
	 * @return @Table annotation instance
	 */
	public static Table getAnnotationTable(Class<?> clazz) {
		if (clazz == null)
			return null;
		Annotation[] annotations = clazz.getAnnotations();
		if (annotations == null || annotations.length == 0)
			return null;
		for (Annotation annotation : annotations) {
			if (annotation instanceof Table)
				return (Table) annotation;
		}
		return null;
	}

	/**
	 * get declared annotations of the given field
	 *
	 * @param field given field
	 * @return annotations
	 */
	public static Annotation[] getFieldAnnotations(Field field) {
		return field.getAnnotations();
	}

	/**
	 * Whether the given field has annotation @Column
	 *
	 * @param field given field
	 * @return true/false
	 */
	public static boolean hasAnnotationColumn(Field field) {
		Annotation[] annotations = field.getAnnotations();
		if (annotations == null || annotations.length == 0)
			return false;
		for (Annotation annotation : annotations) {
			if (annotation instanceof Column)
				return true;
		}
		return false;
	}

	/**
	 * Get annotation @Column instance of the given field if exists.
	 *
	 * @param field given field
	 * @return @Column annotation instance
	 */
	public static Column getAnnotationColumn(Field field) {
		if (field == null)
			return null;
		Annotation[] annotations = field.getAnnotations();
		if (annotations == null || annotations.length == 0) {
			return null;
		}
		for (Annotation annotation : annotations) {
			if (annotation instanceof Column)
				return (Column) annotation;
		}
		return null;
	}

	/**
	 * Whether the given field has annotation @Id
	 *
	 * @param field given field
	 * @return true/false
	 */
	public static boolean hasAnnotationId(Field field) {
		Annotation[] annotations = field.getAnnotations();
		if (annotations == null || annotations.length == 0)
			return false;
		for (Annotation annotation : annotations) {
			if (annotation instanceof Id)
				return true;
		}
		return false;
	}

	/**
	 * Get annotation @Id instance of the given field if exists.
	 *
	 * @param field given field
	 * @return @Id annotation instance
	 */
	public static Id getAnnotationId(Field field) {
		if (field == null)
			return null;
		Annotation[] annotations = field.getAnnotations();
		if (annotations == null || annotations.length == 0) {
			return null;
		}
		for (Annotation annotation : annotations) {
			if (annotation instanceof Id)
				return (Id) annotation;
		}
		return null;
	}

	/**
	 * Whether the given field has annotation @Indexed
	 *
	 * @param field given field
	 * @return true/false
	 */
	public static boolean hasAnnotationIndexed(Field field) {
		Annotation[] annotations = field.getAnnotations();
		if (annotations == null || annotations.length == 0)
			return false;
		for (Annotation annotation : annotations) {
			if (annotation instanceof Indexed)
				return true;
		}
		return false;
	}

	/**
	 * Get annotation @Indexed instance of the given field if exists.
	 *
	 * @param field given field
	 * @return @Indexed annotation instance
	 */
	public static Indexed getAnnotationIndexed(Field field) {
		if (field == null)
			return null;
		Annotation[] annotations = field.getAnnotations();
		if (annotations == null || annotations.length == 0) {
			return null;
		}
		for (Annotation annotation : annotations) {
			if (annotation instanceof Indexed)
				return (Indexed) annotation;
		}
		return null;
	}

	/**
	 * Whether the given field has annotation @ForeignKey
	 *
	 * @param field given field
	 * @return true/false
	 */
	public static boolean hasAnnotationForeignKey(Field field) {
		Annotation[] annotations = field.getAnnotations();
		if (annotations == null || annotations.length == 0)
			return false;
		for (Annotation annotation : annotations) {
			if (annotation instanceof ForeignKey)
				return true;
		}
		return false;
	}

	/**
	 * Get annotation @ForeignKey instance of the given field if exists.
	 *
	 * @param field given field
	 * @return @ForeignKey annotation instance
	 */
	public static ForeignKey getAnnotationForeginKey(Field field) {
		if (field == null)
			return null;
		Annotation[] annotations = field.getAnnotations();
		if (annotations == null || annotations.length == 0) {
			return null;
		}
		for (Annotation annotation : annotations) {
			if (annotation instanceof ForeignKey)
				return (ForeignKey) annotation;
		}
		return null;
	}

	/**
	 * Whether the given field has annotation @PrimaryKey
	 *
	 * @param field given field
	 * @return true/false
	 */
	public static boolean hasAnnotationPrimaryKey(Field field) {
		Annotation[] annotations = field.getAnnotations();
		if (annotations == null || annotations.length == 0)
			return false;
		for (Annotation annotation : annotations) {
			if (annotation instanceof PrimaryKey)
				return true;
		}
		return false;
	}

	/**
	 * Get annotation @PrimaryKey instance of the given field if exists.
	 *
	 * @param field given field
	 * @return @PrimaryKey annotation instance
	 */
	public static PrimaryKey getAnnotationPrimaryKey(Field field) {
		if (field == null)
			return null;
		Annotation[] annotations = field.getAnnotations();
		if (annotations == null || annotations.length == 0) {
			return null;
		}
		for (Annotation annotation : annotations) {
			if (annotation instanceof PrimaryKey)
				return (PrimaryKey) annotation;
		}
		return null;
	}
}
