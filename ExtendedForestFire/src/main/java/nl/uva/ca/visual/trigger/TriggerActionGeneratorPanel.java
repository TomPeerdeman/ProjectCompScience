/**
 * File: TriggerActionGeneratorPanel.java
 * 
 */
package nl.uva.ca.visual.trigger;

import nl.uva.ca.TriggerAction;

/**
 * @param <T>
 * 
 */
public abstract class TriggerActionGeneratorPanel<T extends TriggerAction>
		extends TriggerPanel {
	private static final long serialVersionUID = 7831966063074212936L;
	
	/**
	 * 
	 */
	public TriggerActionGeneratorPanel() {
	}
	
	public TriggerActionGeneratorPanel(T parent) {
	}
	
	public abstract T generate();
}
