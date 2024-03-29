/**
 * File: ExForestFireData.java
 * 
 */
package nl.uva.ca;

import java.io.Serializable;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.DataSet;
import nl.tompeerdeman.ca.Grid;

public class ExForestFireData implements DataSet, Serializable {
	private static final long serialVersionUID = 1L;
	
	public Grid grid;
	public double[][] neighborhood;
	
	public transient long bushes;
	public transient long trees;
	public transient long barren;
	public transient long burning;
	public transient long burnt;
	
	public transient boolean fireFighters;
	public transient double fireFighterSpawnProb;
	public transient double extinguishProb;
	public transient int nTicksTreeBurn;
	public transient int nTicksBushBurn;
	public transient int probDivider;
	
	public boolean oppositeReached;
	public int nFireFighters;
	public int nDeadFireFighters;
	
	public final int testNr;
	public final int simNr;
	
	public int type;
	
	/**
	 * @param grid
	 * @param type
	 * @param testNr
	 * @param simNr
	 */
	public ExForestFireData(Grid grid, int type, int testNr, int simNr) {
		this.grid = grid;
		this.type = type;
		this.testNr = testNr;
		this.simNr = simNr;
		
		reset();
	}
	
	public void loadFrom(ExForestFireData data) {
		type = data.type;
		grid.grid = data.grid.grid;
		
		neighborhood = data.neighborhood;
	}
	
	@Override
	public void reset() {
		fireFighters = false;
		probDivider = 2;
		extinguishProb = 0.2;
		nTicksTreeBurn = 10;
		nTicksBushBurn = 4;
		fireFighterSpawnProb = 0.4;
		
		nFireFighters = 0;
		nDeadFireFighters = 0;
		oppositeReached = false;
		
		// TODO: Depends on type?
		neighborhood = new double[][] {
										{0.1, 0.1, 0.1},
										{0.1, 0.0, 0.1},
										{0.1, 0.1, 0.1}
		};
		
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
