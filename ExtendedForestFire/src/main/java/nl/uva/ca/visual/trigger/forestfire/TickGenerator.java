/**
 * File: TickGenerator.java
 * 
 */
package nl.uva.ca.visual.trigger.forestfire;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerAction;
import nl.uva.ca.triggers.TickTrigger;
import nl.uva.ca.visual.trigger.TriggerGeneratorPanel;

/**
 *
 */
public class TickGenerator extends TriggerGeneratorPanel<TickTrigger> {
	private static final long serialVersionUID = -5189809393993119923L;
	private JTextField tickTextField;
	
	/**
	 * 
	 */
	public TickGenerator() {
	}
	
	/**
	 * @param parent
	 */
	public TickGenerator(Trigger parent) {
		super(parent);
		if(parent instanceof TickTrigger) {
			tickTextField.setText("" + ((TickTrigger) parent).getTick());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerGeneratorPanel#generate(nl.uva.ca.
	 * TriggerAction)
	 */
	@Override
	public TickTrigger generate(TriggerAction action) {
		try {
			int n = Integer.parseInt(tickTextField.getText());
			return new TickTrigger(n, action);
		} catch(NumberFormatException e) {
			tickTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerGeneratorPanel#init()
	 */
	@Override
	public void init() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		tickTextField = new JTextField("0");
		
		// This label refuses to align right
		add(new JLabel("On tick", SwingConstants.RIGHT));
		add(tickTextField);
		Dimension d = new Dimension(150, 85);
		add(new Box.Filler(d, d, d));
	}
}
