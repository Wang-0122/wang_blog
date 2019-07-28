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

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: CommonUtils
 * 
 * @description
 * @author framework
 * @Date 2013-4-1
 */
public class CommonUtils {

	/**
	 * @param rewardRegx
	 * @param num
	 * @return
	 */
	public static boolean verifyRegisterReward2(String rewardRegx, Long num) {
		boolean rewardFlag = false;
		if (StringUtils.isNotEmpty(rewardRegx) && num != null) {
			String[] itemArr = rewardRegx.split(",");
			if (itemArr.length == 1 && isNumeric(itemArr[0])) {
				try {
					if (num <= Long.parseLong(itemArr[0])) {
						rewardFlag = true;
					}
				} catch (Exception e) {
				}
			}
			else {
				for (String item : itemArr) {
					if (item.indexOf("-") != -1) {
						String[] nums = item.split("-");
						if (isNumeric(nums[0]) && isNumeric(nums[1])) {
							try {
								if (num >= Long.parseLong(nums[0]) && num <= Long.parseLong(nums[1])) {
									rewardFlag = true;
									break;
								}
							} catch (Exception e) {
							}
						}
					}
					else if (isNumeric(item)) {
						try {
							if (num == Long.parseLong(item)) {
								rewardFlag = true;
								break;
							}
						} catch (Exception e) {
						}
					}
				}
			}
		}
		return rewardFlag;
	}

	/**
	 * @param rewardRegx
	 * @param num
	 * @return
	 */
	public static boolean verifyRegisterReward(String rewardRegx, Long num) {
		boolean rewardFlag = false;
		if (StringUtils.isNotEmpty(rewardRegx) && num != null) {
			String[] rewardRegxArr = rewardRegx.split(",");
			for (String regx : rewardRegxArr) {
				regx = regx.replaceAll("\\*", "\\\\d\\*").replaceAll("\\?", "\\\\d");
				rewardFlag = Pattern.compile("^" + regx + "$").matcher(num.toString()).matches();
				if (rewardFlag) {
					break;
				}
			}

		}
		return rewardFlag;
	}

	public static void main(String[] argvs) {
		String moveNearMobile = "18951812474";
		for (int i = 0; i < 200; i++) {
			moveNearMobile = moveNearMobile(moveNearMobile);
			System.out.println(moveNearMobile);
		}

		for (long index = 0; index < 400; index += 10) {
			System.out.println(index + " = " + verifyRegisterReward("(?|??|1??|2??|300)", index));
		}

	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
	}

	public static String getLookupKey(String identity, String ip) {
		String key = null;
		try {
			key = UUID.nameUUIDFromBytes((identity + ip).getBytes()).toString();
			byte[] ips = InetAddress.getByName(ip).getAddress();
			key = "DS" + (ips[3] % 2) + "-" + key;
		} catch (Exception e) {
			key = "DS0-" + UUID.randomUUID();
		}
		return key;
	}

	/**
	 * @param originLst
	 * @param distLst
	 */
	public static <T> void appendAllToList(List<T> originLst, List<T> distLst) {
		if (originLst != null) {
			for (T t : originLst) {
				distLst.add(t);
			}
		}
	}

	/**
	 * @param originLst
	 * @param distLst
	 */
	public static <T> List<T> appendAllToNewList(List<T> originLst) {
		List<T> distLst = new ArrayList<T>();
		appendAllToList(originLst, distLst);
		return distLst;
	}

	/**
	 * @param obj
	 * @return
	 */
	public static Integer convertToInt(Object obj) {
		Integer rel = null;

		if (obj != null) {
			if (obj instanceof Integer) {
				rel = (Integer) obj;
			}
			else {
				rel = Integer.parseInt(obj.toString());
			}
		}

		return rel;
	}

	/**
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static Long toLong(Object obj) {
		Long r = null;
		if (obj != null) {
			if (obj instanceof BigInteger) {
				r = ((BigInteger) obj).longValue();
			}
			else if (obj instanceof Long) {
				r = (Long) obj;
			}
			else if (obj instanceof Integer) {
				r = ((Integer) obj).longValue();
			}
			else if (obj instanceof BigDecimal) {
				r = ((BigDecimal) obj).longValue();
			}
		}
		return r;
	}

	public static String toStr(Object obj) {
		String r = null;
		if (obj != null) {
			r = obj.toString();
		}
		return r;
	}

	public static BigDecimal toBigDecimal(Object obj) {
		BigDecimal r = null;
		if (obj != null) {
			try {
				r = new BigDecimal(obj.toString());
			} catch (Exception e) {
			}
		}
		return r;
	}

	public static Integer toInteger(Object obj) {
		Integer r = null;
		if (obj != null) {
			if (obj instanceof BigInteger) {
				r = ((BigInteger) obj).intValue();
			}
			else if (obj instanceof Integer) {
				r = (Integer) obj;
			}
			else if (obj instanceof BigDecimal) {
				r = ((BigDecimal) obj).intValue();
			}
		}
		return r;
	}

	public static Date toDate(Object obj) {
		Date r = null;
		if (obj != null) {
			if (obj instanceof Timestamp) {
				r = (Timestamp) obj;
			}
			// r = obj.toString();

		}
		return r;
	}

	public static boolean toBool(Object obj) {
		boolean r = false;
		if (obj != null) {
			r = Boolean.parseBoolean(obj.toString());
		}
		return r;
	}

	/**
	 * @return
	 */
	public static <T> List<T> removeDuplicateList(List<T> lst) {
		List<T> rel = new ArrayList<T>();
		if (lst != null) {
			for (T t : lst) {
				if (!rel.contains(t)) {
					rel.add(t);
				}
			}
		}
		return rel;
	}

