/**
 * File: TestAction.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.DataSet;

import nl.uva.ca.TriggerAction;

/**
 *
 */
public class TestAction implements TriggerAction {
	
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
	public void execute(DataSet data) {
		System.out.println("Test trigger activated");
	}
	
	@Override
	public String toString() {
		return "test print";
	}
}
