/**
 * File: ExForestFireCellType.java
 * 
 */
package nl.uva.ca;

import java.awt.Color;

import nl.tompeerdeman.ca.CellType;

public enum ExForestFireCellType implements CellType {
	WATER("Water", new Color(0x33DDDD)),
	TREE("Tree", new Color(0x336600)),
	BUSH("Bush", new Color(0x33CC11)),
	BURNING_TREE("Burning tree", new Color(0xFF2200)),
	BURNING_BUSH("Burning bush", new Color(0xFF5500)),
	BURNT_TREE("Burnt tree", Color.BLACK),
	BURNT_BUSH("Burnt bush", Color.BLACK),
	EXTINGUISHED_TREE("Extinguished tree", new Color(0x37799)),
	EXTINGUISHED_BUSH("Extinguished bush", new Color(0x39999)),
	FIRE_FIGHTER("Fire Fighters", Color.YELLOW);
	
	private final String desc;
	private final Color color;
	
	/**
	 * @param desc
	 */
	private ExForestFireCellType(String desc, Color color) {
		this.desc = desc;
		this.color = color;
	}
	
	/**
	 * @return the description of this type
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
}
