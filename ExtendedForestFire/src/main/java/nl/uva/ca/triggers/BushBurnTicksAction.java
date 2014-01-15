/**
 * File: BushBurnTicksAction.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.DataSet;

import nl.uva.ca.ExForestFireData;

/**
 *
 */
public class BushBurnTicksAction extends TreeBurnTicksAction {
	
	/**
	 * @param burnTicks
	 */
	public BushBurnTicksAction(int burnTicks) {
		super(burnTicks);
	}
	
	@Override
	public void execute(DataSet data) {
		((ExForestFireData) data).nTicksBushBurn = burnTicks;
	}
	
	@Override
	public String toString() {
		return "set bush burn ticks";
	}
	
	@Override
	public String getActionName() {
		return "Bush burn ticks";
	}
}
