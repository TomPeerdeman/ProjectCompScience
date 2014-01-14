/**
 * File: NBActionGenerator.java
 * 
 */
package nl.uva.ca.visual.trigger.forestfire;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import nl.uva.ca.TriggerAction;
import nl.uva.ca.triggers.NBAction;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;

/**
 *
 */
public class NBActionGenerator extends TriggerActionGeneratorPanel<NBAction> {
	private static final long serialVersionUID = 2553481122213934488L;
	
	private GridBagConstraints c;
	
	private JTextField gridProb1;
	private JTextField gridProb2;
	private JTextField gridProb3;
	private JTextField gridProb4;
	private JTextField gridProb5;
	private JTextField gridProb6;
	private JTextField gridProb7;
	private JTextField gridProb8;
	
	/**
	 * 
	 */
	public NBActionGenerator() {
		super();
	}
	
	/**
	 * @param parent
	 */
	public NBActionGenerator(TriggerAction parent) {
		super(parent);
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
		
		gridProb1 = new JTextField("1");
		gridProb2 = new JTextField("2");
		gridProb3 = new JTextField("3");
		gridProb4 = new JTextField("4");
		gridProb5 = new JTextField("5");
		gridProb6 = new JTextField("6");
		gridProb7 = new JTextField("7");
		gridProb8 = new JTextField("8");
	}
	
	public void setGridType(int type) {
		switch(type) {
			case 0:
				drawStandardGrid(0, 0);
				break;
			case 1:
				drawHexGrid(0, 0);
				break;
			case 2:
				drawTriangleGrid(0, 0);
				break;
		}
	}
	
	private void drawStandardGrid(int offsx, int offsy) {
		// column 1
		
		c.gridx = offsx;
		c.gridy = offsy;
		add(gridProb1, c);
		
		c.gridy++;
		add(gridProb4, c);
		
		c.gridy++;
		add(gridProb6, c);
		
		// column 2
		
		c.gridx++;
		c.gridy = offsy;
		add(gridProb2, c);
		
		c.gridy++;
		add(new JLabel("Fire", SwingConstants.CENTER), c);
		
		c.gridy++;
		add(gridProb7, c);
		
		// column 3
		
		c.gridx++;
		c.gridy = offsy;
		add(gridProb3, c);
		
		c.gridy++;
		add(gridProb5, c);
		
		c.gridy++;
		add(gridProb8, c);
	}
	
	private void drawHexGrid(int offsx, int offsy) {
		// row 1
		
		c.gridx = offsx + 1;
		c.gridy = offsy;
		add(gridProb2, c);
		
		c.gridx++;
		add(gridProb3, c);
		
		// Row 2
		
		c.gridx = offsx;
		c.gridy++;
		add(gridProb4, c);
		
		c.gridwidth = 2;
		
		c.gridx++;
		add(new JLabel("Fire", SwingConstants.CENTER), c);
		
		c.gridwidth = 1;
		
		c.gridx = offsx + 3;
		add(gridProb5, c);
		
		// row 3
		
		c.gridx = offsx + 1;
		c.gridy++;
		add(gridProb7, c);
		
		c.gridx++;
		add(gridProb8, c);
	}
	
	private void drawTriangleGrid(int offsx, int offsy) {
		// column 1
		
		c.gridx = offsx;
		c.gridy = offsy + 2;
		add(gridProb4, c);
		
		// column 2
		
		c.gridx++;
		c.gridy = offsy;
		add(gridProb2, c);
		
		c.gridy++;
		add(new JLabel("Fire", SwingConstants.CENTER), c);
		
		// column 3
		
		c.gridx++;
		c.gridy++;
		add(gridProb5, c);
		
		c.gridwidth = 3;
		c.gridx = offsx;
		c.gridy = offsy + 3;
		Dimension d = new Dimension(1, 5);
		JSeparator sep = new JSeparator();
		sep.setPreferredSize(d);
		add(sep, c);
		
		c.gridwidth = 1;
		
		// column 1
		
		c.gridx = offsx;
		c.gridy = offsy + 4;
		add(gridProb7, c);
		
		// column 2
		
		c.gridx++;
		c.gridy = offsy + 6;
		add(gridProb3, c);
		
		c.gridy--;
		add(new JLabel("Fire", SwingConstants.CENTER), c);
		
		// column 3
		
		c.gridx++;
		c.gridy = offsy + 4;
		add(gridProb6, c);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel#generate()
	 */
	@Override
	public NBAction generate() {
		return new NBAction(null);
	}
}
