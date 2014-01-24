/**
 * File: ImprovedPlot.java
 * 
 */
package nl.uva.ca;

import java.util.Locale;

import nl.tompeerdeman.ca.Plot;

/**
 *
 */
public class ImprovedPlot extends Plot {
	public double[] savedData;
	
	/**
	 * @param file
	 * @param leng
	 */
	public ImprovedPlot(String file, int leng) {
		super(file);
		savedData = new double[leng];
	}
	
	public void setAxisLabels(String xLabel, String yLabel) {
		instr.printf("\nset xlabel \"%s\"\nset ylabel \"%s\"", xLabel, yLabel);
	}
	
	public void addData(Object... objects) {
		addData(getFormat(objects), objects);
	}
	
	private String getFormat(Object... objects) {
		StringBuilder format = new StringBuilder();
		for(int i = 0; i < objects.length; i++) {
			if(i != 0) {
				format.append("\t");
			}
			Class<?> clzz = objects[i].getClass();
			
			if(clzz == int.class || clzz == long.class) {
				format.append("%d");
			} else if(clzz == double.class || clzz == float.class) {
				format.append("%f");
			} else {
				format.append("%s");
			}
		}
		format.append("\n");
		
		return format.toString();
	}
	
	public void addData(String format, Object... objects) {
		data.printf(Locale.US, format, objects);
	}
	
	public void addSavedData(double[] xAxis) {
		if(savedData.length > 0 && savedData.length == xAxis.length) {
			for(int i = 0; i < savedData.length; i++) {
				addData("%f\t%f\n", xAxis[i], savedData[i]);
			}
		}
	}
}
