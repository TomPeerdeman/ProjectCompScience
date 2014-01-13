/**
 * File: Trigger.java
 * 
 */
package nl.uva.ca;

import nl.tompeerdeman.ca.Simulator;

/**
 *
 */
public abstract class Trigger {
	protected final TriggerAction action;
	
	/**
	 * @param action
	 */
	public Trigger(TriggerAction action) {
		this.action = action;
	}
	
	/**
	 * @param sim
	 * @return True if this trigger is activated, otherwise false
	 */
	public abstract boolean process(Simulator sim);
}
