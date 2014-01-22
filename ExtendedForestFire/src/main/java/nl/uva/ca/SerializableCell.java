/**
 * File: SerializableCell.java
 * 
 */
package nl.uva.ca;

import java.io.Serializable;

import nl.tompeerdeman.ca.Cell;
import nl.tompeerdeman.ca.CellType;

/**
 *
 */
public abstract class SerializableCell extends Cell implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param x
	 * @param y
	 * @param type
	 */
	public SerializableCell(int x, int y, CellType type) {
		super(x, y, type);
	}
}
