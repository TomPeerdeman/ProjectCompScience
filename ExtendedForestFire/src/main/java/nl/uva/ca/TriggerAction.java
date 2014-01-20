/**
 * File: TriggerAction.java
 * 
 */
package nl.uva.ca;

import java.io.Serializable;

import nl.tompeerdeman.ca.SimulatableSystem;

/**
 *
 */
public interface TriggerAction extends Serializable {
	/**
	 * Execute this action
	 * 
	 * @param sys
	 */
	public void execute(SimulatableSystem sys);
	
	/**
	 * @return The name of this action as used in TriggerManager.ACTIONS
	 */
	public String getActionName();
}
