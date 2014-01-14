/**
 * File: TriggerActionGeneratorPanel.java
 * 
 */
package nl.uva.ca.visual.trigger;

import java.awt.Dimension;

import javax.swing.JPanel;

import nl.uva.ca.TriggerAction;

/**
 * @param <T>
 * 
 */
public abstract class TriggerActionGeneratorPanel<T extends TriggerAction>
		extends JPanel {
	private static final long serialVersionUID = 7831966063074212936L;
	
	/**
	 * 
	 */
	public TriggerActionGeneratorPanel() {
		setSize(150, 130);
		Dimension d = new Dimension(150, 130);
		setPreferredSize(d);
		setMinimumSize(d);
		
		init();
	}
	
	public TriggerActionGeneratorPanel(TriggerAction parent) {
		setSize(150, 130);
		Dimension d = new Dimension(150, 130);
		setPreferredSize(d);
		setMinimumSize(d);
		
		init();
	}
	
	public abstract void init();
	
	public abstract T generate();
}
