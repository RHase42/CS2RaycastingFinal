import java.awt.Point;

public abstract class Actor implements GameObject {
	double x, y, direction;
	double moveSpeed = 1.1;
	double rotSpeed = 2.5;
	Point pos;
	
	@Override
	public Point getPos() {
		return pos;
	}
	
	public void setPos(double x, double y) {
		pos = new Point((int)x, (int)y);
	}
}