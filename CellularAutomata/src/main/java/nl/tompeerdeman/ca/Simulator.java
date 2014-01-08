package nl.tompeerdeman.ca;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class Simulator{
	protected Grid grid;
	protected DataSet data;
	
	protected LinkedList<Cell> cellList1;
	protected LinkedList<Cell> cellList2;
	protected LinkedList<Cell> cellAddList;
	protected LinkedList<Cell> cellActiveList;
	
	protected LinkedList<SimulateChangeListener> changeListeners;
	
	protected boolean running;
	protected boolean paused;
	protected long tick;
	
	public Simulator(Grid g, DataSet d){
		grid = g;
		data = d;
		
		cellList1 = new LinkedList<Cell> ();
		cellList2 = new LinkedList<Cell> ();
		cellAddList = cellList1;
		cellActiveList = cellList2;
		
		changeListeners = new LinkedList<SimulateChangeListener> ();
		
		reset();
	}
	
	public void addChangeListener(SimulateChangeListener listener){
		changeListeners.addLast(listener);
	}
	
	public void reset(){
		cellAddList.clear();
		cellActiveList.clear();
		
		tick = 0;
		
		Cell c;
		for(int y = 0; y < grid.grid[0].length; y++){
			for(int x = 0; x < grid.grid.length; x++){
				c = grid.getCell(x, y);
				if(c == null){
					continue;
				}
				
				if(c.shouldSimulate()){
					addSimulatable(c);
				}
			}
		}
	}
	
	public void addSimulatable(Cell c){
		cellAddList.add(c);
	}
	
	public void afterSimulateTick(){
		Iterator<SimulateChangeListener> i = changeListeners.iterator();
		
		while(i.hasNext()){
			i.next().simulationUpdated(this);
		}
	}
	
	public void swapLists(){
		if(cellActiveList == cellList1){
			cellActiveList = cellList2;
			cellAddList = cellList1;
			cellAddList.clear();
		}else{
			cellActiveList = cellList1;
			cellAddList = cellList2;
			cellAddList.clear();
		}
	}
	
	public void simulateTick(){
		if(cellAddList.size() == 0){
			stop();
			return;
		}
		
		swapLists();
		
		Cell c;
		Iterator<Cell> it = cellActiveList.iterator();
		while(it.hasNext()){
			c = it.next();
			if(c.simulate(grid, data, this)){
				cellAddList.add(c);
			}
		}
		
		tick++;
		
		afterSimulateTick();
	}
	
	public Grid getGrid(){
		return grid;
	}
	
	public DataSet getData(){
		return data;
	}
	
	public long getTick(){
		return tick;
	}
	
	public abstract void start();
	public abstract void stop();
	public abstract void pause();
}
