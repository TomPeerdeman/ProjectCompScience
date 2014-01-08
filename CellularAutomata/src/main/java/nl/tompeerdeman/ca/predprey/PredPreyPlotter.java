package nl.tompeerdeman.ca.predprey;

import nl.tompeerdeman.ca.Plot;
import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;

public class PredPreyPlotter implements SimulateChangeListener{
	private Plot plot;
	
	public PredPreyPlotter(){
		plot = new Plot("predprey");
		plot.instr.printf("\nset xlabel \"Tick\"\nset ylabel \"\"");
		
		plot.data.println("Tick\tNum_prey\tNum_predator");
	}
	
	@Override
	public void simulationUpdated(Simulator sim){
		PredPreyData data = (PredPreyData) sim.getData();
		plot.data.printf("%d\t%d\t%d\n", sim.getTick(), data.nprey, data.npred);
		
		if(data.npred == 0 || data.nprey == 0){
			plot.plotFunction(1, 2);
			plot.plotFunction(1, 3);
			plot.close();
		}
	}
}
