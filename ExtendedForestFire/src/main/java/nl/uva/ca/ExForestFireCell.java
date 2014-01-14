/**
 * File: ExForestFireCell.java
 * 
 */
package nl.uva.ca;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.DataSet;
import nl.tompeerdeman.ca.Grid;
import nl.tompeerdeman.ca.Simulator;

public class ExForestFireCell extends Cell {
	private double temperature;
	
	/**
	 * @param x
	 * @param y
	 * @param t
	 */
	public ExForestFireCell(int x, int y, ExForestFireCellType t) {
		super(x, y, t);
	}
	
	@Override
	public boolean shouldSimulate() {
		return type == ExForestFireCellType.BURNING_BUSH
				|| type == ExForestFireCellType.BURNING_TREE;
	}
	
	/**
	 * @return the temperature of this cell
	 */
	public double getTemperature() {
		return temperature;
	}
	
	/**
	 * @param temperature
	 *            the temperature to set
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	/**
	 * @param temperature
	 *            the temperature to add
	 */
	public void addTemperature(double temperature) {
		this.temperature += temperature;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.tompeerdeman.ca.Cell#simulate(nl.tompeerdeman.ca.Grid,
	 * nl.tompeerdeman.ca.DataSet, nl.tompeerdeman.ca.Simulator)
	 */
	@Override
	public boolean simulate(Grid grid, DataSet data, Simulator sim) {
		@SuppressWarnings("unused")
		ExForestFireData ffdata = (ExForestFireData) data;
		
		// TODO: Implement
		return true;
	}
}
