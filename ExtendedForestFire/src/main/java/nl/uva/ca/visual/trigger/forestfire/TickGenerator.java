/**
 * File: TickGenerator.java
 * 
 */
package nl.uva.ca.visual.trigger.forestfire;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;

import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerAction;
import nl.uva.ca.triggers.TickTrigger;
import nl.uva.ca.visual.trigger.TriggerGeneratorPanel;

/**
 *
 */
public class TickGenerator extends TriggerGeneratorPanel<TickTrigger> {
	private static final long serialVersionUID = -5189809393993119923L;
	
	private GridBagConstraints c;
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
			if(n < 0) {
				throw new NumberFormatException();
			}
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
		setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		c.gridx = 0;
		c.gridy = 0;
		
		// This label refuses to align right
		add(new JLabel("On tick [0->"), c);
		
		c.gridy++;
		
		tickTextField = new JTextField("0");
		add(tickTextField, c);
		
		c.gridy++;
		Dimension d = new Dimension(150, 85);
		add(new Box.Filler(d, d, d), c);
	}
}
