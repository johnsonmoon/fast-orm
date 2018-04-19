package com.github.johnsonmoon.fastorm.core.common;

import com.github.johnsonmoon.fastorm.core.meta.ColumnMetaInfo;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;
import com.github.johnsonmoon.fastorm.core.util.ReflectionUtils;
import com.github.johnsonmoon.fastorm.core.util.ValueUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnsonmoon at 2018/4/12 15:41.
 */
public class ObjectConverter {
	/**
	 * Convert map list into given type list.
	 *
	 * @param result        map list
	 * @param clazz         given type
	 * @param tableMetaInfo table-type mapping information
	 * @return object instance list
	 */
	@SuppressWarnings("all")
	public static <T> List<T> convert(List<LinkedHashMap<String, Object>> result, Class<T> clazz,
			TableMetaInfo tableMetaInfo) {
		List<T> tList = new ArrayList<>();
		for (Map<String, Object> map : result) {
			T t = (T) ReflectionUtils.newObjectInstance(clazz);
			for (ColumnMetaInfo columnMetaInfo : tableMetaInfo.getColumnMetaInfoList()) {
				String columnName = columnMetaInfo.getColumnName();
				String fieldName = columnMetaInfo.getFieldName();
				String fieldJavaType = columnMetaInfo.getFieldType();
				Object value = map.get(columnName);
				if (value == null)
					continue;
				ReflectionUtils.setFieldValue(t, fieldName,
						ValueUtils.parseValue(value, ReflectionUtils.getClassByName(fieldJavaType)));
			}
			tList.add(t);
		}
		return tList;
	}
}
