package nl.tompeerdeman.ca.visual;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class CaFrame extends JFrame{
	private static final long serialVersionUID = -4518078590930192090L;
	protected Container main;
	
	public CaFrame(String title){
		super(title);
		
		main = getContentPane();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		//setBounds(100, 100, 450, 550);
		setSize(450, 650);
	}
}
