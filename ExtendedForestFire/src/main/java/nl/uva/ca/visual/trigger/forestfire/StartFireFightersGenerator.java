/**
 * File: StartFireFightersGenerator.java
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
import nl.uva.ca.triggers.FireFightersStatusAction;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;

/**
 *
 */
public class StartFireFightersGenerator extends
		TriggerActionGeneratorPanel<FireFightersStatusAction> {
	private static final long serialVersionUID = 6773065783102249671L;
	
	private GridBagConstraints c;
	private JTextField spawnProbField;
	
	/**
	 * 
	 */
	public StartFireFightersGenerator() {
		super();
	}
	
	/**
	 * @param parent
	 */
	public StartFireFightersGenerator(TriggerAction parent) {
		super(parent);
		if(parent instanceof FireFightersStatusAction) {
			spawnProbField.setText(String.valueOf(((FireFightersStatusAction) parent).getSpawnProb()));
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
		add(new JLabel("Spawn prob [0-1]"), c);
		
		spawnProbField = new JTextField("0.4");
		c.gridy++;
		add(spawnProbField, c);
		
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
	public FireFightersStatusAction generate() {
		try {
			double n = Double.parseDouble(spawnProbField.getText());
			if(n > 1 || n < 0) {
				throw new NumberFormatException();
			}
			return new FireFightersStatusAction(true, n);
		} catch(NumberFormatException e) {
			spawnProbField.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		
		return null;
	}
}
