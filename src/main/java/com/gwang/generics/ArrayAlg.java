package com.gwang.generics;

import java.io.Serializable;

import javax.annotation.Nullable;

public class ArrayAlg {

	/**
	 * 范型方法<T extends Comparable & Serializable, U> 用逗号隔开多个范型类
	 * @param array
	 * @return
	 */
	public static <T extends Comparable & Serializable> Pair<T> minMax (@Nullable T [] array) {
		if (array == null || array.length == 0) {
			return null;
		}
		T min = array[0];
		T max = array[0];
		for (T one : array) {
			if (one.compareTo(min) < 0) {
				min = one;
			}
			if (one.compareTo(max) > 0) {
				max = one;
			}
		}
		return new Pair<T>(min, max);
	}
	
//	public static <T> boolean hasNulls(Pair<T> pair) {
//		return pair.getFirst() == null || pair.getSecond() == null;
//	}
	
	/**
	 * 如果方法体中没有用到T，可以用通配符？
	 * @param pair
	 * @return
	 */
	public static boolean hasNulls(Pair<?> pair) {
		return pair.getFirst() == null || pair.getSecond() == null;
	}
	
	public static void main(String[] args) {
		ArrayAlg.minMax(null);
		String [] array = {"ddd", "aaa", "ccc", "bbb", "eee"};
		Pair<String> pair = ArrayAlg.<String>minMax(array);
		System.out.println(ArrayAlg.hasNulls(pair));
		System.out.println(pair);
		
		Integer [] intArray = {345, 123, 443, 678, 223};
		Pair<Integer> intPair = ArrayAlg.<Integer>minMax(intArray);
//		System.out.println(ArrayAlg.<Integer>hasNulls(intPair));
		System.out.println(ArrayAlg.hasNulls(intPair));
		System.out.println(intPair);
	}
}
