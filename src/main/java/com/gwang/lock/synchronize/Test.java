package com.gwang.lock.synchronize;

import java.util.HashMap;

public class Test {

	public static void main(String[] args) {
		SDemo sDemo1 = new SDemo();
//		SDemo sDemo2 = new SDemo();
		new Thread(new ThreadDemo(sDemo1)).start();
		new Thread(new ThreadDemo1(sDemo1)).start();
	}
}
