/**
 * File: NBAction.java
 * 
 */
package nl.uva.ca.triggers;

import nl.tompeerdeman.ca.DataSet;

import nl.uva.ca.ExForestFireData;
import nl.uva.ca.TriggerAction;

/**
 *
 */
public class NBAction implements TriggerAction {
	private final double[][] nb;
	private final int type;
	
	/**
	 * @param nb
	 */
	public NBAction(double[][] nb, int type) {
		this.nb = nb;
		this.type = type;
	}
	
	/**
	 * @return the nb
	 */
	public double[][] getNb() {
		return nb;
	}
	
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#execute(nl.tompeerdeman.ca.DataSet)
	 */
	@Override
	public void execute(DataSet data) {
		ExForestFireData d = (ExForestFireData) data;
		d.neighborhood = nb;
	}
	
	@Override
	public String toString() {
		return "set neighborhood";
	}
}
