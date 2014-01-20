/**
 * File: BushBurnTicksAction.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.SimulatableSystem;

import nl.uva.ca.ExForestFireData;

/**
 *
 */
public class BushBurnTicksAction extends TreeBurnTicksAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param burnTicks
	 */
	public BushBurnTicksAction(int burnTicks) {
		super(burnTicks);
	}
	
	@Override
	public void execute(SimulatableSystem sys) {
		((ExForestFireData) sys.getSimulator().getData()).nTicksBushBurn =
			burnTicks;
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
