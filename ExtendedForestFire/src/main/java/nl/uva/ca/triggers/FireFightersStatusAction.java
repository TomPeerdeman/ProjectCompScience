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
	private static final long serialVersionUID = 1L;
	
	private final boolean newStatus;
	private final double newSpawnProb;
	
	/**
	 * @param newStatus
	 * @param spawnProb
	 * 
	 */
	public FireFightersStatusAction(boolean newStatus, double spawnProb) {
		this.newStatus = newStatus;
		newSpawnProb = spawnProb;
	}
	
	public FireFightersStatusAction(boolean newStatus) {
		this.newStatus = newStatus;
		newSpawnProb = 0.4;
	}
	
	/**
	 * @return the newStatus
	 */
	public boolean isNewStatus() {
		return newStatus;
	}
	
	/**
	 * @return the newSpawnProb
	 */
	public double getSpawnProb() {
		return newSpawnProb;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#execute(nl.tompeerdeman.ca.DataSet)
	 */
	@Override
	public void execute(SimulatableSystem sys) {
		ExForestFireData data = (ExForestFireData) sys.getSimulator().getData();
		data.fireFighters = newStatus;
		data.fireFighterSpawnProb = newSpawnProb;
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
