package maze;

import java.util.Random;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;

/**
 * This class represents a rectangular grid maze where each cell can be
 * either a path or a wall. It generates a random, solvable maze of specified
 * rows and columns, with guaranteed connectivity from the starting cell at the
 * top-left corner to the ending cell at the bottom-right corner.
 */

public class Maze {
	private final int rows;
	private final int cols;
	private final boolean[][] grid;

	public Maze(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.grid = new boolean[rows][cols];
		generateSolvableMaze();
	}

	/**
	 * Repeatedly generates a random maze until a solvable one is created. Ensures
	 * there is a valid path from the start cell (0, 0) to the end cell (rows-1,
	 * cols-1).
	 */
	private void generateSolvableMaze() {
		do {
			generateMaze();
		} while (!isSolvable());
	}

	/**
	 * Generates a random maze by setting each cell as either a path or a wall. The
	 * top-left and bottom-right corners are always paths to designate the start and
	 * end points.
	 */
	private void generateMaze() {
		Random rand = new Random();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = rand.nextBoolean(); // Randomly decide if it's a wall or path
			}
		}
		grid[0][0] = true; // Start
		grid[rows - 1][cols - 1] = true; // End
	}

	/**
	 * Checks if the maze has a valid path from the start to the end cell using
	 * Breadth-First Search (BFS). Converts the maze grid to a graph representation
	 * and verifies connectivity.
	 *
	 * @return true if the maze is solvable; false otherwise
	 */
	private boolean isSolvable() {
		Graph graph = createGraph();
		int start = 0;
		int end = rows * cols - 1;
		BreadthFirstPaths bfs = new BreadthFirstPaths(graph, start);
		return bfs.hasPathTo(end);
	}

	/**
	 * Creates a graph representation of the maze grid. Each cell is treated as a
	 * node, and edges are added between adjacent path cells.
	 *
	 * @return the graph representation of the maze
	 */
	private Graph createGraph() {
		Graph graph = new Graph(rows * cols);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (isPath(row, col)) {
					int v = row * cols + col;
					if (row + 1 < rows && isPath(row + 1, col)) {
						graph.addEdge(v, (row + 1) * cols + col);
					}
					if (col + 1 < cols && isPath(row, col + 1)) {
						graph.addEdge(v, row * cols + col + 1);
					}
				}
			}
		}
		return graph;
	}

	/**
	 * Checks if the cell at the specified row and column is a path (rather than a
	 * wall).
	 *
	 * @param row - the row index of the cell
	 * @param col - the column index of the cell
	 * @return true if the cell is a path; false if it is a wall
	 */
	public boolean isPath(int row, int col) {
		return row >= 0 && col >= 0 && row < rows && col < cols && grid[row][col];
	}

	/**
	 * Gets the number of rows in the maze.
	 *
	 * @return the number of rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Gets the number of columns in the maze.
	 *
	 * @return the number of columns
	 */
	public int getCols() {
		return cols;
	}
}