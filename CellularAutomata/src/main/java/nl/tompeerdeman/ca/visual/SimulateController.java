package nl.tompeerdeman.ca.visual;

public interface SimulateController {
	public boolean onStart();
	
	public boolean onPause();
	
	public boolean onReset();
	
	public boolean onRandomize();
	
	public boolean onStop();
}
