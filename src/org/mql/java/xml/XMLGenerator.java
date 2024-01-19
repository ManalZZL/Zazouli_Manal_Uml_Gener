package org.mql.java.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLGenerator {

	private static String getSimpleTypeName(Class<?> type) {
		if (type.isArray()) {
			return getSimpleTypeName(type.getComponentType()) + "[]";
		} else {
			String typeName = type.getTypeName();
			int lastDotIndex = typeName.lastIndexOf('.');
			return (lastDotIndex != -1) ? typeName.substring(lastDotIndex + 1) : typeName;
		}
	}

	public static void generateXML(Map<String, Set<Class<?>>> packages, Map<String, List<String>> classRelations,
			String outputPath) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element rootElement = document.createElement("project");
			document.appendChild(rootElement);

			for (Map.Entry<String, Set<Class<?>>> entry : packages.entrySet()) {
				Element packageElement = document.createElement("package");
				rootElement.appendChild(packageElement);

				Element packageNameElement = document.createElement("name");
				packageNameElement.appendChild(document.createTextNode(entry.getKey()));
				packageElement.appendChild(packageNameElement);

				Set<Class<?>> classes = entry.getValue();
				for (Class<?> clazz : classes) {

					if (clazz.isAnnotation()) {
						Element classElement = document.createElement("annotaion");
						packageElement.appendChild(classElement);
						Element classNameElement = document.createElement("name");
						classNameElement.appendChild(document.createTextNode(clazz.getSimpleName()));
						classElement.appendChild(classNameElement);

					} else if (clazz.isInterface()) {
						Element classElement = document.createElement("interface");
						packageElement.appendChild(classElement);
						Element classNameElement = document.createElement("name");
						classNameElement.appendChild(document.createTextNode(clazz.getSimpleName()));
						classElement.appendChild(classNameElement);

						Element methodsElement = document.createElement("methods");
						classElement.appendChild(methodsElement);
						Method[] methods = clazz.getDeclaredMethods();
						for (Method m : methods) {
							// Créer un élément pour le type de retour de la méthod
							Element typeElement = document.createElement("methodtype");
							typeElement.appendChild((document.createTextNode(getSimpleTypeName(m.getReturnType()))));

							// Créer un élément pour le nom de la method
							Element methoddNameElement = document.createElement("methodName");
							methoddNameElement.appendChild(document.createTextNode(m.getName()));

							// Ajouter les éléments au parent (methodsElement)
							methodsElement.appendChild(typeElement);
							methodsElement.appendChild(methoddNameElement);

						}

					} else if (clazz.isEnum()) {
						Element classElement = document.createElement("Enum");
						packageElement.appendChild(classElement);
						Element classNameElement = document.createElement("name");
						classNameElement.appendChild(document.createTextNode(clazz.getSimpleName()));
						classElement.appendChild(classNameElement);

					} else {
						Element classElement = document.createElement("class");
						packageElement.appendChild(classElement);

						Element classNameElement = document.createElement("name");
						classNameElement.appendChild(document.createTextNode(clazz.getSimpleName()));
						classElement.appendChild(classNameElement);

						Element fieldsElement = document.createElement("fields");
						classElement.appendChild(fieldsElement);
						Field[] fields = clazz.getDeclaredFields();
						for (Field field : fields) {
							if (!field.getName().equals("serialVersionUID")) {
								Element fieldd = document.createElement("field");
								// Créer un élément pour le modificateur du champ
								Element modifierElement = document.createElement("fieldModifier");
								modifierElement
										.appendChild(document.createTextNode(Modifier.toString(field.getModifiers())));

								// Créer un élément pour le type du champ
								Element typeElement = document.createElement("fieldtype");
								typeElement.appendChild((document.createTextNode(getSimpleTypeName(field.getType()))));

								// Créer un élément pour le nom du champ
								Element fieldNameElement = document.createElement("fieldName");
								fieldNameElement.appendChild(document.createTextNode(field.getName()));

								// Ajouter les éléments au parent (fieldsElement)
								fieldd.appendChild(modifierElement);
								fieldd.appendChild(typeElement);
								fieldd.appendChild(fieldNameElement);
								fieldsElement.appendChild(fieldd);
							}
						}

						Element methodsElement = document.createElement("methods");
						classElement.appendChild(methodsElement);
						Method[] methods = clazz.getDeclaredMethods();
						for (Method m : methods) {
							Element method = document.createElement("method");
							// Créer un élément pour le type de retour de la méthod
							Element typeElement = document.createElement("methodtype");
							typeElement.appendChild((document.createTextNode(getSimpleTypeName(m.getReturnType()))));

							// Créer un élément pour le nom de la method
							Element methoddNameElement = document.createElement("methodName");
							methoddNameElement.appendChild(document.createTextNode(m.getName()));

							// Ajouter les éléments au parent (methodsElement)
							method.appendChild(typeElement);
							method.appendChild(methoddNameElement);
							methodsElement.appendChild(method);

						}

						List<String> relations = classRelations.get(clazz.getSimpleName());
						if (relations != null && !relations.isEmpty()) {
							Element relationsElement = document.createElement("relations");
							classElement.appendChild(relationsElement);

							for (String relation : relations) {
								Element relationElement;

								if (relation.startsWith("Aggregates")) {
									relationElement = document.createElement("aggregation");
									String className = relation.substring("Aggregates: ".length());
									relationElement.setAttribute("depart",clazz.getSimpleName());
									relationElement.setAttribute("arrive",className);
									relationElement.appendChild(document.createTextNode(className));

								} else if (relation.startsWith("Composes")) {
									relationElement = document.createElement("composition");
									String classname = relation.substring("Composes: ".length());
									relationElement.setAttribute("depart",clazz.getSimpleName());
									relationElement.setAttribute("arrive",classname);
									relationElement.appendChild(document.createTextNode(classname));

								} else if (relation.startsWith("extends")) {
									relationElement = document.createElement("extends");
									String iclassnamee = relation.substring("extends: ".length());
									relationElement.setAttribute("depart",clazz.getSimpleName());
									relationElement.setAttribute("arrive",iclassnamee);
									relationElement.appendChild(document.createTextNode(iclassnamee));

								} else if (relation.startsWith("Implements")) {
									relationElement = document.createElement("implements");
									String interfaceName = relation.substring("Implements: ".length());
									relationElement.setAttribute("depart",clazz.getSimpleName());
									relationElement.setAttribute("arrive",interfaceName);
									relationElement.appendChild(document.createTextNode(interfaceName));

								} else {
									relationElement = document.createElement("association");
									relationElement.appendChild(document.createTextNode(relation));
								}

								relationsElement.appendChild(relationElement);
							}
						}
					}
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(outputPath));

			transformer.transform(source, result);
			System.out.println("XML généré!");

		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}

	}
}
