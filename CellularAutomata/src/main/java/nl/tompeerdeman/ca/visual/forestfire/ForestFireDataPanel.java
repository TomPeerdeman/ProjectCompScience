package nl.tompeerdeman.ca.visual.forestfire;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Formatter;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;
import nl.tompeerdeman.ca.forestfire.ForestFire;
import nl.tompeerdeman.ca.forestfire.ForestFireData;
import nl.tompeerdeman.ca.visual.SimulateControlPanel;
import nl.tompeerdeman.ca.visual.SimulateController;

public class ForestFireDataPanel extends JPanel implements SimulateChangeListener, SimulateController{
	private static final long serialVersionUID = -7218172283495918517L;

	private Simulator sim;
	private ForestFireData data;
	private ForestFire fire;
	
	private SimulateControlPanel control;
	
	private JLabel tick;
	private JLabel burnt;
	private JLabel burning;
	private JLabel veg;
	private JLabel barren;
	private JLabel densityText;
	private JTextField density;
	private JLabel windText;
	private JComboBox<String> wind;
	private JLabel fracBurned;
	private JLabel oppReached;
	
	public ForestFireDataPanel(ForestFire fire){
		this.fire = fire;
		sim = fire.getSimulator();
		data = (ForestFireData) sim.getData();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 10;
		c.ipady = 0;
		
		tick = new JLabel();
		burnt = new JLabel();
		burning = new JLabel();
		veg = new JLabel();
		barren = new JLabel();
		densityText = new JLabel("Density: ");
		density = new JTextField("0.5");
		windText = new JLabel("Neigborhood: ");
		
		fracBurned = new JLabel("Fraction burned: 0.0");
		oppReached = new JLabel("Opposite reached: false");
		
		String windStr[] = {"Von Neumann", "Moore", "Wind up Neumann",
				"Wind left Neumann", "Wind right Neumann", "Wind up Moore",
				"Wind left Moore",	"Wind right Moore"};
		wind = new JComboBox<String> (windStr);
		wind.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				updateNb();
			}
		});
		
		c.gridx = 0;
		c.gridy = 0;
		add(tick, c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(burnt, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(burning, c);
		
		c.gridx = 0;
		c.gridy = 3;
		add(veg, c);
		
		c.gridx = 0;
		c.gridy = 4;
		add(barren, c);
		
		c.gridx = 0;
		c.gridy = 5;
		add(fracBurned, c);
		
		c.gridx = 0;
		c.gridy = 6;
		add(oppReached, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 1;
		c.gridy = 0;
		add(windText);
		
		c.gridx = 1;
		c.gridy = 1;
		add(wind, c);
		
		c.gridx = 1;
		c.gridy = 2;
		add(densityText, c);
		
		c.gridx = 1;
		c.gridy = 3;
		add(density, c);
		
		control = new SimulateControlPanel(fire, this, this, 2, 0);
		
		simulationUpdated(sim);
	}
	
	@Override
	public void simulationUpdated(Simulator sim){
		tick.setText("Tick: " + sim.getTick());
		burnt.setText("Burnt: " + data.burnt);
		burning.setText("Burning: " + data.burning);
		veg.setText("Vegetation: " + data.vegetation);
		barren.setText("Barren: " + data.barren);
		
		Formatter format = new Formatter();
		format = format.format(Locale.US, "%.2f", (double) data.burnt / (double) (data.vegetation + data.burnt));
		
		fracBurned.setText("Fraction burned: " + format.toString());
		oppReached.setText("Opposite reached: " + ((data.reachedOpposite) ? "true" : "false"));
		
		if(data.burning == 0){
			control.stop();
		}
	}
	
	public boolean onRandomize(){
		try{
			double ddensity =  Double.parseDouble(density.getText());
			if(ddensity < 0 || ddensity > 1.0){
				throw new NumberFormatException();
			}
			fire.randomizeGrid(ddensity, 0);
			fire.igniteGrid();
		}catch(NumberFormatException e){
			density.setText("0.5");
			return false;
		}
		return true;
	}
	
	public boolean onReset(){
		fire.resetGrid();
		return true;
	}
	
	public void updateNb(){
		int i = wind.getSelectedIndex();
		switch(i){
			case 0:
				data.setNb(ForestFireData.NB_NEUMANN);
				break;
			case 1:
				data.setNb(ForestFireData.NB_MOORE);
				break;
			case 2:
				data.setNb(ForestFireData.NB_WIND_UP_N);
				break;
			case 3:
				data.setNb(ForestFireData.NB_WIND_LEFT_N);
				break;
			case 4:
				data.setNb(ForestFireData.NB_WIND_RIGHT_N);
				break;
			case 5:
				data.setNb(ForestFireData.NB_WIND_UP_M);
				break;
			case 6:
				data.setNb(ForestFireData.NB_WIND_LEFT_M);
				break;
			case 7:
				data.setNb(ForestFireData.NB_WIND_RIGHT_M);
		}
	}

	@Override
	public boolean onStart(){
		return true;
	}

	@Override
	public boolean onPause(){
		return true;
	}

	@Override
	public boolean onStop(){
		return true;
	}
}
