import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Portal implements GameObject {
	Point loc;
	BufferedImage img;	
	Game engine;
	
	public Portal (Point goal, Game engine) {
		this.loc = goal;
		this.engine = engine;
	    try
	    {
	      img = ImageIO.read(new File("res/portal.png"));
	    }
	    catch (IOException e)
	    {}
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
