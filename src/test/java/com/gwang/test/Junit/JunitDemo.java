package com.gwang.test.Junit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * stub和mock定义
 * 先从stub说起,什么是stub呢,CodeSchool给出这样的定义:
 * Stub: For replacing a method with code that returns a specified result
 * 简单说就是你可以用stub去伪造(fake)一个方法,阻断对原来方法的调用
 * 接下来我们来说mock, CodeSchool上给的定义是这样的:
 * Mock:A stub with an expectations that the method gets called.
 * 简单来说mock就是stub + expectation, 说它是stub是因为它也可以像stub一样伪造方法,阻断对原来方法的调用, 
 * expectation是说它不仅伪造了这个方法,它还期望你(必须)调用这个方法,如果没有被调用到,这个test就fail了
 * @author wanggang
 */
@RunWith(MockitoJUnitRunner.class)
public class JunitDemo {

	@Mock
	private Iterator<String> iterator;

	@Test
	public void simpleTest () {
		when(iterator.next()).thenReturn("Hello").thenReturn("World");
		String result=iterator.next()+" "+iterator.next();
		System.out.println(result);
		verify(iterator, times(2)).next();
		assertEquals("Hello World", result);
	}
}
