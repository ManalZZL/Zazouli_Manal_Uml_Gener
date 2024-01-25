package org.mql.java.diagramme;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.mql.java.reflect.ProjetExplore;
import org.mql.java.xml.XMLGenerator;


public class Button extends JButton {
	private static final long serialVersionUID = 1L;
	public Button() {
		super("dessiner les diagrammes");
		this.setAlignmentY(HEIGHT);
		actionEvenement();
		}

	@SuppressWarnings("unused")
	public void parser(String projectPath) {
		ProjetExplore prj = null;
		try {
			if(prj==null) {
				JOptionPane.showMessageDialog(this, "Veuillez spécifier le chemin /bin de votre projet ou vérifier les diagrammes précédemment dessinés.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			else {
			prj = new ProjetExplore(projectPath);
			XMLGenerator.generateXML(prj.getPack(), prj.extractRelations(),
					"C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");
		
		} }catch (ClassNotFoundException | MalformedURLException e) {
			System.out.println("erreur :"+e);
			
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