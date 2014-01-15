/**
 * File: StopFireFightersGenerator.java
 * 
 */
package nl.uva.ca.visual.trigger.forestfire;

import nl.uva.ca.triggers.FireFightersStatusAction;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;

/**
 *
 */
public class StopFireFightersGenerator extends
		TriggerActionGeneratorPanel<FireFightersStatusAction> {
	private static final long serialVersionUID = 5131637180693743814L;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel#init()
	 */
	@Override
	public void init() {
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel#generate()
	 */
	@Override
	public FireFightersStatusAction generate() {
		return new FireFightersStatusAction(false);
	}
}
