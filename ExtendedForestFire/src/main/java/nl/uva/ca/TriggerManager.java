/**
 * File: TriggerManager.java
 * 
 */
package nl.uva.ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;

import nl.uva.ca.visual.trigger.TestActionGenerator;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;
import nl.uva.ca.visual.trigger.TriggerGeneratorPanel;
import nl.uva.ca.visual.trigger.forestfire.NBActionGenerator;
import nl.uva.ca.visual.trigger.forestfire.TickGenerator;

/**
 *
 */
public class TriggerManager implements SimulateChangeListener {
	public static final Map<String, Class<? extends TriggerGeneratorPanel<?>>> TRIGGERS =
		new HashMap<String, Class<? extends TriggerGeneratorPanel<?>>>();
	public static final Map<String, Class<? extends TriggerActionGeneratorPanel<?>>> ACTIONS =
		new HashMap<String, Class<? extends TriggerActionGeneratorPanel<?>>>();
	
	static {
		try {
			TRIGGERS.put("Tick", TickGenerator.class);
			
			ACTIONS.put("Neighborhood", NBActionGenerator.class);
			ACTIONS.put("Test", TestActionGenerator.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Trigger> triggers;
	
	/**
	 * 
	 */
	public TriggerManager() {
		triggers = new ArrayList<Trigger>();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.tompeerdeman.ca.SimulateChangeListener#simulationUpdated(nl.
	 * tompeerdeman
	 * .ca.Simulator)
	 */
	@Override
	public void simulationUpdated(Simulator sim) {
		Iterator<Trigger> it = triggers.iterator();
		while(it.hasNext()) {
			if(it.next().process(sim)) {
				it.remove();
			}
		}
	}
}
