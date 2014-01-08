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

package nl.tompeerdeman.ca.visual;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class CaFrame extends JFrame {
	private static final long serialVersionUID = -4518078590930192090L;
	protected Container main;
	
	public CaFrame(String title) {
		super(title);
		
		main = getContentPane();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		// setBounds(100, 100, 450, 550);
		setSize(450, 650);
	}
}
