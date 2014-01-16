/**
 * File: FireFightersStatusAction.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.SimulatableSystem;

import nl.uva.ca.ExForestFireData;
import nl.uva.ca.TriggerAction;

/**
 *
 */
public class FireFightersStatusAction implements TriggerAction {
	private final boolean newStatus;
	
	/**
	 * @param newStatus
	 * 
	 */
	public FireFightersStatusAction(boolean newStatus) {
		this.newStatus = newStatus;
	}
	
	/**
	 * @return the newStatus
	 */
	public boolean isNewStatus() {
		return newStatus;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#execute(nl.tompeerdeman.ca.DataSet)
	 */
	@Override
	public void execute(SimulatableSystem sys) {
		((ExForestFireData) sys.getSimulator().getData()).fireFighters =
			newStatus;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#getActionName()
	 */
	@Override
	public String getActionName() {
		if(newStatus) {
			return "Start firefighting";
		} else {
			return "Stop firefighting";
		}
	}
	
	@Override
	public String toString() {
		if(!newStatus)
			return "stop firefighting";
		else
			return "start firefighting";
	}
}
