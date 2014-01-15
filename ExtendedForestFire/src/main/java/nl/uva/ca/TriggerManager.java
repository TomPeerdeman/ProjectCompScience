/**
 * File: TriggerManager.java
 * 
 */
package nl.uva.ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nl.tompeerdeman.ca.SimulatableSystem;
import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;

import nl.uva.ca.visual.trigger.TestActionGenerator;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;
import nl.uva.ca.visual.trigger.TriggerGeneratorPanel;
import nl.uva.ca.visual.trigger.forestfire.BushBurnTicksGenerator;
import nl.uva.ca.visual.trigger.forestfire.NBActionGenerator;
import nl.uva.ca.visual.trigger.forestfire.PercBurnedGenerator;
import nl.uva.ca.visual.trigger.forestfire.ProbDividerGenerator;
import nl.uva.ca.visual.trigger.forestfire.StartFireFightersGenerator;
import nl.uva.ca.visual.trigger.forestfire.StopFireFightersGenerator;
import nl.uva.ca.visual.trigger.forestfire.TickGenerator;
import nl.uva.ca.visual.trigger.forestfire.TreeBurnTicksGenerator;

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
			TRIGGERS.put("% burned", PercBurnedGenerator.class);
			
			ACTIONS.put("Neighborhood", NBActionGenerator.class);
			ACTIONS.put("Print test", TestActionGenerator.class);
			ACTIONS.put("Bush burn ticks", BushBurnTicksGenerator.class);
			ACTIONS.put("Tree burn ticks", TreeBurnTicksGenerator.class);
			ACTIONS.put("Start firefighting", StartFireFightersGenerator.class);
			ACTIONS.put("Stop firefighting", StopFireFightersGenerator.class);
			ACTIONS.put("Set prob divider", ProbDividerGenerator.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Trigger> triggers;
	private SimulatableSystem sys;
	
	/**
	 * @param sys
	 * 
	 */
	public TriggerManager(SimulatableSystem sys) {
		triggers = new ArrayList<Trigger>();
		
		this.sys = sys;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.tompeerdeman.ca.SimulateChangeListener#simulationUpdated(nl.tompeerdeman
	 * .ca.Simulator)
	 */
	@Override
	public void simulationUpdated(Simulator sim) {
		Iterator<Trigger> it = triggers.iterator();
		while(it.hasNext()) {
			Trigger t = it.next();
			if(!t.isActivated() && t.process(sys)) {
				t.setActivated(true);
			}
		}
	}
	
	public void reset() {
		for(Trigger t : triggers) {
			t.setActivated(false);
		}
	}
}
