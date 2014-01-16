/**
 * File: StartFireGenerator.java
 * 
 */
package nl.uva.ca.visual.trigger.forestfire;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import nl.uva.ca.TriggerAction;
import nl.uva.ca.triggers.StartFireAction;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;

/**
 *
 */
public class StartFireGenerator extends
		TriggerActionGeneratorPanel<StartFireAction> implements ActionListener {
	private static final long serialVersionUID = 1722749930370912441L;
	
	private GridBagConstraints c;
	private JCheckBox singlePointCheckbox;
	private JTextField xUpperField;
	private JTextField yUpperField;
	private JTextField xLowerField;
	private JTextField yLowerField;
	private JTextField nPointsField;
	
	/**
	 * 
	 */
	public StartFireGenerator() {
		super();
	}
	
	/**
	 * @param parent
	 */
	public StartFireGenerator(TriggerAction parent) {
		super(parent);
		if(parent instanceof StartFireAction) {
			StartFireAction a = (StartFireAction) parent;
			if(a.isSinglePoint()) {
				guiSinglePoint();
				xLowerField.setText(String.valueOf(a.getLowerPoint().x));
				yLowerField.setText(String.valueOf(a.getLowerPoint().y));
			} else {
				guiMultiPoint();
				xUpperField.setText(String.valueOf(a.getUpperPoint().x));
				yUpperField.setText(String.valueOf(a.getUpperPoint().y));
				
				xLowerField.setText(String.valueOf(a.getLowerPoint().x));
				yLowerField.setText(String.valueOf(a.getLowerPoint().y));
				
				nPointsField.setText(String.valueOf(a.getNumPoints()));
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
		setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		singlePointCheckbox = new JCheckBox("Single point");
		singlePointCheckbox.addActionListener(this);
		
		xUpperField = new JTextField("99");
		yUpperField = new JTextField("99");
		xLowerField = new JTextField("0");
		yLowerField = new JTextField("0");
		nPointsField = new JTextField("1");
		
		c.gridwidth = 1;
		c.gridy = 0;
		c.gridx = 2;
		add(xUpperField, c);
		
		c.gridx = 3;
		add(yUpperField, c);
		
		c.gridy++;
		c.gridx = 0;
		add(xLowerField, c);
		
		c.gridx = 1;
		add(yLowerField, c);
		
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 4;
		add(new JLabel("Num points"), c);
		
		c.gridy++;
		c.gridx = 0;
		add(nPointsField, c);
		
		c.gridy++;
		add(singlePointCheckbox, c);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel#generate()
	 */
	@Override
	public StartFireAction generate() {
		int[][] bounds = new int[2][2];
		
		bounds[0][0] = parseField(xLowerField, 0, 99);
		bounds[0][1] = parseField(yLowerField, 0, 99);
		if(bounds[0][0] < 0 || bounds[0][1] < 0)
			return null;
		if(xUpperField.isEnabled()) {
			bounds[1][0] = parseField(xUpperField, bounds[0][0], 99);
			bounds[1][1] = parseField(yUpperField, bounds[0][1], 99);
			if(bounds[1][0] < 0 || bounds[1][1] < 0)
				return null;
			
			int surface =
				(bounds[1][0] - bounds[0][0] + 1)
						* (bounds[1][1] - bounds[0][1] + 1);
			int n = parseField(nPointsField, 1, surface);
			if(n < 0)
				return null;
			
			return new StartFireAction(new Point(bounds[0][0], bounds[0][1]),
					new Point(bounds[1][0], bounds[1][1]), n);
		} else {
			return new StartFireAction(new Point(bounds[0][0], bounds[0][1]),
					new Point(-1, -1), 1);
		}
		
	}
	
	private int parseField(JTextField field, int low, int high) {
		try {
			int n = Integer.parseInt(field.getText());
			if(n < low || n > high) {
				throw new NumberFormatException();
			}
			
			field.setBorder(UIManager.getBorder("TextField.border"));
			return n;
		} catch(NumberFormatException e) {
			field.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		return -1;
	}
	
	private void guiSinglePoint() {
		singlePointCheckbox.setSelected(true);
		nPointsField.setText("1");
		nPointsField.setEnabled(false);
		
		xUpperField.setText("");
		xUpperField.setEnabled(false);
		yUpperField.setText("");
		yUpperField.setEnabled(false);
	}
	
	private void guiMultiPoint() {
		singlePointCheckbox.setSelected(false);
		nPointsField.setEnabled(true);
		
		xUpperField.setText(xLowerField.getText());
		xUpperField.setEnabled(true);
		yUpperField.setText(yLowerField.getText());
		yUpperField.setEnabled(true);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Single point yes/no.
		if(e.getSource() instanceof JCheckBox) {
			if(((JCheckBox) e.getSource()).isSelected()) {
				guiSinglePoint();
			} else {
				guiMultiPoint();
			}
		}
	}
}
