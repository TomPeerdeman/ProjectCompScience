package nl.tompeerdeman.ca.predprey;

import java.util.Random;

import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.MainSystem;
import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;

public class PredPreySystem extends MainSystem implements SimulateChangeListener{
	private PredPreyData ppdata;
	
	public static int parseInt(String s){
		int i = -1;
		try{
			i = Integer.parseInt(s);
		}catch(NumberFormatException e){
		}
		return i;
	}
	
	public static double parseDouble(String s){
		double d = -1;
		try{
			d = Double.parseDouble(s);
		}catch(NumberFormatException e){
		}
		return d;
	}
	
	public static void main(String[] args){
		if(args.length != 11 && args.length != 10){
			System.out.println("Usage PredPreySystem width height npreystart npredstart preyenergystart predenergystart"
					+ " preysplit predsplit preyenergyinc predenergydec [seed]");
			return;
		}
		
		final int w = parseInt(args[0]);
		final int h = parseInt(args[1]);
		if(h < 0 || h > 400 || w < 0 || w > 400){
			System.out.println("Grid size out of range: 0-400");
			return;
		}
		
		final int npy = parseInt(args[2]);
		final int npd = parseInt(args[3]);
		if(npy < 0 || npd < 0){
			System.out.println("NstartPred or NstartPrey out of range: 0-n");
			return;
		}
		
		int seed = 0;
		if(args.length == 11){
			seed = parseInt(args[10]);
			if(seed == -1){
				System.out.println("Possible invalid seed: " + seed);
			}
		}
		
		final double ey = parseDouble(args[4]);
		final double ep = parseDouble(args[5]);
		if(ey < 0 || ep < 0){
			System.out.println("prey or pred start energy out of range: 0.0-n");
			return;
		}
		
		PredPreySystem sys = new PredPreySystem(w, h, npy, npd, (long) seed, ey, ep);
		sys.buildThread();
		Simulator sim = sys.getSimulator();
		PredPreyData data = (PredPreyData) sim.getData();
		
		
		data.preyEnergyIncrease = parseDouble(args[8]);
		data.predEnergyDecrease = parseDouble(args[9]);
		if(data.predEnergyDecrease < 0){
			System.out.println("Pred energy loss out of range: 0.0-n");
			return;
		}
		if(data.preyEnergyIncrease < 0){
			System.out.println("Prey energy gain out of range: 0.0-n");
			return;
		}
		
		data.preySplitTreshold = parseDouble(args[6]);
		data.predSplitTreshold = parseDouble(args[7]);
		
		
		if(data.preySplitTreshold < 0){
			System.out.println("Prey split treshold out of range: 0.0-n");
			return;
		}
		if(data.predSplitTreshold < 0){
			System.out.println("Pred split treshold out of range: 0.0-n");
			return;
		}
		
		sim.addChangeListener(sys);
		sim.addChangeListener(new PredPreyPlotter());
		
		sim.afterSimulateTick();
		sys.start();
	}
	
	public PredPreySystem(final int nx, final int ny, final int prey, final int pred,
			final long seed, final double preyEstart, final double predEstart){
		// Create an empty grid
		grid = new Grid(nx, ny);
		
		randomizeGrid(prey, pred, 100, 100, seed);
		
		data = new PredPreyData(grid);
		ppdata = (PredPreyData) data;
	}

	public void randomizeGrid(final int prey, final int pred,
			final double preyEnergy, final double predEnergy, final long seed){
		grid.clear();
		
		Random rand = new Random();
		if(seed > 0){
			rand.setSeed(seed);
		}
		int t = 0;
		int rx, ry;
		int tries = 0;
		while(t < prey){
			rx = rand.nextInt(grid.grid.length);
			ry = rand.nextInt(grid.grid[0].length);
			if(grid.getCell(rx, ry) == null){
				grid.setCell(new PredPreyCell(rx, ry, PredPreyCellType.PREY, rand.nextInt((int) preyEnergy)));
				t++;
				tries = 0;
			}else{
				tries++;
				if(tries > 100){
					break;
				}
			}
		}
		
		tries = 0;
		t = 0;
		while(t < pred){
			rx = rand.nextInt(grid.grid.length);
			ry = rand.nextInt(grid.grid[0].length);
			if(grid.getCell(rx, ry) == null){
				grid.setCell(new PredPreyCell(rx, ry, PredPreyCellType.PREDATOR, rand.nextInt((int) predEnergy)));
				t++;
				tries = 0;
			}else{
				tries++;
				if(tries > 100){
					break;
				}
			}
		}
	}
	
	@Override
	public void simulationUpdated(Simulator sim){
		System.out.printf("[%d] prey: %d, pred: %d\n", sim.getTick(), ppdata.nprey, ppdata.npred);
	}
}
