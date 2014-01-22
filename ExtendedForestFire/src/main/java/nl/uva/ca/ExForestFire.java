/**
 * File: ExForestFire.java
 * 
 */
package nl.uva.ca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.SimulatableSystem;

public class ExForestFire extends SimulatableSystem {
	public final String DEFAULT_GRID_EXTENSION = "cag";
	
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
	public boolean randPath;
	public double treeDensity;
	public double bushDensity;
	public int type;
	
	private JFileChooser fileChooser;
	
	/**
	 * @param nx
	 * @param ny
	 * @param seed
	 * @param randWater
	 * @param randPath
	 * @param treeDensity
	 * @param bushDensity
	 * @param type
	 */
	public ExForestFire(int nx, int ny, long seed, boolean randWater,
			boolean randPath, double treeDensity, double bushDensity, int type) {
		this.randPath = randPath;
		this.randWater = randWater;
		this.treeDensity = treeDensity;
		this.bushDensity = bushDensity;
		this.type = type;
		
		// Create an empty grid
		grid = new SerializableGrid(nx, ny);
		
		randomizeGrid(seed);
		igniteGrid();
		
		data = new ExForestFireData(grid, type);
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileHidingEnabled(true);
		FileFilter filter =
			new FileNameExtensionFilter("CA grid files", DEFAULT_GRID_EXTENSION);
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(true);
		fileChooser.setFileFilter(filter);
	}
	
	public void saveGrid(JComponent parent) throws IOException {
		int rc = fileChooser.showSaveDialog(parent);
		if(rc == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			if(f.getName().indexOf('.') < 0
					&& fileChooser.getFileFilter() instanceof FileNameExtensionFilter) {
				f =
					new File(f.getAbsolutePath() + "." + DEFAULT_GRID_EXTENSION);
			}
			ObjectOutputStream out =
				new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(data);
			out.flush();
			out.close();
		}
	}
	
	public void loadGrid(JComponent parent)
			throws Exception {
		int rc = fileChooser.showOpenDialog(parent);
		if(rc == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			loadGrid(f);
		}
	}
	
	public void loadGrid(File f) throws Exception {
		ObjectInputStream in =
			new ObjectInputStream(new FileInputStream(f));
		Object obj = in.readObject();
		if(obj instanceof ExForestFireData) {
			ExForestFireData data = (ExForestFireData) obj;
			
			// Load the data into the local one, so we don't have to change all
			// the pointers.
			((ExForestFireData) this.data).loadFrom(((ExForestFireData) data));
			
			// Reset grid
			resetGrid();
			
			// Count cell type's
			data.reset();
			
			sim.reset();
			sim.afterSimulateTick();
		}
		in.close();
	}
	
	public void randomizeGrid(final long seed) {
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
						randPathStd(pointcoord[i][0], pointcoord[i][1],
								pointcoord[pointcoord[i][2]][0],
								pointcoord[pointcoord[i][2]][1],
								ExForestFireCellType.WATER);
					}
					else {
						randPathTriangle(pointcoord[i][0], pointcoord[i][1],
								pointcoord[pointcoord[i][2]][0],
								pointcoord[pointcoord[i][2]][1],
								ExForestFireCellType.WATER);
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
					randPathStd(pointcoord[index][0],
							pointcoord[index][1], 0, edge,
							ExForestFireCellType.WATER);
				}
				// triangle walking
				else {
					randPathTriangle(pointcoord[index][0],
							pointcoord[index][1], 0, edge,
							ExForestFireCellType.WATER);
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
					randPathStd(pointcoord[index][0],
							pointcoord[index][1], edge, 0,
							ExForestFireCellType.WATER);
				}
				// triangle walking
				else {
					randPathTriangle(pointcoord[index][0],
							pointcoord[index][1], edge, 0,
							ExForestFireCellType.WATER);
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
		
		if(randPath)
			buildPath(rand);
		
		plantVegetation(totaltrees, ExForestFireCellType.TREE);
		plantVegetation(totalbushes, ExForestFireCellType.BUSH);
	}
	
