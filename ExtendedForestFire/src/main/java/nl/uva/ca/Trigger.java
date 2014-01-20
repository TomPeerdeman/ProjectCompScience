/**
 * File: Trigger.java
 * 
 */
package nl.uva.ca;

import java.io.Serializable;

import nl.tompeerdeman.ca.SimulatableSystem;

/**
 *
 */
public abstract class Trigger implements Serializable {
	private static final long serialVersionUID = 706111937933284072L;
	
	protected final TriggerAction action;
	protected boolean activated;
	
	/**
	 * @param action
	 */
	public Trigger(TriggerAction action) {
		this.action = action;
		activated = false;
	}
	
	/**
	 * @return the action
	 */
	public TriggerAction getAction() {
		return action;
	}
	
	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return activated;
	}
	
	/**
	 * @param activated
	 *            the activated to set
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	/**
	 * @param sim
	 * @return True if this trigger is activated, otherwise false
	 */
	public abstract boolean process(SimulatableSystem sim);
	
	/**
	 * @return The name of this trigger as used in TriggerManager.TRIGGERS
	 */
	public abstract String getTriggerName();
}
