/**
 * File: ExForestFire.java
 * 
 */
package nl.uva.ca;

import nl.tompeerdeman.ca.forestfire.ForestFire;

public class ExForestFire extends ForestFire {
	private ExForestFireData ffdata;
	private boolean randWater;
	private double treeDensity;
	private double bushDensity;
	
	/**
	 * @param nx
	 * @param ny
	 * @param seed
	 * @param nb
	 * @param randWater
	 * @param treeDensity
	 * @param bushDensity
	 * @param fireFightTresh
	 * @param extinguishProb
	 * @param useTemperature
	 */
	public ExForestFire(int nx, int ny, long seed,
			boolean[][] nb, boolean randWater, double treeDensity,
			double bushDensity, double fireFightTresh, double extinguishProb,
			boolean useTemperature) {
		super(0, nx, ny, seed, nb, 0);
		this.randWater = randWater;
		this.treeDensity = treeDensity;
		this.bushDensity = bushDensity;
		
		data =
			new ExForestFireData(nb, grid, 0, fireFightTresh, extinguishProb,
					useTemperature);
		ffdata = (ExForestFireData) data;
	}
	
	@Override
	public void randomizeGrid(final double density, final long seed) {
		// Note: density is not used; treeDensity & bushDensity is used instead.
		// TODO: overwrite
		
		// TODO: mega awesome terrain generation here
	}
	
	@Override
	public void igniteGrid() {
		// Fill the bottom line of the grid with burning vegetation.
		for(int x = 0; x < grid.grid.length; x++) {
			ExForestFireCell cell = (ExForestFireCell) grid.getCell(x, 0);
			// Set cell's of type BUSH to BURNING_BUSH and TREE to BURNING_TREE.
			if(cell.getType() == ExForestFireCellType.BUSH) {
				cell.setType(ExForestFireCellType.BURNING_BUSH);
			} else if(cell.getType() == ExForestFireCellType.TREE) {
				cell.setType(ExForestFireCellType.BURNING_TREE);
			}
		}
	}
	
	@Override
	public void resetGrid() {
		for(int y = 0; y < grid.grid[0].length; y++) {
			for(int x = 0; x < grid.grid.length; x++) {
				ExForestFireCell cell = (ExForestFireCell) grid.getCell(x, y);
				
				if(cell != null) {
					switch((ExForestFireCellType) cell.getType()) {
					// Reset all variants of BUSH back to a BUSH type.
						case BURNING_BUSH:
						case BURNT_BUSH:
						case EXTINGUISHED_BUSH:
							cell.setType(ExForestFireCellType.BUSH);
							// Reset all variants of TREE back to a TREE type.
						case BURNING_TREE:
						case BURNT_TREE:
						case EXTINGUISHED_TREE:
							cell.setType(ExForestFireCellType.TREE);
							// Ignore water and existing BUSH & TREE cell's.
						default:
							break;
					
					}
				}
			}
		}
		// Re ignite the fresh grid.
		igniteGrid();
	}
}
