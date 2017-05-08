import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JPanel;

public class Camera extends JPanel {

	private static final long serialVersionUID = 1L;
	private Portal goal;
	private BufferedImage image;
	private Game engine;
	private int[][] map;
	private boolean foundGoal;
	private double goalDist;
	public boolean test;
	public Stack<Point> test2;
	private final int FOV = 75;
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private final double ANGLE_INC = (double)FOV/(double)WIDTH;
	private final double PROJ_DIST = (HEIGHT/2) / Math.tan(Math.toRadians(FOV/2));
	private final int CENTER_HEIGHT = HEIGHT/2;
	
	public Camera(Game engine) {
		test = false;
		this.engine = engine;
		image = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	protected void draw () {
		Graphics g = this.getGraphics();
		Graphics buffG = image.getGraphics();
		if (!engine.isTitle()) {
			if (!engine.isShowMap()) {
				draw3D(buffG);
			} else {
				draw2D(buffG);
			}
			
		} else if (test) {
			draw2D(buffG, test2);
		}
		g.drawImage(image, 0, 0, engine);
		g.dispose();
	}
		
	private void draw2D(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.WHITE);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j] == 0) {
					g.fillRect((i*16), (j*16), 16, 16);
				}
			}
		}
		g.setColor(Color.RED);
		g.fillOval((int)engine.player.x/2 - 5, (int)engine.player.y/2 - 5, 10, 10);
		g.setColor(Color.BLUE);
		Point goal = this.goal.getPos();
		g.fillOval((int)goal.getX() * 16, (int)goal.getY() * 16, 10, 10);
		
//		Old raycasting test in 2D; no need to show on minimap
//		g.setColor(Color.BLUE);
//		for (int i = (WIDTH/2); i > -(WIDTH/2); i--) {
//			double angle = Math.toRadians(engine.player.direction + (i *ANGLE_INC));
//			double sin = Math.sin(angle);
//			double cos = Math.cos(angle);
//			double length = rayCast(engine.player.x, engine.player.y, angle, i, g);		
//			g.drawLine((int)engine.player.x, (int)engine.player.y, (int)(engine.player.x + length*sin), (int)(engine.player.y + length *cos));
//		}
	}
	private void draw2D(Graphics g, Stack<Point> test) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.WHITE);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j] == 0) {
					g.fillRect((i*16), (j*16), 16, 16);
				}
			}
		}
		g.setColor(Color.BLUE);		
		while (!test2.isEmpty()) {
			Point a = test2.pop();
			g.fillRect(((int)a.getX()*16), ((int)a.getY()*16), 16, 16);
		}
		g.setColor(Color.RED);
		g.fillOval((int)engine.player.x/2 - 5, (int)engine.player.y/2 - 5, 10, 10);
		g.setColor(Color.GREEN);
		Point goal = this.goal.getPos();
		g.fillOval((int)goal.getX() * 16, (int)goal.getY() * 16, 10, 10);
	}

	private void draw3D(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT/2);
		g.setColor(Color.PINK);
		g.fillRect(0, HEIGHT/2, WIDTH, HEIGHT/2);
		for (int i = -(WIDTH/2); i < (WIDTH/2); i++) {
			double angle = Math.toRadians(engine.player.direction + (i *ANGLE_INC));
			double length = rayCast(engine.player.x, engine.player.y, angle, i, g);
			drawWall(g, length, i+(WIDTH/2), angle);
			foundGoal = false;
		}
		g.setColor(Color.WHITE);
		g.setFont( new Font("Dialog",Font.BOLD, 36));
		g.drawString("Time: " + (int)engine.player.getTime(), WIDTH/32, HEIGHT/16);
	}
	
	private double rayCast (double x, double y,double angle, int i, Graphics g) {
		double length = 0;
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		int x2, y2;
		do {
			length += .1;
			x2 = (int) (x + length*sin);
			y2 = (int) (y + length*cos);
			if (!foundGoal) {
				Point coordinate = new Point(x2/32, y2/32);
				if (coordinate.equals(goal.getPos())) {
					foundGoal = true;
					goalDist = length;
				}
			}
		} while (map[x2/32][y2/32] != 1);
		return length;
	}
	
	private void drawWall(Graphics g, double dist, int x, double angle) {
		double relativeAngle = Math.toRadians(engine.player.direction) - angle;
		double adjDist = dist * Math.cos(relativeAngle);
        double wallHeight = (32*PROJ_DIST / (adjDist));
        int intensity = (int)(adjDist);
        if (intensity > 255) {
        	intensity = 255;
        }
        g.setColor(new Color(255-intensity,0,255-intensity));
//        g.drawLine(Math.abs(x-799), CENTER_HEIGHT - (int)wallHeight, Math.abs(x-799), CENTER_HEIGHT + (int)wallHeight);
        if (foundGoal && (adjDist > goalDist)) {
        	g.drawLine(Math.abs(x-799), CENTER_HEIGHT - (int)wallHeight, Math.abs(x-799), CENTER_HEIGHT + (int)wallHeight);
        	g.setColor(Color.BLUE);
        	drawGoal(g, x, angle);
        } else {
            g.drawLine(Math.abs(x-799), CENTER_HEIGHT - (int)wallHeight, Math.abs(x-799), CENTER_HEIGHT + (int)wallHeight);
        }
	}
	
	private void drawGoal(Graphics g, int x, double angle) {
        double goalHeight = (8*PROJ_DIST / (goalDist));
        g.drawLine(Math.abs(x-799), CENTER_HEIGHT - (int)(goalHeight), Math.abs(x-799), CENTER_HEIGHT + (int)(goalHeight));
	}

	protected void setMap(int[][] map, Portal goal) {
		this.map = map;
		this.goal = goal;
	}
}