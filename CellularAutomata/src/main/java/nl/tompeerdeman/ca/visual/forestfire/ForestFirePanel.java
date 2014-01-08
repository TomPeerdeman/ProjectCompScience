package nl.tompeerdeman.ca.visual.forestfire;

import java.awt.Color;
import java.awt.Graphics2D;

import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.forestfire.ForestFireCell;
import nl.tompeerdeman.ca.forestfire.ForestFireCellType;
import nl.tompeerdeman.ca.visual.CaPanel;

public class ForestFirePanel extends CaPanel {
	private static final long serialVersionUID = 3871766750986566999L;
	
	public ForestFirePanel(Grid g, int w, int h) {
		super(g, w, h);
	}
	
	@Override
	public void paintTile(Graphics2D g, int x, int y) {
		ForestFireCell cell = (ForestFireCell) grid.getCell(x, y);
		if(cell == null) {
			g.setColor(Color.YELLOW);
		} else if(cell.getType() == ForestFireCellType.VEG) {
			g.setColor(Color.GREEN);
		} else if(cell.getType() == ForestFireCellType.BURNING) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLACK);
		}
		
		g.fillRect(x * dx + offsx, y * dy, dx, dy);
	}
	
}
