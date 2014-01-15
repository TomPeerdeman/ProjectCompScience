/**
 * File: StartFireFightersGenerator.java
 * 
 */
package nl.uva.ca.visual.trigger.forestfire;

import nl.uva.ca.TriggerAction;
import nl.uva.ca.triggers.FireFightersStatusAction;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;

/**
 *
 */
public class StartFireFightersGenerator extends
		TriggerActionGeneratorPanel<FireFightersStatusAction> {
	private static final long serialVersionUID = 6773065783102249671L;
	
	/**
	 * 
	 */
	public StartFireFightersGenerator() {
		super();
	}
	
	/**
	 * @param parent
	 */
	public StartFireFightersGenerator(TriggerAction parent) {
		super(parent);
	}
	
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
		return new FireFightersStatusAction(true);
	}
	
}
