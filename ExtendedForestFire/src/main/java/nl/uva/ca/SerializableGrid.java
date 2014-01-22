/**
 * File: SerializableGrid.java
 * 
 */
package nl.uva.ca;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import nl.tompeerdeman.ca.Cell;

/**
 *
 */
public class SerializableGrid extends AbstractGrid implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param nx
	 * @param ny
	 */
	public SerializableGrid(int nx, int ny) {
		super(nx, ny);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(grid);
	}
	
	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		grid = (Cell[][]) in.readObject();
	}
}
