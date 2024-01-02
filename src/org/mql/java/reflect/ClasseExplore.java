package org.mql.java.reflect;

import java.util.ArrayList;
import java.util.List;

public class ClasseExplore {
    private ProjetExplore projetExplore;
    private List<String> classNames;
	@SuppressWarnings("rawtypes")
	private List<Class> classes;

    public ClasseExplore(ProjetExplore projetExplore) {
        this.projetExplore = projetExplore;
        this.classNames = new ArrayList<>();
        this.classes = new ArrayList<>();
    }

    public List<Class<?>> showClasses() {
        List<Class<?>> classList = projetExplore.getClasse();
        if (classList != null) {
            System.out.println("*******Classes********************");
            for (Class<?> cla : classList) {
                System.out.println(cla.getName());
               
                classNames.add(cla.getName());
                
                classes.add(cla);
            }
        } else {
            System.out.println("Aucune classe n'a été chargée.");
        }
        return classList;
    }

    public List<String> getClassNames() {
        return classNames;
    }


	public List<Class> getClasses() {
        return classes;
    }
}




