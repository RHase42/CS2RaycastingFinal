/**
 * Abstract class that is used as parent for the Player and Bot class
 * @author Robin A. and Zach D.
 */
import java.awt.Point;

public abstract class Actor implements GameObject {
	
	double time;
	double x, y, direction;
	double moveSpeed = 1.1;
	double rotSpeed = 2;
	Point pos;
	
	/* Return Actor's current position
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
	
	/**
	 * Resets the turn to keep it within the bounds of 0-360 degrees
	 * @param angle - angle to normalize
	 * @return - normalized angle
	 */
	public double normalizeTurn(double angle) {
		double a = angle % 360;
		if (a < 0) {
			a += 360;
		}
		return a;
	}
	
	/**
	 * @param time - adds time to current time pool, after converting from milliseconds to seconds
	 */
	protected void addTime(double time) {
		this.time += time/1000;
	}
	
	/**
	 * @return - returns current time player has been in maze for
	 */
	protected double getTime() {
		return time;
	}
}