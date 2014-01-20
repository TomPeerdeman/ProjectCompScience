/**
 * File: ExtinguishProbAction.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.SimulatableSystem;

import nl.uva.ca.ExForestFireData;
import nl.uva.ca.TriggerAction;

/**
 *
 */
public class ExtinguishProbAction implements TriggerAction {
	private final double extProb;
	
	/**
	 * @param prob
	 * 
	 */
	public ExtinguishProbAction(double prob) {
		extProb = prob;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.uva.ca.TriggerAction#execute(nl.tompeerdeman.ca.SimulatableSystem)
	 */
	@Override
	public void execute(SimulatableSystem sys) {
		ExForestFireData data = (ExForestFireData) sys.getSimulator().getData();
		data.extinguishProb = extProb;
	}
	
	/**
	 * @return the extProb
	 */
	public double getExtProb() {
		return extProb;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#getActionName()
	 */
	@Override
	public String getActionName() {
		return "Set ext prob";
	}
	
	@Override
	public String toString() {
		return "set extinguish prob";
	}
}
