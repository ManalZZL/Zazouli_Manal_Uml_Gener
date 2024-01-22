package org.mql.java.diagramme;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabelTextField extends JPanel {

	private static final long serialVersionUID = 1L;
	JTextField t1;

	public LabelTextField(String label, int size, int labelSize) {
		this(label, size);
		JLabel l1 = (JLabel) getComponent(0);
		l1.setPreferredSize(new Dimension(labelSize, l1.getPreferredSize().height));
	}

	public LabelTextField(String label) {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		if (!label.contains(":")) {
			label += ":";
			JLabel l1 = new JLabel(label);
			add(l1);

		}
	}

	public LabelTextField(String label, String forchange) {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l1 = new JLabel(label);
		add(l1);

	}
	public LabelTextField(String label, int size) {

		if (!label.endsWith(":")) {
			label += ":";
		}
		JLabel l1 = new JLabel(label);
		t1 = new JTextField(size);
		add(l1);
		add(t1);
	}

	public String gettext() {
		return t1.getText();

	}
}
