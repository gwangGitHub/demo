package com.gwang.reflex;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 参考文章 http://www.cnblogs.com/rollenholt/archive/2011/09/02/2163758.html
 * @author wanggang
 *
 * @param <T>
 */
public class ReflexDemo<T> {

	private String className;

	private Class<?> classDemo;

	/**
	 * 反射出对应的类
	 * 
	 * @param className
	 */
	public ReflexDemo(String className) {
		this.className = className;
		try {
			classDemo = Class.forName(this.className);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得反射出的类名字
	 * 
	 * @return 类名字
	 */
	public String getClassName() {
		String className = this.classDemo.getName();
		System.out.println("类名称：" + className);
		return className;
	}

	/**
	 * 获得反射出的类实例
	 * 
	 * @return 类实例
	 */
	@SuppressWarnings("unchecked")
	public T getNewInstance() {
		try {
			T instance = (T) this.classDemo.newInstance();
			System.out.println("类实例：" + instance);
			return instance;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得反射出的类的所有构造方法
	 * 
	 * @return 构造方法数组
	 */
	public Constructor<?>[] getConstructor() {
		Constructor<?>[] cons = this.classDemo.getConstructors();
		for (int i = 0; i < cons.length; i++) {
			Class<?> p[] = cons[i].getParameterTypes();
			System.out.print("构造方法：");
			int mo = cons[i].getModifiers();
			System.out.print(Modifier.toString(mo) + " ");
			System.out.print(cons[i].getName());
			System.out.print("(");
			for (int j = 0; j < p.length; ++j) {
				System.out.print(p[j].getName() + " arg" + i);
				if (j < p.length - 1) {
					System.out.print(",");
				}
			}
			System.out.println("){}");
		}
		return cons;
	}

	/**
	 * 获得反射出的类继承的所有接口
	 * 
	 * @return 接口数组
	 */
	public Class<?>[] getInterfaces() {
		Class<?>[] intes = this.classDemo.getInterfaces();
		for (int i = 0; i < intes.length; i++) {
			System.out.println("实现接口：" + intes[i].getName());
		}
		return intes;
	}

	/**
	 * 获得反射出的类中的所有方法
	 * 
	 * @return 所有方法
	 */
	public Method[] getMethods() {
		Method[] methods = this.classDemo.getMethods();
		for (int i = 0; i < methods.length; ++i) {
			int temp = methods[i].getModifiers();
			System.out.print(Modifier.toString(temp) + " "); // 方法修饰符
																// public/private等
			Class<?> returnType = methods[i].getReturnType(); // 方法返回值
			System.out.print(returnType.getName() + "  ");
			System.out.print(methods[i].getName() + " "); // 方法名
			Class<?> para[] = methods[i].getParameterTypes(); // 方法参数
			System.out.print("(");
			for (int j = 0; j < para.length; ++j) {
				System.out.print(para[j].getName() + " " + "arg" + j);
				if (j < para.length - 1) {
					System.out.print(",");
				}
			}
			Class<?> exce[] = methods[i].getExceptionTypes(); // 方法异常
			if (exce.length > 0) {
				System.out.print(") throws ");
				for (int k = 0; k < exce.length; ++k) {
					System.out.print(exce[k].getName() + " ");
					if (k < exce.length - 1) {
						System.out.print(",");
					}
				}
			} else {
				System.out.print(")");
			}
			System.out.println();
		}
		return methods;
	}

	/**
	 * 获得反射类本类申明属性
	 * 
	 * @return
	 */
	public Field[] getFields() {
		Field[] fields = this.classDemo.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			int mo = fields[i].getModifiers(); // 权限修饰符
			String priv = Modifier.toString(mo);
			Class<?> type = fields[i].getType(); // 属性类型
			System.out.println(priv + " " + type.getName() + " "
					+ fields[i].getName() + ";");
		}
		return fields;
	}

	/**
	 * 获得反射类实现接口或父类中属性
	 * 
	 * @return
	 */
	public Field[] getSupperFields() {
		Field[] fields = this.classDemo.getFields();
		for (int i = 0; i < fields.length; i++) {
			int mo = fields[i].getModifiers(); // 权限修饰符
			String priv = Modifier.toString(mo);
			Class<?> type = fields[i].getType(); // 属性类型
			System.out.println(priv + " " + type.getName() + " "
					+ fields[i].getName() + ";");
		}
		return fields;
	}

	/**
	 * 反射调用方法
	 * @param instance
	 * @param methodName 调用方法名
	 * @param classes 调用方法参数类型
	 * @param args 调用方法参数
	 */
	public void callMethod(Object instance, String methodName, Class<?> [] classes, Object[] args) {
		try {
			// 调用类中的methodName方法，注意如果这个方法带参数的话一定要传对应的参数类型classes
			Method method = classDemo.getMethod(methodName, classes);
//			Method method = classDemo.getDeclaredMethod(methodName, classes);
			method.invoke(instance, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过反射设置类中属性值
	 * @param instance
	 */
	public void changeField(Object instance) {
		try {
			Field field = this.classDemo.getDeclaredField("name");
			field.setAccessible(true);
			field.set(instance, "bbb");
			System.out.println(field.get(instance));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ReflexDemo<Person> demo = new ReflexDemo<Person>("com.gwang.reflex.Person");
		demo.getClassName();
		Person person = demo.getNewInstance();
		demo.callMethod(person, "sound", null, null);
		Object [] methodArgs = {1};
		Class<?> [] classes = {int.class};
		demo.callMethod(person, "getVoiceType", classes, methodArgs);
		demo.changeField(person);
		Constructor<?>[] constructors = demo.getConstructor();
		try { // 构造函数数组中的构造函数顺序是由Person中构造函数位置先后顺序有关【0，1】位置颠倒会报错
			person = (Person) constructors[0].newInstance();
			System.out.println(person);
			person = (Person) constructors[1].newInstance("bbb", 10);
			System.out.println(person);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		demo.getInterfaces();
		demo.getMethods();
		demo.getFields();
		demo.getSupperFields();
	}
}
