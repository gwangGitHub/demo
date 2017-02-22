package com.gwang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 参考文章 http://www.uml.org.cn/j2ee/201301102.asp
 * @author wanggang
 *
 * @param <T>
 */
public class ProxyFactory <T> implements InvocationHandler {
	
	private T deleget;
	
	public ProxyFactory(T deleget) {
		this.deleget = deleget;
	}
	
	@SuppressWarnings("unchecked")
	public T getProxy () {
		return (T)Proxy.newProxyInstance(deleget.getClass().getClassLoader(), deleget.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("--------before-------");
		Object object = method.invoke(deleget, args);
		System.out.println("--------after-------");
		return object;
	}
	
	public static void main(String[] args) {
		UserService userService = new ProxyFactory<UserService>(new UserServiceImpl()).getProxy();
		userService.add();
	}
}
