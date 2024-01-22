package org.mql.java;

import java.util.List;


import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.mql.java.diagramme.DiagrammeApp;
import org.mql.java.diagramme.DiagrammeClasse;
import org.mql.java.diagramme.DiagrammePackage;
import org.mql.java.models.PackageModel;
import org.mql.java.reflect.ProjetExplore;
import org.mql.java.xml.XMLGenerator;
import org.mql.java.xml.XMLParser;

public class Examples {

	public Examples() {
	     lesDiagrammes();
	}

	void exp01() {
		try {

			ProjetExplore prj = new ProjetExplore("C:\\Users\\th\\eclipse-workspace\\workM1\\p05-Multithreading\\bin");
			XMLGenerator.generateXML(prj.getPack(), prj.extractRelations(),
					"C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");
			List<PackageModel> dataModel = XMLParser.parseXML(
					"C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");
			XMLParser.displayModel(dataModel);

			
//			PackageExplore pck = new PackageExplore(prj, "org.mql.java.generic");
//			ClasseExplore cls = new ClasseExplore(prj);
//			 System.out.println( prj.extractRelations());
//
//			System.out.println("**********************");
//		    System.out.println(pck.getName());
//			System.out.println("**********************");
//		    cls.showClasses();
//			System.out.println("**********************");
//			System.out.println(cls.getClasses());
//			System.out.println("**********************");
//			pck.explorPackages();
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
void diagrammeDeClasse() {
	DiagrammeClasse diagramme = new DiagrammeClasse();

	JScrollPane scrollPane = diagramme.parse("C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");

	JFrame frame = new JFrame("Diagramme de classe");
	frame.setSize(400, 300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(scrollPane);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);

}
	void diagrammeDePackage() {

		DiagrammePackage pc = new DiagrammePackage();
		JScrollPane scrollPane = pc.parse("C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");

		JFrame frame = new JFrame("Diagramme de package");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(scrollPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	void lesDiagrammes() {
		DiagrammeApp dg = new DiagrammeApp();
	}

	public static void main(String[] args) {

		new Examples();
	}
}
