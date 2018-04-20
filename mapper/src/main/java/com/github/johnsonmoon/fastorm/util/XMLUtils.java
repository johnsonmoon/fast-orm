package com.github.johnsonmoon.fastorm.util;

import com.github.johnsonmoon.fastorm.core.common.MapperException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * XML file read utilities.
 * <p>
 * Created by Xuyh at 2017年4月21日 下午7:41:42.
 */
public class XMLUtils {
	public static Document readXMLFile(String filePathName) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File(filePathName));
		} catch (DocumentException e) {
			throw new MapperException(e.getMessage(), e);
		}
		return document;
	}

	public static Document readXMLFile(File file) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			throw new MapperException(e.getMessage(), e);
		}
		return document;
	}
}
