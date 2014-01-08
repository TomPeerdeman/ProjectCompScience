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

import java.util.Timer;
import java.util.TimerTask;

public class TimedSimulator extends Simulator {
	private long delay;
	private Timer timer;
	
	public TimedSimulator(Grid g, DataSet d, long delay) {
		super(g, d);
		this.delay = delay;
	}
	
	public void setDelay(long delay) {
		if(running) {
			stop();
		}
		
		this.delay = delay;
	}
	
	@Override
	public void start() {
		running = true;
		paused = false;
		
		timer = new Timer();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runTick();
			}
		}, delay, delay);
		
	}
	
	public void runTick() {
		if(cellAddList.size() == 0) {
			stop();
			return;
		}
		
		if(paused) {
			return;
		}
		
		simulateTick();
	}
	
	@Override
	public void stop() {
		running = false;
		paused = false;
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	@Override
	public void pause() {
		paused = !paused;
	}
	
	@Override
	public void reset() {
		stop();
		super.reset();
	}
	
}
