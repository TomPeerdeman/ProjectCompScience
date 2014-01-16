/**
 * File: ExForestFireCell.java
 * 
 */
package nl.uva.ca;

import java.util.Random;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.CellType;
import nl.tompeerdeman.ca.DataSet;
import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.Simulator;

public class ExForestFireCell extends Cell {
	private double temperature;
	private int nBurningTicks;
	private CellType secondaryType;
	
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
				|| type == ExForestFireCellType.BURNING_TREE
				|| type == ExForestFireCellType.FIRE_FIGHTER
				|| type == ExForestFireCellType.PATH;
	}
	
	public void addFireFighter() {
		secondaryType = type;
		type = ExForestFireCellType.FIRE_FIGHTER;
	}
	
	/**
	 * Remove the fire fighter from this cell
	 * 
	 * @param grid
	 * @return True if this cell should be simulated after the removal,
	 *         otherwise false
	 */
	public boolean removeFireFighter(Grid grid) {
		type = secondaryType;
		secondaryType = null;
		if(type == null) {
			grid.clearCell(x, y);
			return false;
		}
		return shouldSimulate();
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
		ExForestFireCell c = null;
		int probvar = ffdata.probDivider;
		
		if(ffdata.type == 0) {
			if(type == ExForestFireCellType.BURNING_TREE
					|| type == ExForestFireCellType.BURNING_BUSH) {
				int x, y, x2, y2;
				double prob = 0.0;
				for(int ny = 0; ny < 3; ny++) {
					for(int nx = 0; nx < 3; nx++) {
						x = this.x + nx - 1;
						// Grid y increases north so cell above is y + 1
						y = this.y - ny + 1;
						checkFire(x, y, nx, ny, grid, ffdata, c, sim);
						
					}
				}
				boolean fire = false;
				for(int ny = 0; ny < 5; ny++) {
					for(int nx = 0; nx < 5; nx++) {
						fire = false;
						x2 = this.x + nx - 2;
						// Grid y increases north so cell above is y + 1
						y2 = this.y - ny + 2;
						if(ny == 0) {
							if(nx == 0) {
								prob = ffdata.neighborhood[0][0] / probvar;
								fire = true;
							}
							else if(nx == 1) {
								prob =
									((ffdata.neighborhood[0][0] / probvar) + (ffdata.neighborhood[0][1] / probvar)) / 2;
								fire = true;
							}
							else if(nx == 2) {
								prob =
									((ffdata.neighborhood[0][0] / probvar)
											+ (ffdata.neighborhood[0][1] / probvar) + (ffdata.neighborhood[0][2] / probvar)) / 3;
								fire = true;
							}
							else if(nx == 3) {
								prob =
									((ffdata.neighborhood[0][1] / probvar) + (ffdata.neighborhood[0][2] / probvar)) / 2;
								fire = true;
							}
							else if(nx == 4) {
								prob = ffdata.neighborhood[0][2] / probvar;
								fire = true;
							}
						}
						else if(ny == 1) {
							if(nx == 0) {
								prob =
									((ffdata.neighborhood[0][0] / probvar) + (ffdata.neighborhood[1][0] / probvar)) / 2;
								fire = true;
							}
							else if(nx == 4) {
								prob =
									((ffdata.neighborhood[0][2] / probvar) + (ffdata.neighborhood[1][2] / probvar)) / 2;
								fire = true;
							}
						}
						else if(ny == 2) {
							if(nx == 0) {
								prob =
									((ffdata.neighborhood[0][0] / probvar)
											+ (ffdata.neighborhood[1][0] / probvar) + (ffdata.neighborhood[2][0] / probvar)) / 3;
								fire = true;
							}
							else if(nx == 4) {
								prob =
									((ffdata.neighborhood[0][2] / probvar)
											+ (ffdata.neighborhood[1][2] / probvar) + (ffdata.neighborhood[2][2] / probvar)) / 3;
								fire = true;
							}
						}
						else if(ny == 3) {
							if(nx == 0) {
								prob =
									((ffdata.neighborhood[2][0] / probvar) + (ffdata.neighborhood[1][0] / probvar)) / 2;
								fire = true;
							}
							else if(nx == 4) {
								prob =
									((ffdata.neighborhood[2][2] / probvar) + (ffdata.neighborhood[1][2] / probvar)) / 2;
								fire = true;
							}
						}
						else if(ny == 4) {
							if(nx == 0) {
								prob = ffdata.neighborhood[2][0] / probvar;
								fire = true;
							}
							else if(nx == 1) {
								prob =
									((ffdata.neighborhood[2][0] / probvar) + (ffdata.neighborhood[2][1] / probvar)) / 2;
								fire = true;
							}
							else if(nx == 2) {
								prob =
									((ffdata.neighborhood[2][0] / probvar)
											+ (ffdata.neighborhood[2][1] / probvar) + (ffdata.neighborhood[2][2] / probvar)) / 3;
								fire = true;
							}
							else if(nx == 3) {
								prob =
									((ffdata.neighborhood[2][1] / probvar) + (ffdata.neighborhood[2][2] / probvar)) / 2;
								fire = true;
							}
							else if(nx == 4) {
								prob = ffdata.neighborhood[2][2] / probvar;
								fire = true;
							}
						}
						if(fire)
							checkFireRadius2(x2, y2, grid, ffdata, c, sim, prob);
					}
				}
			}
			else if(type == ExForestFireCellType.FIRE_FIGHTER) {
				if(!ffdata.fireFighters) {
					System.out.println("imah ff");
					return removeFireFighter(grid);
				}
				else {
					ExForestFireCell newCell;
					// initialize max distance to fire
					// can't be more than 200
					int distToFire = 200;
					int newDistance;
					int indexX = this.x, indexY = this.y;
					int newX = this.x, newY = this.y;
					// search nearest fire
					for(int i = Math.max(0, this.x - 3); i < Math.min(
							grid.grid[0].length, this.x + 3); i++) {
						for(int j = Math.max(0, this.y - 3); j < Math.min(
								grid.grid.length, this.y + 3); j++) {
							ExForestFireCell cell =
								(ExForestFireCell) grid.getCell(i, j);
							if(cell != null) {
								if(cell.getType() == ExForestFireCellType.BURNING_TREE
										|| cell.getType() == ExForestFireCellType.BURNING_BUSH) {
									newDistance =
										Math.abs(j - this.x)
												+ Math.abs(i - this.y);
									if(newDistance < distToFire) {
										distToFire = newDistance;
										indexX = i;
										indexY = j;
									}
								}
							}
						}
					}
					 // System.out.println("Distance to fire: " + distToFire +
					 // " Im at: (" +this.x+ " ; " +this.y+ ") Fire is at: ("
					 // +indexX+" ; "+ indexY+ ")");
					if(distToFire > 1 && distToFire < 200) {
						// move to nearest fire
						if(indexX < this.x)
							newX--;
						else if(indexX > this.x)
							newX++;
						if(indexY < this.y)
							newY--;
						else if(indexY > this.y)
							newY++;
						// System.out.println("Im at: (" +this.x+ " ; " +this.y+
						// ") im going to: (" +newX+" ; "+ newY+ ")");
						newCell = (ExForestFireCell) grid.getCell(newX, newY);
						
						if(newCell != null
								&& newCell.getType() != ExForestFireCellType.BURNING_BUSH
								&& newCell.getType() != ExForestFireCellType.BURNING_TREE
								&& newCell.getType() != ExForestFireCellType.FIRE_FIGHTER) {
							// System.out.println("i wasnt null");
							newCell.addFireFighter();
							sim.addSimulatable(newCell);
						} else if(newCell == null) {
							// System.out.println("i was null");
							newCell =
								new ExForestFireCell(newX, newY, null);
							// Add new cell to grid.
							grid.setCell(newCell);
							// New cell is FFighter only.
							newCell.addFireFighter();
							sim.addSimulatable(newCell);
						}
						else
							return true;
						
						// Remove FFighter from this cell.
						return removeFireFighter(grid);
					}
				}
				// System.out.println("");
			}
			else if(type == ExForestFireCellType.PATH) {
				if(ffdata.fireFighters) {
					// maybe break here after x ticks fire fighter adding?
					
					Random r = new Random();
					// Set to firefighter in 1% of the cases (change to variable
					// maybe?)
					if(r.nextInt(101) == 1)
						addFireFighter();
				}
			}
		}
		else if(ffdata.type == 1) {
			if(type == ExForestFireCellType.BURNING_TREE
					|| type == ExForestFireCellType.BURNING_BUSH) {
				int x, y, x2, y2;
				double prob = 0.0;
				for(int ny = 0; ny < 3; ny++) {
					for(int nx = 0; nx < 3; nx++) {
						if((ny == 0 && (nx == 1 || nx == 2))
								|| (ny == 1 && (nx == 0 || nx == 2))
								|| (ny == 2 && (nx == 1 || nx == 2))) {
							if(this.y % 2 == 1 || ny == 1)
								x = this.x + nx - 1;
							else
								x = this.x + nx - 2;
							// Grid y increases north so cell above is y + 1
							y = this.y - ny + 1;
							checkFire(x, y, nx, ny, grid, ffdata, c, sim);
						}
					}
				}
				boolean fire = false;
				for(int ny = 0; ny < 5; ny++) {
					for(int nx = 0; nx < 5; nx++) {
						fire = false;
						x2 = this.x + nx - 2;
						// Grid y increases north so cell above is y + 1
						y2 = this.y - ny + 2;
						if(ny == 0) {
							if(nx == 1) {
								prob = ffdata.neighborhood[0][1] / probvar;
								fire = true;
							}
							else if(nx == 2) {
								prob =
									((ffdata.neighborhood[0][1] / probvar) + (ffdata.neighborhood[0][1] / probvar)) / 2;
								fire = true;
							}
							else if(nx == 3) {
								prob = ffdata.neighborhood[0][2] / probvar;
								fire = true;
							}
						}
						else if(ny == 1) {
							if(this.y % 2 == 0) {
								if(nx == 0) {
									prob =
										((ffdata.neighborhood[0][1] / probvar) + (ffdata.neighborhood[1][0] / probvar)) / 2;
									fire = true;
								}
								else if(nx == 3) {
									prob =
										((ffdata.neighborhood[0][2] / probvar) + (ffdata.neighborhood[1][2] / probvar)) / 2;
									fire = true;
								}
							}
							else if(this.y % 2 == 1) {
								if(nx == 1) {
									prob =
										((ffdata.neighborhood[0][1] / probvar) + (ffdata.neighborhood[1][0] / probvar)) / 2;
									fire = true;
								}
								else if(nx == 4) {
									prob =
										((ffdata.neighborhood[0][2] / probvar) + (ffdata.neighborhood[1][2] / probvar)) / 2;
									fire = true;
								}
							}
						}
						else if(ny == 2) {
							if(nx == 0) {
								prob = ffdata.neighborhood[1][0] / probvar;
								fire = true;
							}
							else if(nx == 4) {
								prob = ffdata.neighborhood[1][2] / probvar;
								fire = true;
							}
						}
						else if(ny == 3) {
							if(this.y % 2 == 0) {
								if(nx == 0) {
									prob =
										((ffdata.neighborhood[1][0] / probvar) + (ffdata.neighborhood[2][1] / probvar)) / 2;
									fire = true;
								}
								else if(nx == 3) {
									prob =
										((ffdata.neighborhood[1][2] / probvar) + (ffdata.neighborhood[2][2] / probvar)) / 2;
									fire = true;
								}
							}
							else if(this.y % 2 == 1) {
								if(nx == 1) {
									prob =
										((ffdata.neighborhood[1][0] / probvar) + (ffdata.neighborhood[2][1] / probvar)) / 2;
									fire = true;
								}
								else if(nx == 4) {
									prob =
										((ffdata.neighborhood[1][2] / probvar) + (ffdata.neighborhood[2][2] / probvar)) / 2;
									fire = true;
								}
							}
						}
						else if(ny == 4) {
							if(nx == 1) {
								prob = ffdata.neighborhood[2][1] / probvar;
								fire = true;
							}
							else if(nx == 2) {
								prob =
									((ffdata.neighborhood[2][1] / probvar) + (ffdata.neighborhood[2][1] / probvar)) / 2;
								fire = true;
							}
							else if(nx == 3) {
								prob = ffdata.neighborhood[2][2] / probvar;
								fire = true;
							}
						}
						if(fire)
							checkFireRadius2(x2, y2, grid, ffdata, c, sim, prob);
					}
				}
			}
		}
		
		else if(ffdata.type == 2) {
			if(type == ExForestFireCellType.BURNING_TREE
					|| type == ExForestFireCellType.BURNING_BUSH) {
				int x, y, x2, y2;
				double prob = 0.0;
				for(int ny = 0; ny < 3; ny++) {
					for(int nx = 0; nx < 3; nx++) {
						if((ny == 1 && this.x % 2 == 1 && this.y % 2 == 0)
								|| (ny == 1 && this.x % 2 == 0 && this.y % 2 == 1)
								|| (ny == 0 && nx == 1 && this.x % 2 == 1 && this.y % 2 == 0)
								|| (ny == 0 && nx == 1 && this.x % 2 == 0 && this.y % 2 == 1)) {
							x = this.x + nx - 1;
							// Grid y increases north so cell above is y + 1
							y = this.y - ny + 1;
							
							checkFire(x, y, nx, ny, grid, ffdata, c, sim);
						}
						else if((ny == 1 && this.x % 2 == 0 && this.y % 2 == 0)
								|| (ny == 1 && this.x % 2 == 1 && this.y % 2 == 1)
								|| (ny == 2 && nx == 1 && this.x % 2 == 0 && this.y % 2 == 0)
								|| (ny == 2 && nx == 1 && this.x % 2 == 1 && this.y % 2 == 1)) {
							x = this.x + nx - 1;
							// Grid y increases north so cell above is y + 1
							y = this.y - ny + 1;
							int tempny;
							tempny = ny - 1;
							checkFire(x, y, nx, tempny, grid, ffdata, c, sim);
						}
					}
				}
				boolean fire = false;
				for(int ny = 0; ny < 5; ny++) {
					for(int nx = 0; nx < 5; nx++) {
						fire = false;
						x2 = this.x + nx - 2;
						// Grid y increases north so cell above is y + 1
						y2 = this.y - ny + 2;
						// triangle type 1
						if((this.x % 2 == 0 && this.y % 2 == 0)
								|| (this.x % 2 == 1 && this.y % 2 == 1)) {
							if(ny == 1) {
								if(nx == 1) {
									prob =
										(ffdata.neighborhood[0][0] / probvar);
									fire = true;
								}
								else if(nx == 3) {
									prob =
										(ffdata.neighborhood[0][2] / probvar);
									fire = true;
								}
							}
							else if(ny == 2) {
								if(nx == 0) {
									prob =
										(ffdata.neighborhood[0][0] / probvar);
									fire = true;
								}
								else if(nx == 4) {
									prob =
										(ffdata.neighborhood[0][2] / probvar);
									fire = true;
								}
							}
							if(ny == 3) {
								if(nx == 1 || nx == 3) {
									prob =
										(ffdata.neighborhood[1][1] / probvar);
									fire = true;
								}
							}
						}
						// triangle type 2
						else if((this.x % 2 == 1 && this.y % 2 == 0)
								|| (this.x % 2 == 0 && this.y % 2 == 1)) {
							if(ny == 1) {
								if(nx == 1 || nx == 3) {
									prob =
										(ffdata.neighborhood[0][1] / probvar);
									fire = true;
								}
							}
							else if(ny == 2) {
								if(nx == 0) {
									prob =
										(ffdata.neighborhood[1][0] / probvar);
									fire = true;
								}
								else if(nx == 4) {
									prob =
										(ffdata.neighborhood[1][2] / probvar);
									fire = true;
								}
							}
							else if(ny == 3) {
								if(nx == 1) {
									prob =
										(ffdata.neighborhood[1][0] / probvar);
									fire = true;
								}
								else if(nx == 3) {
									prob =
										(ffdata.neighborhood[1][2] / probvar);
									fire = true;
								}
							}
						}
						if(fire)
							checkFireRadius2(x2, y2, grid, ffdata, c, sim, prob);
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
	
	private void checkFire(int x, int y, int nx, int ny, Grid grid,
			ExForestFireData ffdata,
			ExForestFireCell c, Simulator sim) {
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
	
	// checkFire for neighbors with distance 2 from me
	private void checkFireRadius2(int x, int y, Grid grid,
			ExForestFireData ffdata,
			ExForestFireCell c, Simulator sim, double prob) {
		if(x >= 0 && y >= 0 && x < grid.grid.length
				&& y < grid.grid[0].length) {
			c = (ExForestFireCell) grid.getCell(x, y);
			// If there is a probability given in the range 0-1,
			// draw a random number
			if(prob < 1.0
					&& prob > 0.0) {
				double randomDouble = Math.random();
				// If the probability is reached, put the cell on
				// fire, if it is a burnable cell
				if(randomDouble <= prob) {
					setFire(ffdata,
							ExForestFireCellType.BURNING_TREE, c,
							sim);
					setFire(ffdata,
							ExForestFireCellType.BURNING_BUSH, c,
							sim);
				}
			}
		}
	}
	
	private void setFire(ExForestFireData ffdata, ExForestFireCellType type,
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
