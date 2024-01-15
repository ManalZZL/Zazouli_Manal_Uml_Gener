package org.mql.java.models;

import java.util.ArrayList;
import java.util.List;


public class PackageModel {

    private String name;
    private List<ClasseModel> classes;

    public PackageModel(String name) {
    	this.classes = new ArrayList<>(); 
    	this.name = name;
    }

    public PackageModel(String name, List<ClasseModel> classes) {
        this(name);
        this.classes = classes;
    }

    public String getName() {
        return name;
    }

    public List<ClasseModel> getClasses() {
        return classes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClasses(List<ClasseModel> classes) {
        this.classes = classes;
    }

	public void addClass(ClasseModel classeModel) {
		classes.add(classeModel);
		
	}

   
}
