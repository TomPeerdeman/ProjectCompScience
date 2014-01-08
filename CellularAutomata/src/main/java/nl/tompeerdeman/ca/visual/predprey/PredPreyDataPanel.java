package nl.tompeerdeman.ca.visual.predprey;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;
import nl.tompeerdeman.ca.predprey.PredPreyData;
import nl.tompeerdeman.ca.predprey.PredPreySystem;
import nl.tompeerdeman.ca.visual.SimulateControlPanel;
import nl.tompeerdeman.ca.visual.SimulateController;

public class PredPreyDataPanel extends JPanel implements SimulateChangeListener, SimulateController{
	private static final long serialVersionUID = 8515623085359951365L;

	private SimulateControlPanel control;
	private PredPreySystem sys;
	private Simulator sim;
	private PredPreyData data;
	private long seed;
	
	private JLabel tick;
	private JLabel pred;
	private JLabel prey;
	
	private JLabel predStartText;
	private JLabel preyStartText;
	private JTextField predStart;
	private JTextField preyStart;
	
	private JLabel predLoseText;
	private JLabel preyGainText;
	private JTextField predLose;
	private JTextField preyGain;
	
	private JLabel predSplitText;
	private JLabel preySplitText;
	private JTextField predSplit;
	private JTextField preySplit;
	
	private JLabel predStartEText;
	private JLabel preyStartEText;
	private JTextField predStartE;
	private JTextField preyStartE;
	
	public PredPreyDataPanel(PredPreySystem sys){
		this.sys = sys;
		sim = sys.getSimulator();
		data = (PredPreyData) sim.getData();
		
		seed = 1L;
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 10;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		
		tick = new JLabel();
		pred = new JLabel();
		prey = new JLabel();
		
		predStartText = new JLabel("Num predator start");
		preyStartText = new JLabel("Num prey start");	
		predStart = new JTextField("100");
		preyStart = new JTextField("100");
		
		predLoseText = new JLabel("Predator energy lose");
		preyGainText = new JLabel("Prey energy gain");	
		predLose = new JTextField("5.0");
		preyGain = new JTextField("3.0");
		
		predSplitText = new JLabel("Predator split tresh");
		preySplitText = new JLabel("Prey split tresh");	
		predSplit = new JTextField("200.0");
		preySplit = new JTextField("200.0");
		
		predStartEText = new JLabel("Pred start energy");
		preyStartEText = new JLabel("Prey start energy");	
		predStartE = new JTextField("100.0");
		preyStartE = new JTextField("100.0");
		
		add(tick, c);
		
		c.gridy++;
		add(pred, c);
		
		c.gridy++;
		add(prey, c);
		
		
		c.gridy++;
		add(predStartText, c);
		
		c.gridy++;
		add(predStart, c);
		
		c.gridy++;
		add(preyStartText, c);
		
		c.gridy++;
		add(preyStart, c);
		
		c.gridy++;
		add(predStartEText, c);
		
		c.gridy++;
		add(predStartE, c);
		
		c.gridy++;
		add(preyStartEText, c);
		
		c.gridy++;
		add(preyStartE, c);
		
		c.gridy = 0;
		c.gridx++;
		
		add(predLoseText, c);
		
		c.gridy++;
		add(predLose, c);
		
		c.gridy++;
		add(preyGainText, c);
		
		c.gridy++;
		add(preyGain, c);
		
		c.gridy++;
		add(predSplitText, c);
		
		c.gridy++;
		add(predSplit, c);
		
		c.gridy++;
		add(preySplitText, c);
		
		c.gridy++;
		add(preySplit, c);
		
		control = new SimulateControlPanel(sys, this, this, 2, 0);
		
		simulationUpdated(sim);
	}
	
	@Override
	public boolean onStart(){
		return true;
	}
	
	@Override
	public boolean onPause(){
		return true;
	}
	
	private int parseInt(JTextField field, int def){
		int i;
		
		try{
			i = Integer.parseInt(field.getText());
			if(i < 0){
				throw new NumberFormatException();
			}
			field.setText("" + i);
		}catch(NumberFormatException e){
			field.setText("" + def);
			i = -1;
		}
		
		return i;
	}
	
	private double parseDouble(JTextField field, double def){
		double d;
		
		try{
			d = Double.parseDouble(field.getText());
			if(d < 0){
				throw new NumberFormatException();
			}
			field.setText("" + d);
		}catch(NumberFormatException e){
			field.setText("" + def);
			d = -1;
		}
		
		return d;
	}
	
	@Override
	public boolean onReset(){
		int pred = parseInt(predStart, 100);
		int prey = parseInt(preyStart, 100);
		
		double dPredLose = parseDouble(predLose, 5.0);
		double dPreygain = parseDouble(preyGain, 3.0);
		double dPredSplit = parseDouble(predSplit, 200.0);
		double dPreySplit = parseDouble(preySplit, 200.0);
		
		double dPredStartE = parseDouble(predStartE, 100.0);
		double dPreyStartE = parseDouble(preyStartE, 100.0);
		
		if(pred < 0 || prey < 0 || dPredLose < 0 || dPreygain < 0
				|| dPredSplit < 0 || dPreySplit < 0
				|| dPredStartE < 0 || dPreyStartE < 0){
			return false;
		}
		
		data.predEnergyDecrease = dPredLose;
		data.preyEnergyIncrease = dPreygain;
		data.predSplitTreshold = dPredSplit;
		data.preySplitTreshold = dPreySplit;
		
		sys.randomizeGrid(prey, pred, dPreyStartE, dPredStartE, seed);
		return true;
	}
	
	@Override
	public boolean onRandomize(){
		seed++;

		return onReset();
	}
	
	@Override
	public boolean onStop(){
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void simulationUpdated(Simulator sim){
		tick.setText("Tick: " + sim.getTick());
		pred.setText("predator: " + data.npred);
		prey.setText("Prey: " + data.nprey);
		
		if(data.npred == 0 || data.nprey == 0){
			control.stop();
		}
	}
	
}
