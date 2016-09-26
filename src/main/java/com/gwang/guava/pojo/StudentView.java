package com.gwang.guava.pojo;

import com.google.common.base.MoreObjects;


public class StudentView {

	private long id;
	private String name;
	private long schoolClass; //班级
	private boolean valid;
	
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
	
	@Override
	public String toString() {
//		return "Student [id=" + id + ", name=" + name + ", subject=" + subject
//				+ ", valid=" + valid + "]";
		return MoreObjects.toStringHelper(StudentView.class)
				.add("id", this.id)
				.add("name", this.name)
				.toString();
	}
}
