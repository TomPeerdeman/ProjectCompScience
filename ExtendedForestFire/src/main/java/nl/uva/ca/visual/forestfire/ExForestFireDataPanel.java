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
	private JLabel height;
	private JLabel fracBurned;
	private JLabel gridProbText;
	private JLabel treeBurningText;
	private JLabel bushBurningText;
	// oppReached to be removed later
	private JLabel oppReached;
	
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
		
		// old string[]
		
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
		
		c.gridx = 3;
		c.gridy = 0;
		add(treeBurningText, c);
		
		c.gridx = 3;
		c.gridy = 1;
		add(bushBurningText, c);
		
		c.gridx = 3;
		c.gridy = 2;
		add(typeText, c);

	    //c.gridwidth = 2;
		c.gridx = 3;
		c.gridy = 3;
		add(gridProbText, c);
	    //c.gridwidth = 1;
		
		c.gridx = 4;
		c.gridy = 0;
		add(treeBurning, c);
		
		c.gridx = 4;
		c.gridy = 1;
		add(bushBurning, c);
		
		c.gridx = 4;
		c.gridy = 2;
		add(gridtype, c);
				
		control = new ExSimulateControlPanel(fire, this, this, 5, 0);
		
		simulationUpdated(sim);
	}
	
	@Override
	public void simulationUpdated(Simulator sim) {
		tick.setText("Tick: " + sim.getTick());
		burnt.setText("Burnt: " + data.burnt);
		burning.setText("Burning: " + data.burning);
		veg.setText("Vegetation: " + (data.vegetation + data.trees));
		barren.setText("Barren: " + data.barren);
		
		@SuppressWarnings("resource")
		Formatter format = new Formatter();
		format =
			format.format(Locale.US, "%.2f", (double) data.burnt
					/ (double) (data.vegetation + data.burnt + data.trees));
		
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
				break;
			case 1:
				System.out.println("We don't need no water");
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
