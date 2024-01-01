package com.jsfcourse.params;

import java.io.Serializable;

public class Param implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Object name;
	private Object value;

	public Object getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Param(Object name, Object value) {
		this.name = name;
		this.value = value;
	}
	
}
