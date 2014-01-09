/**
 * File: ExCaLegendPanel.java
 * 
 */
package nl.uva.ca.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import nl.tompeerdeman.ca.Grid;

public abstract class ExCaLegendPanel extends JPanel {
	private static final long serialVersionUID = -3382366317021535745L;
	
	protected int offsx;
	protected int dx;
	protected int nx;
	
	/**
	 * @param g
	 * 
	 */
	public ExCaLegendPanel(Grid g) {
		// Create black border
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		nx = g.grid.length;
		
		// dx = px horizontal per cell
		dx = (int) Math.floor(400.0 / nx);
		
		setSize(nx * dx, 100);
		setPreferredSize(new Dimension(nx * dx, 100));
		
		offsx = (getWidth() - nx * dx) / 2;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		// getWidth could be changed by layout manager
		offsx = (getWidth() - nx * dx) / 2;
		
		onPaint(g2d);
	}
	
	public abstract void onPaint(Graphics2D g2d);
	
	public void drawAt(Graphics2D g2d, int x, int y, Color color, String desc) {
		g2d.setColor(color);
		g2d.fillRect(x, y, x + dx, y + dx);
		g2d.setColor(Color.BLACK);
		g2d.drawString(desc, x + dx + 3, y);
	}
}
