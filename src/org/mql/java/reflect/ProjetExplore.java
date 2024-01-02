package org.mql.java.reflect;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

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

		

	private void loadClass(String qName) throws ClassNotFoundException {
		loadedFiles.add(classloader.loadClass(qName));
	}

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

	public String getQName(String path) {
		int ir = path.lastIndexOf(".class");
		path = path.substring(0, ir);
		path = path.replace(project.getAbsolutePath(), "");
		path = path.replace('\\', '.');
		if (path.startsWith(".")) {
			path = path.replaceFirst(".", "");
		}
		return path;
	}

	public List<Class<?>> getfiles() {
		return loadedFiles;
	}

	public List<Class<?>> getClasse() {
		return loaAClasse;
	}

	public List<Class<?>> getAnnotation() {
		return loaAnnotation;
	}

	public List<Class<?>> getInterface() {
		return loaInterface;
	}

	public List<Class<?>> getEnum() {
		return loadEnuml;
	}

	public List<Package> getLoadedPackages() {
		List<Package> loadedPackages = new Vector<Package>();
		Collections.addAll(loadedPackages, classloader.getDefinedPackages());
		return loadedPackages;
	}

	public URLClassLoader getClassloader() {
		return classloader;
	}

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

	
	
	
	
	/*public void extraireRelations() {
	    List<Class<?>> classList = this.getfiles();

	    for (Class<?> clazz : classList) {
	        System.out.println("Relations pour la classe : " + clazz.getName());

	        // Interfaces 
	        Class<?>[] interfaces = clazz.getInterfaces();
	        for (Class<?> interf : interfaces) {
	            System.out.println("    Implémente l'interface : " + interf.getName());
	        }

	        // Classe mère 
	        Class<?> superClass = clazz.getSuperclass();
	        if (superClass != null) {
	            System.out.println("    Hérite de la classe : " + superClass.getName());
	        }

	        // champs
	        Field[] fields = clazz.getDeclaredFields();
	        for (Field field : fields) {
	            Class<?> fieldType = field.getType();
	            System.out.println("    A le champ de type : " + fieldType.getName() + " - Nom : " + field.getName());
	        }

	        //  les méthodes
	        Method[] methods = clazz.getDeclaredMethods();
	        for (Method method : methods) {
	            Class<?> returnType = method.getReturnType();
	            System.out.println("    A la méthode : " + method.getName() + " - Retourne : " + returnType.getName());

	            // Paramètres de la méthode
	            Class<?>[] paramTypes = method.getParameterTypes();
	            for (Class<?> paramType : paramTypes) {
	                System.out.println("        Paramètre : " + paramType.getName());
	            }
	        }

	        System.out.println();
	    }
	}*/


}
