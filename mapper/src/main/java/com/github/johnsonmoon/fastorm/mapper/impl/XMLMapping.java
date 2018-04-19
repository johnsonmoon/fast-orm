package com.github.johnsonmoon.fastorm.mapper.impl;

import java.util.HashMap;
import java.util.Map;

import com.github.johnsonmoon.fastorm.core.common.MapperException;
import com.github.johnsonmoon.fastorm.core.meta.TableMetaInfo;
import com.github.johnsonmoon.fastorm.core.util.ReflectionUtils;
import com.github.johnsonmoon.fastorm.mapper.AbstractMapping;

public class XMLMapping extends AbstractMapping {
	private static Map<String, TableMetaInfo> TABLE_META_INFO_CACHE = new HashMap<>();

	@Override
	public <T> TableMetaInfo getTableMetaInfo(Class<T> clazz) {
		// TODO Auto-generated method stub
		if (clazz == null)
			throw new MapperException("Unsupported operation: parameter is null.");
		String typeName = ReflectionUtils.getClassNameEntire(clazz);
		if (TABLE_META_INFO_CACHE.containsKey(typeName)) {
			return TABLE_META_INFO_CACHE.get(typeName);
		} else {
			TableMetaInfo tableMetaInfo = new TableMetaInfo();
			TABLE_META_INFO_CACHE.put(typeName, tableMetaInfo);
			return tableMetaInfo;
		}
	}
}
