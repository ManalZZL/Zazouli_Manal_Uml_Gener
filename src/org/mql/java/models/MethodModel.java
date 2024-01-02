package org.mql.java.models;

import java.util.List;
import java.util.Vector;



public class MethodModel {

	private String name;
	private String modifier;
	private List<String> params;
	private String returnType;

	public MethodModel() {
		super();
		this.params = new Vector<String>();
	}

	public MethodModel(String name, String returnType, List<String> params, String modifier) {
		super();
		this.name = name;
		this.returnType = returnType;
		this.modifier = modifier;
		this.params = params;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String type) {
		this.returnType = type;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Override
	public String toString() {
		return " name=" + name + ", returnType=" + returnType + ", modifier=" + modifier + ", params="
				+ params;
	}

}

