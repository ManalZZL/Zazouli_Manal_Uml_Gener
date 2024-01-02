package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class AnnotationModel {
	private String name;

	private List<FieldModel> fields;
	
	public AnnotationModel() {
		fields = new Vector<FieldModel>();
		
	}

	public AnnotationModel(String name, List<FieldModel> fields, List<MethodModel> methods,
			List<ConstructeurModel> constructors, List<ClasseModel> supperClass) {
		this.name = name;
		this.fields = fields;
		
	}

	public void addField(FieldModel fieldModel) {
		fields.add(fieldModel);
	}

	public String getName() {
		return name;
	}

	public List<FieldModel> getFields() {
		return fields;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFields(List<FieldModel> fields) {
		this.fields = fields;
	}

}


