import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;

import javax.swing.Timer;

/**
 * This class represents the Bomb powerup.
 * @author Yuta Arai
 */
public class Bomb extends StillPowerUp{

	/**
	 * Creates a default instance of the Bomb with
	 * the location set to (x,y) and the image set to
	 * "TNT.png"
	 * @param x - the x-value of the top left corner of Bomb
	 * @param y - the y-value of the top left corner of Bomb
	 */
	public Bomb(int x, int y) {
		super("TNT.png", x, y);
		
		
	}
	
}
