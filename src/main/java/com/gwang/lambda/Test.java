package com.gwang.lambda;

import java.util.HashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by gangwang on 2019/8/7.
 */
public class Test {

    public static void main(String[] args) {
        //lambda写法
        Action action = (context) -> System.out.println(context);
        action.excute("12345");

        //传统匿名内部类实现写法
        Action action1 = new Action() {
            @Override
            public void excute(String context) {
                System.out.println(context);
            }
        };
        action1.excute("12345");

        //lambda写法
        //实现方法不带大括号
        BinaryOperator<Integer> operator1 = (x, y) -> x + y;
        //当实现方法不只一行，需要带大括号
        BinaryOperator<Integer> operator2 = (x, y) -> {
            Integer a = x * 2 + y ;
            return a;
        };

        //传统匿名内部类实现写法
        BinaryOperator<Integer> operator = new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        };



        Function<String, String> function = (String string) -> string.toUpperCase();
        System.out.println(function.apply("a"));

        Stream.of("a", "b").map(function);
        Stream.of("a", "b").map((String string) -> string.toUpperCase());

        Boolean isPrefer = true;
        Boolean isPrefer1 = false;
        System.out.println(isPrefer.toString());
        System.out.println(isPrefer1.toString());
    }
}
