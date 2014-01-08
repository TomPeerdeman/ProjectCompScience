package nl.tompeerdeman.ca;

public class ThreadSimulator extends Simulator implements Runnable{
	
	public ThreadSimulator(Grid g, DataSet d){
		super(g, d);
	}

	@Override
	public void start(){
		running = true;
		paused = false;
		
		new Thread(this).start();
	}
	
	@Override
	public void stop(){
		running = false;
		if(paused){
			pause();
		}
	}
	
	public void run(){
		if(cellAddList.size() == 0){
			stop();
			return;
		}
		
		while(running){
			while(paused){
				try{
					synchronized(this){
						wait();
					}
				}catch(InterruptedException e){
				}
			}
			
			simulateTick();
		}
	}
	
	@Override
	public void pause(){
		paused = !paused;
		if(!paused){
			synchronized(this){
				notify();
			}
		}
	}
	
	@Override
	public void reset(){
		stop();
		super.reset();
	}
	
}
