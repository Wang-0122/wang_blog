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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName: DateTimeUtils
 * 
 * @description
 * @author framework
 * @Date 2013-4-1
 */
public final class DateTimeUtils {

	public static void main(String[] argvs) {
		System.out.println(DateTimeUtils.getTodayWithCleanTime());
		System.out.println(DateTimeUtils.formatStrToDate("2010/12/12", DATE_TYPE_01));

		Date d1 = formatStrToDate("2012/02/23", DATE_TYPE_01);
		Date d2 = formatStrToDate("2012/12/12", DATE_TYPE_01);

		System.out.println(isValidRangeDate(d1, d2, formatStrToDate("2012/02/23", DATE_TYPE_01)));
	}

	/** the day of the format date **/
	public final static int DAY = 0;
	/** the week of the format date **/
	public final static int WEEK = 1;
	/** the time of the format date **/
	public final static int TIME = 2;
	/** the all of the format date **/
	public final static int ALL = 3;
	/** Formatter with: yyyy/MM/dd **/
	public final static int DATE_TYPE_01 = 4;

	/** Formatter with: yy年M月d日 **/
	public final static int DATE_TYPE_02 = 5;

	/** Formatter with: yyyy年M月d日 **/
	public final static int DATE_TYPE_03 = 6;

	public final static String DF_FOR_SCHEDULE = "yyyy-MM-dd HH:mm:ss";

	private final static SimpleDateFormat[] DATE_FORMATTER = new SimpleDateFormat[] { new SimpleDateFormat("MMM d, yyy"), new SimpleDateFormat("EEE, MMM d, yyyy"), new SimpleDateFormat("MMM d, yyyy HH:mm:ss"),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss"), new SimpleDateFormat("yyyy/MM/dd"), new SimpleDateFormat("yy年M月d日"), new SimpleDateFormat("yyyy年M月d日") };

	/**
	 * @return
	 */
	public static Date getTodayWithCleanTime() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);

		return today.getTime();
	}

	/**
	 * @return
	 */
	public static Date getDateWithDiff(Date date, int amount) {
		return getDateWithDiff(date, amount, Calendar.DATE);
	}

	/**
	 * @return
	 */
	public static Date getDateWithDiff(Date date, int amount, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareWithDay(Date date1, Date date2) {
		return formatDateToString(date1, DATE_TYPE_01).compareTo(formatDateToString(date2, DATE_TYPE_01));
	}

	/**
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static boolean isSameDay(Calendar c1, Calendar c2) {
		return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param from
	 * @param to
	 * @param date
	 * @return
	 */
	public static boolean isValidRangeDate(Date from, Date to, Date date) {
		boolean rel = true;

		if (from != null) {
			rel = rel && from.compareTo(date) <= 0;
		}
		if (to != null) {
			rel = rel && to.compareTo(date) >= 0;
		}
		return rel;
	}

	/**
	 * Do format date with specified type.
	 * 
	 * @param date
	 *            the date
	 * @param format
	 *            the format type
	 * @return the formatted date
	 */
	public final static String formatDateToString(Date date, int format) {
		String rel = null;
		if (date != null) {
			if (format >= DATE_FORMATTER.length || format < 0) {
				format = 0;
			}
			synchronized (DATE_FORMATTER[format]) {
				rel = DATE_FORMATTER[format].format(date);
			}
		}
		return rel;
	}

	public final static String formatDateToString(Date date, String format) {
		String rel = null;
		if (date != null) {
			rel = new SimpleDateFormat(format).format(date);
		}
		return rel;
	}

	public final static Date formatStrToDate(String strDate, int format) {
		Date rel = null;
		if (strDate != null && !strDate.equals("")) {
			if (format >= DATE_FORMATTER.length || format < 0) {
				format = 0;
			}
			synchronized (DATE_FORMATTER[format]) {
				try {
					rel = DATE_FORMATTER[format].parse(strDate);
				} catch (ParseException e) {
					rel = null;
				}
			}
		}
		return rel;
	}
}
