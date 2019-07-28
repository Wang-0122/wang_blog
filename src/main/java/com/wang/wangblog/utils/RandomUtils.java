package com.wang.wangblog.utils;

import java.util.Random;

/**
 * 
 * @author lu_feng
 * 
 */
public class RandomUtils {

    /**
     * 
     * @param ratio
     * @return
     */
    public static boolean randomTrue(Integer ratio) {
        Integer rand = new Random().nextInt(100);
        System.out.println(rand);
        return rand < ratio;
    }

    /**
     * 
     * @param ratio
     * @return
     */
    public static Integer randomChoose(Integer max) {
        return randomChoose(max, 0);
    }

    /**
     * 
     * @param max
     * @param from
     * @return
     */
    public static Integer randomChoose(Integer max, Integer from) {
        return new Random().nextInt(max - from) + from;
    }

    public static void main(String[] args) {
    	System.out.println(randomChoose(1));
//        try {
//            Integer num = 0;
//            Integer times = 1000000;
//            Integer ratio = 40;
//            for (int i = 0; i < times; i++) {
//                if (randomTrue(ratio)) {
//                    num++;
//                }
//            }
//            System.out.println(num * 100.0 / times);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
