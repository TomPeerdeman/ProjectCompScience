/**
 * File: TickTrigger.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.SimulatableSystem;
import nl.tompeerdeman.ca.Simulator;

import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerAction;

/**
 *
 */
public class TickTrigger extends Trigger {
	private final int tick;
	
	/**
	 * @param tick
	 * @param action
	 */
	public TickTrigger(int tick, TriggerAction action) {
		super(action);
		this.tick = tick;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.Trigger#process(nl.tompeerdeman.ca.SimulatableSystem)
	 */
	@Override
	public boolean process(SimulatableSystem sys) {
		Simulator sim = sys.getSimulator();
		if(sim.getTick() >= tick) {
			action.execute(sim.getData());
			return true;
		}
		return false;
	}
	
	/**
	 * @return the tick
	 */
	public int getTick() {
		return tick;
	}
	
	@Override
	public String toString() {
		return "On tick " + tick + " " + action;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.Trigger#getTriggerName()
	 */
	@Override
	public String getTriggerName() {
		return "Tick";
	}
}
