/**
 * File: ProbDividerAction.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.SimulatableSystem;

import nl.uva.ca.ExForestFireData;
import nl.uva.ca.TriggerAction;

/**
 *
 */
public class ProbDividerAction implements TriggerAction {
	private final int div;
	
	/**
	 * @param div
	 */
	public ProbDividerAction(int div) {
		this.div = div;
	}
	
	/**
	 * @return the div
	 */
	public int getDivider() {
		return div;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#execute(nl.tompeerdeman.ca.DataSet)
	 */
	@Override
	public void execute(SimulatableSystem sys) {
		((ExForestFireData) sys.getSimulator().getData()).probDivider = div;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#getActionName()
	 */
	@Override
	public String getActionName() {
		return "Set prob divider";
	}
	
	@Override
	public String toString() {
		return "set prob divider";
	}
}
