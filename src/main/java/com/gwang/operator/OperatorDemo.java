package com.gwang.operator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wanggang on 26/03/2017.
 */
public class OperatorDemo {

    private static final Logger log = LoggerFactory.getLogger(OperatorDemo.class);

    public static void main(String[] args) {

        int a = 32;
        int b = 31;
        int c = 11;
        int d = -11;
        log.info("a:{},b:{},c:{},d:{}", a, b, c, d, Integer.toBinaryString(a), Integer.toBinaryString(b), Integer.toBinaryString(c));
        log.info("二进制表示a:{},b:{},c:{},d:{}",Integer.toBinaryString(a), Integer.toBinaryString(b), Integer.toBinaryString(c), Integer.toBinaryString(d));
        //与运算符用符号“&”表示，运算规律如下：两个操作数中位都为1，结果才为1，否则结果为0
        log.info("b和c与的结果是:{},二进制:{}, b和d与的结果是:{},二进制:{}", b & c, Integer.toBinaryString(b & c), b & d, Integer.toBinaryString(b & d));
        //或运算符用符号“|”表示，运算规律如下：两个位只要有一个为1，那么结果就是1，否则就为0
        log.info("a和c或的结果是:{},二进制:{},a和d或的结果是:{},二进制:{}", a | c, Integer.toBinaryString(a | c), a | d, Integer.toBinaryString(a | d));
        //异或运算符是用符号“^”表示的，运算规律是：两个操作数的位中，相同则结果为0，不同则结果为1
        log.info("b和c异或的结果是:{},二进制:{},b和d异或的结果是:{},二进制:{}", b ^ c, Integer.toBinaryString(b ^ c), b ^ d, Integer.toBinaryString(b ^ d));
        //非运算符用符号“~”表示，运算规律如下：如果位为0，结果是1，如果位为1，结果是0
        log.info("c非的结果是:{},二进制:{},d非的结果是:{},二进制:{}", ~ c, Integer.toBinaryString(~ c), ~ d, Integer.toBinaryString(~ d));
        //左移运算符"<<"，将运算符左边的对象向左移动运算符右边指定的位数（在低位补0）
        log.info("c左移2位的结果是:{},二进制:{},d左移2位的结果是:{},二进制:{}", c << 2, Integer.toBinaryString(c << 2), d << 2, Integer.toBinaryString(d << 2));
        //"有符号"右移运算符">>"，将运算符左边的对象向右移动运算符右边指定的位数。使用符号扩展机制，也就是说，如果值为正，则在高位补0，如果值为负，则在高位补1.
        log.info("c右移2位的结果是:{},二进制:{},d右移2位的结果是:{},二进制:{}", c >> 2, Integer.toBinaryString(c >> 2), d >> 2, Integer.toBinaryString(d >> 2));
        //"无符号"右移运算符">>>"，将运算符左边的对象向右移动运算符右边指定的位数。采用0扩展机制，也就是说，无论值的正负，都在高位补0.
        log.info("c无符号右移2位的结果是:{},二进制:{},d无符号右移2位的结果是:{},二进制:{}", c >>> 2, Integer.toBinaryString(c >>> 2), d >>> 2, "00" + Integer.toBinaryString(d >>> 2));
    }
}
