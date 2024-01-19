package org.mql.java.diagramme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DiagrammePackage {
	private Map<String, JPanel> packPanels;
	private Map<String, JPanel> relationPanels;

	public DiagrammePackage() {
		packPanels = new HashMap<>();
		relationPanels = new HashMap<>();
	}

	public JScrollPane parse(String source) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(source);

			NodeList packageNodes = document.getElementsByTagName("package");

			for (int i = 0; i < packageNodes.getLength(); i++) {
				Node packNode = packageNodes.item(i);
				if (packNode.getNodeType() == Node.ELEMENT_NODE) {
					Element packageElement = (Element) packNode;
					String packageName = packageElement.getElementsByTagName("name").item(0).getTextContent();

					JPanel pckPanel = new JPanel();
					pckPanel.setLayout(new BoxLayout(pckPanel, BoxLayout.Y_AXIS));
					pckPanel.setBorder(BorderFactory.createTitledBorder(packageName));
					pckPanel.setBackground(Color.lightGray);

					JPanel clssPanel = new JPanel();
					clssPanel.setLayout(new BoxLayout(clssPanel, BoxLayout.Y_AXIS));
					clssPanel.setBorder(BorderFactory.createEmptyBorder());
					handleClass(packageElement, clssPanel);
					clssPanel.setBackground(Color.lightGray);

					pckPanel.add(clssPanel);
					packPanels.put(packageName, pckPanel);
				}
			}

			//handleRelations(document);

			JScrollPane scrollPane = new JScrollPane(createDiagramPanel());
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			return scrollPane;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void handleClass(Element classElement, JPanel classPanel) {
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
		fieldsPanel.setLayout(new GridLayout(0, 2));
		NodeList classNodes = classElement.getElementsByTagName("class");

		for (int i = 0; i < classNodes.getLength(); i++) {
			Element classNode = (Element) classNodes.item(i);
			String className = classNode.getElementsByTagName("name").item(0).getTextContent();

			LabelTextField classLabel = new LabelTextField(className, "");
			JPanel classPanelItem = new JPanel(new BorderLayout());

			classPanelItem.setLayout(new FlowLayout(10, 20, 10));
			classPanelItem.setBorder(BorderFactory.createEtchedBorder());
			classPanelItem.add(classLabel, BorderLayout.WEST);

			fieldsPanel.add(classPanelItem);
		}

		classPanel.add(fieldsPanel);
	}

	/*private void handleRelations(Document document) {
		NodeList relationNodes = document.getElementsByTagName("relations");

		for (int i = 0; i < relationNodes.getLength(); i++) {
			Node relationNode = relationNodes.item(i);
			if (relationNode.getNodeType() == Node.ELEMENT_NODE) {
				Element relationElement = (Element) relationNode;
				String fromPackage = relationElement.getAttribute("depart");
				String toPackage = relationElement.getAttribute("arrive");

				JPanel fromPanel = packPanels.get(fromPackage);
				JPanel toPanel = packPanels.get(toPackage);

				if (fromPanel != null && toPanel != null) {
					addRelationLine(fromPanel, toPanel);
				}
			}
		}
	}

	private void addRelationLine(JPanel fromPanel, JPanel toPanel) {
		Point fromPoint = getCenter(fromPanel);
		Point toPoint = getCenter(toPanel);
		Relationui relationLine = new Relationui(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
		relationPanels.put(fromPanel.getName() + "-" + toPanel.getName(), relationLine);
	}

	private Point getCenter(JPanel panel) {
		int x = panel.getX() + panel.getWidth() / 2;
		int y = panel.getY() + panel.getHeight() / 2;
		return new Point(x, y);
	}
*/
	private JPanel createDiagramPanel() {
		JPanel diagramPanel = new JPanel();
		diagramPanel.setLayout(new FlowLayout(200, 100, 200));

		for (JPanel classPanel : packPanels.values()) {
			diagramPanel.add(classPanel);
		}
//
//		for (JPanel relationPanel : relationPanels.values()) {
//			diagramPanel.add(relationPanel);
//		}

		return diagramPanel;
	}
}
