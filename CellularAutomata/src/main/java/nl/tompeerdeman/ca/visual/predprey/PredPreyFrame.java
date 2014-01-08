package nl.tompeerdeman.ca.visual.predprey;

import nl.tompeerdeman.ca.Simulator;
import nl.tompeerdeman.ca.predprey.PredPreySystem;
import nl.tompeerdeman.ca.visual.CaFrame;
import nl.tompeerdeman.ca.visual.CaPanel;

public class PredPreyFrame extends CaFrame{
	private static final long serialVersionUID = 9121099724811421852L;

	public static void main(String[] args){
		new PredPreyFrame();
	}
	
	public PredPreyFrame(){
		super("Predator prey simulation");
		
		setSize(430, 700);
		
		PredPreySystem sys = new PredPreySystem(100, 100, 100, 100, 1L, 100, 100);
		sys.buildTimed(10);
		
		Simulator sim = sys.getSimulator();

		CaPanel capanel = new PredPreyPanel(sim.getGrid(), getWidth(), getHeight());
		sim.addChangeListener(capanel);
		sim.afterSimulateTick();
		
		main.add(capanel);
		
		PredPreyDataPanel dataPanel = new PredPreyDataPanel(sys);
		sim.addChangeListener(dataPanel);
		
		main.add(dataPanel);
		
		setVisible(true);
	}
	
}
