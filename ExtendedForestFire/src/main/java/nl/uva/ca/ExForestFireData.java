/**
 * File: ExForestFireData.java
 * 
 */
package nl.uva.ca;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.DataSet;
import nl.tompeerdeman.ca.Grid;

public class ExForestFireData implements DataSet {
	protected Grid grid;
	public double[][] neighborhood;
	
	public long bushes;
	public long trees;
	public long barren;
	public long burning;
	public long burnt;
	
	public double fireFightTresh;
	public double extinguishProb;
	public boolean useTemperature;
	
	/**
	 * @param grid
	 * @param neighborhood
	 * @param fireFightTresh
	 * @param extinguishProb
	 * @param useTemperature
	 */
	public ExForestFireData(Grid grid, double[][] neighborhood,
			double fireFightTresh, double extinguishProb, boolean useTemperature) {
		this.grid = grid;
		this.neighborhood = neighborhood;
		this.fireFightTresh = fireFightTresh;
		this.extinguishProb = extinguishProb;
		this.useTemperature = useTemperature;
		
		reset();
	}
	
	@Override
	public void reset() {
		burning = 0;
		burnt = 0;
		bushes = 0;
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
					case BURNT_TREE:
					case BURNT_BUSH:
						burnt++;
						break;
					case TREE:
						trees++;
						break;
					case BUSH:
						bushes++;
						break;
					default:
						barren++;
						break;
				}
			}
		}
	}
}
