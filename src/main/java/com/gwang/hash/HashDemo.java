package com.gwang.hash;

import java.util.*;

/**
 * Created by wanggang on 26/03/2017.
 */
public class HashDemo {

    public static void main(String[] args) {
        HashDemo demo = new HashDemo();
        System.out.println(demo.hashCode());
        HashDemo demo1 = new HashDemo();
        System.out.println(demo1.hashCode());
        HashMap map;
        System.out.println(Integer.toBinaryString((1 << 4) - 1));
        System.out.println(Integer.toBinaryString(1 << 4));
        System.out.println(Integer.toBinaryString(1 << 8 | 1 << 4 | 3));
        System.out.println(Integer.toBinaryString(1 << 8 & 1 << 4 & 3));
        System.out.println(1 << 30);

        Integer a = 128;
        Integer b = 128;
        Integer c = 127;
        Integer d = 127;
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(c == d);
        System.out.println(c.equals(d));

        String str = "哈哈哈哈";
        System.out.println(str.hashCode());

        System.out.println(668135936 & (32-1));
        Objects.hash();

        int i = 4090;
        long start1 = System.currentTimeMillis();
        for (int j = 0; j < i; j++) {
            createWaybillId(123, i);
        }
        long time1 = System.currentTimeMillis() - start1;
        System.out.println(time1);

        long start2 = System.currentTimeMillis();
        for (int j = 0; j < i; j++) {
            settlementId(123, i);
        }
        long time2 = System.currentTimeMillis() - start2;
        System.out.println(time2);

        List<Integer> list = new ArrayList<Integer>();
        initializeArray(list);
        System.out.println(list);

        System.out.println(4l & 4095);
        System.out.println(4l & 4096);
    }

    public static long settlementId (long workerId, long seq) {
        long id = (System.currentTimeMillis() - 1483200000000l) << (22) | (workerId << 12) | (seq & 4095);
        return id;
    }

    public static long createWaybillId (long deviceNumber, long seq) {
        long id = Long.parseLong(System.currentTimeMillis() / 1000L + String.format("%03d", deviceNumber) + String.format("%03d", seq));
        return id;
    }

    /**
     * 初始化随机数组
     *
     * @param list
     */
    private static void initializeArray(List<Integer> list) {
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
    }

}
