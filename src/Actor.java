/**
 * Abstract class that is used as parent for the Player, PathfinderBot, and (never implemented) Enemy class
 * @author Robin A. and Zach D.
 */
import java.awt.Point;

public abstract class Actor implements GameObject {
	double x, y, direction;
	double moveSpeed = 1.1;
	double rotSpeed = 2.5;
	Point pos;
	
	/* 
	 * Return Actor's current position
	 * (non-Javadoc)
	 * @see GameObject#getPos()
	 */
	@Override
	public Point getPos() {
		return pos;
	}
	
	/**
	 * @param x - location of X for Actor
	 * @param y - location of Y for Actor
	 */
	public void setPos(double x, double y) {
		pos = new Point((int)x, (int)y);
	}
}