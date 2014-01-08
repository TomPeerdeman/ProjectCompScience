package nl.tompeerdeman.ca.forestfire;

import java.util.Random;

import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.MainSystem;
import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;

public class ForestFire extends MainSystem implements SimulateChangeListener {
	private ForestFireData ffdata;
	
	public static void main(String[] args) {
		if(args.length != 3 && args.length != 4) {
			System.out.println("Usage: java ForestFire {forest density} {width} {height} [seed]");
			return;
		}
		
		double density;
		try {
			density = Double.parseDouble(args[0]);
		} catch(NumberFormatException e) {
			System.out.println("Usage: java ForestFire {forest density} {width} {height} [seed]");
			return;
		}
		
		int x, y;
		try {
			x = Integer.parseInt(args[1]);
			y = Integer.parseInt(args[2]);
		} catch(NumberFormatException e) {
			System.out.println("Usage: java ForestFire {forest density} {width} {height} [seed]");
			return;
		}
		long seed = 0;
		if(args.length == 4) {
			try {
				seed = Long.parseLong(args[3]);
			} catch(NumberFormatException e) {
				System.out.println("Usage: java ForestFire {forest density} {width} {height} [seed]");
				return;
			}
		}
		
		ForestFire fire =
			new ForestFire(density, x, y, seed, ForestFireData.NB_NEUMANN, 0);
		fire.buildThread();
		fire.getSimulator().addChangeListener(fire);
		fire.start();
	}
	
	public ForestFire(final double density, final int nx, final int ny,
			final long seed, boolean[][] nb, int didx) {
		// Create an empty grid
		grid = new Grid(nx, ny);
		
		randomizeGrid(density, seed);
		igniteGrid();
		
		data = new ForestFireData(nb, grid, didx);
		ffdata = (ForestFireData) data;
	}
	
	public void randomizeGrid(final double density, final long seed) {
		final int trees =
			(int) Math.ceil(density
					* (grid.grid.length * (grid.grid[0].length - 1)));
		
		grid.clear();
		
		Random rand = new Random();
		if(seed > 0) {
			rand.setSeed(seed);
		}
		int t = 0;
		int rx, ry;
		int tries = 0;
		while(t < trees) {
			rx = rand.nextInt(grid.grid.length);
			ry = rand.nextInt((grid.grid[0].length - 1)) + 1;
			if(grid.getCell(rx, ry) == null) {
				grid.setCell(new ForestFireCell(rx, ry, ForestFireCellType.VEG));
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
	
	public void resetGrid() {
		for(int y = 0; y < grid.grid[0].length; y++) {
			for(int x = 0; x < grid.grid.length; x++) {
				ForestFireCell cell = (ForestFireCell) grid.getCell(x, y);
				
				if(cell != null && cell.getType() != ForestFireCellType.VEG) {
					cell.setType(ForestFireCellType.VEG);
				}
			}
		}
		igniteGrid();
	}
	
	public void igniteGrid() {
		// Fill the bottom line of the grid with burning trees
		for(int x = 0; x < grid.grid.length; x++) {
			grid.setCell(new ForestFireCell(x, 0, ForestFireCellType.BURNING));
		}
	}
	
	@Override
	public void simulationUpdated(Simulator sim) {
		System.out.printf(
				"tick %d: veg: %d, burning: %d, burnt: %d, barren: %d, opposite: %b\n",
				sim.getTick(),
				ffdata.vegetation, ffdata.burning, ffdata.burnt, ffdata.barren,
				ffdata.reachedOpposite);
	}
}
