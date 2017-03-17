package com.gwang.serialize;

import java.io.Serializable;

public class SerializeDemo implements Serializable {

	private static final long serialVersionUID = 2256488703338713426L;
	
	private int id;
	private String value;
	
	public SerializeDemo() {
	}
	
	public SerializeDemo(int id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SerializeDemo [id=" + id + ", value=" + value + "]";
	}
}
