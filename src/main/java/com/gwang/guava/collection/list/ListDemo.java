package com.gwang.guava.collection.list;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.gwang.guava.pojo.Student;
import com.gwang.guava.pojo.StudentView;

public class ListDemo {

	public static void main(String[] args) {
		List<Student> students = Student.createStudents(10);
		System.out.println(students);
		//List转换
		List<StudentView> studentViews = Lists.transform(students, new Function<Student, StudentView>() {
			public StudentView apply(Student input) {
				StudentView studentView = new StudentView();
				studentView.setId(input.getId());
				studentView.setName(input.getName());
				return studentView;
			}
		});
		System.out.println(studentViews);
	}
}
