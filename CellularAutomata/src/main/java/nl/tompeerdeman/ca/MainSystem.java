package nl.tompeerdeman.ca;

public abstract class MainSystem {
	protected Grid grid;
	protected DataSet data;
	protected Simulator sim;
	
	public Simulator getSimulator() {
		return sim;
	}
	
	public void buildThread() {
		sim = new ThreadSimulator(grid, data);
	}
	
	public void buildTimed(long delay) {
		sim = new TimedSimulator(grid, data, delay);
	}
	
	public void start() {
		sim.start();
	}
}
