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

public class ThreadSimulator extends Simulator implements Runnable {
	
	public ThreadSimulator(Grid g, DataSet d) {
		super(g, d);
	}
	
	@Override
	public void start() {
		running = true;
		paused = false;
		
		new Thread(this).start();
	}
	
	@Override
	public void stop() {
		running = false;
		if(paused) {
			pause();
		}
	}
	
	@Override
	public void run() {
		if(cellAddList.size() == 0) {
			stop();
			return;
		}
		
		while(running) {
			while(paused) {
				try {
					synchronized(this) {
						wait();
					}
				} catch(InterruptedException e) {
				}
			}
			
			simulateTick();
		}
	}
	
	@Override
	public void pause() {
		paused = !paused;
		if(!paused) {
			synchronized(this) {
				notify();
			}
		}
	}
	
	@Override
	public void reset() {
		stop();
		super.reset();
	}
	
}
