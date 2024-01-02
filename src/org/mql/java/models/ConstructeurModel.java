package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class ConstructeurModel {

	private String name;

	private String modifier;

	private List<String> paras;

	public ConstructeurModel() {
		super();
		this.paras = new Vector<String>();
	}

	public ConstructeurModel(String name, List<String> paras, String modifier) {
		super();
		this.name = name;
		this.modifier = modifier;
		this.paras = paras;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getParams() {
		return paras;
	}

	public void setParams(List<String> params) {
		this.paras = params;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Override
	public String toString() {
		return "name=" + name + ", modifier=" + modifier + ", params=" + paras;
	}

}
