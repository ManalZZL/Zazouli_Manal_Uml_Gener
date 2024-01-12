package org.mql.java.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLGenerator {

    public static void generateXML(Map<String, Set<Class<?>>> packages, Map<String, List<String>> classRelations, String outputPath) {
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
                	if(clazz.isAnnotation()) {
                		   Element classElement = document.createElement("annotaion");
                           packageElement.appendChild(classElement);
                           Element classNameElement = document.createElement("name");
                           classNameElement.appendChild(document.createTextNode(clazz.getSimpleName()));
                           classElement.appendChild(classNameElement);

                	}
                	else if(clazz.isInterface()) {
                		 Element classElement = document.createElement("interface");
                         packageElement.appendChild(classElement);
                         Element classNameElement = document.createElement("name");
                         classNameElement.appendChild(document.createTextNode(clazz.getSimpleName()));
                         classElement.appendChild(classNameElement);

                	}else if(clazz.isEnum()) {
               		 Element classElement = document.createElement("Enum");
                     packageElement.appendChild(classElement);
                     Element classNameElement = document.createElement("name");
                     classNameElement.appendChild(document.createTextNode(clazz.getSimpleName()));
                     classElement.appendChild(classNameElement);

            	}else {
                    Element classElement = document.createElement("class");
                    packageElement.appendChild(classElement);

                    Element classNameElement = document.createElement("name");
                    classNameElement.appendChild(document.createTextNode(clazz.getSimpleName()));
                    classElement.appendChild(classNameElement);

                    List<String> relations = classRelations.get(clazz.getSimpleName());
                    if (relations != null && !relations.isEmpty()) {
                        Element relationsElement = document.createElement("relations");
                        classElement.appendChild(relationsElement);

                        for (String relation : relations) {
                            Element relationElement = document.createElement("relation");
                            relationElement.appendChild(document.createTextNode(relation));
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
