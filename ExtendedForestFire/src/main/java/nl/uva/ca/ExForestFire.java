/**
 * File: ExForestFire.java
 * 
 */
package nl.uva.ca;

import java.util.Random;

import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.SimulatableSystem;

public class ExForestFire extends SimulatableSystem {
	public static final double[][] NB_NEUMANN = {
													{0, 1, 0},
													{1, 0, 1},
													{0, 1, 0}};
	/*
	 *  Hexagon neighbours
	 *         (Xn, Yn+1)  (Xn+1, Yn+1)
	 *   (Xn-1, Yn)    (Xn, Yn)    (Xn+1, Yn)  
	 *         (Xn, Yn-1)  (Xn+1, Yn-1)
	 */

	public static final double[][] NB_MOORE_HEX = {
													{0, 1, 1},
													{1, 0, 1},
													{0, 1, 1}};

	/*
	 *  Triangle1 neighbours
	 *   (Xn-1, Yn)    (Xn, Yn)    (Xn+1, Yn)  /\
	 *                (Xn, Yn-1)              /__\
	 */

	public static final double[][] NB_MOORE_TRI1 = {
													{0, 0, 0},
													{1, 0, 1},
													{0, 1, 0}};
	/*
	 *  Triangle2 neighbours                  ____
     *                (Xn, Yn+1)              \  /
	 *   (Xn-1, Yn)    (Xn, Yn)    (Xn+1, Yn)  \/

	 */

	public static final double[][] NB_MOORE_TRI2 = {
													{0, 1, 0},
													{1, 0, 1},
													{0, 0, 0}};

	
	public boolean randWater;
	public double treeDensity;
	public double bushDensity;
	public int type;
	
	/**
	 * @param nx
	 * @param ny
	 * @param seed
	 * @param nb
	 * @param randWater
	 * @param firefighters
	 * @param treeDensity
	 * @param bushDensity
	 * @param fireFightTresh
	 * @param extinguishProb
	 * @param useTemperature
	 */
	public ExForestFire(int nx, int ny, long seed,
			boolean[][] nb, boolean randWater, boolean firefighters,
			double treeDensity,
			double bushDensity, double fireFightTresh, double extinguishProb,
			boolean useTemperature, int type) {
		this.randWater = randWater;
		this.treeDensity = treeDensity;
		this.bushDensity = bushDensity;
		this.type = type;
		
		// Create an empty grid
		grid = new Grid(nx, ny);
		
		randomizeGrid(seed);
		igniteGrid();
		
		data =
			new ExForestFireData(grid, NB_NEUMANN,
					((firefighters) ? fireFightTresh : -1.0),
					extinguishProb, useTemperature);
	}
	
	public void randomizeGrid(final long seed) {
		// TODO: mega awesome terrain generation here
		
		grid.clear();
		
		/*for(int y = 0; y < grid.grid[0].length; y++) {
			grid.setCell(new ExForestFireCell(49, y, ExForestFireCellType.BUSH));
			if(randWater) {
				grid.setCell(new ExForestFireCell(50, y,
						ExForestFireCellType.WATER));
			}
			grid.setCell(new ExForestFireCell(51, y, ExForestFireCellType.TREE));
		}*/
		if(randWater){
			Random rand = new Random(12); 
			int xwater1 = rand.nextInt(100);
			int ywater1 = rand.nextInt(100);
			int xwater2 = rand.nextInt(100);
			int ywater2 = rand.nextInt(100);
			int edge = rand.nextInt(100);
			int xory = rand.nextInt(2);
			grid.setCell(new ExForestFireCell(xwater1, ywater1, ExForestFireCellType.WATER));
			grid.setCell(new ExForestFireCell(xwater2, ywater2, ExForestFireCellType.WATER));
			if(xory == 0){
				grid.setCell(new ExForestFireCell(0, edge, ExForestFireCellType.WATER));
			}
			else{
				grid.setCell(new ExForestFireCell(edge, 0, ExForestFireCellType.WATER));
			}
			randomWater(xwater1, ywater1, xwater2, ywater2);

		}
	}
	
	public void randomWater(int xstart, int ystart, int xend, int yend){
		int xcurr = xstart;
		int ycurr = ystart;
		int xory;
		int distance = Math.abs(xcurr - xend) + Math.abs(ycurr - yend);
		Random rand2 = new Random(); 
		while(distance > 0){
			if(xend > xcurr && yend > ycurr){
				xory = rand2.nextInt(2);
				if(xory == 0){
					xcurr++;
				}
				else{
					ycurr++;
				}				
			}
			else if(xend < xcurr && yend < ycurr){
				xory = rand2.nextInt(2);
				if(xory == 0){
					xcurr--;
				}
				else{
					ycurr--;
				}				
			}
			else if(xend > xcurr && yend < ycurr){
				xory = rand2.nextInt(2);
				if(xory == 0){
					xcurr++;
				}
				else{
					ycurr--;
				}				
			}
			else if(xend < xcurr && yend > ycurr){
				xory = rand2.nextInt(2);
				if(xory == 0){
					xcurr--;
				}
				else{
					ycurr++;
				}				
			}
			else if(xend > xcurr){
				xcurr++;
			}
			else if(yend > ycurr){
				ycurr++;
			}
			else if(xend < xcurr){
				xcurr--;
			}
			else if(yend < ycurr){
				ycurr--;
			}
			grid.setCell(new ExForestFireCell(xcurr, ycurr, ExForestFireCellType.WATER));

			distance = Math.abs(xcurr - xend) + Math.abs(ycurr - yend);
		}
	}
	
	public void igniteGrid() {
		// Fill the bottom line of the grid with burning vegetation.
		for(int x = 0; x < grid.grid.length; x++) {
			ExForestFireCell cell = (ExForestFireCell) grid.getCell(x, 0);
			if(cell != null) {
				// Set cell's of type BUSH to BURNING_BUSH and TREE to
				// BURNING_TREE.
				if(cell.getType() == ExForestFireCellType.BUSH) {
					cell.setType(ExForestFireCellType.BURNING_BUSH);
				} else if(cell.getType() == ExForestFireCellType.TREE) {
					cell.setType(ExForestFireCellType.BURNING_TREE);
				}
			}
		}
	}
	
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
