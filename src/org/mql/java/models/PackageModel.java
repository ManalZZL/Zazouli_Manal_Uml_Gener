package org.mql.java.models;

import java.util.List;

import java.util.Vector;

import org.mql.java.reflect.PackageExplore;

public class PackageModel {

	private String name;

	private List<PackageModel> packages;

	private List<Object> classes;

	public PackageModel(PackageExplore pkg) {
		name = pkg.getName();
		packages = new Vector<PackageModel>();
	}

	public List<PackageModel> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageModel> packages) {
		this.packages = packages;
	}

	public List<Object> getClasses() {
		return classes;
	}

	public void setClasses(List<Object> classes) {
		this.classes = classes;
	}

	public String getName() {
		return name;
	}

	public void addPackage(PackageModel pack) {
		packages.add(pack);
	}

	public void addClass(ClasseModel classe) {
		classes.add(classe);
	}

}
