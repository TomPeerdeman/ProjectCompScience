/**
 * File: ExForestFireCell.java
 * 
 */
package nl.uva.ca;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.CellType;
import nl.tompeerdeman.ca.DataSet;
import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.Simulator;

public class ExForestFireCell extends AbstractCell implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int nBurningTicks;
	private CellType secondaryType;
	public int[] prevX = {200, 200, 200, 200};
	public int[] prevY = {200, 200, 200, 200};
	
	/**
	 * @param x
	 * @param y
	 * @param t
	 */
	public ExForestFireCell(int x, int y, ExForestFireCellType t) {
		super(x, y, t);
		nBurningTicks = 0;
		prevX = new int[4];
		prevY = new int[4];
		prevX[0] = x;
		prevY[0] = y;
	}
	
	/**
	 * Custom write type, x and y since Cell type is not serializable
	 * 
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(type);
		out.writeInt(x);
		out.writeInt(y);
	}
	
	/**
	 * Custom read type, x and y since Cell type is not serializable
	 * 
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		type = (CellType) in.readObject();
		x = in.readInt();
		y = in.readInt();
	}
	
	@Override
	public boolean shouldSimulate() {
		return type == ExForestFireCellType.BURNING_BUSH
				|| type == ExForestFireCellType.BURNING_TREE
				|| type == ExForestFireCellType.FIRE_FIGHTER
				|| (type == ExForestFireCellType.PATH && (x == 0 || x == 99
						|| y == 0 || y == 99));
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
	
	public boolean murderFireFighter(Grid grid, ExForestFireData ffdata) {
		type = secondaryType;
		secondaryType = null;
		if(type == null) {
			ffdata.nDeadFireFighters++;
			ffdata.nFireFighters--;
			grid.clearCell(this.x, this.y);
			return false;
		}
		else if(type == ExForestFireCellType.TREE) {
			type = ExForestFireCellType.BURNING_TREE;
			ffdata.burning++;
		}
		else if(type == ExForestFireCellType.BUSH) {
			type = ExForestFireCellType.BURNING_BUSH;
			ffdata.burning++;
		}
		return shouldSimulate();
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
		
		// I'm extinguished, don't simulate me
		if(type == ExForestFireCellType.EXTINGUISHED_TREE
				|| type == ExForestFireCellType.EXTINGUISHED_BUSH)
			return false;
		
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
				if(probvar > 0) {
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
								checkFireRadius2(x2, y2, grid, ffdata, c, sim,
										prob);
						}
					}
				}
			} else if(type == ExForestFireCellType.FIRE_FIGHTER) {
				if(!ffdata.fireFighters) {
					return removeFireFighter(grid);
				}
				else {
					// initialize max distance to fire
					// can't be more than 200
					int newX = this.x, newY = this.y;
					int[] distToFire = {200, this.x, this.y};
					
					distToFire = searchFire(grid, distToFire);
					
					// search nearest fire
					if(distToFire[0] > 1 && distToFire[0] < 200) {
						// move to nearest fire
						if(distToFire[1] < this.x)
							newX--;
						else if(distToFire[1] > this.x)
							newX++;
						if(distToFire[2] < this.y)
							newY--;
						else if(distToFire[2] > this.y)
							newY++;
						
						return walkFireFighter(grid, newX, newY, sim);
					}
					else if(distToFire[0] == 1) {
						int x, y;
						double randomDouble;
						int fireCount = 0;
						Random rand = new Random();
						// count surrounding fires
						for(int ny = 0; ny < 3; ny++) {
							for(int nx = 0; nx < 3; nx++) {
								x = this.x + nx - 1;
								y = this.y - ny + 1;
								if(x >= 0 && x < 100 && y >= 0 && y < 100) {
									c = (ExForestFireCell) grid.getCell(x, y);
									if(c != null
											&& (c.getType() == ExForestFireCellType.BURNING_TREE
											|| c.getType() == ExForestFireCellType.BURNING_BUSH)) {
										fireCount++;
									}
								}
							}
						}
						// if there are fires around me, i could die
						if(fireCount > 0) {
							// exponentially increasing chance of death
							double deathProb = Math.pow(1.45, (8 - fireCount));
							int deathProbInt = (int) Math.round(deathProb);
							// already died, no chance of escape
							if(deathProbInt == 1) {
								// should this return?
								return murderFireFighter(grid, ffdata);
							}
							// else probability
							else {
								int died = rand.nextInt(deathProbInt);
								if(died == 0) {
									// should this return?
									return murderFireFighter(grid, ffdata);
								}
							}
						}
						for(int ny = 0; ny < 3; ny++) {
							for(int nx = 0; nx < 3; nx++) {
								x = this.x + nx - 1;
								y = this.y - ny + 1;
								if(x >= 0 && x < 100 && y >= 0 && y < 100) {
									c =
										(ExForestFireCell) grid.getCell(x,
												y);
									// If a tree is burning, try to
									// extinguish
									if(c != null
											&& c.getType() == ExForestFireCellType.BURNING_TREE) {
										randomDouble = Math.random();
										if(randomDouble <= ffdata.extinguishProb) {
											c.setType(ExForestFireCellType.EXTINGUISHED_TREE);
											ffdata.burning--;
											ffdata.trees++;
										}
									}
									// If a bush is burning, try to
									// extinguish
									else if(c != null
											&& c.getType() == ExForestFireCellType.BURNING_BUSH) {
										randomDouble = Math.random();
										if(randomDouble <= ffdata.extinguishProb) {
											c.setType(ExForestFireCellType.EXTINGUISHED_BUSH);
											ffdata.burning--;
											ffdata.bushes++;
										}
										
									}
								}
							}
						}
					}
				}
			}
			else if(type == ExForestFireCellType.PATH) {
				setPathToFireFighter(ffdata);
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
				if(probvar > 0) {
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
								checkFireRadius2(x2, y2, grid, ffdata, c, sim,
										prob);
						}
					}
				}
			}
			else if(type == ExForestFireCellType.FIRE_FIGHTER) {
				if(!ffdata.fireFighters) {
					return removeFireFighter(grid);
				}
				else {
					// initialize max distance to fire
					// can't be more than 200
					int newX = this.x, newY = this.y;
					int[] distToFire = {200, this.x, this.y};
					
					distToFire = searchFire(grid, distToFire);
					// System.out.println("Distance to fire: " + distToFire[0] +
					// " Im at: (" +this.x+ " ; " +this.y+ ") Fire is at: ("
					// +distToFire[1]+" ; "+ distToFire[2]+ ")");
					if(distToFire[0] > 1 && distToFire[0] < 200) {
						// move to nearest fire
						if(distToFire[1] < this.x) {
							// same row
							if(distToFire[2] == this.y || this.y % 2 == 0)
								newX--;
						}
						else if(distToFire[1] > this.x) {
							// same row
							if(distToFire[2] == this.y || this.y % 2 == 1)
								newX++;
						}
						
						if(distToFire[2] < this.y)
							newY--;
						else if(distToFire[2] > this.y)
							newY++;
						return walkFireFighter(grid, newX, newY, sim);
					}
					else if(distToFire[0] == 1) {
						int x, y;
						double randomDouble;
						int fireCount = 0;
						Random rand = new Random();
						// count surrounding fires
						for(int ny = 0; ny < 3; ny++) {
							for(int nx = 0; nx < 3; nx++) {
								if(this.y % 2 == 1 || ny == 1)
									x = this.x + nx - 1;
								else
									x = this.x + nx - 2;
								// Grid y increases north so cell above is y + 1
								y = this.y - ny + 1;
								if(x >= 0 && x < 100 && y >= 0 && y < 100) {
									c = (ExForestFireCell) grid.getCell(x, y);
									if(c != null
											&& (c.getType() == ExForestFireCellType.BURNING_TREE
											|| c.getType() == ExForestFireCellType.BURNING_BUSH)) {
										fireCount++;
									}
								}
							}
						}
						// if there are fires around me, i could die
						if(fireCount > 0) {
							// exponentially increasing chance of death
							double deathProb = Math.pow(1.6, (6 - fireCount));
							int deathProbInt = (int) Math.round(deathProb);
							// already died, no chance of escape
							if(deathProbInt <= 1) {
								// should this return?
								return murderFireFighter(grid, ffdata);
							}
							// else probability
							else {
								int died = rand.nextInt(deathProbInt);
								if(died == 0) {
									// should this return?
									return murderFireFighter(grid, ffdata);
								}
							}
						}
						for(int ny = 0; ny < 3; ny++) {
							for(int nx = 0; nx < 3; nx++) {
								if((ny == 0 && (nx == 1 || nx == 2))
										|| (ny == 1 && (nx == 0 || nx == 2))
										|| (ny == 2 && (nx == 1 || nx == 2))) {
									if(this.y % 2 == 1 || ny == 1)
										x = this.x + nx - 1;
									else
										x = this.x + nx - 2;
									// Grid y increases north so cell above is y
									// + 1
									y = this.y - ny + 1;
									if(x >= 0 && x < 100 && y >= 0 && y < 100) {
										c =
											(ExForestFireCell) grid.getCell(x,
													y);
										// If a tree is burning, try to
										// extinguish
										if(c != null
												&& c.getType() == ExForestFireCellType.BURNING_TREE) {
											randomDouble = Math.random();
											if(randomDouble <= ffdata.extinguishProb) {
												c.setType(ExForestFireCellType.EXTINGUISHED_TREE);
												ffdata.burning--;
												ffdata.trees++;
											}
										}
										// If a bush is burning, try to
										// extinguish
										else if(c != null
												&& c.getType() == ExForestFireCellType.BURNING_BUSH) {
											randomDouble = Math.random();
											if(randomDouble <= ffdata.extinguishProb) {
												c.setType(ExForestFireCellType.EXTINGUISHED_BUSH);
												ffdata.burning--;
												ffdata.bushes++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			else if(type == ExForestFireCellType.PATH) {
				setPathToFireFighter(ffdata);
			}
		}
		
		else if(ffdata.type == 2) {
			if(type == ExForestFireCellType.BURNING_TREE
					|| type == ExForestFireCellType.BURNING_BUSH) {
				int x, y, x2, y2;
				double prob = 0.0;
				
				for(int ny = 0; ny < 3; ny++) {
					for(int nx = 0; nx < 5; nx++) {
						// triangletype1
						if(this.x % 2 == this.y % 2) {
							// bottom row
							if(ny == 0) {
								y = this.y - 1;
								// bottom left
								if(nx == 0) {
									x = this.x - 2;
									checkFire(x, y, 0, 0, grid, ffdata, c, sim);
								}
								// bottom center left
								else if(nx == 1) {
									x = this.x - 1;
									prob =
										(ffdata.neighborhood[0][0] + ffdata.neighborhood[1][1]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
								// bottom center
								else if(nx == 2) {
									x = this.x;
									checkFire(x, y, 1, 1, grid, ffdata, c, sim);
								}
								// bottom center right
								else if(nx == 3) {
									x = this.x + 1;
									prob =
										(ffdata.neighborhood[0][2] + ffdata.neighborhood[1][1]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
								// bottom right
								else if(nx == 4) {
									x = this.x + 2;
									checkFire(x, y, 2, 0, grid, ffdata, c, sim);
								}
							}
							else if(ny == 1) {
								y = this.y;
								// 2 to the left
								if(nx == 0) {
									x = this.x - 2;
									prob =
										(ffdata.neighborhood[1][0] + ffdata.neighborhood[0][0]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
								// 1 to the left
								else if(nx == 1) {
									x = this.x - 1;
									checkFire(x, y, 0, 1, grid, ffdata, c, sim);
								}
								// 1 to the right
								else if(nx == 3) {
									x = this.x + 1;
									checkFire(x, y, 2, 1, grid, ffdata, c, sim);
								}
								// 2 to the right
								else if(nx == 4) {
									x = this.x + 2;
									prob =
										(ffdata.neighborhood[1][2] + ffdata.neighborhood[0][2]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
							}
							else if(ny == 2) {
								y = this.y + 1;
								// top left
								if(nx == 1) {
									x = this.x - 1;
									prob =
										(ffdata.neighborhood[0][1] + ffdata.neighborhood[1][0]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
								// top center
								else if(nx == 2) {
									x = this.x;
									checkFire(x, y, 1, 0, grid, ffdata, c, sim);
								}
								// top right
								else if(nx == 3) {
									x = this.x + 1;
									prob =
										(ffdata.neighborhood[0][1] + ffdata.neighborhood[1][2]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
							}
						}
						
						// triangle type 2
						else if(this.x % 2 != this.y % 2) {
							// bottom row
							if(ny == 0) {
								y = this.y - 1;
								// bottom left
								if(nx == 1) {
									x = this.x - 1;
									prob =
										(ffdata.neighborhood[1][1] + ffdata.neighborhood[0][0]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
								// bottom center
								else if(nx == 2) {
									x = this.x;
									checkFire(x, y, 1, 1, grid, ffdata, c, sim);
								}
								// bottom right
								else if(nx == 3) {
									x = this.x + 1;
									prob =
										(ffdata.neighborhood[1][1] + ffdata.neighborhood[0][2]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
							}
							else if(ny == 1) {
								y = this.y;
								// 2 to the left
								if(nx == 0) {
									x = this.x - 2;
									prob =
										(ffdata.neighborhood[0][0] + ffdata.neighborhood[1][0]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
								// 1 to the left
								else if(nx == 1) {
									x = this.x - 1;
									checkFire(x, y, 0, 0, grid, ffdata, c, sim);
								}
								// 1 to the right
								else if(nx == 3) {
									x = this.x + 1;
									checkFire(x, y, 2, 0, grid, ffdata, c, sim);
								}
								// 2 to the right
								if(nx == 4) {
									x = this.x + 2;
									prob =
										(ffdata.neighborhood[0][2] + ffdata.neighborhood[1][2]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
							}
							else if(ny == 2) {
								y = this.y + 1;
								// top left
								if(nx == 0) {
									x = this.x - 2;
									checkFire(x, y, 0, 1, grid, ffdata, c, sim);
								}
								// top center left
								else if(nx == 1) {
									x = this.x - 1;
									prob =
										(ffdata.neighborhood[0][1] + ffdata.neighborhood[1][0]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
								// top center
								else if(nx == 2) {
									x = this.x;
									checkFire(x, y, 1, 0, grid, ffdata, c, sim);
								}
								// top center right
								else if(nx == 3) {
									x = this.x + 1;
									prob =
										(ffdata.neighborhood[0][1] + ffdata.neighborhood[1][2]) / 2;
									checkFire(x, y, prob, grid, ffdata, c, sim);
								}
								// top right
								else if(nx == 4) {
									x = this.x + 2;
									checkFire(x, y, 2, 1, grid, ffdata, c, sim);
								}
							}
						}
					}
				}
				prob = 0.0;
				if(probvar > 0) {
					boolean fire = false;
					for(int ny = 0; ny < 5; ny++) {
						for(int nx = 0; nx < 7; nx++) {
							fire = false;
							x2 = this.x + nx - 3;
							// Grid y increases north so cell above is y + 1
							y2 = this.y + ny - 2;
							// triangle type 1
							if(this.x % 2 == this.y % 2) {
								if(ny == 0) {
									if(nx == 2) {
										fire = true;
										prob =
											((ffdata.neighborhood[0][0] + ffdata.neighborhood[1][1]) / 2)
													/ probvar;
									}
									else if(nx == 4) {
										fire = true;
										prob =
											((ffdata.neighborhood[0][2] + ffdata.neighborhood[1][1]) / 2)
													/ probvar;
									}
								}
								else if(ny == 1) {
									if(nx == 0) {
										fire = true;
										prob =
											ffdata.neighborhood[0][0] / probvar;
									}
									else if(nx == 6) {
										fire = true;
										prob =
											ffdata.neighborhood[0][2] / probvar;
									}
								}
								else if(ny == 2) {
									if(nx == 0) {
										fire = true;
										prob =
											((ffdata.neighborhood[1][0] + ffdata.neighborhood[0][0]) / 2)
													/ probvar;
									}
									else if(nx == 6) {
										fire = true;
										prob =
											((ffdata.neighborhood[1][2] + ffdata.neighborhood[0][0]) / 2)
													/ probvar;
									}
								}
								else if(ny == 3) {
									if(nx == 1) {
										fire = true;
										prob =
											((ffdata.neighborhood[1][0] + ffdata.neighborhood[0][1]) / 2)
													/ probvar;
									}
									else if(nx == 5) {
										fire = true;
										prob =
											ffdata.neighborhood[0][1] / probvar;
									}
								}
								else if(ny == 4) {
									if(nx == 3) {
										fire = true;
										prob =
											((ffdata.neighborhood[1][2] + ffdata.neighborhood[0][1]) / 2)
													/ probvar;
									}
								}
							}
							// triangle type 2
							else if(this.x % 2 != this.y % 2) {
								if(ny == 0) {
									if(nx == 3) {
										fire = true;
										prob =
											ffdata.neighborhood[1][1] / probvar;
									}
								}
								else if(ny == 1) {
									if(nx == 1) {
										fire = true;
										prob =
											((ffdata.neighborhood[1][1] + ffdata.neighborhood[0][0]) / 2)
													/ probvar;
									}
									else if(nx == 5) {
										fire = true;
										prob =
											((ffdata.neighborhood[1][1] + ffdata.neighborhood[0][2]) / 2)
													/ probvar;
									}
								}
								else if(ny == 2) {
									if(nx == 0) {
										fire = true;
										prob =
											((ffdata.neighborhood[0][0] + ffdata.neighborhood[1][0]) / 2)
													/ probvar;
									}
									else if(nx == 6) {
										fire = true;
										prob =
											((ffdata.neighborhood[0][2] + ffdata.neighborhood[1][2]) / 2)
													/ probvar;
									}
								}
								else if(ny == 3) {
									if(nx == 0) {
										fire = true;
										prob =
											ffdata.neighborhood[1][0] / probvar;
									}
									else if(nx == 6) {
										fire = true;
										prob =
											ffdata.neighborhood[1][2] / probvar;
									}
								}
								else if(ny == 4) {
									if(nx == 2) {
										fire = true;
										prob =
											((ffdata.neighborhood[0][1] + ffdata.neighborhood[1][0]) / 2)
													/ probvar;
									}
									else if(nx == 4) {
										fire = true;
										prob =
											((ffdata.neighborhood[0][1] + ffdata.neighborhood[1][2]) / 2)
													/ probvar;
									}
								}
								
							}
							if(fire)
								checkFireRadius2(x2, y2, grid, ffdata, c, sim,
										prob);
						}
					}
				}
			}
			else if(type == ExForestFireCellType.FIRE_FIGHTER) {
				if(!ffdata.fireFighters) {
					return removeFireFighter(grid);
				}
				else {
					// initialize max distance to fire
					// can't be more than 200
					int newX = this.x, newY = this.y;
					int[] distToFire = {200, this.x, this.y};
					
					distToFire = searchFire(grid, distToFire);
					if(distToFire[0] > 1 && distToFire[0] < 200) {
						// move to nearest fire
						if(distToFire[2] < this.y) {
							if(this.y % 2 == this.x % 2) {
								newY--;
							}
							else {
								if(this.x == 0)
									newX++;
								else if(this.x == 99)
									newX--;
								else if(this.x < 99 && this.x > 0) {
									ExForestFireCell cell1;
									cell1 =
										(ExForestFireCell) grid.getCell(
												this.x + 1, this.y);
									ExForestFireCell cell2;
									cell2 =
										(ExForestFireCell) grid.getCell(
												this.x - 1, this.y);
									if(cell1 != null
											&& cell1.getType() == ExForestFireCellType.PATH)
										newX++;
									else if(cell2 != null
											&& cell2.getType() == ExForestFireCellType.PATH)
										newX--;
								}
							}
						}
						else if(distToFire[2] > this.y) {
							if(this.y % 2 != this.x % 2) {
								newY++;
							}
							else {
								if(this.x == 0)
									newX++;
								else if(this.x == 99)
									newX--;
								else if(this.x < 99 && this.x > 0) {
									ExForestFireCell cell1;
									cell1 =
										(ExForestFireCell) grid.getCell(
												this.x + 1, this.y);
									ExForestFireCell cell2;
									cell2 =
										(ExForestFireCell) grid.getCell(
												this.x - 1, this.y);
									if(cell1 != null
											&& cell1.getType() == ExForestFireCellType.PATH)
										newX++;
									else if(cell2 != null
											&& cell2.getType() == ExForestFireCellType.PATH)
										newX--;
								}
							}
						}
						else if(distToFire[1] < this.x)
							newX--;
						else if(distToFire[1] > this.x)
							newX++;
						return walkFireFighter(grid, newX, newY, sim);
					}
					else if(distToFire[0] == 1) {
						if((this.x % 2 == distToFire[1] % 2 && this.y % 2 == 0 && distToFire[2] % 2 == 1)
								|| (this.x % 2 == distToFire[1] % 2
										&& this.y % 2 == 1 && distToFire[2] % 2 == 0)) {
							if(this.x == 0)
								newX++;
							else if(this.x == 99)
								newX--;
							else if(this.x < 99 && this.x > 0) {
								ExForestFireCell cell1;
								cell1 =
									(ExForestFireCell) grid.getCell(this.x + 1,
											this.y);
								ExForestFireCell cell2;
								cell2 =
									(ExForestFireCell) grid.getCell(this.x - 1,
											this.y);
								if(cell1 != null
										&& cell1.getType() == ExForestFireCellType.PATH)
									newX++;
								else if(cell2 != null
										&& cell2.getType() == ExForestFireCellType.PATH)
									newX--;
							}
							else {
								Random leftRight = new Random();
								if(leftRight.nextInt(1) == 0)
									newX--;
								else
									newX++;
							}
							return walkFireFighter(grid, newX, newY, sim);
						}
						else {
							int x = -1, y = -1;
							double randomDouble;
							int fireCount = 0;
							Random rand = new Random();
							// count surrounding fires
							byte[][] nb = null;
							if(this.x % 2 == this.y % 2) {
								nb = new byte[][] {
													{0, 1},
													{1, 1},
													{1, 0},
													{2, 0},
													{2, -1},
													{1, -1},
													{0, -1},
													{-1, -1},
													{-2, -1},
													{-2, 0},
													{-1, 0},
													{-1, 1}
								};
							} else {
								nb = new byte[][] {
													{0, -1},
													{-1, -1},
													{-1, 0},
													{-2, 0},
													{-2, 1},
													{-1, 1},
													{0, 1},
													{1, 1},
													{2, 1},
													{2, 0},
													{1, 0},
													{1, -1}
								};
							}
							
							for(int i = 0; i < nb.length; i++) {
								x = this.x + nb[i][0];
								y = this.y + nb[i][1];
								if(x >= 0 && x < 100 && y >= 0 && y < 100) {
									c =
										(ExForestFireCell) grid.getCell(x,
												y);
									if(c != null
											&& (c.getType() == ExForestFireCellType.BURNING_TREE
											|| c.getType() == ExForestFireCellType.BURNING_BUSH)) {
										fireCount++;
									}
								}
							}
							// if there are fires around me, i could die
							if(fireCount > 0) {
								// exponentially increasing chance of death
								int deathProbInt =
									(int) Math.pow(1.3, (nb.length - fireCount));
								// already died, no chance of escape
								if(deathProbInt == 1) {
									// should this return?
									return murderFireFighter(grid, ffdata);
								}
								// else probability
								else {
									int died = rand.nextInt(deathProbInt);
									if(died == 0) {
										// should this return?
										return murderFireFighter(grid, ffdata);
									}
								}
							}
							
							for(int i = 0; i < nb.length; i++) {
								x = this.x + nb[i][0];
								y = this.y + nb[i][1];
								if(x >= 0 && x < 100 && y >= 0 && y < 100) {
									c =
										(ExForestFireCell) grid.getCell(x,
												y);
									if(c != null
											&& (c.getType() == ExForestFireCellType.BURNING_TREE
											|| c.getType() == ExForestFireCellType.BURNING_BUSH)) {
										if(c != null
												&& c.getType() == ExForestFireCellType.BURNING_TREE) {
											randomDouble = Math.random();
											if(randomDouble <= ffdata.extinguishProb) {
												c.setType(ExForestFireCellType.EXTINGUISHED_TREE);
												ffdata.burning--;
												ffdata.trees++;
											}
										}
										// If a bush is burning, try to
										// extinguish
										else if(c != null
												&& c.getType() == ExForestFireCellType.BURNING_BUSH) {
											randomDouble = Math.random();
											if(randomDouble <= ffdata.extinguishProb) {
												c.setType(ExForestFireCellType.EXTINGUISHED_BUSH);
												ffdata.burning--;
												ffdata.bushes++;
											}
										}
									}
								}
							}

						}
					}
				}
			}
			else if(type == ExForestFireCellType.PATH) {
				setPathToFireFighter(ffdata);
			}
		}
		
		if(type == ExForestFireCellType.BURNING_BUSH) {
			if(y == 99) {
				ffdata.oppositeReached = true;
			}
			
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
			if(y == 99) {
				ffdata.oppositeReached = true;
			}
			
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
		
		// Stop if nothing is burning anymore.
		if(ffdata.burning == 0) {
			sim.stop();
			return false;
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
	
	// given a probability and coordinates, try to set on fire.
	private void checkFire(int x, int y, double prob, Grid grid,
			ExForestFireData ffdata,
			ExForestFireCell c, Simulator sim) {
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
			// If the probability is 1, put the cell on fire if it
			// is a burnable cell
			else if(prob == 1.0) {
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
			// if prob = 1.0 always set fire
			if(prob == 1.0) {
				setFire(ffdata,
						ExForestFireCellType.BURNING_TREE, c,
						sim);
				setFire(ffdata,
						ExForestFireCellType.BURNING_BUSH, c,
						sim);
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
	
	private void setPathToFireFighter(ExForestFireData ffdata) {
		if(ffdata.fireFighters) {
			// maybe break here after x ticks fire fighter adding?
			
			Random r = new Random();
			if(ffdata.fireFighterSpawnProb == 1.0
					|| r.nextInt((int) (101)) < (100 * ffdata.fireFighterSpawnProb)) {
				ffdata.nFireFighters++;
				addFireFighter();
			}
		}
	}
	
	private int[] searchFire(Grid grid, int[] distToFire) {
		int newDistance;
		boolean fireFound = false;
		// search nearest fire
		for(int i = Math.max(0, this.x - 4); i < Math.min(
				grid.grid[0].length, this.x + 4); i++) {
			for(int j = Math.max(0, this.y - 4); j < Math.min(
					grid.grid.length, this.y + 4); j++) {
				ExForestFireCell cell =
					(ExForestFireCell) grid.getCell(i, j);
				if(cell != null) {
					if(cell.getType() == ExForestFireCellType.BURNING_TREE
							|| cell.getType() == ExForestFireCellType.BURNING_BUSH) {
						newDistance =
							Math.abs(i - this.x)
									+ Math.abs(j - this.y);
						// smallest distance and at a new location
						if(newDistance < distToFire[0]) {
							fireFound = true;
							distToFire[0] = newDistance;
							distToFire[1] = i;
							distToFire[2] = j;
						}
					}
				}
			}
		}
		if(fireFound)
			return distToFire;
		int possibilities = 0;
		// search in 3x3 area for paths
		for(int i = Math.max(0, this.x - 2); i < Math.min(
				grid.grid[0].length, this.x + 2); i++) {
			for(int j = Math.max(0, this.y - 2); j < Math.min(
					grid.grid.length, this.y + 2); j++) {
				ExForestFireCell cell =
					(ExForestFireCell) grid.getCell(i, j);
				// Prefer fire over path.
				if(cell != null && cell.getType() == ExForestFireCellType.PATH
						&& (i != prevX[0] || j != prevY[0])
						&& (i != prevX[1] || j != prevY[1])
						&& (i != prevX[2] || j != prevY[2])
						&& (i != prevX[3] || j != prevY[3])) {
					possibilities++;
					newDistance =
						Math.abs(i - this.x)
								+ Math.abs(j - this.y)
								+ 18;
					
					// Discourage a path with a fire fighter in front.
					if(i < this.x) {
						for(int i2 =
							Math.max(0, i - 1); i2 < Math.max(
								0, i - 3); i2++) {
							Cell ce = grid.getCell(i2, j);
							if(ce != null
									&& ce.getType() == ExForestFireCellType.FIRE_FIGHTER) {
								newDistance += 2;
							}
						}
					}
					
					else if(i > this.x) {
						for(int i2 =
							Math.min(grid.grid[0].length,
									i + 1); i2 < Math.min(
								grid.grid[0].length, i + 3); i2++) {
							Cell ce = grid.getCell(i2, j);
							if(ce != null
									&& ce.getType() == ExForestFireCellType.FIRE_FIGHTER) {
								newDistance += 2;
							}
						}
					}
					
					else if(j < this.y) {
						for(int j2 =
							Math.max(0, j - 1); j2 < Math.max(
								0, j - 3); j2++) {
							Cell ce = grid.getCell(i, j2);
							if(ce != null
									&& ce.getType() == ExForestFireCellType.FIRE_FIGHTER) {
								newDistance += 2;
							}
						}
					}
					
					else if(j > this.y) {
						for(int j2 =
							Math.min(grid.grid[0].length,
									j + 1); j2 < Math.min(
								grid.grid[0].length, j + 3); j2++) {
							Cell ce = grid.getCell(i, j2);
							if(ce != null
									&& ce.getType() == ExForestFireCellType.FIRE_FIGHTER) {
								newDistance += 2;
							}
						}
					}
					if(newDistance < distToFire[0] && possibilities > 1) {
						distToFire[0] = newDistance;
						distToFire[1] = i;
						distToFire[2] = j;
					}
					// walk back if there is no other possibility
					else if(possibilities == 1) {
						distToFire[0] = newDistance;
						distToFire[1] = i;
						distToFire[2] = j;
					}
				}
			}
		}
		return distToFire;
	}
	
	private boolean walkFireFighter(Grid grid, int newX, int newY, Simulator sim) {
		ExForestFireCell newCell = (ExForestFireCell) grid.getCell(newX, newY);
		
		if(newCell != null
				&& newCell.getType() != ExForestFireCellType.BURNING_BUSH
				&& newCell.getType() != ExForestFireCellType.BURNING_TREE
				&& newCell.getType() != ExForestFireCellType.FIRE_FIGHTER) {
			newCell.addFireFighter();
			newCell.prevX[3] = prevX[2];
			newCell.prevY[3] = prevY[2];
			newCell.prevX[2] = prevX[1];
			newCell.prevY[2] = prevY[1];
			newCell.prevX[1] = prevX[0];
			newCell.prevY[1] = prevY[0];
			newCell.prevX[0] = this.x;
			newCell.prevY[0] = this.y;
			sim.addSimulatable(newCell);
		} else if(newCell == null) {
			newCell =
				new ExForestFireCell(newX, newY, null);
			// Add new cell to grid.
			grid.setCell(newCell);
			// New cell is FFighter only.
			newCell.addFireFighter();
			newCell.prevX[3] = prevX[2];
			newCell.prevY[3] = prevY[2];
			newCell.prevX[2] = prevX[1];
			newCell.prevY[2] = prevY[1];
			newCell.prevX[1] = prevX[0];
			newCell.prevY[1] = prevY[0];
			newCell.prevX[0] = this.x;
			newCell.prevY[0] = this.y;
			sim.addSimulatable(newCell);
		}
		else
			return true;
		
		// Remove FFighter from this cell.
		return removeFireFighter(grid);
	}
	
	public void resetNumBurnTicks() {
		nBurningTicks = 0;
	}
}
