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
	 * Hexagon neighbours
	 * (Xn, Yn+1) (Xn+1, Yn+1)
	 * (Xn-1, Yn) (Xn, Yn) (Xn+1, Yn)
	 * (Xn, Yn-1) (Xn+1, Yn-1)
	 */
	
	public static final double[][] NB_MOORE_HEX = {
													{0, 1, 1},
													{1, 0, 1},
													{0, 1, 1}};
	
	/*
	 * Triangle1 neighbours
	 * (Xn-1, Yn) (Xn, Yn) (Xn+1, Yn) /\
	 * (Xn, Yn-1) /__\
	 */
	
	public static final double[][] NB_MOORE_TRI1 = {
													{0, 0, 0},
													{1, 0, 1},
													{0, 1, 0}};
	/*
	 * Triangle2 neighbours ____
	 * (Xn, Yn+1) \ /
	 * (Xn-1, Yn) (Xn, Yn) (Xn+1, Yn) \/
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
	 * @param type
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
		
		Random rand = new Random();
		if(seed > 0) {
			rand.setSeed(seed);
		}
		/*
		 * for(int y = 0; y < grid.grid[0].length; y++) {
		 * grid.setCell(new ExForestFireCell(49, y, ExForestFireCellType.BUSH));
		 * if(randWater) {
		 * grid.setCell(new ExForestFireCell(50, y,
		 * ExForestFireCellType.WATER));
		 * }
		 * grid.setCell(new ExForestFireCell(51, y, ExForestFireCellType.TREE));
		 * }
		 */
		if(randWater) {
			// generate a number of random water points
			int points = rand.nextInt(5) + 1;
			int[][] pointcoord = new int[5][3];
			for(int i = 0; i < points; i++) {
				// generate coordinates for each point
				pointcoord[i][0] = rand.nextInt(100);
				pointcoord[i][1] = rand.nextInt(100);
				// if there is more then 1 point, connect it to a random other
				// point
				if(points > 1) {
					while(true) {
						int temp = rand.nextInt(points);
						// don't connect to self
						if(temp != i) {
							pointcoord[i][2] = temp;
							break;
						}
					}
				}
				// fill the blank if i'm the only point
				else {
					pointcoord[i][2] = 0;
				}
			}
			// draw all connections
			if(points > 1) {
				// connect random water points
				for(int i = 0; i < points; i++) {
					if(type == 0 || type == 1) {
						randomWaterStandard(pointcoord[i][0], pointcoord[i][1],
								pointcoord[pointcoord[i][2]][0],
								pointcoord[pointcoord[i][2]][1]);
					}
					else {
						randomWaterTriangle(pointcoord[i][0], pointcoord[i][1],
								pointcoord[pointcoord[i][2]][0],
								pointcoord[pointcoord[i][2]][1]);
					}
					grid.setCell(new ExForestFireCell(pointcoord[i][0],
							pointcoord[i][1], ExForestFireCellType.WATER));
				}
			}
			
			// connect the closest point to an edge -> fancy
			int edge = rand.nextInt(100);
			int xory = rand.nextInt(2);
			
			// can't be more than 200
			int distance = 200;
			int newdistance;
			int index = 0;
			if(xory == 0) {
				for(int i = 0; i < points; i++) {
					newdistance =
						Math.abs(pointcoord[i][0] - 0)
								+ Math.abs(pointcoord[i][1] - edge);
					if(newdistance < distance) {
						distance = newdistance;
						index = i;
					}
				}
				// normal walking
				if(type == 0 || type == 1) {
					randomWaterStandard(pointcoord[index][0],
							pointcoord[index][1], 0, edge);
				}
				// triangle walking
				else {
					randomWaterTriangle(pointcoord[index][0],
							pointcoord[index][1], 0, edge);
				}
			}
			else {
				for(int i = 0; i < points; i++) {
					newdistance =
						Math.abs(pointcoord[i][0] - edge)
								+ Math.abs(pointcoord[i][1] - 0);
					if(newdistance < distance) {
						distance = newdistance;
						index = i;
					}
				}
				// normal walking
				if(type == 0 || type == 1) {
					randomWaterStandard(pointcoord[index][0],
							pointcoord[index][1], edge, 0);
				}
				// triangle walking
				else {
					randomWaterTriangle(pointcoord[index][0],
							pointcoord[index][1], edge, 0);
				}
			}
		}
		int watercount = 0;
		for(int i = 0; i < grid.grid.length; i++) {
			for(int j = 0; j < grid.grid.length; j++) {
				ExForestFireCell cell =
					(ExForestFireCell) grid.getCell(i, j);
				// only water or empty at this point
				if(cell != null)
					watercount++;
			}
		}
		final int totaltrees =
			(int) Math.ceil(treeDensity
					* (grid.grid.length * (grid.grid[0].length - 1) - watercount));
		final int totalbushes =
			(int) Math.ceil(bushDensity
					* (grid.grid.length * (grid.grid[0].length - 1) - watercount));
		plantVegetation(totaltrees, ExForestFireCellType.TREE);
		plantVegetation(totalbushes, ExForestFireCellType.BUSH);
	}
	
	public void plantVegetation(int total, ExForestFireCellType cellType) {
		Random rand = new Random();
		int t = 0;
		int rx, ry;
		int tries = 0;
		while(t < total) {
			rx = rand.nextInt(grid.grid.length);
			ry = rand.nextInt((grid.grid[0].length));
			if(grid.getCell(rx, ry) == null) {
				grid.setCell(new ExForestFireCell(rx, ry, cellType));
				t++;
				tries = 0;
			} else {
				tries++;
				if(tries > 100) {
					break;
				}
			}
		}
		
	}
	
	public void randomWaterStandard(int xstart, int ystart, int xend, int yend) {
		int xcurr = xstart;
		int ycurr = ystart;
		
		// calculate distance left
		int distance = Math.abs(xcurr - xend) + Math.abs(ycurr - yend);
		int distX = Math.abs(xcurr - xend);
		int distY = Math.abs(ycurr - yend);
		Random rand = new Random();
		
		// find out the correct possible direction and randomly choose one
		while(distance > 0) {
			boolean goX = rand.nextInt(distX + distY) < distX;
			
			if(xend > xcurr && yend > ycurr) {
				if(goX) {
					xcurr++;
				}
				else {
					ycurr++;
				}
			}
			else if(xend < xcurr && yend < ycurr) {
				if(goX) {
					xcurr--;
				}
				else {
					ycurr--;
				}
			}
			else if(xend > xcurr && yend < ycurr) {
				if(goX) {
					xcurr++;
				}
				else {
					ycurr--;
				}
			}
			else if(xend < xcurr && yend > ycurr) {
				if(goX) {
					xcurr--;
				}
				else {
					ycurr++;
				}
			}
			else if(xend > xcurr) {
				xcurr++;
			}
			else if(yend > ycurr) {
				ycurr++;
			}
			else if(xend < xcurr) {
				xcurr--;
			}
			else if(yend < ycurr) {
				ycurr--;
			}
			
			// if we intersect with an existing river, break
			ExForestFireCell cell =
				(ExForestFireCell) grid.getCell(xcurr, ycurr);
			if(cell == null)
				grid.setCell(new ExForestFireCell(xcurr, ycurr,
						ExForestFireCellType.WATER));
			else
				break;
			
			distance = Math.abs(xcurr - xend) + Math.abs(ycurr - yend);
			distX = Math.abs(xcurr - xend);
			distY = Math.abs(ycurr - yend);
		}
		
		// Debug, see starting points
		// if(grid.getCell(xstart, ystart) != null) {
		// grid.getCell(xstart, ystart).setType(
		// ExForestFireCellType.BURNING_TREE);
		// } else {
		// grid.setCell(new ExForestFireCell(xstart, ystart,
		// ExForestFireCellType.BURNING_TREE));
		// }
	}
	
	public void randomWaterTriangle(int xstart, int ystart, int xend, int yend) {
		int xcurr = xstart;
		int ycurr = ystart;
		int xprev;
		int yprev;
		
		// calculate distance left
		int distance = Math.abs(xcurr - xend) + Math.abs(ycurr - yend);
		int distX = Math.abs(xcurr - xend);
		int distY = Math.abs(ycurr - yend);
		Random rand = new Random();
		
		// find out the correct possible direction and randomly choose one
		while(distance > 0) {
			xprev = xcurr;
			yprev = ycurr;
			boolean same = false;
			boolean goX = rand.nextInt(distX + distY) < distX;
			
			if(xend > xcurr && yend > ycurr) {
				if(goX) {
					xcurr++;
				}
				else if((ycurr % 2 == 1 && xcurr % 2 == 0)
						|| (ycurr % 2 == 0 && xcurr % 2 == 1)) {
					ycurr++;
				}
				else {
					xcurr++;
				}
			}
			else if(xend < xcurr && yend < ycurr) {
				if(goX) {
					xcurr--;
				}
				else if((ycurr % 2 == 0 && xcurr % 2 == 0)
						|| (ycurr % 2 == 1 && xcurr % 2 == 1)) {
					ycurr--;
				}
				else {
					xcurr--;
				}
			}
			else if(xend > xcurr && yend < ycurr) {
				if(goX) {
					xcurr++;
				}
				else if((ycurr % 2 == 0 && xcurr % 2 == 0)
						|| (ycurr % 2 == 1 && xcurr % 2 == 1)) {
					ycurr--;
				}
				else {
					xcurr++;
				}
			}
			else if(xend < xcurr && yend > ycurr) {
				if(goX) {
					xcurr--;
				}
				else if((ycurr % 2 == 1 && xcurr % 2 == 0)
						|| (ycurr % 2 == 0 && xcurr % 2 == 1)) {
					ycurr++;
				}
				else {
					xcurr--;
				}
			}
			else if(xend > xcurr) {
				xcurr++;
			}
			else if(yend > ycurr
					&& ((ycurr % 2 == 1 && xcurr % 2 == 0) || (ycurr % 2 == 0 && xcurr % 2 == 1))) {
				ycurr++;
			}
			else if(xend < xcurr) {
				xcurr--;
			}
			else if(yend < ycurr
					&& ((ycurr % 2 == 0 && xcurr % 2 == 0) || (ycurr % 2 == 1 && xcurr % 2 == 1))) {
				ycurr--;
			}
			
			// if no movement was made, i'm stuck going up or down, fix me
			if(xprev == xcurr && yprev == ycurr) {
				same = true;
				if(xcurr < grid.grid.length - 1 && xcurr > 0) {
					if(rand.nextInt(2) < 0) {
						grid.setCell(new ExForestFireCell(xcurr - 1, ycurr,
								ExForestFireCellType.WATER));
						ycurr = triangleUpDownFix(xcurr - 1, ycurr, yend);
					}
					else {
						grid.setCell(new ExForestFireCell(xcurr + 1, ycurr,
								ExForestFireCellType.WATER));
						ycurr = triangleUpDownFix(xcurr + 1, ycurr, yend);
					}
				}
				else if(xcurr == grid.grid.length - 1) {
					grid.setCell(new ExForestFireCell(xcurr - 1, ycurr,
							ExForestFireCellType.WATER));
					ycurr = triangleUpDownFix(xcurr - 1, ycurr, yend);
				}
				else {
					grid.setCell(new ExForestFireCell(xcurr + 1, ycurr,
							ExForestFireCellType.WATER));
					ycurr = triangleUpDownFix(xcurr + 1, ycurr, yend);
				}
			}
			
			// if we intersect with an existing river,
			// break (if normal conditions occurred)
			ExForestFireCell cell =
				(ExForestFireCell) grid.getCell(xcurr, ycurr);
			if(cell == null)
				grid.setCell(new ExForestFireCell(xcurr, ycurr,
						ExForestFireCellType.WATER));
			else if(same == false)
				break;
			
			distX = Math.abs(xcurr - xend);
			distY = Math.abs(ycurr - yend);
			distance = distX + distY;
			
		}
		
		// Debug, see starting points
		// if(grid.getCell(xstart, ystart) != null) {
		// grid.getCell(xstart, ystart).setType(
		// ExForestFireCellType.BURNING_TREE);
		// } else {
		// grid.setCell(new ExForestFireCell(xstart, ystart,
		// ExForestFireCellType.BURNING_TREE));
		// }
	}
	
	public int triangleUpDownFix(int xcurr, int ycurr, int yend) {
		// i'm stuck going up
		if(ycurr < yend) {
			grid.setCell(new ExForestFireCell(xcurr, ycurr + 1,
					ExForestFireCellType.WATER));
			ycurr++;
		}
		// i'm stuck going down
		else {
			grid.setCell(new ExForestFireCell(xcurr, ycurr - 1,
					ExForestFireCellType.WATER));
			ycurr--;
		}
		return ycurr;
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
					
						case BURNING_BUSH:
						case BURNT_BUSH:
						case EXTINGUISHED_BUSH:
							// Reset all variants of BUSH back to a BUSH type.
							cell.setType(ExForestFireCellType.BUSH);
						case BURNING_TREE:
						case BURNT_TREE:
						case EXTINGUISHED_TREE:
							// Reset all variants of TREE back to a TREE type.
							cell.setType(ExForestFireCellType.TREE);
						default:
							// Ignore water and existing BUSH & TREE cell's.
							break;
					
					}
				}
			}
		}
		// Re ignite the fresh grid.
		igniteGrid();
	}
}
