package nl.uva.ca.visual.forestfire;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import nl.tompeerdeman.ca.visual.SimulateController;

import nl.uva.ca.ExForestFire;
import nl.uva.ca.ExForestFireData;
import nl.uva.ca.visual.ExSimulateControlPanel;

public class ExForestFireDataPanel extends JPanel implements
		SimulateChangeListener, SimulateController {
	private static final long serialVersionUID = 9104081158410085080L;
	
	private Simulator sim;
	private ExForestFireData data;
	private ExForestFire fire;
	
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
	private JLabel firefighters;
	private JLabel fftreshText;
	private JLabel ffextText;
	private JLabel randwater;
	private JLabel fracBurned;
	private JLabel gridProbText;
	private JLabel treeBurningText;
	private JLabel bushBurningText;
	// oppReached to be removed later
	private JLabel oppReached;
	private JLabel height;
	
	private JComboBox<String> gridtype;
	private JComboBox<String> waterCheck;
	private JComboBox<String> ffCheck;
	
	private JTextField density;
	private JTextField density2;
	private JTextField temp;
	private JTextField fftresh;
	private JTextField ffext;
	private JTextField bushBurning;
	private JTextField treeBurning;
	private JTextField gridProb1;
	private JTextField gridProb2;
	private JTextField gridProb3;
	private JTextField gridProb4;
	private JTextField gridProb5;
	private JTextField gridProb6;
	private JTextField gridProb7;
	private JTextField gridProb8;
	private JLabel filler;
	
	public ExForestFireDataPanel(ExForestFire fire) {
		this.fire = fire;
		sim = fire.getSimulator();
		data = (ExForestFireData) sim.getData();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 5, 0, 5);
		
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
		gridProb1 = new JTextField("1");
		gridProb2 = new JTextField("2");
		gridProb3 = new JTextField("3");
		gridProb4 = new JTextField("4");
		gridProb5 = new JTextField("5");
		gridProb6 = new JTextField("6");
		gridProb7 = new JTextField("7");
		gridProb8 = new JTextField("8");
		filler = new JLabel("Fire");
		treeBurning = new JTextField("1");
		bushBurning = new JTextField("1");
		temp = new JTextField("18");
		typeText = new JLabel("Grid Type: ");
		firefighters = new JLabel("Firefighters:");
		fftreshText = new JLabel("Firefighter treshold:");
		ffextText = new JLabel("Extinguish Probability:");
		randwater = new JLabel("Generate random water:");
		gridProbText = new JLabel("Grid Fire transfer Probabilities:");
		height = new JLabel("Height");
		treeBurningText = new JLabel("Tree Burning steps:");
		bushBurningText = new JLabel("Bush Burning steps:");
		
		fracBurned = new JLabel("Fraction burned: 0.0");
		oppReached = new JLabel("Opposite reached: false");
		
		String GridStr[] =
		{"Standard", "Hexagonal", "Triangular"};
		
		String YesNoStr[] =
		{"Yes", "No"};
		
		gridtype = new JComboBox<String>(GridStr);
		waterCheck = new JComboBox<String>(YesNoStr);
		ffCheck = new JComboBox<String>(YesNoStr);
		
		// Standard no firefighters, set to false
		ffCheck.setSelectedIndex(1);
		fftresh.setEnabled(false);
		ffext.setEnabled(false);
		gridtype.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				updateType();
			}
		});
		waterCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				updateWater();
			}
		});
		ffCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				updateFirefighter();
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
		add(densityText, c);
		
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
		
		c.gridx = 2;
		c.gridy = 0;
		add(density, c);
		
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
		
		c.gridwidth = 2;
		
		c.gridx = 3;
		c.gridy = 0;
		add(treeBurningText, c);
		
		c.gridx = 3;
		c.gridy = 1;
		add(bushBurningText, c);
		
		c.gridx = 3;
		c.gridy = 2;
		add(typeText, c);
		
		c.gridwidth = 6;
		c.gridx = 3;
		c.gridy = 3;
		add(gridProbText, c);
		
		// probabilities start
		c.gridwidth = 1;
		
		// column 1
		c.weightx = 0.1;
		
		c.gridx = 3;
		c.gridy = 4;
		add(gridProb1, c);
		
		c.gridx = 3;
		c.gridy = 5;
		add(gridProb4, c);
		
		c.gridx = 3;
		c.gridy = 6;
		add(gridProb6, c);
		
		// column 2
		
		c.gridx = 4;
		c.gridy = 4;
		add(gridProb2, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		
		c.gridx = 4;
		c.gridy = 5;
		add(filler, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 4;
		c.gridy = 6;
		add(gridProb7, c);
		
		// column 3
		
		c.gridx = 5;
		c.gridy = 4;
		add(gridProb3, c);
		
		c.gridx = 5;
		c.gridy = 5;
		add(gridProb5, c);
		
		c.gridx = 5;
		c.gridy = 6;
		add(gridProb8, c);
		
		// probabilities end
		
		c.gridwidth = 2;
		
		c.gridx = 5;
		c.gridy = 0;
		add(treeBurning, c);
		
		c.gridx = 5;
		c.gridy = 1;
		add(bushBurning, c);
		
		c.gridx = 5;
		c.gridy = 2;
		add(gridtype, c);
		
		c.gridwidth = 1;
		
		control = new ExSimulateControlPanel(fire, this, this, 7, 0);
		
		simulationUpdated(sim);
	}
	
	@Override
	public void simulationUpdated(Simulator sim) {
		tick.setText("Tick: " + sim.getTick());
		burnt.setText("Burnt: " + data.burnt);
		burning.setText("Burning: " + data.burning);
		veg.setText("Vegetation: " + (data.bushes + data.trees));
		barren.setText("Barren: " + data.barren);
		
		@SuppressWarnings("resource")
		Formatter format = new Formatter();
		format =
			format.format(Locale.US, "%.2f", (double) data.burnt
					/ (double) (data.bushes + data.burnt + data.trees));
		
		fracBurned.setText("Fraction burned: " + format.toString());
		oppReached.setText("Opposite reached: maybe");
		
		format.close();
		
		if(data.burning == 0) {
			control.stop();
		}
	}
	
	@Override
	public boolean onRandomize() {
		try {
			double d = Double.parseDouble(density.getText());
			if(d < 0 || d > 1.0) {
				throw new NumberFormatException();
			}
			fire.treeDensity = d;
			
			d = Double.parseDouble(density2.getText());
			if(d < 0 || d > 1.0) {
				throw new NumberFormatException();
			}
			fire.bushDensity = d;
			
			fire.randomizeGrid(0);
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
	
	public void updateWater() {
		int i = waterCheck.getSelectedIndex();
		switch(i) {
			case 0:
				fire.randWater = true;
				break;
			case 1:
				fire.randWater = false;
				break;
		}
	}
	
	public void updateFirefighter() {
		int i = ffCheck.getSelectedIndex();
		switch(i) {
			case 0:
				fftresh.setEnabled(true);
				ffext.setEnabled(true);
				break;
			case 1:
				fftresh.setEnabled(false);
				ffext.setEnabled(false);
				break;
		
		}
	}
	
	public void updateType() {
		int i = gridtype.getSelectedIndex();
		switch(i) {
			case 0:
				System.out.println(gridProb1.getText());
				break;
			case 1:
				break;
			case 2:
				break;
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
