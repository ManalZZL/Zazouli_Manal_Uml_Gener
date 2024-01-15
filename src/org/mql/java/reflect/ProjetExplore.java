package org.mql.java.reflect;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

//Classe principale pour explorer un projet Java et extraire des informations sur les classes, interfaces, etc.
public class ProjetExplore {
	public File project;
	private List<Class<?>> loadedFiles;
	private List<Class<?>> loaAClasse;
	private List<Class<?>> loaAnnotation;
	private List<Class<?>> loaInterface;
	private List<Class<?>> loadEnuml;
	private URLClassLoader classloader;
	public Map<String, Set<Class<?>>> packageslist = new HashMap<>();

	public ProjetExplore(String projectPathBin) throws ClassNotFoundException, MalformedURLException {
		project = new File(projectPathBin);
		if ((project.exists() || project.isDirectory())) {
			this.loadedFiles = new Vector<Class<?>>();
			this.loaAClasse = new ArrayList<>();
			this.loaAnnotation = new ArrayList<>();
			this.loaInterface = new ArrayList<>();
			this.loadEnuml = new ArrayList<>();
			// Crée un URLClassLoader pour charger des classes à partir du répertoire du
			// projet.
			this.classloader = new URLClassLoader(new URL[] { project.toURI().toURL() }, null);
			exploreFile();
			filtrer(projectPathBin);
		}
	}

	private void exploreFile() throws ClassNotFoundException {
		explorer(this.project);
		explorerPack(this.project);

	}

	// Explore les fichiers récursivement pour trouver les fichiers de classe
	// (.class)
	private void explorer(File file) throws ClassNotFoundException {
		File[] content = file.listFiles();
		for (File suf : content) {
			if (suf.isDirectory()) {
				explorer(suf);
			} else if (suf.getName().endsWith(".class")) {
				loadClass(getQName(suf.getAbsolutePath()));

			}
		}
	}

	// Charge une classe à partir de son nom qualifié
		private void loadClass(String qName) throws ClassNotFoundException {
			loadedFiles.add(classloader.loadClass(qName));
		}
		
		
	
	// Explore les fichiers pour détecter les packages et les associer aux classes
	private void explorerPack(File file) throws ClassNotFoundException {
		List<Package> packageList = this.getLoadedPackages();
		for (Package pkg : packageList) {
			packageslist.put(pkg.getName(), new HashSet<>());
		}
		for (Class<?> clazz : loadedFiles) {
			Package classPackage = clazz.getPackage();
			if (classPackage != null) {
				String packageName = classPackage.getName();
				packageslist.get(packageName).add(clazz);
			}
		}
	}

	
	// Getter pour les packages chargés par le classloader
				public List<Package> getLoadedPackages() {
					List<Package> loadedPackages = new Vector<Package>();
					Collections.addAll(loadedPackages, classloader.getDefinedPackages());
					return loadedPackages;
				}

	// Filtre les fichiers en fonction de leur type (classe, interface, énumération,
	// annotation)
	private void filtrer(String qName) {
		List<Class<?>> classList = this.getfiles();
		for (Class<?> clss : classList) {
			if (clss.isInterface()) {
				loaInterface.add(clss);
			} else if (clss.isEnum()) {
				loadEnuml.add(clss);
			} else if (clss.isAnnotation()) {
				loaAnnotation.add(clss);
			} else {
				loaAClasse.add(clss);
			}
		}
	}

	// Obtient le nom qualifié d'une classe à partir de son chemin absolu
	private String getQName(String path) {
		int ir = path.lastIndexOf(".class");
		path = path.substring(0, ir);
		path = path.replace(project.getAbsolutePath(), "");
		path = path.replace('\\', '.');
		if (path.startsWith(".")) {
			path = path.replaceFirst(".", "");
		}
		return path;
	}

// Getter pour tous les fichiers chargés
	public List<Class<?>> getfiles() {
		return loadedFiles;
	}

	// Getter pour les classes
	public List<Class<?>> getClasse() {
		return loaAClasse;
	}

	// Getter pour les annotations
	public List<Class<?>> getAnnotation() {
		return loaAnnotation;
	}

	// Getter pour les interfaces
	public List<Class<?>> getInterface() {
		return loaInterface;
	}

	// Getter pour les énumérations
	public List<Class<?>> getEnum() {
		return loadEnuml;
	}

	// Getter pour les packages associés aux classes
	public Map<String, Set<Class<?>>> getPack() {
		return packageslist;
	}

	
	// Getter pour le classloader
	public URLClassLoader getClassloader() {
		return classloader;
	}

	// Affiche les noms chargées

	public void getClasses() {
		List<Class<?>> classList = this.getClasse();
		if (classList != null) {
			System.out.println("*******Classes********************");
			for (Class<?> cla : classList) {
				System.out.println(cla.getName());
			}
		} else {
			System.out.println("Aucune classe n'a été chargée.");
		}
	}

