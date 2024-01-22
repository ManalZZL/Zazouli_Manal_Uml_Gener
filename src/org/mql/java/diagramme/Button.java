package org.mql.java.diagramme;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import javax.swing.JButton;

import org.mql.java.reflect.ProjetExplore;
import org.mql.java.xml.XMLGenerator;


public class Button extends JButton {
	private static final long serialVersionUID = 1L;
	public Button() {
		super("réinitialisez le document xml");
		this.setAlignmentY(HEIGHT);
		actionEvenement();
		}

	public void parser(String projectPath) {
		ProjetExplore prj;
		try {
			prj = new ProjetExplore(projectPath);
			XMLGenerator.generateXML(prj.getPack(), prj.extractRelations(),
					"C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");
		
		} catch (ClassNotFoundException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	void actionEvenement() {
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = DiagrammeApp.textf.gettext();
				parser(txt);
			}
		});
	}
}