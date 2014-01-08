package nl.tompeerdeman.ca.predprey;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.CellType;
import nl.tompeerdeman.ca.DataSet;
import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.Simulator;

public class PredPreyCell extends Cell{
	private static int xc[] = {-1, 0, 1, 0};
	private static int yc[] = {0, -1, 0, 1};
	
	public double energy;
	
	public PredPreyCell(int x, int y, CellType type, double e){
		super(x, y, type);
		energy = e;
	}
	
	@Override
	public boolean shouldSimulate(){
		return true;
	}

	@Override
	public boolean simulate(Grid grid, DataSet data, Simulator sim){
		PredPreyData d = (PredPreyData) data;
		
		if(x == -1 || y == -1){
			return false;
		}
		
		int xy[];
		if(type == PredPreyCellType.PREY){
			energy += d.preyEnergyIncrease;
			
			xy = getRandFreeCell(d, grid);
			if(xy == null){
				return true;
			}
			grid.move(x, y, xy[0], xy[1]);
			
			if(energy > d.preySplitTreshold){
				/* xy can't be null because we were able to move,
				 *  so there is a free spot.
				 */
				xy = getRandFreeCell(d, grid);
				
				PredPreyCell ppc = new PredPreyCell(xy[0], xy[1],
						PredPreyCellType.PREY, energy / 2.0);
				grid.setCell(ppc);
				sim.addSimulatable(ppc);
				d.nprey++;
				
				energy /= 2.0;
			}
		}else{
			energy -= d.predEnergyDecrease;
			if(energy <= 0.0){
				grid.clearCell(x, y);
				d.npred--;
				return false;
			}
			
			xy = getRandCell(d, grid);
			// No free space or prey to move to
			if(xy == null){
				return true;
			}
			
			PredPreyCell c = (PredPreyCell) grid.getCell(xy[0], xy[1]);
			// Predator meets prey
			if(c != null){
				energy += c.energy;
				// If the cell was in the simulation queue it will be removed next tick
				grid.clearCell(xy[0], xy[1]);
				d.nprey--;
			}
			
			grid.move(x, y, xy[0], xy[1]);
			
			if(energy > d.predSplitTreshold){
				/* xy can't be null because we were able to move,
				 *  so there is a free spot.
				 */
				xy = getRandFreeCell(d, grid);
				
				PredPreyCell ppc = new PredPreyCell(xy[0], xy[1],
						PredPreyCellType.PREDATOR, energy / 2.0);
				grid.setCell(ppc);
				sim.addSimulatable(ppc);
				d.npred++;
				
				energy /= 2.0;
			}
		}
		
		if(d.npred == 0 || d.nprey == 0){
			sim.stop();
			return false;
		}
		
		return true;
	}
	
	public int normalizeToRound(int max, int x){
		if(x < 0){
			x += max;
		}
		if(x >= max){
			x -= max;
		}
		return x;
	}
	
	public int[] getRandFreeCell(PredPreyData data, Grid g){	
		Cell c;
		int i = data.random.nextInt(4);
		int n = 0;
		int newx, newy;
		while(n < 4){
			n++;
			newx = normalizeToRound(g.grid.length, x + xc[i]);
			newy = normalizeToRound(g.grid[0].length, y + yc[i]);
			c = g.getCell(newx, newy);
			if(c == null){
				return new int[] {newx, newy};
			}
			i = (i + 1) % 4;
		}
		
		return null;
	}
	
	public int[] getRandCell(PredPreyData data, Grid g){
		Cell c;
		int i = data.random.nextInt(4);
		int n = 0;
		int newx, newy;
		while(n < 4){
			n++;
			newx = normalizeToRound(g.grid.length, x + xc[i]);
			newy = normalizeToRound(g.grid[0].length, y + yc[i]);
			c = g.getCell(newx, newy);
			if(c == null || c.getType() == PredPreyCellType.PREY){
				return new int[] {newx, newy};
			}
			i = (i + i) % 4;
		}
		
		return null;
	}
}
