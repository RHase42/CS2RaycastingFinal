import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.Queue;

public class Maze {
	private int width;
	private int height;
	private int[][] maze;
	private Point goal;
	private Point playerStart;
	private HashSet<Point> visited;
	private Map<Point, Point> prev;

	public Maze(int x, int y) {
		this.visited = new HashSet<Point>();
		this.width = x*2+1;
		this.height = y*2+1;
		this.maze = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i % 2 == 1 && j % 2 == 1) {
					maze[i][j] = 0;
				} else {
					maze[i][j] = 1;
				}
			}
		}
		createMaze(1, 1);
		setPoints();
	}
	
	private void createMaze(int cWidth, int cHeight) {
		Compass[] direction = Compass.values();
		Collections.shuffle(Arrays.asList(direction));
		for (Compass pointing: direction) {
			Point currentPoint = new Point(cWidth, cHeight);
			visited.add(currentPoint);
			int nextW = cWidth + pointing.genX;
			int nextH = cHeight + pointing.genY;
			Point nextPoint = new Point(nextW, nextH);
			if (arrayBounds(nextW, width) && arrayBounds (nextH, height) && maze[nextW][nextH] == 0 && !visited.contains(nextPoint)) {
				maze[cWidth + pointing.mapX][cHeight + pointing.mapY] = 0;
				createMaze(nextW, nextH);
			}
		} 
	}

	private boolean arrayBounds (int index, int max) {
		return (index >= 0 && index < max );
	}
	
	private enum Compass {
		N(0, -2, 0, -1),S(0, 2, 0, 1),W(-2, 0, -1, 0),E(2, 0, 1, 0);
		private int genX, genY, mapX, mapY;
		private Compass(int compassX, int compassY, int mapX, int mapY) {
			this.genX = compassX; this.genY = compassY;
			this.mapX = mapX; this.mapY = mapY;
		}
	}

	private void setPoints () {
		ArrayList<Point> emptySpot = new ArrayList<Point>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
			if (maze[i][j] == 0) {
				emptySpot.add(new Point (i, j));
				}
			}
		}
		Collections.shuffle(emptySpot);
		this.goal = emptySpot.get(0);
		double goalRange = this.goal.getX() + this.goal.getY();
		for (int i = 1; i < emptySpot.size(); i++) {
			Point playerStart = emptySpot.get(i);
			double playerRange = playerStart.getX() + playerStart.getY();
			if (Math.abs(playerRange - goalRange) > width/2)
			this.playerStart = playerStart;
		}
	}
	
	public Stack<Point> shortestPath() {
		resetVisited();
		Stack <Point> shortestPath = new Stack<Point>();
		prev = new HashMap<Point,Point>();
		Compass[] direction = Compass.values();
		Queue <Point> path = new LinkedList<Point>();
		path.add(playerStart);
		while(!path.isEmpty()) {
			Point current = path.poll();
			for (Compass pointing: direction) {
				if (current.equals(goal)) {
					shortestPath.push(current);
					while (prev.containsKey(shortestPath.peek()) && !shortestPath.peek().equals(playerStart)){
						shortestPath.push(prev.get(shortestPath.peek()));
					}
					return shortestPath;
				}
				int nextW = (int)current.getX() + pointing.mapX;
				int nextH = (int)current.getY() + pointing.mapY;
				Point nextPoint = new Point(nextW, nextH);
				if (arrayBounds(nextW, width) && arrayBounds (nextH, height) && maze[nextW][nextH] == 0 && !visited.contains(nextPoint)) {
					path.add(nextPoint);
					visited.add(nextPoint);
					prev.put(nextPoint, current);
				}
			}
		}
		return shortestPath;		
	}
	
	public void resetVisited() {
		this.visited = new HashSet<Point>();
	}
	public Point getGoal() {
		return goal;
	}
	
	public Point getStart () {
		return playerStart;
	} 
	
	public int[][] getMap() {
		return maze;
	}
	
	public static void main (String[] args) {
		Maze maze = new Maze(5,5);
		System.out.println("starting point: " + maze.getStart().toString());
		System.out.println("ending point: " + maze.getGoal().toString());
		Stack<Point> path = maze.shortestPath();
		while (!path.isEmpty()) {
			System.out.println(path.pop());
		}
	}
}