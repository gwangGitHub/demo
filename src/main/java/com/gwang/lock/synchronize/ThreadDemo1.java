package com.gwang.lock.synchronize;

public class ThreadDemo1 implements Runnable {
	
	private SDemo sDemo;
	
	public ThreadDemo1 (SDemo sDemo) {
		this.sDemo = sDemo;
	}

	public void run() {
		sDemo.method2();
	}
}
