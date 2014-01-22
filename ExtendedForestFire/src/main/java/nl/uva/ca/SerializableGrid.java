/**
 * File: SerializableGrid.java
 * 
 */
package nl.uva.ca;

import java.io.Serializable;

import nl.tompeerdeman.ca.Grid;

/**
 *
 */
public class SerializableGrid extends Grid implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param nx
	 * @param ny
	 */
	public SerializableGrid(int nx, int ny) {
		super(nx, ny);
	}
}
