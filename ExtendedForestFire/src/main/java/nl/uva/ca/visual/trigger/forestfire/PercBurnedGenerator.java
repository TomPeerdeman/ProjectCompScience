/**
 * File: PercBurnedGenerator.java
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
import nl.uva.ca.triggers.PercBurnedTrigger;
import nl.uva.ca.visual.trigger.TriggerGeneratorPanel;

/**
 *
 */
public class PercBurnedGenerator extends
		TriggerGeneratorPanel<PercBurnedTrigger> {
	private static final long serialVersionUID = 4877128035190561235L;
	
	private GridBagConstraints c;
	private JTextField percBurnedField;
	
	/**
	 * 
	 */
	public PercBurnedGenerator() {
		super();
	}
	
	/**
	 * @param parent
	 */
	public PercBurnedGenerator(Trigger parent) {
		super(parent);
		if(parent instanceof PercBurnedTrigger) {
			percBurnedField.setText(String.valueOf(((PercBurnedTrigger) parent).getPercBurned()));
		}
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
		add(new JLabel("% burned [0-1]"), c);
		
		percBurnedField = new JTextField("0.1");
		c.gridy++;
		add(percBurnedField, c);
		
		c.gridy++;
		Dimension d = new Dimension(150, 70);
		add(new Box.Filler(d, d, d), c);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerGeneratorPanel#generate(nl.uva.ca.
	 * TriggerAction)
	 */
	@Override
	public PercBurnedTrigger generate(TriggerAction action) {
		try {
			double n = Double.parseDouble(percBurnedField.getText());
			if(n < 0 || n > 1) {
				throw new NumberFormatException();
			}
			return new PercBurnedTrigger(action, n);
		} catch(NumberFormatException e) {
			percBurnedField.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		
		return null;
	}
}
