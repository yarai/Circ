import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * This class represents the BoostPad powerup.
 * @author Yuta Arai
 *
 */
public class BoostPad extends StillPowerUp{

	/**
	 * Creates a default instance of the BoostPad with
	 * the location set to (x,y) and the image set to
	 * "BoostPad.png"
	 * @param x - the x-value of the top left corner of BoostPad
	 * @param y - the y-value of the top left corner of BoostPad
	 */
	public BoostPad(int x, int y) {
		super("BoostPad.png", x, y);
	}
}
