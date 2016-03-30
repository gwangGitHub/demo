package com.gwang.designPattern.singleton.eager;

/**
 * 饿汉式单例：
 * 饿汉式单例类在自己被类加载时就将自己实例化。单从资源利用效率角度来讲，这个比懒汉式单例类稍差些。从速度和反应时间角度来讲，则比懒汉式单例类稍好些。
 * @author wanggang
 */
public class Singleton {
	
	private final static Singleton instance = new Singleton();

	private Singleton () {
		
	}
	
	public static Singleton getInstance () {
		return instance;
	}
}
