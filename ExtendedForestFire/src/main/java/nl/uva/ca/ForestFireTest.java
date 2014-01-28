/**
 * File: ForestFireTest.java
 * 
 */
package nl.uva.ca;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
	public final boolean SHOW_PROGRESS = true;
	
	public final int GRID_WIDTH = 100;
	public final int GRID_HEIGHT = 100;
	public final int RUNS_PER_PARAM_CHANGE = 50;
	public final File TRIGGER_FILE = new File("nodist2_trigger.tca");
	
	public final boolean PATH = false;
	public final boolean WATER = false;
	public final boolean TRIGGERS = (TRIGGER_FILE.exists() & PATH & false);
	public final int TYPE = 0;
	
	private ExecutorService threadPool;
	private double[] xAxis;
	
	private List<Future<?>> tasks;
	
	private ImprovedPlot oppReached;
	private ImprovedPlot oppTime;
	private ImprovedPlot burned;
	private ImprovedPlot oppTimeAll;
	private ImprovedPlot burnedAll;
	
	private ImprovedPlot[] fireFighters;
	
	public static void main(String[] args) throws Exception {
		new ForestFireTest(0.05, 1, 0.05);
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
		final int nCores = Runtime.getRuntime().availableProcessors();
		threadPool = Executors.newFixedThreadPool(nCores);
		System.out.printf("Starting threadpool on %d cores\n", nCores);
		
		System.out.printf("Trigger file exists %s, load it %s\n",
				TRIGGER_FILE.exists(), TRIGGERS);
		
		tasks = new LinkedList<Future<?>>();
		
		final int nDensities =
			(int) Math.ceil((endDensity - startDensity) / densityStep) + 1;
		xAxis = new double[nDensities];
		
		oppReached = new ImprovedPlot("oppreached" + TYPE, nDensities);
		oppTime = new ImprovedPlot("opptime" + TYPE, nDensities);
		burned = new ImprovedPlot("fracburned" + TYPE, nDensities);
		oppTimeAll = new ImprovedPlot("opptimeall" + TYPE, nDensities);
		burnedAll = new ImprovedPlot("burnedall" + TYPE, nDensities);
		
		if(TRIGGERS) {
			fireFighters = new ImprovedPlot[nDensities];
		}
		
		oppTimeAll.setAxisLabels("Density", "Time");
		burnedAll.setAxisLabels("Density", "Num burned");
		
		// Same as setAxisLabels
		oppReached.instr.printf("\nset xlabel \"Density\"\nset ylabel \"Fraction reached\"");
		oppTime.instr.printf("\nset xlabel \"Density\"\nset ylabel \"Time\"");
		burned.instr.printf("\nset xlabel \"Density\"\nset ylabel \"Fraction burned\"");
		
		List<Future<?>> tasksAdd = new ArrayList<Future<?>>(nDensities);
		for(int i = 0; i < nDensities; i++) {
			xAxis[i] = i * densityStep + startDensity;
			
			if(TRIGGERS) {
				fireFighters[i] =
					new ImprovedPlot(String.format(Locale.US,
							"firefighter[%.2f]"
									+ TYPE, xAxis[i]), 0);
				fireFighters[i].setAxisLabels("Num firefighters", "Tick");
				fireFighters[i].data.println("Tick\tAlive\tDead");
			}
			
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
		int n = 0;
		for(Future<?> task : tasks) {
			task.get();
			if(SHOW_PROGRESS) {
				n++;
				System.out.printf("Done %d\\%d\n", n, tasks.size());
			}
		}
		System.out.println("All tasks done");
		threadPool.shutdown();
		
		for(int i = 0; i < nDensities; i++) {
			System.out.printf("[%.2f] %.2f reached, time %.2f burned %f\n",
					xAxis[i],
					(oppReached.savedData[i] / (double) RUNS_PER_PARAM_CHANGE),
					(oppTime.savedData[i] / oppReached.savedData[i]),
					(burned.savedData[i] / (double) RUNS_PER_PARAM_CHANGE));
			
			oppReached.addData("%f\t%f\n", xAxis[i],
					(oppReached.savedData[i] / (double) RUNS_PER_PARAM_CHANGE));
			
			if(oppReached.savedData[i] > 0) {
				oppTime.data.printf(Locale.US, "%f\t%f\n", xAxis[i],
						(oppTime.savedData[i] / oppReached.savedData[i]));
			}
			burned.data.printf(Locale.US, "%f\t%f\n", xAxis[i],
					(burned.savedData[i] / (double) RUNS_PER_PARAM_CHANGE));
			
			if(TRIGGERS) {
				fireFighters[i].plotFunction(1, 2);
				fireFighters[i].plotFunction(1, 3);
				fireFighters[i].close();
			}
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
		
		if(data.simNr == 0 && TRIGGERS) {
			fireFighters[data.testNr].addData("%d\t%d\t%d\t%d\n",
					sim.getTick(),
					data.nFireFighters, data.nDeadFireFighters, data.burning);
			fireFighters[data.testNr].data.flush();
		}
		
		if(data.burning == 0) {
			burned.savedData[data.testNr] +=
				((double) data.burnt / (double) (GRID_WIDTH * GRID_HEIGHT));
			
			if(data.oppositeReached) {
				oppReached.savedData[data.testNr]++;
				oppTime.savedData[data.testNr] += sim.getTick();
				
				oppTimeAll.addData("%f\t%d\n", xAxis[data.testNr],
						sim.getTick());
			}
			burnedAll.addData("%f\t%d\n", xAxis[data.testNr], data.burnt);
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
			double d;
			ExForestFire fire;
			ThreadSimulator sim;
			TriggerManager triggerManager;
			d = xAxis[i] / 2.0;
			for(int j = 0; j < RUNS_PER_PARAM_CHANGE; j++) {
				fire =
					new ExForestFire(GRID_WIDTH, GRID_HEIGHT, 0, WATER, PATH,
							d, d, TYPE, i, j);
				fire.buildThreadedSimulator();
				sim = (ThreadSimulator) fire.getSimulator();
				
				// Load triggers.
				if(TRIGGERS) {
					try {
						triggerManager = new TriggerManager(fire);
						triggerManager.load(TRIGGER_FILE);
						sim.addChangeListener(triggerManager);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				// Add test callback.
				sim.addChangeListener(superTest);
				
				// Insert into task list & thread pool.
				tasks.add(threadPool.submit(sim));
			}
		}
	}
}
