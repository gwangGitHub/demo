package com.gwang.lock.synchronize;

public class ThreadDemo implements Runnable {
	
	private SDemo sDemo;
	
	public ThreadDemo (SDemo sDemo) {
		this.sDemo = sDemo;
	}

	public void run() {
		sDemo.method21();
	}
}
