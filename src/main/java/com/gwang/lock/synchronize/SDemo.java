package com.gwang.lock.synchronize;

public class SDemo {

	//不带任何同步锁
	public void method1() {
		System.out.println(Thread.currentThread() + "method1");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread() + "method1 finish");
	}
	
	//方法上加synchronized字段相当于synchronized(this)即用类的实例本身作为锁
	public synchronized void method2() {
		System.out.println(Thread.currentThread() + "method2");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread() + "method2 finish");
	}
	
	//由于method21和method2都为方法上加synchronized，所以用的是同一个实例的锁，执行的时候互斥，不能同时执行
	//不同实例间锁的是不同的实例，可以同时执行
	public synchronized void method21() {
		System.out.println(Thread.currentThread() + "method21");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread() + "method21 finish");
	}
	
	//方法内部用实例synchronized (this)做为锁
	public void method3() {
		synchronized (this) {
			System.out.println(Thread.currentThread() + "method3");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread() + "method3 finish");
		}
	}
	
	//method31和method3都是用类实例(this)作为锁，所以这两个方法互斥
	//method2、method21、method3、method31这四个方法本质上都是用类实例(this)作为锁，所以这四个方法互斥，不能同时执行
	public void method31() {
		synchronized (this) {
			System.out.println(Thread.currentThread() + "method31");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread() + "method31 finish");
		}
	}
	
	//静态方法上添加synchronized字段，表明是类的静态方法用类本身作为锁。
	public static synchronized void method4() {
		System.out.println(Thread.currentThread() + "method4");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread() + "method4 finish");
	}
	
	//method4、method5都是用类本身作为锁，所以这连个方法互斥，不能同时执行。
	public static synchronized void method5() {
		System.out.println(Thread.currentThread() + "method5");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread() + "method5 finish");
	}
	
	//方法内部用类本身作为锁，和在静态方法上添加synchronized字段类似。
	//method4、method5、method6三个方法都是用类本身作为锁，这三个方法互斥，不能同时执行
	//但是类的实例和类本身是不同的锁，所以method4、method5、method6不影响method2、method21、method3、method31
	public void method6() {
		synchronized (SDemo.class) {
			System.out.println(Thread.currentThread() + "method6");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread() + "method6 finish");
		}
	}
}
