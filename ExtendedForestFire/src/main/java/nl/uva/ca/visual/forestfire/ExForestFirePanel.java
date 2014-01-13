package nl.uva.ca.visual.forestfire;

import java.awt.Color;
import java.awt.Graphics2D;

import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.visual.CaPanel;
import nl.uva.ca.ExForestFireCell;
import nl.uva.ca.ExForestFireCellType;

public class ExForestFirePanel extends CaPanel {
	private static final long serialVersionUID = 3871766750986566999L;
	
	public ExForestFirePanel(Grid g, int w, int h) {
		super(g, w, h);
	}
	
	// TODO other types of grids
	// this only works for a normal x, y grid
	
	@Override
	public void paintTile(Graphics2D g, int x, int y) {
		/*if( = 0){
			ExForestFireCell cell = (ExForestFireCell) grid.getCell(x, y);

			// add more colors for different terrains
			if(cell == null) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(((ExForestFireCellType) cell.getType()).getColor());
			}
			
			g.fillRect(x * dx + offsx, y * dy, dx, dy);
		}*/
		//else{
			ExForestFireCell cell = (ExForestFireCell) grid.getCell(x, y);
	
			// add more colors for different terrains
			if(cell == null) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(((ExForestFireCellType) cell.getType()).getColor());
			}
			if(y % 2 == 0){
				if(x % 2 == 0){
					int xPoly[] = {x * dx + offsx, x * dx + offsx + 2 * dx, x * dx + offsx + dx};
					int yPoly[] = {y * dy, y * dy, y * dy + dy};
					g.fillPolygon(xPoly, yPoly, 3);
				}
				else{
					int xPoly[] = {x * dx + offsx, x * dx + offsx + 2 * dx, x * dx + offsx + dx};
					int yPoly[] = {y * dy + dy, y * dy + dy, y * dy};
					g.fillPolygon(xPoly, yPoly, 3);
					
				}
			}
			else{
				if(x % 2 == 0){
					int xPoly[] = {x * dx + offsx, x * dx + offsx + 2 * dx, x * dx + offsx + dx};
					int yPoly[] = {y * dy + dy, y * dy + dy, y * dy};
					g.fillPolygon(xPoly, yPoly, 3);
				}
				else{
					int xPoly[] = {x * dx + offsx, x * dx + offsx + 2 * dx, x * dx + offsx + dx};
					int yPoly[] = {y * dy, y * dy, y * dy + dy};
					g.fillPolygon(xPoly, yPoly, 3);
					
				}
			}
		//}
	}
}
