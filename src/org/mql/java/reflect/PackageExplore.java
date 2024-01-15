package org.mql.java.reflect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PackageExplore {
	private ProjetExplore projetExplore;
	public List<String> packageNames;
	public List<Package> packages;
	public List<Class<?>> classes;
	private String name;

	public PackageExplore(ProjetExplore projetExplore) {
		this.projetExplore = projetExplore;
		this.packageNames = new ArrayList<>();
		this.classes = new ArrayList<>();
		this.packages = new ArrayList<>();
		exploreClassPackages();
		explorPackages();
	}

	public PackageExplore(ProjetExplore projetExplore, String name) {
		this(projetExplore);
		this.name = name;
	}

	
	// Explore les packages de classes et extrait des informations
	public void exploreClassPackages() {
		List<Class<?>> classList = projetExplore.getfiles();
		for (Class<?> clazz : classList) {
			Package classPackage = clazz.getPackage();

			if (classPackage != null) {
				String packageName = classPackage.getName();

				if (!packageNames.contains(packageName)) {
					packageNames.add(packageName);
				}

				classes.add(clazz);
			}
		}

		/*
		 * for (String packageName : packageNames) { System.out.println("Package : " +
		 * packageName); }
		 * 
		 * 
		 *  for (Class<?> clazz : classes) { System.out.println("Classe : " +
		 * clazz.getName()); }
		 */
	}

	// Explore les packages et remplir la liste des package
	public void explorPackages() {
		Map<String, Set<Class<?>>> packageMap = projetExplore.getPack();
		for (Set<Class<?>> classSet : packageMap.values()) {
			for (Class<?> clazz : classSet) {
				packages.add(clazz.getPackage());
			}
		}
	}

	// Getter pour les noms de packages
	public List<String> getPackageNames() {
		return packageNames;
	}

	// Getter pour les packages
	public List<Package> getPackages() {
		return packages;
	}

	// Getter pour les classes
	public List<Class<?>> getClasses() {
		return classes;
	}

	// Getter pour le nom
	public String getName() {
		return name;
	}
}
