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
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerManager;

/**
 *
 */
public class TriggerFrame extends JFrame {
	private static final long serialVersionUID = 4338488275000148085L;
	
	private Container main;
	private GridBagConstraints c;
	
	private JComboBox<String> triggers;
	private JComboBox<String> actions;
	
	private JButton applyButton;
	
	/**
	 * @param trigger
	 * @throws HeadlessException
	 */
	public TriggerFrame(Trigger trigger) throws HeadlessException {
		this(trigger.toString());
		
		applyButton.setText("Apply");
	}
	
	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public TriggerFrame(String title) throws HeadlessException {
		super(title);
		main = getContentPane();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());
		setSize(300, 200);
		
		this.setLocation(955, 360);
		
		triggers =
			new JComboBox<String>(mapKeysToString(TriggerManager.TRIGGERS));
		
		actions =
			new JComboBox<String>(mapKeysToString(TriggerManager.ACTIONS));
		
		applyButton = new JButton("Create");
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 5, 0, 5);
		
		c.gridx = 0;
		c.gridy = 0;
		main.add(triggers, c);
		
		c.gridx = 1;
		c.gridy = 0;
		main.add(actions, c);
		
		c.gridx = 1;
		c.gridy = 3;
		main.add(applyButton, c);
		
		setVisible(true);
	}
	
	public String[] mapKeysToString(Map<String, ?> map) {
		Set<String> set = map.keySet();
		String[] arr = new String[set.size()];
		set.toArray(arr);
		return arr;
	}
}
