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
