/**
 * File: TreeBurnTicksAction.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.DataSet;

import nl.uva.ca.ExForestFireData;
import nl.uva.ca.TriggerAction;

/**
 *
 */
public class TreeBurnTicksAction implements TriggerAction {
	protected final int burnTicks;
	
	/**
	 * @param burnTicks
	 */
	public TreeBurnTicksAction(int burnTicks) {
		this.burnTicks = burnTicks;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#execute(nl.tompeerdeman.ca.DataSet)
	 */
	@Override
	public void execute(DataSet data) {
		((ExForestFireData) data).nTicksTreeBurn = burnTicks;
	}
	
	@Override
	public String toString() {
		return "set tree burn ticks";
	}
	
	/**
	 * @return the burnTicks
	 */
	public int getBurnTicks() {
		return burnTicks;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#getActionName()
	 */
	@Override
	public String getActionName() {
		return "Tree burn ticks";
	}
}
