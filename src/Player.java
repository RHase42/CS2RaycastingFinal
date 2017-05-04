import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener {
	double x, y, startX, startY,direction;
	Game engine;
	private double moveSpeed = .75;
	private double rotSpeed = 1.5;
	
	int[][] map;
	boolean forward, backward, left, right;
	
	public Player (int x, int y, int direction, int[][] map, Game engine, Component t) {
		this.x = x;
		this.startX = x;
		this.y = y;
		this.startY = y;
		this.direction = direction;
		this.map = map;
		this.engine = engine;
		engine.addKeyListener(this);
		t.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent keypress) {
		if(keypress.getKeyCode() == KeyEvent.VK_A)
			left = true;
		if(keypress.getKeyCode() == KeyEvent.VK_D)
			right = true;
		if(keypress.getKeyCode() == KeyEvent.VK_W)
			forward = true;
		if(keypress.getKeyCode() == KeyEvent.VK_S)
			backward = true;		
		if (keypress.getKeyCode() == KeyEvent.VK_R) {x=startX;y=startY;direction=0;}
		if (keypress.getKeyCode() == KeyEvent.VK_ESCAPE) {engine.setIsTitle(true);}
			
	}

	@Override
	public void keyReleased(KeyEvent keyRelease) {
		if(keyRelease.getKeyCode() == KeyEvent.VK_A)
			left = false;
		if(keyRelease.getKeyCode() == KeyEvent.VK_D)
			right = false;
		if(keyRelease.getKeyCode() == KeyEvent.VK_W)
			forward = false;
		if(keyRelease.getKeyCode() == KeyEvent.VK_S)
			backward = false;		
	}
	
	public void update() {
		if (forward) {
			double prevX, prevY;
			prevX = x; prevY = y;
			x -= Math.cos(Math.toRadians(direction + 90)) * moveSpeed;
			y += Math.sin(Math.toRadians(direction + 90)) * moveSpeed;
			
			if (map[(int)x/32][(int)y/32] == 1) {
				x = prevX;
				y = prevY;
			}
		}
		if (backward) {
			double prevX, prevY;
			prevX = x; prevY = y;
			x += Math.cos(Math.toRadians(direction + 90)) * moveSpeed;
			y -= Math.sin(Math.toRadians(direction + 90)) * moveSpeed;
			
			if (map[(int)x/32][(int)y/32] == 1) {
				x = prevX;
				y = prevY;
			}
		}
		if (left) {
			direction -= rotSpeed;
			direction = normalizeTurn(direction);
		}
		if (right) {
			direction += rotSpeed;
			direction = normalizeTurn(direction);
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
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
