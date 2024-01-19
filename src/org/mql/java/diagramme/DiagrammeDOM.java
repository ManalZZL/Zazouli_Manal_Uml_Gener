package org.mql.java.diagramme;

import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;

public class DiagrammeDOM extends JPanel {

	private static final long serialVersionUID = 1L;

	private Map<String, JPanel> classPanels;

	JPanel diagramPanel;

	public DiagrammeDOM() {
		classPanels = new HashMap<>();
		diagramPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	}

	public JScrollPane parse(String source) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(source);

			NodeList classNodes = document.getElementsByTagName("class");

			for (int i = 0; i < classNodes.getLength(); i++) {
				Node classNode = classNodes.item(i);
				if (classNode.getNodeType() == Node.ELEMENT_NODE) {
					Element classElement = (Element) classNode;

					// Récupérer le nom de la classe
					String className = classElement.getElementsByTagName("name").item(0).getTextContent();

					// Créer le panneau de la classe
					JPanel classPanel = new JPanel();
					classPanel.setLayout(new BoxLayout(classPanel, BoxLayout.Y_AXIS));
					classPanel.setBorder(BorderFactory.createTitledBorder(className));
					classPanel.setBackground(Color.lightGray);
					// Créer le panneau des propriétés et les récuperer
					JPanel fieldsPanel = new JPanel();
					fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
					fieldsPanel
							.setBorder(BorderFactory.createDashedBorder(getBackground(), 2, 2, 2, isBackgroundSet()));
					fieldsPanel.setBackground(Color.lightGray);
					handleFields(classElement, fieldsPanel);
					classPanel.add(fieldsPanel);
					// Créer le panneau des méthodes et les récuperer
					JPanel methodsPanel = new JPanel();
					methodsPanel.setLayout(new BoxLayout(methodsPanel, BoxLayout.Y_AXIS));
					methodsPanel
							.setBorder(BorderFactory.createDashedBorder(getBackground(), 2, 2, 2, isBackgroundSet()));
					methodsPanel.setBackground(Color.lightGray);
					handleMethods(classElement, methodsPanel);
					classPanel.add(methodsPanel);

					// Ajouter le panneau de la classe à la liste des panneaux de classes/interfaces
					classPanels.put(className, classPanel);
				}
			}
			NodeList interfaceNodes = document.getElementsByTagName("interface");

			for (int i = 0; i < interfaceNodes.getLength(); i++) {
				Node iNode = interfaceNodes.item(i);
				if (iNode.getNodeType() == Node.ELEMENT_NODE) {
					Element interfaceElement = (Element) iNode;

					// Récupérer le nom de l'interface
					String interfaceName = "<<interface>>"
							+ interfaceElement.getElementsByTagName("name").item(0).getTextContent();
					;

					// Créer le panneau d'interface
					JPanel interfacePanel = new JPanel();
					interfacePanel.setLayout(new BoxLayout(interfacePanel, BoxLayout.Y_AXIS));
					interfacePanel.setBorder(BorderFactory.createTitledBorder(interfaceName));
					interfacePanel.setLayout(new FlowLayout(10, 70, 21));
					interfacePanel.setBackground(Color.lightGray);

					// Traiter les méthodes de l'interface
					JPanel methodsPanel = new JPanel();
					methodsPanel.setLayout(new BoxLayout(methodsPanel, BoxLayout.Y_AXIS));
					methodsPanel
							.setBorder(BorderFactory.createDashedBorder(getBackground(), 2, 2, 2, isBackgroundSet()));
					methodsPanel.setBackground(Color.lightGray);
					// methodsPanel.setLayout(new FlowLayout(10, 50, 21));
					handleMethods(interfaceElement, methodsPanel);
					interfacePanel.add(methodsPanel);

					// Ajouter le panneau d'interface à la liste des panneaux de classes/interfaces
					classPanels.put(interfaceName, interfacePanel);
				}
			}
			handleRelations(document);
			JScrollPane scrollPane = new JScrollPane(createDiagramPanel());
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			return scrollPane;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//méthode privée pour récupérer les propriétés
	private void handleFields(Element classElement, JPanel classPanel) {
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

		NodeList fieldNodes = classElement.getElementsByTagName("field");

		for (int i = 0; i < fieldNodes.getLength(); i++) {
			Element fieldElement = (Element) fieldNodes.item(i);
			String fieldType = "-" + fieldElement.getElementsByTagName("fieldtype").item(0).getTextContent();
			String fieldName = fieldElement.getElementsByTagName("fieldName").item(0).getTextContent();

			LabelTextField fieldLabel = new LabelTextField(fieldType);
			LabelTextField nameLabel = new LabelTextField(fieldName, "pour :");
			JPanel fieldPanel = new JPanel(new BorderLayout());
			fieldPanel.add(fieldLabel, BorderLayout.WEST);
			fieldPanel.add(nameLabel, BorderLayout.CENTER);
			fieldsPanel.add(fieldPanel);
		}

		classPanel.add(fieldsPanel);
	}

