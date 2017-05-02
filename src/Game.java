import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame; 


public class Game extends JFrame implements Runnable {
	
	boolean isRunning;
	Player player;
	public BufferedImage image;
	public int[] pixels;
	public int[][] map;
	public Maze maze;
	
	final int PLAYER_HEIGHT = 32;
	final int GRID_SIZE = 64;
	final int FOV = 60;
	final int WIDTH = 800;
	final int HEIGHT = 600;
	
	public double center = (WIDTH/2) / Math.tan(Math.toRadians(FOV)/2);
	
	Insets insets;
	
	public void newMap() {
		maze = new Maze(8, 8);
		this.map = maze.generateMap();
	}
	
	public void run() {
		init();
		while(isRunning) {
			long frameTime = System.currentTimeMillis();
			update();
			
			frameTime = (1000 / 60) - (System.currentTimeMillis() - frameTime);
			
			if (frameTime > 0) {
				try {
					Thread.sleep(frameTime);
				} catch (Exception e) {
					
				}
			}
			render();
		}
		setVisible(false);
	}
	
//	public void render () {
//		BufferStrategy bs = getBufferStrategy();
//		if(bs == null) {
//			createBufferStrategy(3);
//			return;
//		}
//		Graphics g = bs.getDrawGraphics();
//		g.drawImage(image, insets.left, insets.top, image.getWidth(), image.getHeight(), this);
//
//
//		g.setColor(Color.BLUE);
//		for (int i = (-WIDTH/2); i < WIDTH/2; i+= 3) {
//			rayDraw(player.x, player.y, i, g);
//		}
//
//		bs.show();
//	}
	
	
// 	2D Raycasting test
	
	public void render () {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, insets.left, insets.top, image.getWidth(), image.getHeight(), this);
		
		g.setColor(Color.WHITE);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j] == 0) {
					g.fillRect((i*32), (j*32), 32, 32);
				}
			}
		}
		
		g.setColor(Color.RED);
		g.fillOval((int)player.x - 10, (int)player.y - 10, 20, 20);

		g.setColor(Color.PINK);
		g.fillOval((int)maze.goal.getX() * 32, (int)maze.goal.getY() * 32, 20, 20);
		
		g.setColor(Color.BLUE);
		for (int i = -30; i < 30; i++) {
			rayDraw(player.x, player.y, i, g);
		}

		bs.show();
	}
	
	public void draw() {
		
	}
	
	public double rayDraw (double x, double y, int i, Graphics g) {
		double length = 0;
		double angle = Math.toRadians(player.direction + i);
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		
		int px, py;
		
		do {
			length += .1;
			px = (int) (x + length*sin);
			py = (int) (y + length*cos);
			
		} while (map[px/32][py/32] != 1);
		// drawWall(g, length, i, angle);
		g.drawLine((int)x, (int)y, (int)(x + length*sin), (int)(y + length *cos));

		return length;
	}
	
	public void drawWall(Graphics g, double dist, int x, double angle) {
        int wallHeight = (int) (64 * 400 / (dist * Math.cos(angle)));

        g.setColor(Color.BLUE);
        g.drawLine(x, HEIGHT / 2 - wallHeight, x, HEIGHT / 2 + wallHeight);
	}
	
	public void init() {
		setTitle("3D Maze");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		insets = getInsets();
		setSize(insets.left + WIDTH + insets.right, insets.top + HEIGHT + insets.bottom);
		
		image = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		newMap();
		player = new Player(48, 48, 0, map, this);

		start();
	}
	
	public void update() {
		player.update();
		render();
	}
	
	public void start() {
		if (isRunning) {
			return;
		}
		isRunning = true;
		new Thread(this).start();
	}
	
	public void stop () {
		if (!isRunning) {
			return;
		}
		isRunning = false;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
		System.exit(0);
	}
}