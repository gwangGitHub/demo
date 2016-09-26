package com.gwang.guava.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;


public class CacheDemo {
	
	private CacheDemo (){};
	
	//缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
	private static LoadingCache<Integer, String> cache 
			//CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
			= CacheBuilder.newBuilder()
			//设置并发级别为8，并发级别是指可以同时写缓存的线程数
			.concurrencyLevel(8)
			//设置写缓存后8秒钟过期
			.expireAfterWrite(8, TimeUnit.SECONDS)
			//设置缓存容器的初始容量为10
	        .initialCapacity(10)
	        //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
	        .maximumSize(100)
	        //设置要统计缓存的命中率
	        .recordStats()
			//build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
			//设置缓存的移除通知
	        .removalListener(new RemovalListener<Object, Object>() {
				public void onRemoval(RemovalNotification<Object, Object> notification) {
					System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
				}
	        })
			.build(
					new CacheLoader<Integer, String>() {
	                    @Override
	                    public String load(Integer key) throws Exception {
	                        System.out.println("load " + key);
	                        return "aaa";
	                    }
	                }
			);
	
	public LoadingCache<Integer,String> getInstanceCache() {
		return cache;
	}
	
	public static void put(Integer key, String value) {
		cache.put(key, value);
	}
	
	public static String get(Integer key) throws ExecutionException {
		return cache.get(key);
	}
	
	@Test
	public void testGuavaTest() {
		CacheDemo.put(1, "bbb");
		for (int i = 0; i < 20; i++) {
			try {
				System.out.println(CacheDemo.get(i));
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
