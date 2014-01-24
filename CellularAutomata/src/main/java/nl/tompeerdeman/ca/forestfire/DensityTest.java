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

package nl.tompeerdeman.ca.forestfire;

import java.util.Locale;

import nl.tompeerdeman.ca.Plot;
import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;

public class DensityTest implements SimulateChangeListener {
	private double oppositeReached[];
	private double oppositeReachedTime[];
	private double fracBurned[];
	private double density[];
	private int unfinished;
	private int runsperdd;
	
	private int w;
	private int h;
	
	private Plot oppReached;
	private Plot oppTime;
	private Plot burned;
	
	private Plot oppTimeAll;
	private Plot burnedAll;
	
	public static int parseInt(String s) {
		int i = -1;
		try {
			i = Integer.parseInt(s);
		} catch(NumberFormatException e) {
		}
		return i;
	}
	
	public static double parseDouble(String s) {
		double d = -1;
		try {
			d = Double.parseDouble(s);
		} catch(NumberFormatException e) {
		}
		return d;
	}
	
	public static void main(String[] args) {
		if(args.length != 6) {
			System.out.println("Usage DensityTest width height startdensity enddensity densityincrease runsperdensity");
			return;
		}
		
		int w = parseInt(args[0]);
		int h = parseInt(args[1]);
		if(h < 0 || h > 400 || w < 0 || w > 400) {
			System.out.println("Grid size out of range: 0-400");
			return;
		}
		
		int rpd = parseInt(args[5]);
		if(rpd < 0) {
			System.out.println("runsperdensity out of range: 0-n");
			return;
		}
		
		double d0 = parseDouble(args[2]);
		double d1 = parseDouble(args[3]);
		double dd = parseDouble(args[4]);
		
		if(d0 < 0 || d0 > 1.0 || d1 < 0 || d1 > 1.0 || d0 > d1 || dd < 0
				|| dd > 1.0) {
			System.out.println("Density incorrect");
			return;
		}
		
		new DensityTest(w, h, d0, d1, dd, rpd);
	}
	
	public DensityTest(int w, int h, double d0, double d1, double dd, int runs) {
		this.w = w;
		this.h = h;
		runsperdd = runs;
		
		unfinished = 0;
		
		oppReached = new Plot("oppreached");
		oppTime = new Plot("opptime");
		burned = new Plot("fracburned");
		oppTimeAll = new Plot("opptimeall");
		burnedAll = new Plot("burnedall");
		
		oppTimeAll.instr.printf("\nset xlabel \"Density\"\nset ylabel \"Time\"");
		burnedAll.instr.printf("\nset xlabel \"Density\"\nset ylabel \"Num burned\"");
		
		oppTimeAll.data.printf("Density\tTime\n");
		burnedAll.data.printf("Density\tNum burned\n");
		
		final int ndensities = (int) Math.ceil((d1 - d0) / dd) + 1;
		
		oppositeReached = new double[ndensities];
		oppositeReachedTime = new double[ndensities];
		fracBurned = new double[ndensities];
		density = new double[ndensities];
		
		unfinished = ndensities * runsperdd;
		
		double d;
		int n = 0;
		ForestFire fire;
		for(int i = 0; i < ndensities; i++) {
			d = i * dd + d0;
			density[i] = d;
			for(int j = 0; j < runsperdd; j++) {
				n++;
				fire = new ForestFire(d, w, h, 0, ForestFireData.NB_NEUMANN, i);
				fire.buildThreadedSimulator();
				fire.getSimulator().addChangeListener(this);
				fire.start();
			}
		}
		
		System.out.printf("Fired %d up; %d density's; %d runs per density\n",
				n,
				ndensities, runsperdd);
	}
	
	private void printResult() {
		oppReached.instr.printf("\nset xlabel \"Density\"\nset ylabel \"Fraction reached\"");
		oppTime.instr.printf("\nset xlabel \"Density\"\nset ylabel \"Time\"");
		burned.instr.printf("\nset xlabel \"Density\"\nset ylabel \"Fraction burned\"");
		
		oppReached.data.printf("Density\tFraction reached\n");
		oppTime.data.printf("Density\tTime\n");
		burned.data.printf("Density\tFraction burned\n");
		
		for(int i = 0; i < fracBurned.length; i++) {
			System.out.printf("[%f] %f reached, time %f burned %f\n",
					density[i],
					(oppositeReached[i] / (double) runsperdd),
					(oppositeReachedTime[i] / oppositeReached[i]),
					(fracBurned[i] / (double) runsperdd));
			oppReached.data.printf(Locale.US, "%f\t%f\n", density[i],
					(oppositeReached[i] / (double) runsperdd));
			if(oppositeReached[i] > 0) {
				oppTime.data.printf(Locale.US, "%f\t%f\n", density[i],
						(oppositeReachedTime[i] / oppositeReached[i]));
			}
			burned.data.printf(Locale.US, "%f\t%f\n", density[i],
					(fracBurned[i] / (double) runsperdd));
		}
		
		oppReached.plotPoints(1, 2);
		oppTime.plotPoints(1, 2);
		burned.plotPoints(1, 2);
		
		oppTimeAll.plotDots(1, 2);
		burnedAll.plotDots(1, 2);
		
		oppReached.close();
		oppTime.close();
		burned.close();
		
		oppTimeAll.close();
		burnedAll.close();
	}
	
	public synchronized void simulationUpdated(Simulator sim) {
		ForestFireData data = (ForestFireData) sim.getData();
		
		if(data.burning == 0 && data.reachedOpposite) {
			oppositeReached[data.densityIdx]++;
			oppositeReachedTime[data.densityIdx] += sim.getTick();
		}
		
		if(data.burning == 0) {
			fracBurned[data.densityIdx] +=
				((double) data.burnt / (double) (w * h));
			unfinished--;
			if(data.reachedOpposite) {
				oppTimeAll.data.printf(Locale.US, "%f\t%d\n",
						density[data.densityIdx], sim.getTick());
			}
			burnedAll.data.printf(Locale.US, "%f\t%d\n",
					density[data.densityIdx], data.burnt);
		}
		
		// All fires finished
		if(unfinished == 0) {
			printResult();
		}
	}
	
}
