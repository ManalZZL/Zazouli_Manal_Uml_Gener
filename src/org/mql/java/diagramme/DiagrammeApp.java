package org.mql.java.diagramme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class DiagrammeApp extends JFrame {
	private static final long serialVersionUID = 1L;
	 private Button buttonDeTest;
	 private LabelTextField directions;
	    static public LabelTextField textf = new LabelTextField("donnez le chemin /BIN de votre projet", 40);

	    public DiagrammeApp() {
	        buttonDeTest = new Button();
	        MenuDiagramme pc = new MenuDiagramme();
            directions = new LabelTextField("vous devez réinitialiser le document xml pour avoir les diagrammes de votre projet choisi correctement","for :");
            directions.setBackground(Color.orange);
            setLayout(new BorderLayout());
	        setSize(800, 200);
	        setTitle("Application des diagrammes");

	        add(pc, BorderLayout.NORTH);

	        JPanel centerPanel = new JPanel(new FlowLayout());
	        centerPanel.add(buttonDeTest);
	        centerPanel.add(textf);
	        centerPanel.add(directions);
	       
	        add(centerPanel, BorderLayout.CENTER);

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setVisible(true);
	        setLocationRelativeTo(null);

	    }

}
