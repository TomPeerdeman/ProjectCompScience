/**
 * File: TriggerAction.java
 * 
 */
package nl.uva.ca;

import nl.tompeerdeman.ca.DataSet;

/**
 *
 */
public interface TriggerAction {
	/**
	 * Execute this action
	 * 
	 * @param data
	 */
	public void execute(DataSet data);
	
	/**
	 * @return The name of this action as used in TriggerManager.ACTIONS
	 */
	public String getActionName();
}
