import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.Timer;

/**
 *  Represents PowerUp that doesn't spawn
 * @author Vinh Doan
 *
 */
public abstract class StillPowerUp extends Powerup{
	private Timer powerUpTimer;
	
	/**
	 * Creates StillPowerUp at x ,y
	 * @param filename - image name
	 * @param x - x position
	 * @param y - y position
	 */
	public StillPowerUp(String filename, int x, int y) {
		super(filename, x, y);
		powerUpTimer = new Timer(5000, new PuTimer());
	}

	public void setOn(boolean isOn) {
		super.setOn(isOn);
		if(!isOn)
			powerUpTimer.start();
	}
	
	/**
	 * Draws StillPowerUp
	 * @param g - Graphics
	 * @param io - The Image Observer
	 */
	public void draw(Graphics g, ImageObserver io) {
		g.drawImage(getImage(),(int)x,(int)y,(int)width,(int)height,io);
		if(!isOn())
		{
			int grayFill = 237;
			g.setColor(new Color(grayFill, grayFill,grayFill, 200));
			g.fillRect((int)x,(int)y,(int)width,(int)height);
		}
	}
	
	class PuTimer implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			setOn(true);
			powerUpTimer.stop();
			
		}
		
	}
	
}
