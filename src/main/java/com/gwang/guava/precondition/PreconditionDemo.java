package com.gwang.guava.precondition;

import com.google.common.base.Preconditions;
import com.gwang.guava.pojo.Student;

public class PreconditionDemo {

	public static void main(String[] args) {
		Student student = new Student();
		student.setName("hahahaha");
		student.setId(12);
		//guava中的toString
		System.out.println(student);
		//检查student是否为null
		Preconditions.checkNotNull(student);
		Preconditions.checkNotNull(student, "student can not be null");
		//检查student的名字长度不能大于20
		Preconditions.checkArgument(student.getName() != null && student.getName().length() <= 20, "name of student can not longer then 20");
		//检查student的状态
		Preconditions.checkState(student.isValid(), "valid of student must be true");
	}
}
