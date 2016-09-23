package com.gwang.reflex;

public class Person implements Voice{
	
	public String name;
	
	public int age;
	
	public Person() {
		this.name = "aaa";
		this.age = 20;
	};

	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public void sound() {
		System.out.println("Speaking......");
	}

	public void getVoiceType(int voiceType) {
		System.out.println("Human voiceType:" + voiceType);
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
}
