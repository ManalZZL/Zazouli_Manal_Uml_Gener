package org.mql.java.diagramme;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

public class MenuDiagramme extends JMenuBar {
	private static final long serialVersionUID = 1L;
	JMenu packages = new JMenu("diagramme de Package");
	JMenu classes = new JMenu("diagramme de classe");
	public MenuDiagramme() {
		this.setBounds(0, 0, 800, 30);
		this.add(packages);
		this.add(classes);

		JMenuItem m1Item1 = new JMenuItem("afficher le diagramme de classe");
		JMenuItem m1Item2 = new JMenuItem("afficher le diagramme de package");

		m1Item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DiagrammeClasse diagramme = new DiagrammeClasse();

				JScrollPane scrollPane = diagramme.parse(
						"C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");

				JFrame frame = new JFrame("Diagramme de classe");
				frame.setSize(400, 300);
				frame.add(scrollPane);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});

		m1Item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DiagrammePackage pc = new DiagrammePackage();
				JScrollPane scrollPane = pc.parse(
						"C:\\Users\\th\\eclipse-workspace\\workM1\\Zazouli Manal - UmlDiagGener\\resources\\output.xml");
				JFrame frame = new JFrame("Diagramme de package");
				frame.setSize(400, 300);
				frame.add(scrollPane);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});

		packages.add(m1Item2);
		classes.add(m1Item1);
	}

}
