/**
 * File: NBActionGenerator.java
 * 
 */
package nl.uva.ca.visual.trigger.forestfire;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
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
	private JTextField[] gridProb;
	private int type;
	
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
		
		if(parent instanceof NBAction) {
			NBAction nba = (NBAction) parent;
			
			int type = nba.getType();
			double[][] grid = nba.getNb();
			if(type == 2) {
				gridProb[1].setText(String.valueOf(grid[0][1]));
				gridProb[3].setText(String.valueOf(grid[1][0]));
				gridProb[4].setText(String.valueOf(grid[1][2]));
				
				gridProb[6].setText(String.valueOf(grid[2][0]));
				gridProb[5].setText(String.valueOf(grid[2][2]));
				gridProb[2].setText(String.valueOf(grid[3][1]));
			} else if(type == 1) {
				gridProb[1].setText(String.valueOf(grid[0][1]));
				gridProb[2].setText(String.valueOf(grid[0][2]));
				
				gridProb[3].setText(String.valueOf(grid[1][0]));
				gridProb[4].setText(String.valueOf(grid[1][2]));
				
				gridProb[6].setText(String.valueOf(grid[2][1]));
				gridProb[7].setText(String.valueOf(grid[2][2]));
			} else if(type == 0) {
				gridProb[0].setText(String.valueOf(grid[0][0]));
				gridProb[1].setText(String.valueOf(grid[0][1]));
				gridProb[2].setText(String.valueOf(grid[0][2]));
				
				gridProb[3].setText(String.valueOf(grid[1][0]));
				gridProb[4].setText(String.valueOf(grid[1][2]));
				
				gridProb[5].setText(String.valueOf(grid[2][0]));
				gridProb[6].setText(String.valueOf(grid[2][1]));
				gridProb[7].setText(String.valueOf(grid[2][2]));
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel#init()
	 */
	@Override
	public void init() {
		type = -1;
		
		setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		gridProb = new JTextField[8];
		
		gridProb[0] = new JTextField("0.1");
		gridProb[1] = new JTextField("0.1");
		gridProb[2] = new JTextField("0.1");
		gridProb[3] = new JTextField("0.1");
		gridProb[4] = new JTextField("0.1");
		gridProb[5] = new JTextField("0.1");
		gridProb[6] = new JTextField("0.1");
		gridProb[7] = new JTextField("0.1");
	}
	
	public void setGridType(int type) {
		this.type = type;
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
		add(gridProb[0], c);
		
		c.gridy++;
		add(gridProb[3], c);
		
		c.gridy++;
		add(gridProb[5], c);
		
		// column 2
		
		c.gridx++;
		c.gridy = offsy;
		add(gridProb[1], c);
		
		c.gridy++;
		add(new JLabel("Fire", SwingConstants.CENTER), c);
		
		c.gridy++;
		add(gridProb[6], c);
		
		// column 3
		
		c.gridx++;
		c.gridy = offsy;
		add(gridProb[2], c);
		
		c.gridy++;
		add(gridProb[4], c);
		
		c.gridy++;
		add(gridProb[7], c);
	}
	
	private void drawHexGrid(int offsx, int offsy) {
		// row 1
		
		c.gridx = offsx + 1;
		c.gridy = offsy;
		add(gridProb[1], c);
		
		c.gridx++;
		add(gridProb[2], c);
		
		// Row 2
		
		c.gridx = offsx;
		c.gridy++;
		add(gridProb[3], c);
		
		c.gridwidth = 2;
		
		c.gridx++;
		add(new JLabel("Fire", SwingConstants.CENTER), c);
		
		c.gridwidth = 1;
		
		c.gridx = offsx + 3;
		add(gridProb[4], c);
		
		// row 3
		
		c.gridx = offsx + 1;
		c.gridy++;
		add(gridProb[6], c);
		
		c.gridx++;
		add(gridProb[7], c);
	}
	
	private void drawTriangleGrid(int offsx, int offsy) {
		// column 1
		
		c.gridx = offsx;
		c.gridy = offsy + 2;
		add(gridProb[3], c);
		
		// column 2
		
		c.gridx++;
		c.gridy = offsy;
		add(gridProb[1], c);
		
		c.gridy++;
		add(new JLabel("Fire", SwingConstants.CENTER), c);
		
		// column 3
		
		c.gridx++;
		c.gridy++;
		add(gridProb[4], c);
		
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
		add(gridProb[6], c);
		
		// column 2
		
		c.gridx++;
		c.gridy = offsy + 6;
		add(gridProb[2], c);
		
		c.gridy--;
		add(new JLabel("Fire", SwingConstants.CENTER), c);
		
		// column 3
		
		c.gridx++;
		c.gridy = offsy + 4;
		add(gridProb[5], c);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel#generate()
	 */
	@Override
	public NBAction generate() {
		double[] gridValues = new double[8];
		boolean yay = true;
		for(int i = 0; i < 8; i++) {
			try {
				gridValues[i] = Double.parseDouble(gridProb[i].getText());
				if(gridValues[i] > 1.0 || gridValues[i] < 0.0) {
					throw new NumberFormatException();
				}
			} catch(NumberFormatException e) {
				gridProb[i].setBorder(BorderFactory.createLineBorder(Color.RED));
				yay = false;
			}
		}
		
		if(!yay)
			return null;
		
		double[][] grid;
		if(type == 0) {
			grid = new double[3][3];
			
			grid[0][0] = gridValues[0];
			grid[0][1] = gridValues[1];
			grid[0][2] = gridValues[2];
			
			grid[1][0] = gridValues[3];
			grid[1][2] = gridValues[4];
			
			grid[2][0] = gridValues[5];
			grid[2][1] = gridValues[6];
			grid[2][2] = gridValues[7];
		} else if(type == 1) {
			grid = new double[3][3];
			
			grid[0][1] = gridValues[1];
			grid[0][2] = gridValues[2];
			
			grid[1][0] = gridValues[3];
			grid[1][2] = gridValues[4];
			
			grid[2][1] = gridValues[6];
			grid[2][2] = gridValues[7];
		} else if(type == 2) {
			grid = new double[4][3];
			
			grid[0][1] = gridValues[1];
			grid[1][0] = gridValues[3];
			grid[1][2] = gridValues[4];
			
			grid[2][0] = gridValues[6];
			grid[2][2] = gridValues[5];
			grid[3][1] = gridValues[2];
		} else {
			return null;
		}
		
		return new NBAction(grid, type);
	}
}
