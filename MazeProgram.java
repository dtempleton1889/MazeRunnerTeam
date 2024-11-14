package maze;

/**
 * This class establishes the main framework for the MazeProgram
 * 
 * This version will include buttons......... for selecting color of the path,
 * changing difficulty of the maze, clearing the maze, movement pad, etc.
 * 
 * @author Manzel Kiinu +
 */

public class MazeProgram {

	public static void main(String[] args) {

		// Create a new maze with specified dimensions
		Maze maze = new Maze(20, 20); // Increases size of maze grid. Could use as user selecting difficulty
		
		
		new MazeFrameTesting(maze);
		/*
		 * // Initialize the Maze and draw the maze (SAVE FOR FIRST TRY)
		 * MazeFrameTesting frame = new MazeFrameTesting(maze); frame.drawMaze(); //
		 * Calls the drawMaze() method
		 */	}
}