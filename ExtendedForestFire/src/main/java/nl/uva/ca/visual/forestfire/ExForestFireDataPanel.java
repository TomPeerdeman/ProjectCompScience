package nl.uva.ca.visual.forestfire;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
	private JLabel typeText;
	private JLabel randwater;
	private JLabel fracBurned;
	// oppReached to be removed later
	private JLabel oppReached;
	
	private JComboBox<String> gridtype;
	private JComboBox<String> waterCheck;
	
	private JTextField density;
	private JTextField density2;
	private JTextField fftresh;
	private JTextField ffext;
	
	private JButton triggerAddButton = new JButton("Add");
	private JButton triggerEditButton = new JButton("Edit");
	private JButton triggerDelButton = new JButton("Del");
	private JButton saveTriggersButton = new JButton("Save");
	private JButton loadTriggersButton = new JButton("Load");
	
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
		density = new JTextField("0.3");
		density2 = new JTextField("0.3");
		fftresh = new JTextField("0.3");
		ffext = new JTextField("0.3");
		typeText = new JLabel("Grid Type: ");
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
		
		// Standard no firefighters, set to false
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
		add(randwater, c);
		
		c.gridx = 1;
		c.gridy = 3;
		
		add(typeText, c);
		
		c.gridy = 5;
		add(new JLabel("Trigger list actions:"), c);
		
		c.gridx = 2;
		c.gridy = 0;
		add(density, c);
		
		c.gridx = 2;
		c.gridy = 1;
		add(density2, c);
		
		c.gridx = 2;
		c.gridy = 2;
		add(waterCheck, c);
		
		c.gridx = 2;
		c.gridy = 3;
		add(gridtype, c);
		
		c.gridy = 5;
		saveTriggersButton.addActionListener(this);
		add(saveTriggersButton, c);
		
		c.gridy = 6;
		loadTriggersButton.addActionListener(this);
		add(loadTriggersButton, c);
		
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
			if(d < 0) {
				d = 0;
				density.setText("0.0");
			}
			else if(d > 1.0) {
				d = 1.0;
				density.setText("1.0");
			}
			fire.treeDensity = d;
			
			d = Double.parseDouble(density2.getText());
			if(d < 0) {
				d = 0;
				density2.setText("0.0");
			}
			else if(d > 1.0) {
				d = 1.0;
				density2.setText("1.0");
			}
			fire.bushDensity = d;
			
			d = Double.parseDouble(density.getText());
			double d2 = Double.parseDouble(density2.getText());
			if((d + d2) > 1.0) {
				double x = d + d2 - 1.0;
				d = d - x / 2;
				d2 = d2 - x / 2;
				density.setText(Double.toString(d));
				density2.setText(Double.toString(d2));
				fire.treeDensity = d;
				fire.bushDensity = d2;
			}
			
			fire.randomizeGrid(0);
			fire.igniteGrid();
		} catch(NumberFormatException e) {
			return false;
		}
		enableDropdowns();
		return true;
	}
	
	@Override
	public boolean onReset() {
		triggerManager.reset();
		fire.resetGrid();
		enableDropdowns();
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
	
	public void updateType() {
		int i = gridtype.getSelectedIndex();
		switch(i) {
			case 0:
			case 1:
			case 2:
				fire.type = i;
				data.type = i;
				onRandomize();
				control.reset();
				
				revalidate();
				repaint();
				
				break;
		}
		
		if(triggerFrame != null) {
			triggerFrame.dispose();
		}
	}
	
	@Override
	public boolean onStart() {
		triggerAddButton.setEnabled(false);
		triggerEditButton.setEnabled(false);
		triggerDelButton.setEnabled(false);
		disableDropdowns();
		return true;
	}
	
	@Override
	public boolean onPause() {
		return true;
	}
	
	@Override
	public boolean onStop() {
		triggerAddButton.setEnabled(true);
		if(triggerManager.triggers.size() > 0) {
			triggerEditButton.setEnabled(true);
			triggerDelButton.setEnabled(true);
		}
		enableDropdowns();
		return true;
	}
	
	public void enableDropdowns() {
		gridtype.setEnabled(true);
		waterCheck.setEnabled(true);
	}
	
	public void disableDropdowns() {
		gridtype.setEnabled(false);
		waterCheck.setEnabled(false);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == saveTriggersButton) {
			try {
				triggerManager.save(this);
			} catch(IOException e1) {
				e1.printStackTrace();
			}
		} else if(e.getSource() == loadTriggersButton) {
			try {
				triggerManager.load(this, triggerModel);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		} else {
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
				triggerFrame =
					new TriggerFrame(idx, triggerModel.get(idx), this);
			} else if(e.getSource() == triggerDelButton) {
				if(triggerFrame != null) {
					triggerFrame.dispose();
				}
				triggerModel.remove(idx);
				triggerManager.triggers.remove(idx);
				if(idx == triggerManager.triggers.size()) {
					triggerList.setSelectedIndex(idx - 1);
				}
				setTriggerButtonsState();
			}
		}
	}
	
	public void setTriggerButtonsState() {
		boolean active = triggerModel.size() > 0;
		triggerEditButton.setEnabled(active);
		triggerDelButton.setEnabled(active);
	}
	
	public void onNewTrigger(Trigger newTrigger) {
		int idx = triggerModel.size();
		triggerModel.add(idx, newTrigger);
		triggerManager.triggers.add(idx, newTrigger);
		setTriggerButtonsState();
		triggerList.setSelectedIndex(idx);
		fire.getSimulator().afterSimulateTick();
	}
	
	public void onEditTrigger(int idx, Trigger newTrigger) {
		triggerModel.set(idx, newTrigger);
		triggerManager.triggers.set(idx, newTrigger);
		fire.getSimulator().afterSimulateTick();
	}
	
	public ExForestFire getForestFire() {
		return fire;
	}
}
