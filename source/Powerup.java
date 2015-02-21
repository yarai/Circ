import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 *  Represents a game power up
 * @author Yuta Arai
 *
 */
public class Powerup extends MovingImage{
	private boolean isOn;
	
	/**
	 * Creates StillPowerUp at x ,y
	 * @param filename - image name
	 * @param x - x position
	 * @param y - y position
	 */
	public Powerup(String filename, int x, int y) {
		super(filename, x, y, 40, 40);
		isOn = true;
	}	
	
	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
		
	}
	
	/**
	 * Draws PowerUp
	 * @param g - Graphics
	 * @param io - The Image Observer
	 */
	public void draw(Graphics g, ImageObserver io) {
		if(isOn())
		{
			
			g.drawImage(getImage(),(int)x,(int)y,(int)width,(int)height,io);
		}
	}
}
