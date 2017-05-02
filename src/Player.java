import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener {
	double x, y, xDir, yDir, direction;
	
	double moveSpeed = .5;
	
	int[][] map;
	boolean forward, backward, left, right;
	
	public Player (int x, int y, int direction, int[][] map,Component c) {
		this.x = x;
		this.y = y;
		this.xDir = x;
		this.yDir = y;
		this.direction = direction;
		this.map = map;
		c.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent keypress) {
		if((keypress.getKeyCode() == KeyEvent.VK_A))
			left = true;
		if((keypress.getKeyCode() == KeyEvent.VK_D))
			right = true;
		if((keypress.getKeyCode() == KeyEvent.VK_W))
			forward = true;
		if((keypress.getKeyCode() == KeyEvent.VK_S))
			backward = true;		
	}

	@Override
	public void keyReleased(KeyEvent keyRelease) {
		if((keyRelease.getKeyCode() == KeyEvent.VK_A))
			left = false;
		if((keyRelease.getKeyCode() == KeyEvent.VK_D))
			right = false;
		if((keyRelease.getKeyCode() == KeyEvent.VK_W))
			forward = false;
		if((keyRelease.getKeyCode() == KeyEvent.VK_S))
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
			direction += 0.9;
		}
		if (right) {
			direction -= 0.9;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
