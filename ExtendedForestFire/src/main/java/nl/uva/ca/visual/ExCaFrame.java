/**
 * File: ExCaFrame.java
 * 
 */
package nl.uva.ca.visual;

import nl.tompeerdeman.ca.visual.CaFrame;

public class ExCaFrame extends CaFrame {
	public static void main(String[] args) {
		new ExCaFrame("title");
	}
	
	public ExCaFrame(String title) {
		super(title);
		
		setSize(850, 650);
		
		setVisible(true);
	}
}
