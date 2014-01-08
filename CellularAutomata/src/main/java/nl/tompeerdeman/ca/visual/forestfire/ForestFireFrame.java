package nl.tompeerdeman.ca.visual.forestfire;

import nl.tompeerdeman.ca.Simulator;
import nl.tompeerdeman.ca.forestfire.ForestFire;
import nl.tompeerdeman.ca.forestfire.ForestFireData;
import nl.tompeerdeman.ca.visual.CaFrame;
import nl.tompeerdeman.ca.visual.CaPanel;

public class ForestFireFrame extends CaFrame {
	private static final long serialVersionUID = -1641664667855879006L;
	
	public static void main(String[] args) {
		new ForestFireFrame();
	}
	
	public ForestFireFrame() {
		super("Forest fire simulation");
		
		ForestFire fire =
			new ForestFire(0.6, 100, 100, 0, ForestFireData.NB_MOORE, 0);
		fire.buildTimed(10);
		
		Simulator sim = fire.getSimulator();
		
		CaPanel capanel =
			new ForestFirePanel(sim.getGrid(), getWidth(), getHeight());
		sim.addChangeListener(capanel);
		sim.afterSimulateTick();
		
		main.add(capanel);
		
		ForestFireDataPanel dataPanel = new ForestFireDataPanel(fire);
		sim.addChangeListener(dataPanel);
		
		main.add(dataPanel);
		
		setVisible(true);
	}
	
}
