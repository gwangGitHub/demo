package com.gwang.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyFactory <T> implements MethodInterceptor {
	
	private T deleget;
	
	public CglibProxyFactory(T deleget) {
		this.deleget = deleget;
	}
	
	@SuppressWarnings("unchecked")
	public T getProxy () {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(deleget.getClass()); //设置父类
		enhancer.setCallback(this);
		return (T)enhancer.create();
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("--------before-------");
		Object object = proxy.invoke(this.deleget, args);
		System.out.println("--------after-------");
		return object;
	}
	
	public static void main(String[] args) {
		UserService userService = new CglibProxyFactory<UserService>(new UserServiceImpl()).getProxy();
		userService.add();
	}
}
