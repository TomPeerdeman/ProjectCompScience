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

public class ForestFireData implements DataSet {
	public static final boolean[][] NB_NEUMANN = {
													{false, true, false},
													{true, false, true},
													{false, true, false}};
	
	public static final boolean[][] NB_MOORE = {
												{true, true, true},
												{true, false, true},
												{true, true, true}};
	
	public static final boolean[][] NB_WIND_UP_N = {
													{false, true, false},
													{false, false, false},
													{false, false, false}};
	
	public static final boolean[][] NB_WIND_LEFT_N = {
														{false, true, false},
														{true, false, false},
														{false, false, false}};
	
	public static final boolean[][] NB_WIND_RIGHT_N = {
														{false, true, false},
														{false, false, true},
														{false, false, false}};
	
	public static final boolean[][] NB_WIND_UP_M = {
													{true, true, true},
													{false, false, false},
													{false, false, false}};
	
	public static final boolean[][] NB_WIND_LEFT_M = {
														{true, true, false},
														{true, false, false},
														{false, false, false}};
	
	public static final boolean[][] NB_WIND_RIGHT_M = {
														{false, true, true},
														{false, false, true},
														{false, false, false}};
	
	protected Grid grid;
	public boolean[][] neighborhood;
	
	public int densityIdx;
	public long barren;
	public long vegetation;
	public long burning;
	public long burnt;
	public boolean reachedOpposite;
	
	public ForestFireData(boolean[][] nb, Grid grid, int densityIdx) {
		neighborhood = nb;
		
		this.densityIdx = densityIdx;
		this.grid = grid;
		
		reset();
	}
	
	@Override
	public void reset() {
		burning = 0;
		burnt = 0;
		vegetation = 0;
		barren = 0;
		Cell c;
		
		for(int y = 0; y < grid.grid[0].length; y++) {
			for(int x = 0; x < grid.grid.length; x++) {
				c = grid.getCell(x, y);
				if(c == null) {
					barren++;
					continue;
				}
				
				// Cast necessary, unable to switch on a interface
				switch((ForestFireCellType) c.getType()) {
					case BURNING:
						burning++;
						break;
					case BURNT:
						burnt++;
						break;
					case VEG:
						vegetation++;
						break;
				}
			}
		}
		
		reachedOpposite = false;
	}
	
	public void setNb(boolean nb[][]) {
		neighborhood = nb;
	}
}
