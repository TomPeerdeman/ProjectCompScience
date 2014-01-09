package nl.uva.ca.visual.forestfire;

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
import javax.swing.SwingConstants;

import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;
import nl.tompeerdeman.ca.forestfire.ForestFire;
import nl.tompeerdeman.ca.forestfire.ForestFireData;
import nl.tompeerdeman.ca.visual.SimulateController;
import nl.uva.ca.visual.ExSimulateControlPanel;

public class ExForestFireDataPanel extends JPanel implements
		SimulateChangeListener, SimulateController {
	private static final long serialVersionUID = 9104081158410085080L;
	
	private Simulator sim;
	private ForestFireData data;
	private ForestFire fire;
	
	private ExSimulateControlPanel control;
	
	private JLabel tick;
	private JLabel burnt;
	private JLabel burning;
	private JLabel veg;
	private JLabel barren;
	private JLabel densityText;
	private JLabel density2Text;
	private JLabel tempText;
	private JLabel typeText;
	private JLabel nbText;
	private JLabel windText;
	private JLabel firefighters;
	private JLabel fftreshText;
	private JLabel ffextText;
	private JLabel randwater;
	private JLabel height;
	private JLabel fracBurned;
	// oppReached to be removed later
	private JLabel oppReached;
	
	private JComboBox<String> gridtype;
	private JComboBox<String> wind;
	private JComboBox<String> nb;
	private JComboBox<String> waterCheck;
	private JComboBox<String> ffCheck;
	
	private JTextField density;
	private JTextField density2;
	private JTextField temp;
	private JTextField fftresh;
	private JTextField ffext;
	
	public ExForestFireDataPanel(ForestFire fire) {
		this.fire = fire;
		sim = fire.getSimulator();
		data = (ForestFireData) sim.getData();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 0;
		
		tick = new JLabel();
		burnt = new JLabel();
		burning = new JLabel();
		veg = new JLabel();
		barren = new JLabel();
		densityText = new JLabel("Tree Density: ", SwingConstants.LEFT);
		density2Text = new JLabel("Bush Density: ");
		tempText = new JLabel("Temperature: ");
		density = new JTextField("0.3");
		density2 = new JTextField("0.3");
		fftresh = new JTextField("0.3");
		ffext = new JTextField("0.3");
		temp = new JTextField("18");
		typeText = new JLabel("Grid Type: ");
		nbText = new JLabel("Neigbourhood: ");
		windText = new JLabel("Wind Direction: ");
		firefighters = new JLabel("Firefighters:");
		fftreshText = new JLabel("Firefighter treshold:");
		ffextText = new JLabel("Extinguish Probability:");
		randwater = new JLabel("Generate random water:");
		height = new JLabel("height");
		
		fracBurned = new JLabel("Fraction burned: 0.0");
		oppReached = new JLabel("Opposite reached: false");
		
		//TODO change windStr to windDirectionStr <intentionally left this way to neot murder the program
		
		String windStr[] =
		{"Von Neumann", "Moore", "Wind up Neumann",
			"Wind left Neumann", "Wind right Neumann", "Wind up Moore",
			"Wind left Moore", "Wind right Moore"};
		
		String windDirectionsStr[] =
		{"Up", "Right","Down","Left"};

		String nbStr[] =
				{"Neumann", "Moore", "Extended Neumann"};

		String GridStr[] =
			{"Standard", "Hexagonal", "Triangular"};
		
		String YesNoStr[] =
			{"Yes", "No"};

		gridtype = new JComboBox<String>(GridStr);
		wind = new JComboBox<String>(windStr);
		nb = new JComboBox<String>(nbStr);
		waterCheck = new JComboBox<String>(YesNoStr);
		ffCheck = new JComboBox<String>(YesNoStr);
		wind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
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
		add(densityText);
		
		c.gridx = 1;
		c.gridy = 1;
		add(density2Text, c);
		
		c.gridx = 1;
		c.gridy = 2;
		add(tempText, c);
		
		c.gridx = 1;
		c.gridy = 3;
		add(randwater, c);
		
		c.gridx = 1;
		c.gridy = 4;
		add(firefighters, c);
		
		c.gridx = 1;
		c.gridy = 5;
		add(ffextText, c);
		
		c.gridx = 1;
		c.gridy = 6;
		add(fftreshText, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 2;
		c.gridy = 0;
		add(density);

		c.gridx = 2;
		c.gridy = 1;
		add(density2, c);

		c.gridx = 2;
		c.gridy = 2;
		add(temp, c);
		
		c.gridx = 2;
		c.gridy = 3;
		add(waterCheck, c);
		
		c.gridx = 2;
		c.gridy = 4;
		add(ffCheck, c);
		
		c.gridx = 2;
		c.gridy = 5;
		add(ffext, c);
		
		c.gridx = 2;
		c.gridy = 6;
		add(fftresh, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 3;
		c.gridy = 0;
		add(typeText);

		c.gridx = 3;
		c.gridy = 1;
		add(gridtype, c);
		
		c.gridx = 3;
		c.gridy = 2;
		add(windText, c);
		
		c.gridx = 3;
		c.gridy = 3;
		add(wind, c);
		
		c.gridx = 3;
		c.gridy = 4;
		add(nbText, c);
		
		c.gridx = 3;
		c.gridy = 5;
		add(nb, c);
				
		control = new ExSimulateControlPanel(fire, this, this, 2, 0);
		
		simulationUpdated(sim);
	}
	
	@Override
	public void simulationUpdated(Simulator sim) {
		tick.setText("Tick: " + sim.getTick());
		burnt.setText("Burnt: " + data.burnt);
		burning.setText("Burning: " + data.burning);
		veg.setText("Vegetation: " + data.vegetation);
		barren.setText("Barren: " + data.barren);
		
		@SuppressWarnings("resource")
		Formatter format = new Formatter();
		format =
			format.format(Locale.US, "%.2f", (double) data.burnt
					/ (double) (data.vegetation + data.burnt));
		
		fracBurned.setText("Fraction burned: " + format.toString());
		oppReached.setText("Opposite reached: "
				+ ((data.reachedOpposite) ? "true" : "false"));
		
		format.close();
		
		if(data.burning == 0) {
			control.stop();
		}
	}
	
	@Override
	public boolean onRandomize() {
		try {
			double ddensity = Double.parseDouble(density.getText());
			if(ddensity < 0 || ddensity > 1.0) {
				throw new NumberFormatException();
			}
			fire.randomizeGrid(ddensity, 0);
			fire.igniteGrid();
		} catch(NumberFormatException e) {
			density.setText("0.5");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean onReset() {
		fire.resetGrid();
		return true;
	}
	
	public void updateNb() {
		int i = wind.getSelectedIndex();
		switch(i) {
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
	public boolean onStart() {
		return true;
	}
	
	@Override
	public boolean onPause() {
		return true;
	}
	
	@Override
	public boolean onStop() {
		return true;
	}
}
