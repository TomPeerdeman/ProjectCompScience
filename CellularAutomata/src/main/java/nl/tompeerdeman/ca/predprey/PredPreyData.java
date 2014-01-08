package nl.tompeerdeman.ca.predprey;

import java.util.Random;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.DataSet;
import nl.tompeerdeman.ca.Grid;

public class PredPreyData implements DataSet{
	public double preyEnergyIncrease;
	public double predEnergyDecrease;
	public double preySplitTreshold;
	public double predSplitTreshold;
	
	public int nprey;
	public int npred;
	
	public Random random;
	
	private Grid grid;
	
	public PredPreyData(Grid g){
		grid = g;
		reset();
	}
	
	@Override
	public void reset(){
		random = new Random();
		random.setSeed(1L);
		
		nprey = 0;
		npred = 0;
		
		Cell c;
		for(int y = 0; y < grid.grid[0].length; y++){
			for(int x = 0; x < grid.grid.length; x++){
				c = grid.getCell(x, y);
				if(c == null){
					continue;
				}
				
				// Cast necessary, unable to switch on a interface 
				switch((PredPreyCellType) c.getType()){
					case PREY:
						nprey++;
						break;
					case PREDATOR:
						npred++;
						break;
				}
			}
		}
	}
	
}
