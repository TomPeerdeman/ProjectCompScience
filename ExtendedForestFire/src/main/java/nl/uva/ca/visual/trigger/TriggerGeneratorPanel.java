/**
 * File: TriggerGeneratorPanel.java
 * 
 */
package nl.uva.ca.visual.trigger;

import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerAction;

/**
 * @param <T>
 * 
 */
public abstract class TriggerGeneratorPanel<T extends Trigger> extends
		TriggerPanel {
	private static final long serialVersionUID = -4796731464773731267L;
	
	/**
	 * 
	 */
	public TriggerGeneratorPanel() {
	}
	
	public TriggerGeneratorPanel(T parent) {
	}
	
	public abstract T generate(TriggerAction action);
}
