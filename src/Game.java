import java.awt.*;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private boolean isRunning, isTitle, isShowMap;
	private Point playerStart;
	private Title titleScreen;
	protected Camera raycast;
	protected Maze maze;
	protected Player player;
	protected Portal goal;
	protected int[][] map;

	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private final int FPS = 60;

	protected void newMap(int x, int y) {
		maze = new Maze(x, y);
		this.map = maze.getMap();
		this.playerStart = maze.getStart();
		this.goal = new Portal(maze.getGoal(), this);
		raycast.setMap(map, goal);
		player = new Player((int)(playerStart.getX()*32) + 16, (int)(playerStart.getY()*32) + 16, 0, map, this, raycast);
		this.setShowMap(false);
	}
	
	private void init() {
		setTitle("3D Maze");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		this.titleScreen = new Title(WIDTH,HEIGHT, this);
		this.add(titleScreen);
		this.raycast = new Camera(this);
		this.add(raycast);
		this.map = null; 
		setShowMap(false);
		setIsTitle(true);
		setSize(WIDTH, HEIGHT);
		start();
	}

	public void run() {
		init();
		while(isRunning) {
			long frameTime = System.currentTimeMillis();
			frameTime = (1000 / FPS) - (System.currentTimeMillis() - frameTime);
			if (!isTitle) {
				player.update();
				raycast.draw();
				player.addTime(frameTime*1.3);
				goal.update();
			}
			if (frameTime > 0) {
				try {
					Thread.sleep(frameTime);
<<<<<<< HEAD
=======
					if (!isTitle) {
						player.update();
						raycast.draw();
						player.addTime(frameTime);
						goal.update();
					}
>>>>>>> 61f1f1e295209bcc464bc0fc32110cfdea60c705
				} catch (Exception e) {}
			}
		}
	}

	private void start() {
		if (isRunning) {
			return;
		}
		isRunning = true;
	}
	
	protected void stop() {
		if (!isRunning) {
			return;
		}
		isRunning = false;
	}

	protected boolean isShowMap() {
		return isShowMap;
	}
	
	protected boolean isTitle() {
		return isTitle;
	}
	
	protected void setShowMap(boolean isMap) {
		this.isShowMap = isMap;
	}
	
	protected void setIsTitle(boolean isTitle) {
		this.isTitle = isTitle;
		if (isTitle) {
			titleScreen.setActive(true);
			raycast.setEnabled(false);
			raycast.setVisible(false);
			raycast.transferFocus();
			titleScreen.requestFocus();
		} else {
			titleScreen.setEnabled(false);
			raycast.setEnabled(true);
			raycast.setVisible(true);
			titleScreen.transferFocus();
			raycast.requestFocus();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
		System.exit(0);
	}
}