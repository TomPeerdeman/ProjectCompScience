/**
 * File: BushBurnTicksGenerator.java
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
import nl.uva.ca.triggers.BushBurnTicksAction;
import nl.uva.ca.triggers.TreeBurnTicksAction;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;

/**
 *
 */
public class BushBurnTicksGenerator extends
		TriggerActionGeneratorPanel<BushBurnTicksAction> {
	private static final long serialVersionUID = -131190783182085958L;
	
	private GridBagConstraints c;
	private JTextField burnTicksField;
	
	/**
	 * 
	 */
	public BushBurnTicksGenerator() {
	}
	
	/**
	 * @param parent
	 */
	public BushBurnTicksGenerator(TriggerAction parent) {
		super(parent);
		if(parent instanceof BushBurnTicksAction) {
			burnTicksField.setText(String.valueOf(((TreeBurnTicksAction) parent).getBurnTicks()));
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
		add(new JLabel("Burn ticks [0->"), c);
		
		burnTicksField = new JTextField("10");
		c.gridy++;
		add(burnTicksField, c);
		
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
	public BushBurnTicksAction generate() {
		try {
			int n = Integer.parseInt(burnTicksField.getText());
			return new BushBurnTicksAction(n);
		} catch(NumberFormatException e) {
			burnTicksField.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		
		return null;
	}
	
}
