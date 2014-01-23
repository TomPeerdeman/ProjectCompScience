/**
 * File: AbstractCell.java
 * 
 */
package nl.uva.ca;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.CellType;

/**
 * Java serialization require a empty constructor of a non-serializable
 * superclass.
 * Since Cell is not serializable and does not have a empty constructor we need
 * to create a class that does provide this constructor.
 */
public abstract class AbstractCell extends Cell {
	
	/**
	 * @param x
	 * @param y
	 * @param type
	 */
	public AbstractCell(int x, int y, CellType type) {
		super(x, y, type);
	}
	
	/**
	 * 
	 */
	public AbstractCell() {
		super(-1, -1, null);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractCell [x=" + x + ", y=" + y + ", type=" + type + "]";
	}
}
