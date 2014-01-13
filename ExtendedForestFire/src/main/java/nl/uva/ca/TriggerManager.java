/**
 * File: TriggerManager.java
 * 
 */
package nl.uva.ca;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;

import nl.uva.ca.triggers.NBAction;
import nl.uva.ca.triggers.TickTrigger;

/**
 *
 */
public class TriggerManager implements SimulateChangeListener {
	private static final Map<String, Constructor<? extends Trigger>> TRIGGERS =
		new HashMap<String, Constructor<? extends Trigger>>();
	private static final Map<String, Constructor<? extends TriggerAction>> ACTIONS =
		new HashMap<String, Constructor<? extends TriggerAction>>();
	
	static {
		try {
			TRIGGERS.put("Tick", TickTrigger.class.getConstructor(int.class));
			
			ACTIONS.put("Neighborhood",
					NBAction.class.getConstructor(double[][].class));
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
