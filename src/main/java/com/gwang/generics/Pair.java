package com.gwang.generics;

/**
 * 范型类
 * @author wanggang
 *
 * @param <T>
 */
public class Pair<T> {

	private T first;
	private T second;
	
	public Pair(){}
	
	public Pair(T first, T second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	public T getFirst() {
		return first;
	}
	public void setFirst(T first) {
		this.first = first;
	}
	public T getSecond() {
		return second;
	}
	public void setSecond(T second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "Pair [first=" + first + ", second=" + second + "]";
	}
}
