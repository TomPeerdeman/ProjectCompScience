/**
 * File: TriggerManager.java
 * 
 */
package nl.uva.ca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import nl.tompeerdeman.ca.SimulatableSystem;
import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;

import nl.uva.ca.visual.forestfire.ExForestFireDataPanel;
import nl.uva.ca.visual.trigger.TestActionGenerator;
import nl.uva.ca.visual.trigger.TriggerActionGeneratorPanel;
import nl.uva.ca.visual.trigger.TriggerGeneratorPanel;
import nl.uva.ca.visual.trigger.forestfire.BushBurnTicksGenerator;
import nl.uva.ca.visual.trigger.forestfire.ExtinguishProbGenerator;
import nl.uva.ca.visual.trigger.forestfire.NBActionGenerator;
import nl.uva.ca.visual.trigger.forestfire.PercBurnedGenerator;
import nl.uva.ca.visual.trigger.forestfire.ProbDividerGenerator;
import nl.uva.ca.visual.trigger.forestfire.StartFireFightersGenerator;
import nl.uva.ca.visual.trigger.forestfire.StartFireGenerator;
import nl.uva.ca.visual.trigger.forestfire.StopFireFightersGenerator;
import nl.uva.ca.visual.trigger.forestfire.TickGenerator;
import nl.uva.ca.visual.trigger.forestfire.TreeBurnTicksGenerator;

/**
 *
 */
public class TriggerManager implements SimulateChangeListener {
	public static final Map<String, Class<? extends TriggerGeneratorPanel<?>>> TRIGGERS =
		new LinkedHashMap<String, Class<? extends TriggerGeneratorPanel<?>>>();
	public static final Map<String, Class<? extends TriggerActionGeneratorPanel<?>>> ACTIONS =
		new LinkedHashMap<String, Class<? extends TriggerActionGeneratorPanel<?>>>();
	
	static {
		try {
			TRIGGERS.put("Tick", TickGenerator.class);
			TRIGGERS.put("% burned", PercBurnedGenerator.class);
			
			ACTIONS.put("Neighborhood", NBActionGenerator.class);
			ACTIONS.put("Bush burn ticks", BushBurnTicksGenerator.class);
			ACTIONS.put("Tree burn ticks", TreeBurnTicksGenerator.class);
			ACTIONS.put("Start firefighting", StartFireFightersGenerator.class);
			ACTIONS.put("Stop firefighting", StopFireFightersGenerator.class);
			ACTIONS.put("Set prob divider", ProbDividerGenerator.class);
			ACTIONS.put("Start fire", StartFireGenerator.class);
			ACTIONS.put("Set ext prob", ExtinguishProbGenerator.class);
			ACTIONS.put("Print test", TestActionGenerator.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Trigger> triggers;
	private SimulatableSystem sys;
	private JFileChooser fileChooser;
	
	/**
	 * @param sys
	 * 
	 */
	public TriggerManager(SimulatableSystem sys) {
		triggers = new ArrayList<Trigger>();
		
		this.sys = sys;
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileHidingEnabled(true);
		FileFilter filter =
			new FileNameExtensionFilter("Trigger list files", "tca");
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(true);
		fileChooser.setFileFilter(filter);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.tompeerdeman.ca.SimulateChangeListener#simulationUpdated(nl.tompeerdeman
	 * .ca.Simulator)
	 */
	@Override
	public void simulationUpdated(Simulator sim) {
		Iterator<Trigger> it = triggers.iterator();
		while(it.hasNext()) {
			Trigger t = it.next();
			if(!t.isActivated() && t.process(sys)) {
				t.setActivated(true);
			}
		}
	}
	
	public void reset() {
		for(Trigger t : triggers) {
			t.setActivated(false);
		}
	}
	
	public void save(JComponent parent) throws IOException {
		int rc = fileChooser.showSaveDialog(parent);
		if(rc == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			ObjectOutputStream out =
				new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(triggers);
			out.flush();
			out.close();
		}
	}
	
	public void load(JComponent parent, DefaultListModel<Trigger> model)
			throws Exception {
		int rc = fileChooser.showOpenDialog(parent);
		if(rc == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			load(f);
			
			// Add triggers to list
			model.clear();
			for(Trigger t : triggers) {
				model.addElement(t);
			}
			
			if(parent instanceof ExForestFireDataPanel) {
				((ExForestFireDataPanel) parent).setTriggerButtonsState();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load(File f) throws Exception {
		ObjectInputStream in =
			new ObjectInputStream(new FileInputStream(f));
		Object obj = in.readObject();
		if(obj instanceof ArrayList<?>) {
			triggers = (ArrayList<Trigger>) obj;
		}
		in.close();
	}
}
