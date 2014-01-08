/**
 * Copyright 2012 Tom Peerdeman
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package nl.tompeerdeman.ca.visual;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nl.tompeerdeman.ca.DataSet;
import nl.tompeerdeman.ca.MainSystem;
import nl.tompeerdeman.ca.Simulator;
import nl.tompeerdeman.ca.TimedSimulator;

public class SimulateControlPanel {
	private int offsx;
	private int offsy;
	
	private Simulator sim;
	private DataSet data;
	private SimulateController controller;
	
	private JLabel delayText;
	private JTextField delay;
	private JButton start;
	private JButton randomize;
	private JButton reset;
	private JButton step;
	private JButton pause;
	
	public SimulateControlPanel(MainSystem sys, JPanel panel,
			SimulateController contr, int ox, int oy) {
		controller = contr;
		offsx = ox;
		offsy = oy;
		sim = sys.getSimulator();
		data = sim.getData();
		
		delayText = new JLabel("Simulation delay (ms): ");
		delay = new JTextField("10");
		
		start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				start();
			}
		});
		randomize = new JButton("Randomize");
		randomize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				randomize();
			}
		});
		reset = new JButton("Reset grid");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				reset();
			}
		});
		step = new JButton("Step");
		step.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				sim.simulateTick();
			}
		});
		pause = new JButton("Pause");
		pause.setEnabled(false);
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				pause();
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 10;
		c.ipady = 0;
		c.gridx = offsx;
		c.gridy = offsy;
		
		panel.add(start, c);
		
		c.gridy++;
		panel.add(pause, c);
		
		c.gridy++;
		panel.add(reset, c);
		
		c.gridy++;
		panel.add(randomize, c);
		
		c.gridy++;
		panel.add(step, c);
		
		c.gridy++;
		panel.add(delayText, c);
		
		c.gridy++;
		panel.add(delay, c);
	}
	
	public void start() {
		if(start.getText().equals("Start")) {
			start.setText("Stop");
			if(controller.onStart()) {
				try {
					long ldelay = Long.parseLong(delay.getText());
					
					if(ldelay < 0) {
						throw new NumberFormatException();
					}
					
					start.setEnabled(true);
					randomize.setEnabled(false);
					reset.setEnabled(false);
					step.setEnabled(false);
					pause.setEnabled(true);
					
					((TimedSimulator) sim).setDelay(ldelay);
					
					sim.start();
				} catch(NumberFormatException e) {
					delay.setText("10");
				}
			}
		} else {
			start.setText("Start");
			stop();
		}
	}
	
	public void pause() {
		if(controller.onPause()) {
			if(pause.getText().equals("Pause")) {
				pause.setText("Unpause");
			} else {
				pause.setText("Pause");
			}
			sim.pause();
		}
	}
	
	public void stop() {
		if(controller.onStop()) {
			sim.stop();
			randomize.setEnabled(true);
			reset.setEnabled(true);
			pause.setEnabled(false);
			pause.setText("Pause");
			start.setText("Start");
			start.setEnabled(false);
			step.setEnabled(true);
		}
	}
	
	public void randomize() {
		if(controller.onRandomize()) {
			start.setEnabled(true);
			sim.reset();
			data.reset();
			sim.afterSimulateTick();
		}
	}
	
	public void reset() {
		if(controller.onReset()) {
			start.setEnabled(true);
			sim.reset();
			data.reset();
			sim.afterSimulateTick();
		}
	}
}