	public void getInterfaces() {
		List<Class<?>> classList = this.getInterface();
		if (classList != null) {
			System.out.println("**********interfaces**************");
			for (Class<?> cla : classList) {
				System.out.println(cla.getName());
			}
		} else {
			System.out.println("Aucune interface n'a été chargée.");
		}
	}

	public void getAnnotations() {
		List<Class<?>> classList = this.getAnnotation();
		if (classList != null) {
			System.out.println("********Annotation******************");
			for (Class<?> cla : classList) {
				System.out.println(cla.getName());
			}
		} else {
			System.out.println("Aucune annotation n'a été chargée.");
		}
	}

	public void getEnums() {
		List<Class<?>> classList = this.getEnum();
		if (classList != null) {
			System.out.println("*********ennumeration**************");
			for (Class<?> cla : classList) {
				System.out.println(cla.getName());
			}
		} else {
			System.out.println("Aucune ennumeration n'a été chargée.");
		}
	}

	public void getPackages() {
		List<Package> packageList = this.getLoadedPackages();
		System.out.println("*********les Packages*********");
		for (Package pkg : packageList) {
			System.out.println(pkg.getName());
		}
	}

	// Extrait et affiche les relations entre les classes (agrégation, composition,
	// héritage, implémentation)
	
	/* public void extractRelations() {
		List<Class<?>> classList = this.getClasse();

		for (Class<?> clazz : classList) {
			System.out.println("*-*-*-**--*-*-*-*-**-*-*-*-*-*-**--*-**-*-*---*-");
			System.out.println("Les relation de la classe  : " + clazz.getSimpleName());

			// Aggregation ou Composition
			
			 * Constructor<?> constructeur[] = clazz.getDeclaredConstructors(); for
			 * (Constructor<?> cns : constructeur) { Parameter[] parameters =
			 * cns.getParameters(); for (Parameter prm : parameters) { Class<?> paramType
			 * =prm.getType(); if (!paramType.isPrimitive() &&
			 * !paramType.equals(String.class) && !paramType.equals(Object.class)) {
			 * System.out.println("        Composes: " + paramType.getSimpleName()); }
			

			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				Class<?> fieldType = field.getType();
				System.out.println(
						"    a une propriete de type : " + fieldType.getName() + " - Name: " + field.getName());

				// Vérifie s'il s'agit d'une relation d'agrégation ou de composition
				if (!fieldType.isPrimitive() && !fieldType.equals(String.class) && !fieldType.equals(Object.class)) {
					if (Collection.class.isAssignableFrom(fieldType) || fieldType.isArray()) {
						System.out.println("        Aggregates: " + fieldType.getSimpleName());
					} else {
						System.out.println("        Composes: " + fieldType.getSimpleName());
					}
				}
			}

			// superclass
			Class<?> superClass = clazz.getSuperclass();
			//exclure les classes héritées du package java.
			if (superClass != null && !superClass.getName().startsWith("java.")) {
				System.out.println("    herite de la class: " + superClass.getName());
			}

			// interfaces
			Class<?>[] interfaces = clazz.getInterfaces();
			for (Class<?> iface : interfaces) {
				System.out.println("    Implemente l' interface: " + iface.getName());
			}

			System.out.println();
		}
	}
	*/
	public Map<String, List<String>> extractRelations() {
	    Map<String, List<String>> classRelations = new HashMap<>();

	    List<Class<?>> classList = this.getClasse();

	    for (Class<?> clazz : classList) {
	        List<String> relations = new ArrayList<>();

	        // Aggregation or Composition
	        Field[] fields = clazz.getDeclaredFields();
	        for (Field field : fields) {
	            Class<?> fieldType = field.getType();

	           // aggregation ou composition
	            if (!fieldType.isPrimitive() && !fieldType.equals(String.class) && !fieldType.equals(Object.class)) {
	                if (Collection.class.isAssignableFrom(fieldType) || fieldType.isArray()) {
	                    relations.add("Aggregates:------<> " + fieldType.getSimpleName());
	                } else {
	                    relations.add("Composes: -------<" + fieldType.getSimpleName()+">");
	                }
	            }
	        }

	        // Superclass
	        Class<?> superClass = clazz.getSuperclass();
	      
	        if (superClass != null && !superClass.getName().startsWith("java.")) {
	            relations.add("extends: " + superClass.getSimpleName());
	        }

	        // Interfaces
	        Class<?>[] interfaces = clazz.getInterfaces();
	        for (Class<?> iface : interfaces) {
	            relations.add("Implements: " + iface.getSimpleName());
	            
	        }

	        classRelations.put(clazz.getSimpleName(), relations);
	    }

	    return classRelations;
	}
}
