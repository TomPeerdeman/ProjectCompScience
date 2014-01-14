/**
 * File: TriggerPanel.java
 * 
 */
package nl.uva.ca.visual.trigger;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 */
public class TriggerPanel extends JPanel {
	private static final long serialVersionUID = -6273342822045865252L;
	
	public String active;
	
	/**
	 * 
	 */
	public TriggerPanel() {
		setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		setSize(140, 100);
		Dimension d = new Dimension(140, 100);
		setPreferredSize(d);
		setMinimumSize(d);
		
		setLayout(new CardLayout());
	}
	
	public void show(String name) {
		active = name;
		((CardLayout) getLayout()).show(this, name);
	}
}
