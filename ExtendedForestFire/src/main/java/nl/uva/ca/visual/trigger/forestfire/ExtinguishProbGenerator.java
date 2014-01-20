/**
 * File: ExtinguishProbGenerator.java
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

import nl.uva.ca.TriggerAction;
import nl.uva.ca.triggers.ExtinguishProbAction;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;

/**
 *
 */
public class ExtinguishProbGenerator extends
		TriggerActionGeneratorPanel<ExtinguishProbAction> {
	private static final long serialVersionUID = 4355234928470680832L;
	
	private GridBagConstraints c;
	private JTextField probExtinguishField;
	
	/**
	 * 
	 */
	public ExtinguishProbGenerator() {
		super();
	}
	
	/**
	 * @param parent
	 */
	public ExtinguishProbGenerator(TriggerAction parent) {
		super(parent);
		if(parent instanceof ExtinguishProbAction) {
			probExtinguishField.setText(String.valueOf(((ExtinguishProbAction) parent).getExtProb()));
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel#init()
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
		add(new JLabel("Ext prob [0-1]"), c);
		
		probExtinguishField = new JTextField("0.1");
		c.gridy++;
		add(probExtinguishField, c);
		
		c.gridy++;
		Dimension d = new Dimension(150, 70);
		add(new Box.Filler(d, d, d), c);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel#generate()
	 */
	@Override
	public ExtinguishProbAction generate() {
		try {
			double n = Double.parseDouble(probExtinguishField.getText());
			if(n < 0 || n > 1) {
				throw new NumberFormatException();
			}
			return new ExtinguishProbAction(n);
		} catch(NumberFormatException e) {
			probExtinguishField.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		
		return null;
	}
}
