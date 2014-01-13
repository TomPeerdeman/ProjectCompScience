/**
 * File: TriggerPanel.java
 * 
 */
package nl.uva.ca.visual.trigger;

import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 *
 */
public class TriggerPanel extends JPanel {
	private static final long serialVersionUID = -6273342822045865252L;
	private CardLayout layout;
	
	/**
	 * 
	 */
	public TriggerPanel() {
		setSize(150, 100);
		layout = new CardLayout();
		setLayout(layout);
	}
	
	public void add(JPanel p, String name) {
		layout.addLayoutComponent(p, name);
	}
	
	public void show(String name) {
		layout.show(this, name);
	}
}
