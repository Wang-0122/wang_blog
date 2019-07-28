package com.wang.wangblog.utils;

/**
 * 
 * @author Luke
 *
 */
public class BrowserTypeUtils {
	public final static String[] AGENT = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };

	/**
	 * 
	 * @param ua
	 * @return
	 */
	public static boolean checkAgentIsMobile(String ua) {
		String[] agent = AGENT;
		boolean flag = false;
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : agent) {
					if (ua.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
}
