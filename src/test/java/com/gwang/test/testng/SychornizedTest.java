package com.gwang.test.testng;

import com.gwang.lock.synchronize.SDemo;
import com.gwang.lock.synchronize.ThreadDemo;
import com.gwang.lock.synchronize.ThreadDemo1;
import org.testng.annotations.Test;


public class SychornizedTest {

    @Test
    public void test1(){
        SDemo sDemo1 = new SDemo();
        new Thread(new ThreadDemo(sDemo1)).start();
        new Thread(new ThreadDemo1(sDemo1)).start();
    }
}
