package com.github.johnsonmoon.fastorm.mapper.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Xuyh at 2016/08/05 下午 06:58.
 */
public class DateUtils {
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String formatDateTime(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		String dateFormat = sdf.format(date);
		return dateFormat;
	}

	public static Date parseDateTime(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		try {
			return sdf.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date parseDateTime(Long timeStamp) {
		Format formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
		return parseDateTime(formatter.format(timeStamp));
	}
}
