import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame; 


public class Game extends JFrame implements Runnable {
	
	boolean isRunning;
	Player player;
	public BufferedImage image;
	public int[][] map;
	public Maze maze;
	
	final int PLAYER_HEIGHT = 32;
	final int WALL_SIZE = 64;
	final int FOV = 60;
	final int WIDTH = 800;
	final int HEIGHT = 600;
	final int FPS = 60;
	final double ANGLE_INC = (double)FOV/(double)WIDTH;
	final double PROJ_DIST = (WIDTH/2) / Math.tan(Math.toRadians(FOV/2));
	final int CENTER_WIDTH = WIDTH/2;
	final int CENTER_HEIGHT = HEIGHT/2;
	
	
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
			frameTime = (1000 / FPS) - (System.currentTimeMillis() - frameTime);
			
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
		
	public void render () {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics buff = bs.getDrawGraphics();
		Graphics g = image.getGraphics();
		draw3D(g);
		
		buff.drawImage(image, insets.left, insets.top, image.getWidth(), image.getHeight(), this);
		bs.show();
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
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
		for (int i = -(WIDTH/2); i < (WIDTH/2); i++) {
			double angle = Math.toRadians(player.direction + (i *ANGLE_INC));
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);
			double length = rayCast(player.x, player.y, angle, i, g);		
			g.drawLine((int)player.x, (int)player.y, (int)(player.x + length*sin), (int)(player.y + length *cos));

		}
	}
	
	public void draw3D(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT/2);
		
		g.setColor(Color.GRAY);
		g.fillRect(0, HEIGHT/2, WIDTH, HEIGHT/2);
		
		g.setColor(Color.BLUE);
		for (int i = -(WIDTH/2); i < (WIDTH/2); i++) {
			double angle = Math.toRadians(player.direction + (i *ANGLE_INC));
			double length = rayCast(player.x, player.y, angle, i, g);
			drawWall(g, length, i+400, angle);
		}
	}
	
	public double rayCast (double x, double y,double angle, int i, Graphics g) {
		double length = 0;
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		int x2, y2;
		do {
			length += .1;
			x2 = (int) (x + length*sin);
			y2 = (int) (y + length*cos);
		} while (map[x2/32][y2/32] != 1);

		return length;
	}
	
	public void drawWall(Graphics g, double dist, int x, double angle) {
		double relativeAngle = Math.toRadians(player.direction) - angle;
		double adjDist = dist * Math.cos(relativeAngle);
        double wallHeight = (32*CENTER_HEIGHT / (adjDist));
        g.setColor(Color.BLUE);
        g.drawLine(x, CENTER_HEIGHT - (int)wallHeight, x, CENTER_HEIGHT + (int)wallHeight);
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
		newMap();
		player = new Player(48, 48, 0, map, this);

		start();
	}
	
	public void update() {
		player.update();
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
