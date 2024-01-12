package org.mql.java;



import org.mql.java.reflect.ClasseExplore;
import org.mql.java.reflect.PackageExplore;
import org.mql.java.reflect.ProjetExplore;
import org.mql.java.xml.XMLGenerator;

public class Examples {

	public Examples() {
		exp01();
	}

	void exp01() {
		try {
			ProjetExplore prj = new ProjetExplore("C:\\Users\\th\\eclipse-workspace\\workM1\\jUnit2\\bin");
			PackageExplore pck = new PackageExplore(prj,"org.mql.java.generic");
			ClasseExplore cls = new ClasseExplore(prj);
			XMLGenerator.generateXML(prj.getPack(), prj.extractRelations(), "C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\src\\output.xml");
//prj.extractRelations();;
//			System.out.println("**********************");
//			System.out.println(pck.getName());
//			System.out.println("**********************");
//			cls.showClasses();
//			System.out.println("**********************");
//			System.out.println(cls.getClasses());
//			System.out.println("**********************");
//			//pck.explorePackages();
//			System.out.println("**********************");
//			System.out.println("pack name ::::" + pck.getPackageNames());
//			System.out.println("****************");
//			System.out.println("classe de pack name ::::" + pck.getClasses());
//			System.out.println("**********************");
//			prj.getClasses();
//			System.out.println("**********************");
//			prj.getInterfaces();
//			System.out.println("**********************");
//			prj.getPackages();
//           System.out.println("**********************");
//			System.out.println(pck.getPackages());
//			Map<String, List<String>> classRelations = prj.extractRelations();
//
//            for (Map.Entry<String, List<String>> entry : classRelations.entrySet()) {
//                System.out.println("Class: " + entry.getKey());
//                List<String> relations = entry.getValue();
//                for (String relation : relations) {
//                    System.out.println("  " + relation);
//                }
//                System.out.println();
//            }
            

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new Examples();
	}
}
