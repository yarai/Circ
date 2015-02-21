
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import javax.swing.*;

/**
 * Represents an Image that can be moved in a Panel.
 * @author Yuta Arai
 * @author Vinh Doan
 *
 */
public class MovingImage extends Rectangle2D.Double{
	
	private Image image;
	

	private int squareLen = 40;
	
	/**
	 * Creates a default instance of a MovingImage with the filename
	 * of the image.
	 * @param filename - the name of the image file
	 * @param x - the x-value of the top left point of the Rectangle
	 * @param y - the y-value of the top left point of the Rectangle
	 * @param w - the width of the Rectangle
	 * @param h - the height of the Rectangle
	 */
	public MovingImage(String filename, int x, int y, int w, int h) {
		this((new ImageIcon(filename)).getImage(),x,y,w,h);
	}
	/**
	 * Creates a default instance of a MovingImage with the an image object.
	 * @param img - the Image used for the MovingImage
	 * @param x - the x-coordinate of the top left point of the Rectangle
	 * @param y - the y-coordinate of the top left point of the Rectangle
	 * @param w - the width of the Rectangle
	 * @param h - the height of the Rectangle
	 */
	public MovingImage(Image img, int x, int y, int w, int h) {
		super(x,y,w,h);
		image = img;
	}

	
	/**
	 * Moves the location of the Image to a specific location.
	 * @param x - the x-coordinate of the destination
	 * @param y - the y-coordinate of the destination
	 */
	public void moveToLocation(int x, int y) {
		super.x = x;
		super.y = y;
	}
	
	/**
	 * Moves the image by a specific amount.
	 * @param x - the amount the image will be moved in the x direction
	 * @param y - the amount the image will be moved in the y direction
	 */
	public void moveByAmount(int x, int y) {
		super.x += x;
		super.y += y;
	}
	
	/**
	 * Sets a limit to the size of the window.
	 * @param windowWidth - the horizontal limit of the window
	 * @param windowHeight - the vertical limit of the window
	 */
	public void applyWindowLimits(int windowWidth, int windowHeight) {
		x = Math.min(x,windowWidth-width);
		y = Math.min(y,windowHeight-height);
		x = Math.max(0,x);
		y = Math.max(0,y);
	}
	
	/**
	 * Draws the image onto the specified graphics object.
	 * @param g - the graphics object that the image will be drawn on.
	 * @param io - the updater of the image being created
	 */
	public void draw(Graphics g, ImageObserver io) {
		g.drawImage(image,(int)x,(int)y,(int)width,(int)height,io);
	}
	
	public void setImage(String filename) {
		this.image = (new ImageIcon(filename)).getImage();
	}
	
	public Image getImage() {
		return image;
	}
	
	
}










