/**
 * File: ProbDividerGenerator.java
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
import nl.uva.ca.triggers.ProbDividerAction;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;

/**
 *
 */
public class ProbDividerGenerator extends
		TriggerActionGeneratorPanel<ProbDividerAction> {
	private static final long serialVersionUID = 638487355915124739L;
	
	private GridBagConstraints c;
	private JTextField divField;
	
	/**
	 * 
	 */
	public ProbDividerGenerator() {
	}
	
	/**
	 * @param parent
	 */
	public ProbDividerGenerator(TriggerAction parent) {
		super(parent);
		if(parent instanceof ProbDividerAction) {
			divField.setText(String.valueOf(((ProbDividerAction) parent).getDivider()));
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
		add(new JLabel("Prob divider [0, 1->"), c);
		
		divField = new JTextField("2");
		c.gridy++;
		add(divField, c);
		
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
	public ProbDividerAction generate() {
		try {
			int n = Integer.parseInt(divField.getText());
			if(n < 0) {
				throw new NumberFormatException();
			}
			return new ProbDividerAction(n);
		} catch(NumberFormatException e) {
			divField.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		
		return null;
	}
}
