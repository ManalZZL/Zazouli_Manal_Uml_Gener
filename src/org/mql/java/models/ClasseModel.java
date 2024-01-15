package org.mql.java.models;

import java.util.List;

public class ClasseModel {

	private String name;
	private List<String> fields;

	public ClasseModel() {
	}

	public ClasseModel(String name, List<String> fields) {
		this.name = name;
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	 @Override
	    public String toString() {
	        return "ClasseModel{" +
	                "name='" + name + '\'' +
	                ", fields=" + fields +
	                '}';
	    }
}

