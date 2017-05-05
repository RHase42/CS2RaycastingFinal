import java.awt.Point;

public abstract class Actor implements GameObject {
	double x, y, direction;
	double moveSpeed = .75;
	double rotSpeed = 1.5;
	
	@Override
	public Point getPos() {
		return new Point((int)x, (int)y);
	}
}
