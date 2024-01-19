package org.mql.java.diagramme;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

public class Relationui extends JPanel {

    private static final long serialVersionUID = 1L;
    private int startX, startY, endX, endY;

    public Relationui(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        
//        int maxX = Math.max(startX, endX);
//        int maxY = Math.max(startY, endY);
//        setPreferredSize(new Dimension(maxX + 10, maxY + 10)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Line2D.Double relation = new Line2D.Double(startX, startY, endX, endY);
        g2d.setColor(Color.red);
        g2d.draw(relation);
    }
}
