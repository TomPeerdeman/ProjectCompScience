/**
 * File: NBActionGenerator.java
 * 
 */
package nl.uva.ca.visual.trigger.forestfire;

import nl.uva.ca.TriggerAction;
import nl.uva.ca.triggers.NBAction;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;

/**
 *
 */
public class NBActionGenerator extends TriggerActionGeneratorPanel<NBAction> {
	private static final long serialVersionUID = 2553481122213934488L;
	
	/**
	 * 
	 */
	public NBActionGenerator() {
		super();
	}
	
	/**
	 * @param parent
	 */
	public NBActionGenerator(TriggerAction parent) {
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
	public NBAction generate() {
		return new NBAction(null);
	}
}
