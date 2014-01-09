package nl.uva.ca.visual;

import nl.tompeerdeman.ca.visual.CaFrame;

public class ExCaFrame extends CaFrame {
	private static final long serialVersionUID = 2291576429721955270L;


	public static void main(String[] args) {
		new ExCaFrame("title");
	}
	

	public ExCaFrame(String title) {
		super(title);
		setSize(850, 650);
	}

}
