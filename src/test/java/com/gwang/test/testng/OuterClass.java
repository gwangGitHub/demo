package com.gwang.test.testng;

import java.util.concurrent.ConcurrentLinkedQueue;

public class OuterClass {

    public static class InnerClass1 {

    }

    public class InnerClass2 {

    }

    public static void main(String[] args) {
        new InnerClass1();
//        new InnerClass2(); //这么写会报错
        OuterClass outClass = new OuterClass();
        outClass.new InnerClass2();

        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
        queue.size();
    }
}
