/**
 * File: ExCaFrame.java
 * 
 */
package nl.uva.ca.visual;

public class ExCaFrame extends CaFrame{
	public static void main(String[] args) {
		  new ExCaFrame("title");
	}
		
	public ExCaFrame(String title) extends CaFrame{
		setSize(850, 650);
	}
}
