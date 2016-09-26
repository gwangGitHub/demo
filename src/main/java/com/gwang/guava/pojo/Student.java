package com.gwang.guava.pojo;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;


public class Student implements Comparable<Student> {

	private long id;
	private String name;
	private long schoolClass; //班级
	private boolean valid;
	
	public Student() {};
	
	public Student(int id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSchoolClass() {
		return schoolClass;
	}
	public void setSchoolClass(long schoolClass) {
		this.schoolClass = schoolClass;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public static List<Student> createStudents(int num) {
		List<Student> students = new ArrayList<Student>();
		for (int i = 0; i < num; i++) {
			Student student = new Student();
			student.setId(i);
			student.setName("name" + i);
			student.setSchoolClass(i % 3);
			students.add(student);
		}
		return students;
	}
	
	@Override
	public String toString() {
//		return "Student [id=" + id + ", name=" + name + ", subject=" + subject
//				+ ", valid=" + valid + "]";
		return MoreObjects.toStringHelper(Student.class)
				.add("id", this.id)
				.add("name", this.name)
				.toString();
	}

	public int compareTo(Student that) {
		//ugly
//		int c1 = Long.valueOf(this.id).compareTo(Long.valueOf(this.getId()));
//		if (c1 != 0) {
//			System.out.println("id is different");
//			return c1;
//		}
//		
//		int c2 = this.name.compareTo(this.getName());
//		if (c2 != 0) {
//			System.out.println("name is different");
//			return c2;
//		}
		//利用guava的ComparisonChain优化比较代码
		return ComparisonChain.start()
//				.compare(this.id, that.id)
				.compare(this.name, that.name)
				.result();
	}
}
