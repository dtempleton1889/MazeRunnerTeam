package maze;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * This class provides a graphical interface to display and solve a Maze. It
 * visualizes the maze grid, the start and finish points, and the solution path
 * when the appropriate button is clicked.
 * 
 * The maze is drawn using a grid where walls and paths are represented by
 * different colors. Solution Path will be displayed in blue.
 */

public class MazeFrameTesting extends JFrame {
	private Maze maze;
	private MazeSolver solver;
	private JPanel mazePanel;
	private JPanel controlPanel;

	/**
	 * Constructs a MazeGUI to display and solve the specified Maze.
	 * 
	 * Initializes the JFrame, sets up the maze display panel, and adds a button for
	 * solving the maze. Maze grid drawn at initialization
	 *
	 * @param maze - the Maze object to display and solve
	 */
	public MazeFrameTesting(Maze maze) {

		this.maze = maze;
		this.solver = new MazeSolver(maze);

		// Set up the JFrame
		setTitle("A-Mazing!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(600, 700); // Adjust to fit maze and buttons

		// Initialize and add the maze to north panel
		mazePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawMaze(g); //calls the method to draw the maze
			}
		};

		mazePanel.setPreferredSize(new Dimension(600, 600));
		add(mazePanel, BorderLayout.CENTER);

		// Initialize and add the control panel to south
		controlPanel = new JPanel();
		JButton solveButton = new JButton("Solve Maze");
		solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solveAndDisplayPath();
			}
		});

		controlPanel.add(solveButton);
		add(controlPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	/**
	 * Draws the maze grid on the screen, indicating paths and walls with different
	 * colors. Shows the start and end points, and if a solution path exists,
	 * animates the path in blue.
	 * 
	 * @param g - the Graphics object used to render the maze on the screen
	 */
	public void drawMaze(Graphics g) {
		int rows = maze.getRows();
		int cols = maze.getCols();
		int cellSize = Math.min(mazePanel.getWidth() / cols, mazePanel.getHeight() / rows);

		// Draw the maze
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (maze.isPath(i, j)) {
					g.setColor(Color.WHITE); // Walls
				} else {
					g.setColor(Color.BLACK); // Paths
				}
				g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
			}
		}

		// Draw the start and finish labels
		g.setColor(Color.GREEN);
		g.drawString("Start", 10, 20); // Top-left corner label
		g.setColor(Color.RED);
		g.drawString("Finish", (cols - 1) * cellSize, (rows - 1) * cellSize); // Bottom-right corner label
	}

	/**
	 *Solves the maze and displays the solution. 
	 */
	public void solveAndDisplayPath() {
		// Draw the solution path, if it exists
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				Iterable<Integer> path = solver.solve();
				if (path != null) {
					int rows = maze.getRows();
					int cols = maze.getCols();
					int cellSize = Math.min(mazePanel.getWidth() / cols, mazePanel.getHeight() / rows);

					for (int v : path) {
						int row = v / cols;
						int col = v % cols;
						Graphics g = mazePanel.getGraphics();
						g.setColor(Color.BLUE);
						g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);

						Thread.sleep(100);
					}
				} else {
					JOptionPane.showMessageDialog(MazeFrameTesting.this, "No path found from start to end.");
				}
				return null;
			}
		};
		worker.execute();
	}

}