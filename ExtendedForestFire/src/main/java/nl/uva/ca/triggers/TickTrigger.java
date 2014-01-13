/**
 * File: TickTrigger.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.Simulator;

import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerAction;

/**
 *
 */
public class TickTrigger extends Trigger {
	private final int tick;
	
	/**
	 * @param action
	 * @param tick
	 */
	public TickTrigger(TriggerAction action, int tick) {
		super(action);
		this.tick = tick;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.Trigger#process(nl.tompeerdeman.ca.Simulator)
	 */
	@Override
	public boolean process(Simulator sim) {
		if(sim.getTick() >= tick) {
			action.execute(sim.getData());
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "On tick " + tick + " " + action;
	}
}
