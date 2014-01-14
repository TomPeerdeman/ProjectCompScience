package nl.uva.ca.visual.forestfire;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Formatter;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;
import nl.tompeerdeman.ca.visual.SimulateController;

import nl.uva.ca.ExForestFire;
import nl.uva.ca.ExForestFireData;
import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerManager;
import nl.uva.ca.triggers.TriggerListRenderer;
import nl.uva.ca.visual.ExSimulateControlPanel;
import nl.uva.ca.visual.trigger.TriggerFrame;

public class ExForestFireDataPanel extends JPanel implements
		SimulateChangeListener, SimulateController, ActionListener {
	private static final long serialVersionUID = 9104081158410085080L;
	
	private Simulator sim;
	private ExForestFireData data;
	private ExForestFire fire;
	
	private ExSimulateControlPanel control;
	private GridBagConstraints c;
	
	private TriggerFrame triggerFrame;
	private TriggerManager triggerManager;
	
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
	private JLabel randwater;
	private JLabel fracBurned;
	// oppReached to be removed later
	private JLabel oppReached;
	private JLabel empty;
	
	private JComboBox<String> gridtype;
	private JComboBox<String> waterCheck;
	private JComboBox<String> ffCheck;
	
	private JTextField density;
	private JTextField density2;
	private JTextField temp;
	private JTextField fftresh;
	private JTextField ffext;
	private JTextField gridProb1;
	private JTextField gridProb2;
	private JTextField gridProb3;
	private JTextField gridProb4;
	private JTextField gridProb5;
	private JTextField gridProb6;
	private JTextField gridProb7;
	private JTextField gridProb8;
	private JLabel filler;
	
	private JButton triggerAddButton = new JButton("Add");
	private JButton triggerEditButton = new JButton("Edit");
	private JButton triggerDelButton = new JButton("Del");
	
	private DefaultListModel<Trigger> triggerModel;
	private JList<Trigger> triggerList;
	private JScrollPane triggerPane;
	
	public ExForestFireDataPanel(ExForestFire fire, TriggerManager tManager) {
		this.fire = fire;
		this.triggerManager = tManager;
		
		sim = fire.getSimulator();
		data = (ExForestFireData) sim.getData();
		
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
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
		empty = new JLabel(" ");
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
		temp = new JTextField("18");
		typeText = new JLabel("Grid Type: ");
		firefighters = new JLabel("Firefighters:");
		randwater = new JLabel("Generate random water:");
		
		triggerModel = new DefaultListModel<Trigger>();
		triggerList = new JList<Trigger>(triggerModel);
		triggerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		triggerList.setCellRenderer(new TriggerListRenderer(triggerManager));
		
		triggerPane = new JScrollPane(triggerList);
		triggerPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
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
		c.weightx = 1;
		
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
		
		add(typeText, c);
		
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
		add(gridtype, c);
		
		c.gridx = 3;
		c.gridy = 0;
		add(new JLabel("Triggers"), c);
		
		c.gridx = 4;
		c.gridy = 0;
		triggerAddButton.addActionListener(this);
		add(triggerAddButton, c);
		
		c.gridx = 5;
		c.gridy = 0;
		
		triggerEditButton.setEnabled(false);
		triggerEditButton.addActionListener(this);
		add(triggerEditButton, c);
		
		c.gridx = 6;
		c.gridy = 0;
		
		triggerDelButton.setEnabled(false);
		triggerDelButton.addActionListener(this);
		add(triggerDelButton, c);
		
		c.gridwidth = 4;
		c.gridheight = 6;
		c.gridx = 3;
		c.gridy = 1;
		add(triggerPane, c);
		
		c.gridheight = 1;
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
		
		triggerList.repaint();
	}
	
	@Override
	public boolean onRandomize() {
		try {
			triggerManager.reset();
			
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
			return false;
		}
		return true;
	}
	
	@Override
	public boolean onReset() {
		triggerManager.reset();
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
	
	public void drawStandardGrid() {
		// probabilities start
		c.gridwidth = 1;
		
		// column 1
		
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
		
		c.gridx = 6;
		c.gridy = 4;
		add(empty, c);
		
		c.gridx = 6;
		c.gridy = 5;
		add(empty, c);
		
		c.gridx = 6;
		c.gridy = 6;
		add(empty, c);
		
		// probabilities end
		
		c.gridwidth = 2;
	}
	
	public void updateType() {
		// String[] probabilities = {gridProb1.getText(),
		// gridProb2.getText(),gridProb3.getText(),gridProb4.getText(),gridProb5.getText(),gridProb6.getText(),gridProb7.getText(),gridProb8.getText()};
		int i = gridtype.getSelectedIndex();
		remove(gridProb1);
		remove(gridProb2);
		remove(gridProb3);
		remove(gridProb4);
		remove(gridProb5);
		remove(gridProb6);
		remove(gridProb7);
		remove(gridProb8);
		remove(filler);
		System.out.println(i);
		switch(i) {
			case 0:
				drawStandardGrid();
				revalidate();
				repaint();
				break;
			case 1:
				// probabilities start
				c.gridwidth = 1;
				
				// row 1
				
				c.gridx = 3;
				c.gridy = 4;
				add(empty, c);
				
				c.gridx = 4;
				c.gridy = 4;
				add(gridProb1, c);
				
				c.gridx = 5;
				c.gridy = 4;
				add(gridProb2, c);
				
				c.gridx = 6;
				c.gridy = 4;
				add(empty, c);
				
				// row 2
				c.gridwidth = 1;
				
				c.gridx = 3;
				c.gridy = 5;
				add(gridProb3, c);
				
				c.gridwidth = 2;
				
				c.fill = GridBagConstraints.VERTICAL;
				
				c.gridx = 4;
				c.gridy = 5;
				add(filler, c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				
				c.gridwidth = 1;
				
				c.gridx = 6;
				c.gridy = 5;
				add(gridProb4, c);
				
				// row 3
				
				c.gridx = 3;
				c.gridy = 6;
				add(empty, c);
				
				c.gridx = 4;
				c.gridy = 6;
				add(gridProb5, c);
				
				c.gridx = 5;
				c.gridy = 6;
				add(gridProb6, c);
				
				c.gridx = 6;
				c.gridy = 6;
				add(empty, c);
				
				// probabilities end
				
				c.gridwidth = 2;
				revalidate();
				repaint();
				break;
			case 2:
				// probabilities start
				c.gridwidth = 1;
				
				// column 1
				
				c.gridx = 3;
				c.gridy = 4;
				add(empty, c);
				
				c.gridx = 3;
				c.gridy = 5;
				add(empty, c);
				
				c.gridx = 3;
				c.gridy = 6;
				add(gridProb2, c);
				
				// column 2
				
				c.gridx = 4;
				c.gridy = 4;
				add(gridProb1, c);
				
				c.fill = GridBagConstraints.VERTICAL;
				
				c.gridx = 4;
				c.gridy = 5;
				add(filler, c);
				
				c.fill = GridBagConstraints.HORIZONTAL;
				
				c.gridx = 4;
				c.gridy = 6;
				add(empty, c);
				
				// column 3
				
				c.gridx = 5;
				c.gridy = 4;
				add(empty, c);
				
				c.gridx = 5;
				c.gridy = 5;
				add(empty, c);
				
				c.gridx = 5;
				c.gridy = 6;
				add(gridProb3, c);
				
				c.gridx = 6;
				c.gridy = 4;
				add(empty, c);
				
				c.gridx = 6;
				c.gridy = 5;
				add(empty, c);
				
				c.gridx = 6;
				c.gridy = 6;
				add(empty, c);
				
				// probabilities end
				
				c.gridwidth = 2;
				
				revalidate();
				repaint();
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int idx = triggerList.getSelectedIndex();
		if(e.getSource() != triggerAddButton
				&& (idx < 0 || idx >= triggerManager.triggers.size())) {
			return;
		}
		
		if(e.getSource() == triggerAddButton) {
			if(triggerFrame != null) {
				triggerFrame.dispose();
			}
			triggerFrame = new TriggerFrame("Add new trigger", this);
		} else if(e.getSource() == triggerEditButton) {
			if(triggerFrame != null) {
				triggerFrame.dispose();
			}
			triggerFrame = new TriggerFrame(idx, triggerModel.get(idx), this);
		} else if(e.getSource() == triggerDelButton) {
			if(triggerFrame != null) {
				triggerFrame.dispose();
			}
			triggerModel.remove(idx);
			triggerManager.triggers.remove(idx);
			if(triggerManager.triggers.size() == 0) {
				triggerEditButton.setEnabled(false);
				triggerDelButton.setEnabled(false);
			} else if(idx == triggerManager.triggers.size()) {
				triggerList.setSelectedIndex(idx - 1);
			}
		}
	}
	
	public void onNewTrigger(Trigger newTrigger) {
		int idx = triggerModel.size();
		triggerModel.add(idx, newTrigger);
		triggerManager.triggers.add(idx, newTrigger);
		triggerEditButton.setEnabled(true);
		triggerDelButton.setEnabled(true);
		triggerList.setSelectedIndex(idx);
	}
	
	public void onEditTrigger(int idx, Trigger newTrigger) {
		triggerModel.set(idx, newTrigger);
		triggerManager.triggers.set(idx, newTrigger);
	}
	
	public ExForestFire getForestFire() {
		return fire;
	}
}
