package org.mql.java.reflect;


import java.util.ArrayList;
import java.util.List;

public class PackageExplore {
    private ProjetExplore projetExplore;
    public List<String> packageNames;
    public List<Class<?>> classes;
    private String name;

    public PackageExplore(ProjetExplore projetExplore) {
        this.projetExplore = projetExplore;
        this.packageNames = new ArrayList<>();
        this.classes = new ArrayList<>();
        explorePackages();
    }

    public PackageExplore(ProjetExplore projetExplore, String name) {
        this(projetExplore);
        this.name = name;
    }

   
   

    public void explorePackages() {
        List<Class<?>> classList = projetExplore.getClasse();
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
       
        for (String packageName : packageNames) {
            System.out.println("Package : " + packageName);
        }

     
        for (Class<?> clazz : classes) {
            System.out.println("Classe : " + clazz.getName());
        }
    }

    public List<String> getPackageNames() {
        return packageNames;
    }

    public List<Class<?>> getClasses() {
        return classes;
    }

    public String getName() {
        return name;
    }
}
