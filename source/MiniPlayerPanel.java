import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.*;

/**
 * This class represents the small mode panels within PlayerPanel
 * @author Vinh Doan
 *
 */

public class MiniPlayerPanel extends JPanel{
	
	private static final double DRAWING_WIDTH = Circ.DRAWING_WIDTH / 2 - 5;
	private static final double DRAWING_HEIGHT = Circ.DRAWING_HEIGHT / 2 - 55;

	private int playerNum;
	private int playerType;
	
	private ButtonGroup bg;
	private JRadioButton[] rbs;
	private JTextField nameLabel;
	
	
	/**
	 * Creates mini player panel
	 * @param num - which panel
	 */
	public MiniPlayerPanel(int num)
	{
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());
		
		JPanel buttons = new JPanel();
		buttons.setBackground(Color.DARK_GRAY);
		buttons.setLayout(new GridLayout(0,4));
		JLabel nameStuff1 = new JLabel(" Choose Type:");
		nameStuff1.setForeground(Color.WHITE);
		buttons.add(nameStuff1);
		bg = new ButtonGroup();
		playerType = 0;
		rbs = new JRadioButton[]{new JRadioButton("Human", true),
				new JRadioButton("Computer"), new JRadioButton("None")};
		for(JRadioButton rb: rbs)
			bg.add(rb);
		for(JRadioButton rb: rbs)
		{
			rb.setForeground(Color.WHITE);
			rb.setBackground(Color.DARK_GRAY);
			buttons.add(rb);
			rb.addActionListener(new RadioButtonHandler());
		}
		
		add(buttons, BorderLayout.NORTH);
		
		JPanel bottomName = new JPanel();
		bottomName.setBackground(Color.DARK_GRAY);
		bottomName.setLayout(new GridLayout(0,2));
		JLabel nameStuff = new JLabel(" Enter Player " + num + " Name: (8 character MAX)");
		nameStuff.setForeground(Color.WHITE);
		bottomName.add(nameStuff);
		nameLabel = new JTextField("Name " + num , 30);
		bottomName.add(nameLabel);
		
		add(bottomName, BorderLayout.SOUTH);
		playerNum = num;
		repaint();
		
	}
	
	public String getName()
	{
		return nameLabel.getText();
	}
	
	public int getPlayerType()
	{
		return playerType;
	}
	
	/**
	 * Draws the mini player panel
	 * @param g - the graphics object that the image will be drawn with.
	 */
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
		//g2.drawLine(10, 10, 50, 20);
		
		Font f = new Font("SansSerif", Font.BOLD, 30);
		g2.setFont(f);
		g2.drawImage((new ImageIcon("BallShade.png")).getImage(), width / 4, height / 2 - 100, 240, 200, this);
	
		FontMetrics fm = g2.getFontMetrics();
		String playerWNum = "Player " + playerNum;
		//g.drawString(timeString, w - fm.stringWidth(timeString) / 2, h );
		g2.drawString("Player " + playerNum,(int)( width / 2)  - fm.stringWidth(playerWNum) / 2, (int)(height / 6) );
		
		String[] keyNames;
		if (playerNum == 1)
			keyNames = new String[]{"W","A","S","D"};
		else if (playerNum == 2)
			keyNames = new String[]{"I","J","K","L"};
		else if (playerNum == 3)
			keyNames = new String[]{"T","F","G","H"};
		else
			keyNames = new String[]{"^","<","v",">"};
		g2.fillRect((int)(width * .75) - 25,(int)( height  / 2) + 5, 50, 50);
		g2.fillRect((int)(width * .75) - 25,(int)( height  / 2) - 50, 50, 50); // up
		g2.fillRect((int)(width * .75)  + 30,(int)( height  / 2) + 5, 50, 50);
		g2.fillRect((int)(width * .75) - 80,(int)( height  / 2) + 5, 50, 50);
		g2.setColor(Color.black);
		g2.drawString(keyNames[0], (int)(width * .75) - fm.stringWidth(keyNames[0]) / 2, (int)( height  / 2) - 15);
		for (int i = 0; i < 3; i++)
		{
			g2.drawString(keyNames[i + 1], (int)(width * .75) - fm.stringWidth(keyNames[i + 1]) / 2 - 55 + 55 * i, (int)( height  / 2) + 40);
		}
		
		repaint();
	}
	
	
	class RadioButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			Object jb = (JRadioButton) arg0.getSource();
			if (jb == rbs[0])
			{
				playerType = 0;
			}
			else if (jb == rbs[1])
			{
				playerType = 1;
			}
			else if(jb == rbs[2])
			{
				playerType = 2;
			}
			repaint();
		}

	}
	
}
