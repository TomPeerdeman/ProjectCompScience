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
	 */
	public ExForestFire(int nx, int ny, long seed,
			boolean[][] nb, boolean randWater, double treeDensity,
			double bushDensity, double fireFightTresh, double extinguishProb) {
		super(0, nx, ny, seed, nb, 0);
		this.randWater = randWater;
		this.treeDensity = treeDensity;
		this.bushDensity = bushDensity;
		
		data =
			new ExForestFireData(nb, grid, 0, fireFightTresh, extinguishProb);
		ffdata = (ExForestFireData) data;
	}
	
	@Override
	public void randomizeGrid(final double density, final long seed) {
		// Note: density is not used; treeDensity & bushDensity is used instead
		// TODO: overwrite
	}
	
	@Override
	public void igniteGrid() {
		// TODO: overwrite
	}
	
	@Override
	public void resetGrid() {
		// TODO: overwrite
		super.resetGrid();
	}
	
}
