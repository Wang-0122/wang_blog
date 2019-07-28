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

import java.util.HashMap;
import java.util.Map;

/**
 * @author lu_feng
 */
public class DatabaseProperties {

	private static Map<String, String> codeToValues = new HashMap<String, String>();

	/**
	 * @param code
	 * @return
	 */
	public static String getPropertyValue(String code) {
		synchronized (codeToValues) {
			return codeToValues.get(code);
		}
	}

	/**
	 * @param code
	 * @return
	 */
	public static void setPropertyValue(String code, String value) {
		synchronized (codeToValues) {
			codeToValues.put(code, value);
		}
	}

	/**
	 * @param code
	 * @return
	 */
	public static void reset() {
		synchronized (codeToValues) {
			codeToValues.clear();
		}
	}
}
