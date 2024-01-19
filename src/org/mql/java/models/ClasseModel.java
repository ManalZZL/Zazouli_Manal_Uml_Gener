package org.mql.java.models;

import java.util.List;
import java.util.Set;

public class ClasseModel {

	private String name;
	private List<String> fields;
	private List<String> methods;
	private List<String> relations;

	public ClasseModel() {
	}

	public ClasseModel(String name, List<String> fields, List<String> methods, List<String> relations) {
		this.name = name;
		this.fields = fields;
		this.methods = methods;
		this.relations = relations;
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

	public List<String> getMethods() {
		return methods;
	}

	public void setMethods(List<String> methods) {
		this.methods = methods;
	}

	public void setRelations(List<String> relations) {
		this.relations = relations;
	}

	public List<String> getRelations() {
		return relations;
	}

	@Override
	public String toString() {
		return "ClasseModel : " + "name='" + name + '\'' + "\n \t \t  fields=" + fields + "\n \t \t  methods=" + methods
				+ "\n \t \t  relations=" + relations;
	}
}
