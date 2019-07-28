package com.wang.wangblog.utils;

import java.util.Date;

/**
 * 
 * @author lu_feng
 * 
 */
public class OrderHelper {
    private static long orderNum = 0l;
    private static String date;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            System.out.println(OrderHelper.getOrderNo());
        }
    }

    /**
     * 生成订单编号
     * 
     * @return
     */
    public static synchronized String getOrderNo() {
        String str = SDFFactory.SHORT_DATETIME.format(new Date());
        if (date == null || !date.equals(str)) {
            date = str;
            orderNum = 0l;
        }
        orderNum++;
        long orderNo = Long.parseLong((date)) + orderNum;
        return "S-" + orderNo + "-" + (10000 + orderNum);
    }
}
