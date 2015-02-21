import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.AffineTransform;

/**
 * This class the starting menu panel
 * @author Vinh Doan
 *
 */
public class MenuPanel extends JPanel implements ActionListener {
	
	/**
	 * Width of panel
	 */
	public static final int DRAWING_WIDTH = Circ.DRAWING_WIDTH;
	/**
	 * Height of panels
	 */
	public static final int DRAWING_HEIGHT = Circ.DRAWING_HEIGHT;
	Circ w;
	private JLabel startText;
	
	/**
	 * Creates mini game panel
	 * @param w - the main Circ
	 */
	public MenuPanel(Circ w) {
		this.w = w;
		this.setLayout(new BorderLayout());
		Font f = new Font("SansSerif", Font.BOLD, 50);
		Font f3 = new Font("SansSerif", Font.BOLD, 20);
		/*
		JLabel topText = new JLabel("Circ");
		topText.setFont(f);
		JLabel topText2 = new JLabel("by Vinh Doan and Yuta Arai");
		topText2.setFont(f3);
		JPanel topTexts = new JPanel(new BorderLayout());
		topTexts.add(topText, BorderLayout.NORTH);
		topTexts.add(topText2 ,  BorderLayout.CENTER);
		*/
		
		JPanel startPanel = new JPanel();
		startText = new JLabel("Start");
		startPanel.add(startText);
		startText.setFont(f);
		startText.addMouseListener(new MouseHandler());
		//button.addActionListener(this);
	//	this.add(topTexts,BorderLayout.NORTH);
		this.add(startPanel,BorderLayout.SOUTH);
	}
	
	/**
	 * Draws the menu panel graphics
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

		//g2.drawLine(10, 10, 50, 20);
		String title = "Circ";
		String by = "by: Vinh Doan and Yuta Arai";
		Font f = new Font("SansSerif", Font.BOLD, 150);
		g2.setFont(f);
		FontMetrics fm = g2.getFontMetrics();
		g2.drawString(title,(int)( width /2)  - fm.stringWidth(title) / 2, (int)(height / 2) );
		Font f2 = new Font("SansSerif", Font.BOLD, 20);
		g2.setFont(f2);
		fm = g2.getFontMetrics();
		g2.drawString(by,(int)( width /2)  - fm.stringWidth(by) / 2, (int)(height / 2) + 25);
		g2.setStroke(new BasicStroke(10));
		g2.drawArc(width / 2 - 200, height / 2 - 230, height / 2, height / 2, 0, 360);
		
		g2.setTransform(at);
		repaint();
	}
	
	public void actionPerformed(ActionEvent e) {
		w.changePanel();
	}
	
	class MouseHandler implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JLabel s = (JLabel) arg0.getSource();
			if (s == startText)
				w.changePanel();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
}