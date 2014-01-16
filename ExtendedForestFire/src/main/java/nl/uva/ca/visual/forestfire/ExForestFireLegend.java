/**
 * File: ExForestFireLegend.java
 * 
 */
package nl.uva.ca.visual.forestfire;

import java.awt.Font;
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
		// Default JPanel font is plain, JLabel is bold. We want JLabel style.
		Font f = getFont();
		f = new Font(f.getName(), Font.BOLD, f.getSize());
		setFont(f);
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
			// 3 per column 130px long 15 px high
			int x = (i / 3) * 150 + offsx;
			int y = (i % 3) * 15 + 10;
			drawAt(g2d, x, y, type.getColor(), type.getDesc());
			i++;
		}
	}
}
