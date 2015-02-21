import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * This class represents the small game panels within GamePanel to allow split screen happen
 * @author Vinh Doan
 *
 */
public class MiniGamePanel extends JPanel{
	/**
	 * Width of panel
	 */
	public static int DRAWING_WIDTH = Circ.DRAWING_WIDTH / 2 - 5;
	/**
	 * Height of panels
	 */
	public static int DRAWING_HEIGHT = Circ.DRAWING_HEIGHT / 2 - 5;

	private Ball[] balls;
	private ArrayList<Rectangle2D.Double> obstacles;
	private ArrayList<Rectangle> graySqaures;
	private ArrayList<Rectangle2D.Double> zone1;
	private ArrayList<Rectangle2D.Double> zone2;
	private ArrayList<Powerup> powerUps;
	private ArrayList<Powerup> stillPowerUps;
	private Rectangle[] gridLines;
	private Ball theBall;
	private boolean on;
	private int num;
	private int sizeKind;
	
	private int centerX;
	private int centerY;

	/**
	 * Creates mini game panel
	 * @param num - which panel
	 */
	public MiniGamePanel(int num)
	{
		this.setBackground(Color.DARK_GRAY);
		this.num = num;
		on = false;
		this.addComponentListener(new ResizeListener());
		
		//this.setVisible(false);
	}
	
	/**
	 * Adjust size of panel to accomodate for how many players there are
	 * @param n - amount of players
	 */
	public void setOtherSizes(int n)
	{
		
		sizeKind = n;
		if (n == 1)
		{
			DRAWING_WIDTH = Circ.DRAWING_WIDTH;
			DRAWING_HEIGHT = Circ.DRAWING_WIDTH;
		}
		else if (n == 2)
		{
			DRAWING_WIDTH = Circ.DRAWING_WIDTH / 2 - 5;
			DRAWING_HEIGHT = Circ.DRAWING_WIDTH;
		}
		int width = getWidth();
		int height = getHeight();

		centerX = width / 2 - 20;
		centerY = height / 2 - 20;
		repaint();
		
	}
	
	public void off()
	{
		on = false;
	}

	public void setThings(Ball[] b,  ArrayList<Rectangle2D.Double> o, Rectangle[] g , ArrayList<Rectangle> gs, ArrayList<Rectangle2D.Double> z1, ArrayList<Rectangle2D.Double> z2,  ArrayList<Powerup> pu,  ArrayList<Powerup> spu)
	{
		balls = b;
		obstacles = o;
		gridLines = g;
		graySqaures = gs;
		zone1 = z1;
		zone2 = z2;
		powerUps = pu;
		stillPowerUps = spu;
		on = true;
	}
	
	public void setBall(Ball b)
	{
		theBall = b;
	}

	/**
	 * Draws the mini game panel
	 * @param g - the graphics object that the image will be drawn with.
	 */
	public void paintComponent(Graphics g)
	{
		if (on)
		{
			super.paintComponent(g);  // Call JPanel's paintComponent method to paint the background

			Graphics2D g2 = (Graphics2D)g;
			
			int newdx = centerX - (int)(theBall.getX());
			int newdy = centerY - (int)(theBall.getY());
			g.translate(newdx,newdy);
			
			g.setColor(new Color(205,102,29));
			for (Shape s : obstacles) {
				if(s instanceof Wall)
					((Wall) s).draw(g2, this);
				else
					g2.fill(s);
			}

			int grayFill = 237;
			g.setColor(new Color(grayFill, grayFill,grayFill));
			for (Shape s : graySqaures) {
					g2.fill(s);
			}
			g.setColor(new Color(255, 0, 0, 125));
			for (Shape s : zone1) {
					g2.fill(s);
			}
			g.setColor(new Color(0, 0, 255, 125));
			for (Shape s : zone2) {
					g2.fill(s);
			}
			
			
			for (Powerup p: powerUps)
			{
				p.draw(g2, this);
			}
			for (Powerup p: stillPowerUps)
			{
				StillPowerUp sp = (StillPowerUp)p;
				sp.draw(g2, this);
			}
			g.setColor(Color.DARK_GRAY);
			for (Rectangle r: gridLines)
			{
				g2.fill(r);
			}
			
			//g.setColor(new Color(50,150,140));
			g.setColor(Color.BLACK);
			for (int i = 0; i < balls.length; i++)
			{
				if(!balls[i].isDead() || balls[i].equals(balls[num]))
					balls[i].draw(g2,this);
			}
		}

	}
	
	class ResizeListener implements ComponentListener
	{

		

		@Override
		public void componentHidden(ComponentEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentMoved(ComponentEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentResized(ComponentEvent arg0) {
			int width = getWidth();
			int height = getHeight();

			centerX = width / 2 - 20;
			centerY = height / 2 - 20;
			
		}

		@Override
		public void componentShown(ComponentEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
