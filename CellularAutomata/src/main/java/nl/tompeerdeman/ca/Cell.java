package nl.tompeerdeman.ca;

public abstract class Cell{
	protected int x;
	protected int y;
	protected CellType type;
	
	public Cell(int x, int y, CellType type){
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public CellType getType(){
		return type;
	}
	
	public void setType(CellType t){
		type = t;
	}
	
	public boolean shouldSimulate(){
		return false;
	}
	
	public abstract boolean simulate(Grid grid, DataSet data, Simulator sim);
}