	/**
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Long> objectToLongList(Object obj) {
		List<Long> rels = new ArrayList<Long>();
		if (obj != null) {
			if (obj instanceof List) {
				List<Object> objLst = (List<Object>) obj;
				for (Object item : objLst) {
					if (item != null) {
						rels.add(Long.valueOf(item.toString()));
					}
				}
			}
			else if (obj instanceof Object[]) {
				Object[] objs = (Object[]) obj;
				for (Object item : objs) {
					if (item != null) {
						rels.add(Long.valueOf(item.toString()));
					}
				}
			}
			else {
				rels.add(Long.valueOf(obj.toString()));
			}
		}

		return rels;
	}

	/**
	 * @param imagePath
	 * @return
	 */
	public static String convertToRelativeFileUrl(Long attachId) {
		return attachId != null ? "/common/file/" + attachId : null;
	}

	/**
	 * @param bytes
	 * @return
	 */
	public static String formatFileSize(Long sizeBytes) {
		String size = "";
		try {
			double dSize = (double) sizeBytes;
			if (dSize >= 1024) {
				dSize /= 1024;
				size = "K";
				if (dSize >= 1024) {
					dSize /= 1024;
					size = "M";
				}
			}
			size = new DecimalFormat("0.00").format(dSize) + size;
		} catch (Exception e) {
			size = sizeBytes.toString();
		}

		return size;
	}

	/**
	 * @param type
	 * @return
	 */
	public static String convertAppTypeToNum(String type) {
		String rel = "";
		if (type.equalsIgnoreCase("ipad")) {
			rel = "2";
		}
		else if (type.equalsIgnoreCase("itouch")) {
			rel = "1";
		}
		else if (type.equalsIgnoreCase("iphone")) {
			rel = "1";
		}
		else if (type.equalsIgnoreCase("ipod")) {
			rel = "0";
		}
		return rel;
	}

	/**
	 * TODO: convert system require between description and number type
	 * 
	 * @param nums
	 * @return
	 */
	public static String convertAppNumToType(String nums) {
		String rel = "";

		if (nums != null) {
			String[] numArr = nums.split(",");
			for (String num : numArr) {
				String tmp = "";
				if (num.equals("0")) {
					tmp = "ipod";
				}
				if (num.equals("1")) {
					tmp = "itouch/iphone";
				}
				else if (num.equals("2")) {
					tmp = "ipad";
				}
				rel += "," + tmp;
			}
			rel = rel.substring(1);
		}

		return rel;
	}

	public static <T> List<T> toList(T[] arr) {
		List<T> rel = null;
		if (arr != null && arr.length > 0) {
			rel = new ArrayList<T>();
			for (T item : arr) {
				rel.add(item);
			}
		}
		return rel;
	}

	/**
	 * @param minmumOsVersion
	 * @param targetOSVersion
	 * @return
	 */
	public static boolean isAcceptpedOSVersion(String minmumOsVersion, String targetOSVersion) {
		return targetOSVersion != null && minmumOsVersion.compareToIgnoreCase(targetOSVersion) <= 0;
	}

	public static byte[] objectToBytes(Object obj) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream outputStream = new ObjectOutputStream(out);
			outputStream.writeObject(obj);
			bytes = out.toByteArray();
			outputStream.close();
		} catch (Exception e) {
		}
		return bytes;
	}

	public static Object blobToObject(byte[] bytes) throws IOException {
		Object obj = null;
		try {
			ByteArrayInputStream out = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(out);
			obj = in.readObject();
			in.close();
		} catch (Exception e) {
		}
		return obj;
	}

	/**
	 * 判断是否为合法IP
	 * 
	 * @param ipAddress
	 * @return
	 */
	public static boolean isValidIp(String ipAddress) {
		String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}

	public static String moveNearMobile(String mobile) {
		String startNum = mobile.substring(0, mobile.length() - 2);
		String endNum = mobile.substring(mobile.length() - 2);
		mobile = startNum + String.format("%02d", (Integer.parseInt(endNum) + 1) % 100);
		return mobile;
	}
}
