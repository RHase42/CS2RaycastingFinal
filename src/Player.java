import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Actor implements KeyListener {
	double startX, startY;
	private int[][] map;
	private boolean[] keys;
	Game engine;
	double time;

	public Player (int x, int y, int direction, int[][] map, Game engine, Component t) {
		this.time = 0;
		this.x = x;
		this.startX = x;
		this.y = y;
		this.startY = y;
		this.setPos(x/32,  y/32);
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
			} else {
				this.setPos(x/32, y/32);
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
			direction += rotSpeed;
			direction = normalizeTurn(direction);
		}
		if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
			direction -= rotSpeed;
			direction = normalizeTurn(direction);
		}
		if (keys[KeyEvent.VK_R]) {
			x=startX; y=startY; direction=0;
			this.time = 0;
		}
		if (keys[KeyEvent.VK_ESCAPE]) {
			engine.setIsTitle(true);
		}
		if (keys[KeyEvent.VK_M]) {
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
	
	public void addTime(double time) {
		this.time += time/1000;
	}
	
	public double getTime() {
		return time;
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {}
}