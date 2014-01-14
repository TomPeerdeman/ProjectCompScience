/**
 * File: TestActionGenerator.java
 * 
 */
package nl.uva.ca.visual.trigger;

import nl.uva.ca.TriggerAction;
import nl.uva.ca.triggers.TestAction;

/**
 *
 */
public class TestActionGenerator extends
		TriggerActionGeneratorPanel<TestAction> {
	private static final long serialVersionUID = 4075151165613455253L;
	
	/**
	 * 
	 */
	public TestActionGenerator() {
		super();
	}
	
	/**
	 * @param parent
	 */
	public TestActionGenerator(TriggerAction parent) {
		super(parent);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel#generate()
	 */
	@Override
	public TestAction generate() {
		return new TestAction();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel#init()
	 */
	@Override
	public void init() {
	}
}
