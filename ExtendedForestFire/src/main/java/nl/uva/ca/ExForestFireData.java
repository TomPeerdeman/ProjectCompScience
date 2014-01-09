/**
 * File: ExForestFireData.java
 * 
 */
package nl.uva.ca;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.forestfire.ForestFireData;

public class ExForestFireData extends ForestFireData {
	public int trees;
	
	public double fireFightTresh;
	public double extinguishProb;
	
	/**
	 * @param nb
	 * @param grid
	 * @param densityIdx
	 * @param fireFightTresh
	 * @param extinguishProb
	 */
	public ExForestFireData(boolean[][] nb, Grid grid, int densityIdx,
			double fireFightTresh, double extinguishProb) {
		super(nb, grid, densityIdx);
		this.fireFightTresh = fireFightTresh;
		this.extinguishProb = extinguishProb;
	}
	
	@Override
	public void reset() {
		burning = 0;
		burnt = 0;
		vegetation = 0;
		barren = 0;
		trees = 0;
		
		Cell c;
		
		for(int y = 0; y < grid.grid[0].length; y++) {
			for(int x = 0; x < grid.grid.length; x++) {
				c = grid.getCell(x, y);
				if(c == null) {
					barren++;
					continue;
				}
				
				// Cast necessary, unable to switch on a interface
				switch((ExForestFireCellType) c.getType()) {
					case BURNING_BUSH:
					case BURNING_TREE:
						burning++;
						break;
					case BURNT:
						burnt++;
						break;
					case TREE:
						trees++;
						break;
					case BUSH:
						vegetation++;
						break;
					default:
						barren++;
						break;
				}
			}
		}
		
		reachedOpposite = false;
	}
}
