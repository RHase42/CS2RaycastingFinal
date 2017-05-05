import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener {
	double x, y, startX, startY,direction;
	private double moveSpeed = .75;
	private double rotSpeed = 1.5;
	private int[][] map;
	private boolean[] keys;
	Game engine;

	public Player (int x, int y, int direction, int[][] map, Game engine, Component t) {
		this.x = x;
		this.startX = x;
		this.y = y;
		this.startY = y;
		this.direction = direction;
		this.map = map;
		this.engine = engine;
		this.keys = new boolean[255];
		t.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent keypress) {
		keys[keypress.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent keyRelease) {
		keys[keyRelease.getKeyCode()] = false;	
	}
	
	public void update() {
		if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
			double prevX, prevY;
			prevX = x; prevY = y;
			x -= Math.cos(Math.toRadians(direction + 90)) * moveSpeed;
			y += Math.sin(Math.toRadians(direction + 90)) * moveSpeed;
			
			if (map[(int)x/32][(int)y/32] == 1) {
				x = prevX;
				y = prevY;
			}
		}
		if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
			double prevX, prevY;
			prevX = x; prevY = y;
			x += Math.cos(Math.toRadians(direction + 90)) * moveSpeed;
			y -= Math.sin(Math.toRadians(direction + 90)) * moveSpeed;
			
			if (map[(int)x/32][(int)y/32] == 1) {
				x = prevX;
				y = prevY;
			}
		}
		if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
			direction -= rotSpeed;
			direction = normalizeTurn(direction);
		}
		if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
			direction += rotSpeed;
			direction = normalizeTurn(direction);
		}
		if (keys[KeyEvent.VK_R]) {
			x=startX; y=startY; direction=0;
		}
		if (keys[KeyEvent.VK_ESCAPE]) {
			engine.setIsTitle(true);
		}
		if (keys[KeyEvent.VK_M]) {
			System.out.println("Map set to " + !engine.isShowMap());
			engine.setShowMap(!engine.isShowMap());
			keys[KeyEvent.VK_M] = false;
		}
	}

	public double normalizeTurn(double angle) {
		double a = angle % 360;
		if (a < 0) {
			a += 360;
		}
		return a;
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {}
}