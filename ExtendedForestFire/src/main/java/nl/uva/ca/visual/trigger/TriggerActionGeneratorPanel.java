/**
 * File: TriggerActionGeneratorPanel.java
 * 
 */
package nl.uva.ca.visual.trigger;

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
		init();
	}
	
	public TriggerActionGeneratorPanel(TriggerAction parent) {
		init();
	}
	
	public abstract void init();
	
	public abstract T generate();
}
