/**
 * File: TriggerGeneratorPanel.java
 * 
 */
package nl.uva.ca.visual.trigger;

import javax.swing.JPanel;

import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerAction;

/**
 * @param <T>
 * 
 */
public abstract class TriggerGeneratorPanel<T extends Trigger> extends
		JPanel {
	private static final long serialVersionUID = -4796731464773731267L;
	
	/**
	 * 
	 */
	public TriggerGeneratorPanel() {
		init();
	}
	
	public TriggerGeneratorPanel(Trigger parent) {
		init();
	}
	
	public abstract void init();
	
	public abstract T generate(TriggerAction action);
}
