package nl.tompeerdeman.ca.visual;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;

public abstract class CaPanel extends JPanel implements SimulateChangeListener {
	private static final long serialVersionUID = -2417388660117157544L;
	
	protected Grid grid;
	protected int dx;
	protected int dy;
	protected int nx;
	protected int ny;
	protected int offsx;
	
	public CaPanel(Grid g, int w, int h) {
		super();
		
		grid = g;
		
		nx = g.grid.length;
		ny = g.grid[0].length;
		
		// dx = px horizontal per cell, dy = px vertical per cell
		dx = (int) Math.floor(400.0 / nx);
		dy = (int) Math.floor(400.0 / ny);
		
		setSize(nx * dx, ny * dy);
		setPreferredSize(new Dimension(nx * dx, ny * dy));
		
		offsx = (getWidth() - nx * dx) / 2;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		// Reverse y axis so y=0 is at the bottom
		g2d.translate(0, getHeight());
		g2d.scale(1, -1);
		
		// getWidth could be changed by layout manager
		offsx = (getWidth() - nx * dx) / 2;
		
		// Draw all tiles
		for(int y = 0; y < ny; y++) {
			for(int x = 0; x < nx; x++) {
				paintTile(g2d, x, y);
			}
		}
		
	}
	
	public abstract void paintTile(Graphics2D g, int x, int y);
	
	@Override
	public void simulationUpdated(Simulator sim) {
		repaint();
	}
}
