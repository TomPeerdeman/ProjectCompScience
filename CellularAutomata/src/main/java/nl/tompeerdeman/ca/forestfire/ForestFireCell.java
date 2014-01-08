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

package nl.tompeerdeman.ca.forestfire;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.DataSet;
import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.Simulator;

public class ForestFireCell extends Cell {
	public ForestFireCell(int x, int y, ForestFireCellType t) {
		super(x, y, t);
	}
	
	@Override
	public boolean shouldSimulate() {
		return type == ForestFireCellType.BURNING;
	}
	
	@Override
	public boolean simulate(Grid grid, DataSet d, Simulator sim) {
		ForestFireCell c;
		ForestFireData data = (ForestFireData) d;
		
		int x, y;
		for(int ny = 0; ny < 3; ny++) {
			for(int nx = 0; nx < 3; nx++) {
				x = this.x + nx - 1;
				y = this.y + ny - 1;
				
				if(x >= 0 && y >= 0 && x < grid.grid.length
						&& y < grid.grid[0].length
						&& data.neighborhood[2 - ny][nx]) {
					c = (ForestFireCell) grid.getCell(x, y);
					
					if(c != null && c.getType() == ForestFireCellType.VEG) {
						c.setType(ForestFireCellType.BURNING);
						data.burning++;
						data.vegetation--;
						
						sim.addSimulatable(c);
					}
				}
			}
		}
		
		// This cell isn't burning anymore
		type = ForestFireCellType.BURNT;
		data.burning--;
		data.burnt++;
		
		if(data.burning == 0) {
			sim.stop();
		}
		
		if(this.y + 1 == grid.grid[0].length) {
			data.reachedOpposite = true;
		}
		
		// Do not simulate this cell any more
		return false;
	}
}