	// méthode privée pour récupérer les méthodes
	private void handleMethods(Element classElement, JPanel classPanel) {
		JPanel methodsPanel = new JPanel();
		methodsPanel.setLayout(new BoxLayout(methodsPanel, BoxLayout.Y_AXIS));

		NodeList methodNodes = classElement.getElementsByTagName("method");

		for (int i = 0; i < methodNodes.getLength(); i++) {
			Element methodElement = (Element) methodNodes.item(i);
			String methodType = "+" + methodElement.getElementsByTagName("methodtype").item(0).getTextContent();
			String methodName = methodElement.getElementsByTagName("methodName").item(0).getTextContent() + "()";

			LabelTextField methodLabel = new LabelTextField(methodType);
			LabelTextField nameLabel = new LabelTextField(methodName, "pour les ()");
			JPanel methodPanel = new JPanel(new BorderLayout());
			methodPanel.add(methodLabel, BorderLayout.WEST);
			methodPanel.add(nameLabel, BorderLayout.CENTER);
			methodsPanel.add(methodPanel);
			methodsPanel.setBackground(Color.lightGray);
		}

		classPanel.add(methodsPanel);
	}

	// méthode privée pour récupérer les relation et les dessiner

	private void handleRelations(Document document) {
		NodeList relationNodes = document.getElementsByTagName("relations");

		for (JPanel classPanel : classPanels.values()) {
			diagramPanel.add(classPanel);
		}

		for (int i = 0; i < relationNodes.getLength(); i++) {
			Node relationNode = relationNodes.item(i);
			if (relationNode.getNodeType() == Node.ELEMENT_NODE) {
				Element relationElement = (Element) relationNode;
				String fromClass = relationElement.getAttribute("depart");
				String toClass = relationElement.getAttribute("arrive");

				JPanel fromPanel = classPanels.get(fromClass);
				System.out.println("from"+fromClass+"to"+toClass);
				JPanel toPanel = classPanels.get(toClass);
				if (fromPanel != null && toPanel != null) {
				    addRelationLine(fromPanel, toPanel, diagramPanel);
				} else {
				    // Gérer le cas où l'un des panneaux est null
				    System.out.println("Panneau null. fromClass: " + fromClass + ", toClass: " + toClass);
				}
			}
		}

	}

	// ajouter la ligne des relation au panneau du diagramme
	private void addRelationLine(JPanel fromPanel, JPanel toPanel, JPanel diagp) {
		Point fromPoint = getCenter(fromPanel);
		Point toPoint = getCenter(toPanel);
		Relationui relationLine = new Relationui(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
		diagp.add(relationLine);
	}

//retourner le centre de chaque panneau pour l'utiliser dans le dessin
	private Point getCenter(JPanel panel) {
		int x = panel.getX() + panel.getWidth() / 2;
		int y = panel.getY() + panel.getHeight() / 2;
		return new Point(x, y);
	}

	private JPanel createDiagramPanel() {

		for (JPanel classPanel : classPanels.values()) {
			diagramPanel.setLayout(new FlowLayout(50, 30, 30));
			diagramPanel.add(classPanel);
		}

		return diagramPanel;
	}
}
