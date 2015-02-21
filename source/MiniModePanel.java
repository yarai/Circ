import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * This class represents the small mode panels within ModePanel
 * @author Vinh Doan
 *
 */

public class MiniModePanel extends JPanel{

	private static final double DRAWING_WIDTH = Circ.DRAWING_WIDTH / 2 - 5;
	private static final double DRAWING_HEIGHT = Circ.DRAWING_HEIGHT - 115;

	private int gameNum;
	private boolean selected;
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	private ButtonGroup bg;
	private JRadioButton[] rbs;

	/**
	 * Creates mini mode panel
	 * @param num - which panel
	 * @param t - if the panel is selected
	 */
	public MiniModePanel(int num, boolean t)
	{
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());

		gameNum = num;
		selected = t;
		repaint();
	}


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);  // Call JPanel's paintComponent method to paint the background

		Graphics2D g2 = (Graphics2D)g;

		int width = getWidth();
		int height = getHeight();

		double ratioX = (double)width/DRAWING_WIDTH;
		double ratioY = (double)height/DRAWING_HEIGHT;

		AffineTransform at = g2.getTransform();
		g2.scale(ratioX, ratioY);

		g2.setColor(Color.WHITE);

		Font f = new Font("SansSerif", Font.BOLD, 50);
		g2.setFont(f);
		if(selected)
		{
			g2.setStroke(new BasicStroke(9));
			g2.drawRect(1, 3, width - 3, height+1);
		}
		
		//g2.drawImage((new ImageIcon("BallShade.png")).getImage(), width / 2 - 90, height / 4, 180, 150, this);
		String modeName = "";
		String gameDescript = "";
		String nl = System.getProperty("line.separator");
		if (gameNum == 1)
		{
			modeName = "Last Man";
			
			gameDescript =  "* Each round, some players are marked\n" +
							"* Marked players can pass on thier mark\n" +
							"* When rounds end, those marked lose and die.\n" +
							"* The rounds go until there is one last winner!";
		}
			
		
		else if (gameNum == 2)
		{
			modeName = "Shark Mode";
			gameDescript = "* This mode is based off “Sharks and\n   Minnows”\n" +
							"* In this game, the stage has two ends, red and blue.\n" +
							"* At the beginning, one player is made the shark\n" + 
							"* Then, non-shark players try to make it from\n  red to blue.\n" + 
							"* If a shark tags a player, they die and become a\n  shark the next round.\n" + 
							"* After times run out, those not in blue die and,\n become sharks for the next round begins\n" + 
							"* The next round, remaining non-sharks go from\n  blue to red, as  sharks try to tag them.\n" +
							"* Then rounds alternate between zones\n" +
							"* The rounds go until there is one last winner!";
			
		}
		FontMetrics fm = g2.getFontMetrics();
		g2.drawString(modeName,(int)( width / 2)  - fm.stringWidth(modeName) / 2, (int)(height / 8) );
		g2.setFont(new Font("SansSerif", Font.BOLD, 23) );
		drawString(g,gameDescript, (int)( width / 30), (int)(height  / 7)  );
		//g2.drawString(gameDescript,(int)( width / 2)  - fm.stringWidth(modeName) / 2, (int)(height  / 2) );

		repaint();
	}
	
	 private void drawString(Graphics g, String text, int x, int y) {
         for (String line : text.split("\n"))
             g.drawString(line, x, y += g.getFontMetrics().getHeight());
     }

}
