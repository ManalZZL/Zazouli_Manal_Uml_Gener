package org.mql.java;

import org.mql.java.reflect.ClasseExplore;
import org.mql.java.reflect.PackageExplore;
import org.mql.java.reflect.ProjetExplore;

public class Examples {

	public Examples() {
		exp01();
	}

	void exp01() {
		try {
			ProjetExplore prj = new ProjetExplore("C:\\Users\\th\\eclipse-workspace\\workM1\\jUnit2\\bin");
			PackageExplore pck = new PackageExplore(prj,"org.mql.java.generic");
			ClasseExplore cls = new ClasseExplore(prj);
			//prj.extraireRelations();
			System.out.println("**********************");
			System.out.println(pck.getName());
			System.out.println("**********************");
			cls.showClasses();
			System.out.println("**********************");
			System.out.println(cls.getClasses());
			System.out.println("**********************");
			//pck.explorePackages();
			System.out.println("**********************");
			System.out.println("pack name ::::" + pck.getPackageNames());
			System.out.println("****************");
			System.out.println("classe de pack name ::::" + pck.getClasses());
			System.out.println("**********************");
			prj.getClasses();
			System.out.println("**********************");
			prj.getInterfaces();
			System.out.println("**********************");
			prj.getPackages();
			System.out.println("**********************");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new Examples();
	}
}
