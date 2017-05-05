import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Raycasting extends JPanel {

	private static final long serialVersionUID = 1L;
	private Point goal;
	private BufferedImage image;
	private Game engine;
	private int[][] map;
	private final int FOV = 75;
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private final double ANGLE_INC = (double)FOV/(double)WIDTH;
	private final double PROJ_DIST = (HEIGHT/2) / Math.tan(Math.toRadians(FOV/2));
	private final int CENTER_HEIGHT = HEIGHT/2;
	
	public Raycasting(Game engine) {
		this.engine = engine;
		image = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	public void draw () {
		Graphics g = this.getGraphics();
		Graphics buffG = image.getGraphics();
		if (!engine.isTitle()) {
			if (!engine.isShowMap()) {
				draw3D(buffG);
			} else {
				draw2D(buffG);
			}
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
		g.setColor(Color.GREEN);
		g.fillOval((int)goal.getX() * 16, (int)goal.getY() * 16, 10, 10);
		
//		Old raycasting test in 2D; no need to show on minimap
//		g.setColor(Color.BLUE);
//		for (int i = (WIDTH/2); i > -(WIDTH/2); i--) {
//			double angle = Math.toRadians(player.direction + (i *ANGLE_INC));
//			double sin = Math.sin(angle);
//			double cos = Math.cos(angle);
//			double length = rayCast(player.x, player.y, angle, i, g);		
//			g.drawLine((int)player.x, (int)player.y, (int)(player.x + length*sin), (int)(player.y + length *cos));
//		}
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
		}
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
        g.drawLine(x, CENTER_HEIGHT - (int)wallHeight, x, CENTER_HEIGHT + (int)wallHeight);
	}

	void setMap(int[][] map, Maze maze) {
		this.map = map;
		this.goal = maze.getGoal();
	}
}