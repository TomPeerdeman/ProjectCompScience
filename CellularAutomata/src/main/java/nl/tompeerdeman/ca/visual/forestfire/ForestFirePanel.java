/**
 * Copyright 2012 Tom Peerdeman
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

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
