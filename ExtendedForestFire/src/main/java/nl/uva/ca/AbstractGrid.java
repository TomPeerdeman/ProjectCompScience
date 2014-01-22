/**
 * File: AbstractGrid.java
 * 
 */
package nl.uva.ca;

import nl.tompeerdeman.ca.Grid;

/**
 * Java serialization require a empty constructor of a non-serializable
 * superclass.
 * Since Grid is not serializable and does not have a empty constructor we need
 * to create a class that does provide this constructor.
 */
public class AbstractGrid extends Grid {
	
	/**
	 * @param nx
	 * @param ny
	 */
	public AbstractGrid(int nx, int ny) {
		super(nx, ny);
	}
	
	/**
	 * Constructor for deserialization
	 */
	protected AbstractGrid() {
		super(0, 0);
	}
}
