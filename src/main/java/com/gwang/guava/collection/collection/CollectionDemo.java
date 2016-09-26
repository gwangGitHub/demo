package com.gwang.guava.collection.collection;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Collections2;
import com.gwang.guava.pojo.Student;
import com.gwang.guava.predicate.IntegerBiggerPredicate;

public class CollectionDemo {

	public static void main(String[] args) {
		List<Student> list = Student.createStudents(10);
		//筛选大于5的数字，组成新的集合
		Collection<Student> filterCollection = Collections2.filter(list, new IntegerBiggerPredicate(5));
		System.out.println(filterCollection);
		filterCollection.add(new Student(4));//向新集合中插入小于5的集合会报错抛出异常
		System.out.println(filterCollection);
	}
}
