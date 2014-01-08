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
