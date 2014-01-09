/**
 * File: ExForestFireLegend.java
 * 
 */
package nl.uva.ca.visual.forestfire;

import java.awt.Graphics2D;

import nl.tompeerdeman.ca.Grid;

import nl.uva.ca.ExForestFireCellType;
import nl.uva.ca.visual.ExCaLegendPanel;

public class ExForestFireLegend extends ExCaLegendPanel {
	private static final long serialVersionUID = 1030878969102269166L;
	
	/**
	 * @param g
	 */
	public ExForestFireLegend(Grid g) {
		super(g);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.ExCaLegendPanel#onPaint(java.awt.Graphics2D)
	 */
	@Override
	public void onPaint(Graphics2D g2d) {
		int i = 0;
		for(ExForestFireCellType type : ExForestFireCellType.values()) {
			// 2 per column 25px long 15 px high
			int x = (i / 2) * 25 + offsx;
			int y = (i % 2) * 15;
			drawAt(g2d, x, y, type.getColor(), type.getDesc());
			i++;
		}
	}
}
