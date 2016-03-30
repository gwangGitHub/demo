package com.gwang.designPattern.singleton.lazy;

/**
 * 懒汉式单例：
 * 懒汉式单例类在实例化时，必须处理好在多个线程同时首次引用此类时的访问限制问题，
 * 特别是当单例类作为资源控制器，在实例化时必然涉及资源初始化，而资源初始化很有可能耗费大量时间，
 * 这意味着出现多线程同时首次引用此类的机率变得较大，需要通过同步化机制进行控制。
 * 
 * @author wanggang
 */
public class Singleton {

	//模式中包含一个静态私有成员变量
	private static volatile Singleton instance = null;;

	/**
	 * 单例类拥有一个私有构造函数，确保用户无法通过new关键字直接实例化它
	 */
	private Singleton() {

	}
	
	/**
	 * 如果在一开始调用getInstanceUnsafe()时，是由两个线程同时调用的（这种情况是很常见的），注意是同时，
	 * （或者是一个线程进入 if 判断语句后但还没有实例化 Singleton 时，第二个线程到达，此时 singleton 还是为 null）
	 * 这样的话，两个线程均会进入 getInstanceUnsafe()，而后由于是第一次调用 getInstanceUnsafe()，
	 * 所以存储在 Singleton 中的静态变量 singleton 为 null ，这样的话，就会让两个线程均通过 if 语句的条件判断，
	 * 然后调用 new Singleton()了，这样的话，问题就出来了，因为有两个线程，所以会创建两个实例。
	 * 
	 * @return Singleton
	 */
	@Deprecated
	public static Singleton getInstanceUnsafe() {
		if (instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
	
	/**
	 * 解决了多线程下导致的创建多个实例的问题，但是带来了程序只能串行调用getInstanceInefficient()获得instance低性能问题
	 * @return
	 */
	@Deprecated
	public static synchronized Singleton getInstanceInefficient() {
		if (instance == null) {
			instance = new Singleton();
		}
		return instance;
	}

	/**
	 * 静态公有的工厂方法，该工厂方法负责检验实例的存在性并实例化自己，然后存储在静态成员变量中，以确保只有一个实例被创建
	 * 
	 * 由于上面出现的问题中涉及到多个线程同时访问这个 getInstanceUnsafe()，那么可以先让一个线程先进入 if 语句块，然后
	 * 在外面对这个 if 语句块加锁，对第二个线程呢，由于 if 语句进行了加锁处理，所以这个进程就无法进入 if 语句块而处于阻塞状态，
	 * 当进入了 if 语句块的线程完成 new Singleton()后，这个线程便会退出 if 语句块，此时，第二个线程就从阻塞状态中恢复，
	 * 即就可以访问 if 语句块了，但是由于前面的那个线程已近创建了 Singleton 的实例，所以 singleton != null ，
	 * 此时，第二个线程便无法通过 if 语句的判断条件了，即无法进入 if 语句块了，这样便保证了整个生命周期中只存在一个实例，
	 * @return Singleton
	 */
	public static Singleton getInstance() {
		/**
		 * 这里涉及到一个名词 Double-Check Locking ，也就是双重检查锁定，为何要使用双重检查锁定呢？
		 * 所以在没有第一重instance == null的情况下，也是可以实现单例模式的？那么为什么需要第一重 instance == null 呢？
		 * 这里就涉及一个性能问题了，因为对于单例模式的话，new Singleton()只需要执行一次就 OK 了，而如果没有第一重instance == null的话，
		 * 每一次有线程进入getInstance()时，均会执行锁定操作来实现线程同步，这是非常耗费性能的，而如果加上第一重instance == null的话，
		 * 那么就只有在第一次，也就是instance ==null 成立时的情况下执行一次锁定以实现线程同步，
		 * 而以后的话，便只要直接返回 instance 实例就 OK 了而根本无需再进入 lock 语句块了，这样就可以解决由线程同步带来的性能问题了。 
		 */
		if (instance == null) {
			synchronized (Singleton.class) {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}
		return instance;
	}
	
	public static Singleton getInstanceAdvanced() {
		Singleton temp = instance; // <<< 在这里创建临时变量
		if (temp == null) {
			synchronized (Singleton.class) {
				temp = instance;
				if (temp == null) {
					temp = new Singleton();
					instance = temp;
				}
			}
		}
		return temp; // <<< 注意这里返回的是临时变量
	}
}
