package com.gwang.guava.collection.map;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimaps;
import com.gwang.guava.pojo.Student;

public class GuavaMapDemo {

	public static void main(String[] args) {
		
		List<Student> students = Student.createStudents(10);
		
		ImmutableListMultimap<Long, Student> snapshotMap = Multimaps.index(students, new Function<Student, Long>() {
			public Long apply(Student input) {
				return Long.valueOf(input.getSchoolClass());
			}
		});
		
		ImmutableMap<Long, Collection<Student>> studentMap = snapshotMap.asMap();
		System.out.println(studentMap);
		Collection<Student> stCollection = studentMap.get(2l);
		System.out.println(stCollection);
		Collection<Student> stCollection1 = studentMap.get(2);
		System.out.println(stCollection1); //null 2 is int
	}
}
