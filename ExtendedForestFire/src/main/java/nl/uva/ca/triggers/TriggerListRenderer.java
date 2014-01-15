/**
 * File: TriggerListRenderer.java
 * 
 */
package nl.uva.ca.triggers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;

import nl.uva.ca.Trigger;
import nl.uva.ca.TriggerManager;

/**
 *
 */
public class TriggerListRenderer extends JLabel implements
		ListCellRenderer<Trigger> {
	private static final long serialVersionUID = 2017682689616010049L;
	
	private TriggerManager triggerManager;
	
	/**
	 * @param manager
	 * 
	 */
	public TriggerListRenderer(TriggerManager manager) {
		setOpaque(true);
		triggerManager = manager;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing
	 * .JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(
			JList<? extends Trigger> list, Trigger value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setText(value.toString());
		
		if(isSelected) {
			if(triggerManager.triggers.get(index).isActivated()) {
				setBackground(new Color(0xB033CC77, true));
			} else {
				setBackground(new Color(0xB0FF4444, true));
			}
			setForeground(list.getSelectionForeground());
		} else {
			if(index > 0 && index < triggerManager.triggers.size()
					&& triggerManager.triggers.get(index).isActivated()) {
				setBackground(new Color(0x7033CC77, true));
			} else {
				setBackground(new Color(0x70FF4444, true));
			}
			setForeground(list.getForeground());
		}
		
		Border border = null;
		if(cellHasFocus) {
			if(isSelected) {
				border =
					UIManager.getBorder("List.focusSelectedCellHighlightBorder");
			} else {
				border = UIManager.getBorder("List.focusCellHighlightBorder");
			}
		} else {
			border = UIManager.getBorder("List.noFocusBorder");
		}
		setBorder(border);
		
		return this;
	}
}
