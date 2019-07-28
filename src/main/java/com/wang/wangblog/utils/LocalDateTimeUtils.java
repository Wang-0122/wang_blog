package com.wang.wangblog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LocalDateTimeUtils {

	public static String formatObject(Object date, String pattern, Integer offset) {
		if (date == null || StringUtils.isEmpty(pattern) || offset == null) {
			return "";
		}
		if (date instanceof Date) {
			return format((Date) date, pattern, offset);
		}
		else if (date instanceof String) {
			return formatString((String) date, pattern, offset);
		}
		return "";
	}

	public static String format(Date date, String pattern, Integer offset) {
		if (date == null || StringUtils.isEmpty(pattern) || offset == null) {
			return "";
		}
		TimeZone tz = TimeZone.getTimeZone("UTC");
		if (offset != null) {
			tz.setRawOffset(-offset * 60 * 1000);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(tz);
		String d = sdf.format(date);
		return d;
	}

	public static String showAsTimezone(Integer offset) {
		if (offset == null) {
			return "";
		}
		String returnVal = "UTC";
		if (offset > 0) {
			returnVal += "-";
		}
		else {
			returnVal += "+";
		}
		offset = Math.abs(offset);
		int hour = offset / 60;
		if (hour >= 10) {
			returnVal += hour + ":";
		}
		else {
			returnVal += "0" + hour + ":";
		}
		int minute = offset % 60;
		if (minute >= 10) {
			returnVal += minute;
		}
		else {
			returnVal += "0" + minute;
		}
		return returnVal;
	}

	public static String formatString(String dateString, String pattern, Integer offset) {
		if (StringUtils.isEmpty(dateString) || StringUtils.isEmpty(pattern) || offset == null) {
			return "";
		}
		SimpleDateFormat sdfWithTimeZone = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		sdfWithTimeZone.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = null;
		try {
			date = sdfWithTimeZone.parse(dateString);
		} catch (ParseException e) {
			return "";
		}

		TimeZone tz = TimeZone.getTimeZone("UTC");
		if (offset != null) {
			tz.setRawOffset(-offset * 60 * 1000);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(tz);
		String d = sdf.format(date);
		return d;
	}

	public static Date parse(String dateStr, String pattern, Integer offset) {
		if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(pattern) || offset == null) {
			return null;
		}
		TimeZone tz = TimeZone.getTimeZone("UTC");
		if (offset != null) {
			tz.setRawOffset(-offset * 60 * 1000);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(tz);
		Date d = null;
		try {
			d = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

}
