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

package nl.tompeerdeman.ca;

public abstract class MainSystem {
	protected Grid grid;
	protected DataSet data;
	protected Simulator sim;
	
	public Simulator getSimulator() {
		return sim;
	}
	
	public void buildThread() {
		sim = new ThreadSimulator(grid, data);
	}
	
	public void buildTimed(long delay) {
		sim = new TimedSimulator(grid, data, delay);
	}
	
	public void start() {
		sim.start();
	}
}
