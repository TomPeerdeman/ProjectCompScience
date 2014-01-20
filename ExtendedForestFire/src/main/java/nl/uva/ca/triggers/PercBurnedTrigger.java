/**
 * File: PercBurnedTrigger.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.SimulatableSystem;

import nl.uva.ca.ExForestFireData;
import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerAction;

/**
 *
 */
public class PercBurnedTrigger extends Trigger {
	private static final long serialVersionUID = 1L;
	
	private final double percBurned;
	
	/**
	 * @param action
	 * @param percBurned
	 */
	public PercBurnedTrigger(TriggerAction action, double percBurned) {
		super(action);
		this.percBurned = percBurned;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.Trigger#process(nl.tompeerdeman.ca.SimulatableSystem)
	 */
	@Override
	public boolean process(SimulatableSystem sim) {
		ExForestFireData data = (ExForestFireData) sim.getSimulator().getData();
		if((double) data.burnt
				/ (double) (data.burning + data.burnt + data.bushes + data.trees) >= percBurned) {
			action.execute(sim);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the percBurned
	 */
	public double getPercBurned() {
		return percBurned;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.Trigger#getTriggerName()
	 */
	@Override
	public String getTriggerName() {
		return "% burned";
	}
	
	@Override
	public String toString() {
		return "On % burned " + action;
	}
}
