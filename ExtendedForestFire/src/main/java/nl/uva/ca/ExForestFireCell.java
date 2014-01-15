/**
 * File: ExForestFireCell.java
 * 
 */
package nl.uva.ca;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.DataSet;
import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.Simulator;

public class ExForestFireCell extends Cell {
	private double temperature;
	private int nBurningTicks;
	
	/**
	 * @param x
	 * @param y
	 * @param t
	 */
	public ExForestFireCell(int x, int y, ExForestFireCellType t) {
		super(x, y, t);
		temperature = 0.0;
		nBurningTicks = 0;
	}
	
	@Override
	public boolean shouldSimulate() {
		return type == ExForestFireCellType.BURNING_BUSH
				|| type == ExForestFireCellType.BURNING_TREE;
	}
	
	/**
	 * @return the temperature of this cell
	 */
	public double getTemperature() {
		return temperature;
	}
	
	/**
	 * @param temperature
	 *            the temperature to set
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	/**
	 * @param temperature
	 *            the temperature to add
	 */
	public void addTemperature(double temperature) {
		this.temperature += temperature;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.tompeerdeman.ca.Cell#simulate(nl.tompeerdeman.ca.Grid,
	 * nl.tompeerdeman.ca.DataSet, nl.tompeerdeman.ca.Simulator)
	 */
	@Override
	public boolean simulate(Grid grid, DataSet data, Simulator sim) {
		ExForestFireData ffdata = (ExForestFireData) data;
		ExForestFireCell c;
		
		// debug
		/*
		 * System.out.println(ffdata.type);
		 * for(int i = 0; i < 3; i++){
		 * for(int j = 0; j < 3; j++)
		 * System.out.print(ffdata.neighborhood[i][j] + " ");
		 * System.out.println(" ");
		 * }
		 * System.out.println(" ");
		 */
		if(ffdata.type == 0) {
			int x, y;
			for(int ny = 0; ny < 3; ny++) {
				for(int nx = 0; nx < 3; nx++) {
					x = this.x + nx - 1;
					// Grid y increases north so cell above is y + 1
					y = this.y - ny + 1;
					if(x >= 0 && y >= 0 && x < grid.grid.length
							&& y < grid.grid[0].length) {
						c = (ExForestFireCell) grid.getCell(x, y);
						// If there is a probability given in the range 0-1,
						// draw a random number
						if(ffdata.neighborhood[ny][nx] < 1.0
								&& ffdata.neighborhood[ny][nx] > 0.0) {
							double randomDouble = Math.random();
							// If the probability is reached, put the cell on
							// fire, if it is a burnable cell
							if(randomDouble <= ffdata.neighborhood[ny][nx]) {
								setFire(ffdata,
										ExForestFireCellType.BURNING_TREE, c,
										sim);
								setFire(ffdata,
										ExForestFireCellType.BURNING_BUSH, c,
										sim);
							}
						}
						// If the probability is 1, put the cell on fire if it
						// is a burnable cell
						else if(ffdata.neighborhood[ny][nx] == 1.0) {
							setFire(ffdata, ExForestFireCellType.BURNING_TREE,
									c, sim);
							setFire(ffdata, ExForestFireCellType.BURNING_BUSH,
									c, sim);
						}
						
					}
				}
			}
		}
		else if(ffdata.type == 1){
			int x, y;
			for(int ny = 0; ny < 3; ny++) {
				for(int nx = 0; nx < 3; nx++) {
					if((ny == 0 && (nx == 1 || nx == 2)) || (ny == 1 && (nx == 0 || nx == 2)) || (ny == 2 && (nx == 1 || nx == 2))){
						if(this.y %2 == 1 || ny == 1)
							x = this.x + nx - 1;
						else
							x = this.x + nx - 2;
						// Grid y increases north so cell above is y + 1
						y = this.y - ny + 1;
						if(x >= 0 && y >= 0 && x < grid.grid.length
								&& y < grid.grid[0].length) {
							c = (ExForestFireCell) grid.getCell(x, y);
							// If there is a probability given in the range 0-1,
							// draw a random number
							if(ffdata.neighborhood[ny][nx] < 1.0
									&& ffdata.neighborhood[ny][nx] > 0.0) {
								double randomDouble = Math.random();
								// If the probability is reached, put the cell on
								// fire, if it is a burnable cell
								if(randomDouble <= ffdata.neighborhood[ny][nx]) {
									setFire(ffdata,
											ExForestFireCellType.BURNING_TREE, c,
											sim);
									setFire(ffdata,
											ExForestFireCellType.BURNING_BUSH, c,
											sim);
								}
							}
							// If the probability is 1, put the cell on fire if it
							// is a burnable cell
							else if(ffdata.neighborhood[ny][nx] == 1.0) {
								setFire(ffdata, ExForestFireCellType.BURNING_TREE,
										c, sim);
								setFire(ffdata, ExForestFireCellType.BURNING_BUSH,
										c, sim);
							}
						}
						
					}
				}
			}
		}
		
		else if(ffdata.type == 2){
			int x, y;
			for(int ny = 0; ny < 3; ny++) {
				for(int nx = 0; nx < 3; nx++) {
					System.out.println(ffdata.neighborhood[ny][nx]);
					//System.out.println((ny == 0 && nx == 1 && this.x % 2 == 1 && this.y % 2 == 0) + ", " + (ny == 0 && nx == 1 && this.x % 2 == 0 && this.y % 2 == 1) + ", " + (ny == 2 && nx == 1 && this.x % 2 == 0 && this.y % 2 == 0)
					//		   + ", " + (ny == 2 && nx == 1 && this.x % 2 == 1 && this.y % 2 == 1));
					if(ny == 1 || (ny == 0 && nx == 1 && this.x % 2 == 1 && this.y % 2 == 0)
							   || (ny == 0 && nx == 1 && this.x % 2 == 0 && this.y % 2 == 1)
							   || (ny == 2 && nx == 1 && this.x % 2 == 0 && this.y % 2 == 0)
							   || (ny == 2 && nx == 1 && this.x % 2 == 1 && this.y % 2 == 1)){
						x = this.x + nx - 1;
						// Grid y increases north so cell above is y + 1
						y = this.y - ny + 1;
						if(x >= 0 && y >= 0 && x < grid.grid.length
								&& y < grid.grid[0].length) {
							c = (ExForestFireCell) grid.getCell(x, y);
							// If there is a probability given in the range 0-1,
							// draw a random number
							if(ffdata.neighborhood[ny][nx] < 1.0
									&& ffdata.neighborhood[ny][nx] > 0.0) {
								double randomDouble = Math.random();
								// If the probability is reached, put the cell on
								// fire, if it is a burnable cell
								if(randomDouble <= ffdata.neighborhood[ny][nx]) {
									setFire(ffdata,
											ExForestFireCellType.BURNING_TREE, c,
											sim);
									setFire(ffdata,
											ExForestFireCellType.BURNING_BUSH, c,
											sim);
								}
							}
							// If the probability is 1, put the cell on fire if it
							// is a burnable cell
							else if(ffdata.neighborhood[ny][nx] == 1.0) {
								setFire(ffdata, ExForestFireCellType.BURNING_TREE,
										c, sim);
								setFire(ffdata, ExForestFireCellType.BURNING_BUSH,
										c, sim);
							}
						}
						
					}
				}
			}
		}
		
		if(type == ExForestFireCellType.BURNING_BUSH) {
			nBurningTicks++;
			if(nBurningTicks >= ffdata.nTicksBushBurn) {
				type = ExForestFireCellType.BURNT_BUSH;
				ffdata.burning--;
				ffdata.burnt++;
				
				if(ffdata.burning == 0) {
					sim.stop();
				}
				
				// Don't simulate this cell anymore.
				return false;
			}
		} else if(type == ExForestFireCellType.BURNING_TREE) {
			nBurningTicks++;
			if(nBurningTicks >= ffdata.nTicksTreeBurn) {
				ffdata.burning--;
				ffdata.burnt++;
				type = ExForestFireCellType.BURNT_TREE;
				
				if(ffdata.burning == 0) {
					sim.stop();
				}
				
				// Don't simulate this cell anymore.
				return false;
			}
		}
		return true;
	}
	
	public void setFire(ExForestFireData ffdata, ExForestFireCellType type,
			ExForestFireCell c, Simulator sim) {
		// Check for trees
		if(c != null && c.getType() == ExForestFireCellType.TREE) {
			c.setType(ExForestFireCellType.BURNING_TREE);
			ffdata.burning++;
			ffdata.trees--;
			sim.addSimulatable(c);
		}
		// Check for Bushes
		else if(c != null && c.getType() == ExForestFireCellType.BUSH) {
			c.setType(ExForestFireCellType.BURNING_BUSH);
			ffdata.burning++;
			ffdata.bushes--;
			sim.addSimulatable(c);
		}
	}
}
