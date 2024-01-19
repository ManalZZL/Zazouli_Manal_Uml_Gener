package org.mql.java;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.mql.java.diagramme.DiagrammeDOM;
import org.mql.java.diagramme.DiagrammePackage;
import org.mql.java.diagramme.Relationui;
import org.mql.java.models.PackageModel;
import org.mql.java.reflect.ClasseExplore;
import org.mql.java.reflect.PackageExplore;
import org.mql.java.reflect.ProjetExplore;
import org.mql.java.xml.XMLGenerator;
import org.mql.java.xml.XMLParser;

public class Examples {

	public Examples() {
		exp02();
	}

	void exp01() {
		try {

			ProjetExplore prj = new ProjetExplore("C:\\Users\\th\\eclipse-workspace\\workM1\\p05-Multithreading\\bin");
			PackageExplore pck = new PackageExplore(prj, "org.mql.java.generic");
			ClasseExplore cls = new ClasseExplore(prj);
			XMLGenerator.generateXML(prj.getPack(), prj.extractRelations(),
					"C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");
			List<PackageModel> dataModel = XMLParser.parseXML(
					"C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");
			XMLParser.displayModel(dataModel);

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

	void exp02() {

		DiagrammeDOM diagrammeDOM = new DiagrammeDOM();
		DiagrammePackage pc = new DiagrammePackage();
		//JScrollPane scrollPane = pc.parse("C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");

		JScrollPane scrollPane = diagrammeDOM.parse("C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");

		JFrame frame = new JFrame("Diagramme");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(scrollPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	void exp03() {
		int h = 600;
		int w = 800;
		JFrame f = new JFrame();
		Relationui dc = new Relationui(100, 123, 123, 111);
		f.setSize(w, h);
		f.setTitle("drawing");
		f.add(dc);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public static void main(String[] args) {

		new Examples();
	}
}
