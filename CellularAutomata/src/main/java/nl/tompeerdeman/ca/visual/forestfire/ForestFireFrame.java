/**
 * Copyright 2012 Tom Peerdeman
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package nl.tompeerdeman.ca.visual.forestfire;

import nl.tompeerdeman.ca.Simulator;
import nl.tompeerdeman.ca.forestfire.ForestFire;
import nl.tompeerdeman.ca.forestfire.ForestFireData;
import nl.tompeerdeman.ca.visual.CaFrame;
import nl.tompeerdeman.ca.visual.CaPanel;

public class ForestFireFrame extends CaFrame {
	private static final long serialVersionUID = -1641664667855879006L;
	
	public static void main(String[] args) {
		new ForestFireFrame();
	}
	
	public ForestFireFrame() {
		super("Forest fire simulation");
		
		ForestFire fire =
			new ForestFire(0.6, 100, 100, 0, ForestFireData.NB_MOORE, 0);
		fire.buildTimed(10);
		
		Simulator sim = fire.getSimulator();
		
		CaPanel capanel =
			new ForestFirePanel(sim.getGrid(), getWidth(), getHeight());
		sim.addChangeListener(capanel);
		sim.afterSimulateTick();
		
		main.add(capanel);
		
		ForestFireDataPanel dataPanel = new ForestFireDataPanel(fire);
		sim.addChangeListener(dataPanel);
		
		main.add(dataPanel);
		
		setVisible(true);
	}
	
}