	private void plantVegetation(int total, ExForestFireCellType cellType) {
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
	
	private void buildPath(Random rand) {
		// [2-4] edge points
		int edgePoints = rand.nextInt(3) + 2;
		int nPoints = rand.nextInt(3) + edgePoints + 1;
		
		int[][] points = new int[nPoints][2];
		
		int n = rand.nextInt(400);
		
		for(int i = 0; i < edgePoints; i++) {
			n += rand.nextInt(200) + 100;
			n %= 400;
			
			int edge = n % 100;
			int p = n / 100;
			if(p == 2 || p == 3) {
				edge = 99 - edge;
			}
			
			if(p == 1) {
				points[i][0] = 99;
				points[i][1] = edge;
			} else if(p == 3) {
				points[i][0] = 0;
				points[i][1] = edge;
			} else if(p == 2) {
				points[i][0] = edge;
				points[i][1] = 99;
			} else {
				points[i][0] = edge;
				points[i][1] = 0;
			}
			
		}
		
		for(int i = edgePoints; i < nPoints; i++) {
			// Generate coordinates for each point. No edge points!
			points[i][0] = rand.nextInt(80) + 10;
			points[i][1] = rand.nextInt(80) + 10;
			
		}
		
		int nConnections[] = new int[nPoints];
		boolean[][] connected = new boolean[nPoints][nPoints];
		
		for(int j = 0; j < nPoints; j++) {
			Cell c = grid.getCell(points[j][0], points[j][1]);
			if(c != null && c.getType() != ExForestFireCellType.PATH) {
				c.setType(ExForestFireCellType.PATH);
			} else if(c == null) {
				grid.setCell(new ExForestFireCell(points[j][0], points[j][1],
						ExForestFireCellType.PATH));
			}
			
			/*
			 * Don't build a road from this node if it already has 2 or more
			 * connections.
			 * Also skip id this is an edge node and it already has 1 or more
			 * connections.
			 */
			if(nConnections[j] >= 2 || (j < edgePoints && nConnections[j] >= 1))
				continue;
			
			double min = Double.POSITIVE_INFINITY;
			int minIdx = -1;
			for(int i = 0; i < nPoints; i++) {
				// Skip self and nodes that already have 3 connections
				if(i == j || nConnections[i] >= 3
						|| points[j][0] == points[i][0]
						|| points[j][1] == points[i][1] || connected[j][i]
						|| connected[i][j])
					continue;
				
				double d =
					pointDist(points[j][0], points[j][1], points[i][0],
							points[i][1]);
				if(d < min) {
					min = d;
					minIdx = i;
				}
			}
			
			if(minIdx >= 0) {
				nConnections[j]++;
				nConnections[minIdx]++;
				connected[j][minIdx] = true;
				connected[minIdx][j] = true;
				
				if(type == 0 || type == 1) {
					randPathStd(points[j][0], points[j][1], points[minIdx][0],
							points[minIdx][1], ExForestFireCellType.PATH);
				} else {
					randPathTriangle(points[j][0], points[j][1],
							points[minIdx][0], points[minIdx][1],
							ExForestFireCellType.PATH);
				}
			}
		}
	}
	
	private double pointDist(int startX, int startY, int endX, int endY) {
		return Math.abs(startX - endX) + Math.abs(startY - endY);
	}
	
	private void randPathStd(int xstart, int ystart, int xend, int yend,
			ExForestFireCellType cellType) {
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
				grid.setCell(new ExForestFireCell(xcurr, ycurr, cellType));
			else if(cell.getType() != ExForestFireCellType.PATH)
				cell.setType(cellType);
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
	
	private void randPathTriangle(int xstart, int ystart, int xend, int yend,
			ExForestFireCellType cellType) {
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
								cellType));
						ycurr =
							triangleUpDownFix(xcurr - 1, ycurr, yend, cellType);
					}
					else {
						grid.setCell(new ExForestFireCell(xcurr + 1, ycurr,
								cellType));
						ycurr =
							triangleUpDownFix(xcurr + 1, ycurr, yend, cellType);
					}
				}
				else if(xcurr == grid.grid.length - 1) {
					grid.setCell(new ExForestFireCell(xcurr - 1, ycurr,
							cellType));
					ycurr = triangleUpDownFix(xcurr - 1, ycurr, yend, cellType);
				}
				else {
					grid.setCell(new ExForestFireCell(xcurr + 1, ycurr,
							cellType));
					ycurr = triangleUpDownFix(xcurr + 1, ycurr, yend, cellType);
				}
			}
			
			// if we intersect with an existing river,
			// break (if normal conditions occurred)
			ExForestFireCell cell =
				(ExForestFireCell) grid.getCell(xcurr, ycurr);
			if(cell == null)
				grid.setCell(new ExForestFireCell(xcurr, ycurr, cellType));
			else if(cell.getType() != ExForestFireCellType.PATH)
				grid.getCell(xcurr, ycurr).setType(cellType);
			else if(!same)
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
	
	private int triangleUpDownFix(int xcurr, int ycurr, int yend,
			ExForestFireCellType cellType) {
		// i'm stuck going up
		if(ycurr < yend) {
			grid.setCell(new ExForestFireCell(xcurr, ycurr + 1,
					cellType));
			ycurr++;
		}
		// i'm stuck going down
		else {
			grid.setCell(new ExForestFireCell(xcurr, ycurr - 1,
					cellType));
			ycurr--;
		}
		return ycurr;
	}
	
	/**
	 * Ignite the grid at the bottom
	 */
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
	
	/**
	 * Reset the grid to it's initial randomized generated form.
	 * *_BUSH -> BUSH
	 * *_TREE -> TREE
	 * Also remove all firefighters.
	 */
	public void resetGrid() {
		for(int y = 0; y < grid.grid[0].length; y++) {
			for(int x = 0; x < grid.grid.length; x++) {
				ExForestFireCell cell = (ExForestFireCell) grid.getCell(x, y);
				
				if(cell != null) {
					if(cell.getType() == ExForestFireCellType.FIRE_FIGHTER) {
						cell.removeFireFighter(grid);
						if(cell.getType() == null) {
							grid.clearCell(x, y);
							continue;
						}
					}
					switch((ExForestFireCellType) cell.getType()) {
						case BURNING_BUSH:
						case BURNT_BUSH:
						case EXTINGUISHED_BUSH:
							// Reset all variants of BUSH back to a BUSH type.
							cell.setType(ExForestFireCellType.BUSH);
							break;
						case BURNING_TREE:
						case BURNT_TREE:
						case EXTINGUISHED_TREE:
							// Reset all variants of TREE back to a TREE type.
							cell.setType(ExForestFireCellType.TREE);
							break;
						default:
							// Ignore water and existing BUSH & TREE cell's.
							break;
					}
					
					// Reset number of burned ticks.
					cell.resetNumBurnTicks();
				}
			}
		}
		// Re ignite the fresh grid.
		igniteGrid();
	}
}
