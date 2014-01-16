/**
 * File: StartFireAction.java
 * 
 */
package nl.uva.ca.triggers;

import java.awt.Point;
import java.util.Random;

import nl.tompeerdeman.ca.SimulatableSystem;
import nl.tompeerdeman.ca.Simulator;

import nl.uva.ca.ExForestFireCell;
import nl.uva.ca.ExForestFireCellType;
import nl.uva.ca.ExForestFireData;
import nl.uva.ca.TriggerAction;

/**
 *
 */
public class StartFireAction implements TriggerAction {
	private final Point lowerPoint;
	private final Point upperPoint;
	private final int nPoints;
	
	/**
	 * @param lowerPoint
	 * @param upperPoint
	 * @param nPoints
	 */
	public StartFireAction(Point lowerPoint, Point upperPoint, int nPoints) {
		this.lowerPoint = lowerPoint;
		this.upperPoint = upperPoint;
		this.nPoints = nPoints;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#execute(nl.tompeerdeman.ca.DataSet)
	 */
	@Override
	public void execute(SimulatableSystem sys) {
		Simulator sim = sys.getSimulator();
		ExForestFireData ffdata = (ExForestFireData) sim.getData();
		
		int n = 0;
		int tries = 0;
		Random rand = new Random();
		while(n < nPoints && tries < 100) {
			tries++;
			int x;
			if(upperPoint.x >= 0)
				x =
					rand.nextInt(upperPoint.x - lowerPoint.x + 1)
							+ lowerPoint.x;
			else
				x = lowerPoint.x;
			
			int y;
			if(upperPoint.y >= 0)
				y =
					rand.nextInt(upperPoint.y - lowerPoint.y + 1)
							+ lowerPoint.y;
			else
				y = lowerPoint.x;
			
			ExForestFireCell cell =
				(ExForestFireCell) ffdata.grid.getCell(x, y);
			if(cell != null) {
				if(cell.getType() == ExForestFireCellType.BUSH) {
					cell.setType(ExForestFireCellType.BURNING_BUSH);
					ffdata.burning++;
					ffdata.bushes--;
					sim.addSimulatable(cell);
					tries = 0;
					n++;
				} else if(cell.getType() == ExForestFireCellType.TREE) {
					cell.setType(ExForestFireCellType.BURNING_TREE);
					ffdata.burning++;
					ffdata.trees--;
					sim.addSimulatable(cell);
					tries = 0;
					n++;
				}
			}
		}
	}
	
	/**
	 * @return the lowerPoint
	 */
	public Point getLowerPoint() {
		return lowerPoint;
	}
	
	/**
	 * @return the upperPoint
	 */
	public Point getUpperPoint() {
		return upperPoint;
	}
	
	/**
	 * @return the nPoints
	 */
	public int getNumPoints() {
		return nPoints;
	}
	
	public boolean isSinglePoint() {
		return upperPoint.getX() == -1 && upperPoint.getY() == -1
				&& nPoints == 1;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.uva.ca.TriggerAction#getActionName()
	 */
	@Override
	public String getActionName() {
		return "Start fire";
	}
	
	@Override
	public String toString() {
		return "start fire";
	}
}
