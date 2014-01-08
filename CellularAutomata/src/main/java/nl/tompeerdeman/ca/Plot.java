package nl.tompeerdeman.ca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Plot{
	//public final static String TERMINAL = "pdfcairo";
	//public final static String EXT = "pdf";
	
	public final static String TERMINAL = "jpeg";
	public final static String EXT = "jpg";
	
	// Voor runnen via bestanden in cmd map
	public final static String PATH = "../plot/";
	// Voor eclipse project
	//public final static String PATH = "plot/";
	
	public PrintWriter data;
	public PrintWriter instr;
	
	private boolean hasplotted;
	private String filename;
	
	public Plot(String file){
		filename = file;
		
		hasplotted = false;
		
		File d = new File(PATH + "data/" + file + ".dat");
		File i = new File(PATH + "data/" + file + ".gp");
		
		try{
			data = new PrintWriter(new FileOutputStream(d));
			instr = new PrintWriter(new FileOutputStream(i));
		}catch(FileNotFoundException e){
			System.out.println("Plot file doesn't exists: " + file + "-" + i);
			return;
		}
		
		instr.printf("set terminal %s\nset grid\n"
				+ "set output \"../%s.%s\"\nset key autotitle columnheader",
				TERMINAL, file, EXT);
	}
	
	public void plotFunction(int c1, int c2){
		if(!hasplotted){
			hasplotted = true;
			instr.printf("\nplot \"%s.dat\" using %d:%d with lines",
				filename, c1, c2);
		}else{
			instr.printf(",\\\n\"%s.dat\" using %d:%d with lines",
				filename, c1, c2);
		}
	}
	
	public void plotDots(int c1, int c2){
		if(!hasplotted){
			hasplotted = true;
			instr.printf("\nplot \"%s.dat\" using %d:%d with dots notitle",
				filename, c1, c2);
		}else{
			instr.printf(",\\\n\"%s.dat\" using %d:%d with dots notitle",
				filename, c1, c2);
		}
	}
	
	public void plotPoints(int c1, int c2){
		if(!hasplotted){
			hasplotted = true;
			instr.printf("\nplot \"%s.dat\" using %d:%d with points notitle",
					filename, c1, c2);
		}else{
			instr.printf(",\\\n\"%s.dat\" using %d:%d with points notitle",
					filename, c1, c2);
		}
	}
	
	public void close(){
		data.close();
		instr.close();
	}
}
