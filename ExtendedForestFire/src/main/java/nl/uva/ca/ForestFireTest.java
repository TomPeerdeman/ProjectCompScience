/**
 * File: ForestFireTest.java
 * 
 */
package nl.uva.ca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import nl.tompeerdeman.ca.SimulateChangeListener;
import nl.tompeerdeman.ca.Simulator;
import nl.tompeerdeman.ca.ThreadSimulator;

/**
 *
 */
public class ForestFireTest implements SimulateChangeListener {
	public final int GRID_WIDTH = 100;
	public final int GRID_HEIGHT = 100;
	public final int RUNS_PER_PARAM_CHANGE = 100;
	
	private ExecutorService threadPool;
	private double[] xAxis;
	
	private double startDensity;
	private double densityStep;
	private List<Future<?>> tasks;
	
	private ImprovedPlot oppReached;
	private ImprovedPlot oppTime;
	private ImprovedPlot burned;
	private ImprovedPlot oppTimeAll;
	private ImprovedPlot burnedAll;
	
	public static void main(String[] args) throws Exception {
		new ForestFireTest(0, 1, 0.1);
	}
	
	/**
	 * @param startDensity
	 * @param endDensity
	 * @param densityStep
	 * @throws Exception
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * 
	 */
	public ForestFireTest(double startDensity, double endDensity,
			double densityStep) throws Exception {
		this.startDensity = startDensity;
		this.densityStep = densityStep;
		
		final int nCores = Runtime.getRuntime().availableProcessors();
		threadPool = Executors.newFixedThreadPool(nCores);
		System.out.printf("Starting threadpool on %d cores\n", nCores);
		
		tasks = new LinkedList<Future<?>>();
		
		final int nDensities =
			(int) Math.ceil((endDensity - startDensity) / densityStep) + 1;
		xAxis = new double[nDensities];
		
		oppReached = new ImprovedPlot("oppreached", nDensities);
		oppTime = new ImprovedPlot("opptime", nDensities);
		burned = new ImprovedPlot("fracburned", nDensities);
		oppTimeAll = new ImprovedPlot("opptimeall", nDensities);
		burnedAll = new ImprovedPlot("burnedall", nDensities);
		
		oppTimeAll.setAxisLabels("Density", "Time");
		burnedAll.setAxisLabels("Density", "Num burned");
		
		List<Future<?>> tasksAdd = new ArrayList<Future<?>>(nDensities);
		for(int i = 0; i < nDensities; i++) {
			tasksAdd.add(threadPool.submit(new ForestFireAddTask(i, this)));
		}
		
		System.out.println("Added " + tasksAdd.size() + " tasks");
		
		// Await termination of all add tasks.
		for(Future<?> task : tasksAdd) {
			task.get();
		}
		
		System.out.println("All add tasks done. Added " + tasks.size()
				+ " simulation tasks.");
		
		// All simulation tasks are added, we are save to iterate over tasks.
		for(Future<?> task : tasks) {
			task.get();
		}
		System.out.println("All tasks done");
		threadPool.shutdown();
		
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.tompeerdeman.ca.SimulateChangeListener#simulationUpdated(nl.tompeerdeman
	 * .ca.Simulator)
	 */
	@Override
	public void simulationUpdated(Simulator sim) {
		ExForestFireData data = (ExForestFireData) sim.getData();
		
		if(data.burning == 0) {
			burned.savedData[data.testNr] +=
				((double) data.burnt / (double) (GRID_WIDTH * GRID_HEIGHT));
			
			if(data.oppositeReached) {
				oppReached.savedData[data.testNr]++;
				oppTime.savedData[data.testNr] += sim.getTick();
				
				oppTimeAll.addData("%f\t%d\n", xAxis[data.testNr],
						sim.getTick());
			}
			burnedAll.addData("%f\t%f\n", xAxis[data.testNr], data.burnt);
		}
	}
	
	private class ForestFireAddTask implements Runnable {
		private final int i;
		private final ForestFireTest superTest;
		
		/**
		 * @param i
		 * @param superTest
		 */
		public ForestFireAddTask(int i, ForestFireTest superTest) {
			this.i = i;
			this.superTest = superTest;
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			final boolean water = false;
			final boolean path = false;
			final int type = 0;
			
			double d;
			ExForestFire fire;
			ThreadSimulator sim;
			xAxis[i] = i * densityStep + startDensity;
			d = xAxis[i] / 2.0;
			for(int j = 0; j < RUNS_PER_PARAM_CHANGE; j++) {
				fire =
					new ExForestFire(GRID_WIDTH, GRID_HEIGHT, 0, water, path,
							d, d, type, i);
				fire.buildThreadedSimulator();
				sim = (ThreadSimulator) fire.getSimulator();
				sim.addChangeListener(superTest);
				
				// TODO: Add triggers
				
				// Insert into task list & thread pool
				tasks.add(threadPool.submit(sim));
			}
		}
	}
}
