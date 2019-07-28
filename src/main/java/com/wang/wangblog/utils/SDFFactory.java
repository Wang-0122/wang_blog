/******************************************************************************
 *                                                                             
 *                      Woodare PROPRIETARY INFORMATION                        
 *                                                                             
 *          The information contained herein is proprietary to Woodare         
 *           and shall not be reproduced or disclosed in whole or in part      
 *                    or used for any design or manufacture                    
 *              without direct written authorization from FengDa.              
 *                                                                             
 *            Copyright (c) 2013 by Woodare.  All rights reserved.             
 *                                                                             
 *****************************************************************************/
package com.wang.wangblog.utils;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName: SDFFactory
 * 
 * @description
 * @author Xinxing Jiang
 * @Date Apr 4, 2017
 */
public class SDFFactory {

	private static class ThreadSaftyDateFormat extends SimpleDateFormat {
		private static final long serialVersionUID = -797037383479610096L;

		ThreadSaftyDateFormat(String format) {
			super(format);
		}

		public synchronized StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
			return super.format(date, toAppendTo, fieldPosition);
		}

		public synchronized Date parse(String text, ParsePosition pos) {
			return super.parse(text, pos);
		}
	}

	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static final SimpleDateFormat FULL = new ThreadSaftyDateFormat("yyyyMMddHHmmssSSS");

	/**
	 * yyMMddHHmmssSSS
	 */
	public static final SimpleDateFormat PARTFULL = new ThreadSaftyDateFormat("yyMMddHHmmssSSS");

	/**
	 * yyyyMMddHHmmss
	 */
	public static final SimpleDateFormat DATETIME = new ThreadSaftyDateFormat("yyyyMMddHHmmss");

	/**
	 * yyyyMM
	 */
	public static final SimpleDateFormat MONTH = new ThreadSaftyDateFormat("yyyyMM");

	/**
	 * yyyyMMdd
	 */
	public static final SimpleDateFormat DATE = new ThreadSaftyDateFormat("yyyyMMdd");

	/**
	 * HHmmss
	 */
	public static final SimpleDateFormat TIME = new ThreadSaftyDateFormat("HHmmss");

	/**
	 * HHmm
	 */
	public static final SimpleDateFormat TIME_SHORT = new ThreadSaftyDateFormat("HHmm");

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final SimpleDateFormat DATETIME_DASH = new ThreadSaftyDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static final SimpleDateFormat DATETIME_DASH_FULL = new ThreadSaftyDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * yyyy-MM-dd HH:mm:ssZ
	 */
	public static final SimpleDateFormat DATETIME_DASH_WITH_TIMEZONE = new ThreadSaftyDateFormat("yyyy-MM-dd HH:mm:ssZ");

	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final SimpleDateFormat DATETIME_DASH_SHORT = new ThreadSaftyDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * yyyy-MM-dd
	 */
	public static final SimpleDateFormat DATE_DASH = new ThreadSaftyDateFormat("yyyy-MM-dd");

	/**
	 * yyyy/MM/dd HH:mm
	 */
	public static final SimpleDateFormat DATETIME_SLASH = new ThreadSaftyDateFormat("yyyy/MM/dd HH:mm");
	/**
	 * yyyy/MM/dd
	 */
	public static final SimpleDateFormat DATE_SLASH = new ThreadSaftyDateFormat("yyyy/MM/dd");

	/**
	 * yyMMddHHmm
	 */
	public static final SimpleDateFormat SHORT_DATETIME = new ThreadSaftyDateFormat("yyMMddHHmm");

	/**
	 * @param date
	 * @return
	 */
	public static Date getStartTimeInDay(String dateStr) {
		try {
			Date date = SDFFactory.DATE.parse(dateStr);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			return cal.getTime();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param date
	 * @return
	 */
	public static String getTimezoneStr(Integer timezone) {
		try {
			String result = "";
			if (timezone <= 0) {
				result = "+";
				timezone = Math.abs(timezone);
			}
			else {
				result = "-";
			}
			int hour = timezone / 60;
			int minute = timezone % 60;
			if (hour < 10) {
				result += "0";
			}
			result += hour;

			if (minute < 10) {
				result += "0";
			}
			result += minute;
			return result;
		} catch (Exception e) {
			return "+0000";
		}
	}

	/**
	 * @param date
	 * @return
	 */
	public static Date getEndTimeInDay(String dateStr) {
		try {
			Date date = SDFFactory.DATE.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			return cal.getTime();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return
	 */
	public static Date getDateWithDiff(int diff) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, diff);
		return calendar.getTime();
	}

	private static Integer index = 100;

	public static synchronized String getOrderNo() {
		String order = FULL.format(new Date()) + RandomUtils.randomChoose(10) + index;
		if (index >= 999) {
			index = 100;
		}
		else {
			index++;
		}
		return order;
	}

}
