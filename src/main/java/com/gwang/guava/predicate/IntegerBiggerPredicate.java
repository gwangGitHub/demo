package com.gwang.guava.predicate;

import com.google.common.base.Predicate;
import com.gwang.guava.pojo.Student;

public class IntegerBiggerPredicate implements Predicate<Student> {
	
	private int standard;
	
	public IntegerBiggerPredicate(int standard) {
		this.standard = standard;
	}

	public boolean apply(Student input) {
		//找出ID大于等于standard的学生
		return input.getId() >= standard;
	}

}
