/**
 * File: TestAction.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.SimulatableSystem;

import nl.uva.ca.TriggerAction;

/**
 *
 */
public class TestAction implements TriggerAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public TestAction() {
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#execute(nl.tompeerdeman.ca.DataSet)
	 */
	@Override
	public void execute(SimulatableSystem sys) {
		System.out.println("Test trigger activated");
	}
	
	@Override
	public String toString() {
		return "test print";
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#getActionName()
	 */
	@Override
	public String getActionName() {
		return "Print test";
	}
}
