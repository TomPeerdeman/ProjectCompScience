package nl.tompeerdeman.ca.visual.predprey;

import java.awt.Color;
import java.awt.Graphics2D;

import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.predprey.PredPreyCell;
import nl.tompeerdeman.ca.predprey.PredPreyCellType;
import nl.tompeerdeman.ca.visual.CaPanel;

public class PredPreyPanel extends CaPanel{
	private static final long serialVersionUID = 3719780009255243793L;

	public PredPreyPanel(Grid g, int w, int h){
		super(g, w, h);
	}

	@Override
	public void paintTile(Graphics2D g, int x, int y){
		PredPreyCell cell = (PredPreyCell) grid.getCell(x, y);
		if(cell == null){
			g.setColor(Color.YELLOW);
		}else if(cell.getType() == PredPreyCellType.PREY){
			g.setColor(Color.GREEN);
		}else if(cell.getType() == PredPreyCellType.PREDATOR){
			g.setColor(Color.RED);
		}
		
		g.fillRect(x * dx + offsx, y * dy, dx, dy);
	}
}
