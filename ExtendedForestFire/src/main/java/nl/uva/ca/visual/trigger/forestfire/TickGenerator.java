/**
 * File: TickGenerator.java
 * 
 */
package nl.uva.ca.visual.trigger.forestfire;

import javax.swing.JLabel;
import javax.swing.JTextField;

import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerAction;
import nl.uva.ca.triggers.TickTrigger;
import nl.uva.ca.visual.trigger.TriggerGeneratorPanel;

/**
 *
 */
public class TickGenerator extends TriggerGeneratorPanel<TickTrigger> {
	private static final long serialVersionUID = -5189809393993119923L;
	private JTextField tickTextField;
	
	/**
	 * 
	 */
	public TickGenerator() {
		super();
	}
	
	/**
	 * @param parent
	 */
	public TickGenerator(Trigger parent) {
		super(parent);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerGeneratorPanel#generate(nl.uva.ca.
	 * TriggerAction)
	 */
	@Override
	public TickTrigger generate(TriggerAction action) {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerGeneratorPanel#init()
	 */
	@Override
	public void init() {
		tickTextField = new JTextField("0");
		
		add(new JLabel("After tick"));
		add(tickTextField);
	}
}
