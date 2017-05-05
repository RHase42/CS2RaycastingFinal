import java.awt.*;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private boolean isRunning, isTitle, isShowMap;
	private int[][] map;
	private Maze maze;
	private Point playerStart;
	private Title titleScreen;
	private Raycasting raycast;
	Player player;

	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private final int FPS = 60;

	void newMap(int x, int y) {
		maze = new Maze(x, y);
		this.map = maze.generateMap();
		this.playerStart = maze.getStart();
		raycast.setMap(map, maze);
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
		this.raycast = new Raycasting(this);
		this.add(raycast);
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
			if (frameTime > 0) {
				try {
					Thread.sleep(frameTime);
				} catch (Exception e) {}
			}
			if (!isTitle) {
				player.update();
				raycast.draw();
			}
		}
	}

	private void start() {
		if (isRunning) {
			return;
		}
		isRunning = true;
		new Thread(this).start();
	}
	
	void stop() {
		if (!isRunning) {
			return;
		}
		isRunning = false;
	}

	boolean isShowMap() {
		return isShowMap;
	}
	
	boolean isTitle() {
		return isTitle;
	}
	
	void setShowMap(boolean isMap) {
		this.isShowMap = isMap;
	}
	
	void setIsTitle(boolean isTitle) {
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