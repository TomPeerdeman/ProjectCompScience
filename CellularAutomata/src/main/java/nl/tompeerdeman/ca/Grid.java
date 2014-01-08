package nl.tompeerdeman.ca;

public class Grid {
	public Cell[][] grid;
	
	public Grid(int nx, int ny) {
		grid = new Cell[nx][ny];
	}
	
	public Cell getCell(int x, int y) {
		return grid[x][y];
	}
	
	public void clear() {
		for(int y = 0; y < grid[0].length; y++) {
			for(int x = 0; x < grid.length; x++) {
				clearCell(x, y);
			}
		}
	}
	
	public void clearCell(int x, int y) {
		if(grid[x][y] != null) {
			grid[x][y].x = -1;
			grid[x][y].y = -1;
		}
		
		grid[x][y] = null;
	}
	
	public void setCell(Cell c) {
		grid[c.x][c.y] = c;
	}
	
	public void move(int x, int y, int xdest, int ydest) {
		grid[xdest][ydest] = grid[x][y];
		grid[x][y] = null;
		grid[xdest][ydest].x = xdest;
		grid[xdest][ydest].y = ydest;
	}
}
