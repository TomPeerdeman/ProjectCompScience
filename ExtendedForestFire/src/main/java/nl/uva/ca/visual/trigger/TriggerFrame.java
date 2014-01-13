/**
 * File: TriggerFrame.java
 * 
 */
package nl.uva.ca.visual.trigger;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerManager;
import nl.uva.ca.triggers.TestAction;
import nl.uva.ca.triggers.TickTrigger;
import nl.uva.ca.visual.forestfire.ExForestFireDataPanel;

/**
 *
 */
public class TriggerFrame extends JFrame {
	private static final long serialVersionUID = 4338488275000148085L;
	
	private Container main;
	private GridBagConstraints c;
	
	private JComboBox<String> triggers;
	private JComboBox<String> actions;
	
	private TriggerPanel triggerPanel;
	private TriggerPanel actionPanel;
	
	private Map<String, JPanel> panels;
	
	private JButton applyButton;
	
	/**
	 * @param idx
	 * @param trigger
	 * @param panel
	 * @throws HeadlessException
	 */
	public TriggerFrame(int idx, Trigger trigger, ExForestFireDataPanel panel)
			throws HeadlessException {
		this(trigger.toString(), panel);
		
		applyButton.setText("Apply");
	}
	
	/**
	 * @param title
	 * @param panel
	 * @throws HeadlessException
	 */
	public TriggerFrame(String title, final ExForestFireDataPanel panel)
			throws HeadlessException {
		super(title);
		main = getContentPane();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setSize(300, 200);
		
		this.setLocation(955, 360);
		
		panels = new HashMap<String, JPanel>();
		
		triggerPanel = new TriggerPanel();
		
		for(Entry<String, Class<? extends TriggerGeneratorPanel<?>>> e : TriggerManager.TRIGGERS.entrySet()) {
			try {
				TriggerGeneratorPanel<?> p = e.getValue().newInstance();
				panels.put(e.getKey(), p);
				triggerPanel.add(p, e.getKey());
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		
		triggers =
			new JComboBox<String>(mapKeysToString(TriggerManager.TRIGGERS));
		triggers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				triggerPanel.show((String) triggers.getSelectedItem());
			}
		});
		triggerPanel.show((String) triggers.getSelectedItem());
		
		actionPanel = new TriggerPanel();
		
		for(Entry<String, Class<? extends TriggerActionGeneratorPanel<?>>> e : TriggerManager.ACTIONS.entrySet()) {
			try {
				TriggerActionGeneratorPanel<?> p = e.getValue().newInstance();
				panels.put(e.getKey(), p);
				actionPanel.add(p, e.getKey());
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		
		actions =
			new JComboBox<String>(mapKeysToString(TriggerManager.ACTIONS));
		actions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				actionPanel.show((String) actions.getSelectedItem());
			}
		});
		actionPanel.show((String) actions.getSelectedItem());
		
		applyButton = new JButton("Create");
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.onNewTrigger(new TickTrigger(10, new TestAction()));
				dispose();
			}
		});
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 5, 0, 5);
		
		c.gridx = 0;
		c.gridy = 0;
		main.add(triggers, c);
		
		c.gridx = 0;
		c.gridy = 1;
		main.add(triggerPanel, c);
		triggerPanel.first();
		
		c.gridx = 1;
		c.gridy = 0;
		main.add(actions, c);
		
		c.gridx = 1;
		c.gridy = 1;
		main.add(actionPanel, c);
		actionPanel.first();
		
		c.gridx = 1;
		c.gridy = 2;
		main.add(applyButton, c);
		
		setVisible(true);
	}
	
	private String[] mapKeysToString(Map<String, ?> map) {
		Set<String> set = map.keySet();
		String[] arr = new String[set.size()];
		set.toArray(arr);
		return arr;
	}
}
