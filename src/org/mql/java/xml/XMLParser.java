package org.mql.java.xml;

import org.mql.java.models.ClasseModel;
import org.mql.java.models.PackageModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

	public static List<PackageModel> parseXML(String filePath) {
		List<PackageModel> result = new ArrayList<>();

		try {
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);

			document.getDocumentElement().normalize();

			NodeList packageNodes = document.getElementsByTagName("package");

			for (int i = 0; i < packageNodes.getLength(); i++) {
				Node packageNode = packageNodes.item(i);
				if (packageNode.getNodeType() == Node.ELEMENT_NODE) {
					Element packageElement = (Element) packageNode;
					String packageName = packageElement.getElementsByTagName("name").item(0).getTextContent();

					PackageModel packageObj = new PackageModel(packageName);

					// les infos de la classe
					List<String> classes = new ArrayList<>();
					NodeList classNodes = packageElement.getElementsByTagName("class");

					for (int j = 0; j < classNodes.getLength(); j++) {
						Node classNode = classNodes.item(j);
						if (classNode.getNodeType() == Node.ELEMENT_NODE) {
							Element classElement = (Element) classNode;
							String className = classElement.getElementsByTagName("name").item(0).getTextContent();
							classes.add(className);

							// les infos des propriétés
							List<String> fields = new ArrayList<>();
							NodeList fieldNodes = classElement.getElementsByTagName("fields");
							if (fieldNodes.getLength() > 0) {
								Element fieldElement = (Element) fieldNodes.item(0);
								NodeList fieldNameNodes = fieldElement.getElementsByTagName("fieldName");

								for (int k = 0; k < fieldNameNodes.getLength(); k++) {
									Node fieldNameNode = fieldNameNodes.item(k);
									if (fieldNameNode.getNodeType() == Node.ELEMENT_NODE) {
										String fieldName = fieldNameNode.getTextContent();
										fields.add(fieldName);
									}
								}
							}
							// ***il reste la partie des relations****

							// creation de ClasseModel et l'ajouter à PackageModel
							ClasseModel classeModel = new ClasseModel();
							classeModel.setName(className);
							classeModel.setFields(fields);
							packageObj.addClass(classeModel);
						}
					}

					result.add(packageObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void displayModel(List<PackageModel> model) {
		System.out.println("***** Model Content *****");

		for (PackageModel packageObj : model) {
			System.out.println("Package: " + packageObj.getName());
			System.out.println("Classes:");
			for (ClasseModel className : packageObj.getClasses()) {
				System.out.println("  - " + className);
			}
			System.out.println("-------------------------");
		}
	}
}
