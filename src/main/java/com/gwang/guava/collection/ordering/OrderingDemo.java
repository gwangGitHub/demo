package com.gwang.guava.collection.ordering;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.gwang.guava.pojo.Student;

public class OrderingDemo {

	public static void main(String[] args) {
		List<Student> students = Student.createStudents(10);
		System.out.println(students);
		Ordering<Student> ordering = Ordering.natural().onResultOf(new Function<Student, Comparable<Long>>() {
			public Comparable<Long> apply(Student input) {
				return input.getId();
			}
		}).nullsFirst();
		List<Student> orderStudents = ordering.sortedCopy(students);
		System.out.println(orderStudents);
		List<Student> orderDescStudents = ordering.reverse().sortedCopy(students);//反转
		System.out.println(orderDescStudents);
	}
}
