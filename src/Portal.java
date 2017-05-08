import java.awt.Point;
import javax.swing.JOptionPane;

public class Portal implements GameObject {
	private Point loc;
	private Game engine;
	
	public Portal (Point goal, Game engine) {
		this.loc = goal;
		this.engine = engine;
	}
	
	@Override
	public void update() {
		if (this.getPos().distance(engine.player.getPos()) == 0) {
			String message = "You did it! You finished the map in " + (int)engine.player.getTime() + " seconds!";
			JOptionPane.showMessageDialog(engine, message, "Congratulations!!", JOptionPane.PLAIN_MESSAGE);
			System.out.println("You're winner");
			engine.setIsTitle(true);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
	}

	@Override
	public Point getPos() {
		return loc;
	}
}