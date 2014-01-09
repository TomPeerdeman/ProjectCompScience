package nl.uva.ca.visual.forestfire;

import java.awt.Dimension;

import javax.swing.Box;

import nl.tompeerdeman.ca.Simulator;
import nl.tompeerdeman.ca.forestfire.ForestFireData;
import nl.tompeerdeman.ca.visual.CaPanel;

import nl.uva.ca.ExForestFire;
import nl.uva.ca.visual.ExCaFrame;

public class ExForestFireFrame extends ExCaFrame {
	private static final long serialVersionUID = -6815166825271606570L;
	
	public static void main(String[] args) {
		new ExForestFireFrame();
	}
	
	public ExForestFireFrame() {
		super("Forest fire simulation 2.0");
		
		Dimension d = new Dimension(getWidth(), 10);
		
		main.add(new Box.Filler(d, d, d));
		
		ExForestFire fire =
			// new ForestFire(0.6, 100, 100, 0, ForestFireData.NB_MOORE, 0);
			new ExForestFire(100, 100, 0, ForestFireData.NB_MOORE, true, false,
					0.3, 0.3, 0.3, 0.2, false);
		fire.buildTimedSimulator(10);
		
		Simulator sim = fire.getSimulator();
		
		CaPanel capanel =
			new ExForestFirePanel(sim.getGrid(), getWidth(), getHeight());
		sim.addChangeListener(capanel);
		sim.afterSimulateTick();
		
		main.add(capanel);
		
		ExForestFireDataPanel dataPanel = new ExForestFireDataPanel(fire);
		sim.addChangeListener(dataPanel);
		
		ExForestFireLegend legend =
			new ExForestFireLegend(sim.getGrid());
		
		main.add(legend);
		
		main.add(dataPanel);
		
		setVisible(true);
	}
}
