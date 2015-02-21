import java.awt.CardLayout;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class contains the main method.
 * @author Vinh Doan
 *
 */

public class Circ extends JFrame{
 
	JPanel cardPanel;
	/**
	 * Width of panel
	 */
	public static int DRAWING_WIDTH = 1240;
	/**
	 * Height of panels
	 */
	public static int DRAWING_HEIGHT = 760;
	
	/**
	 * Creates Circ
	 * @param title - The title name of the frame
	 */
	public Circ(String title) {
		super(title);
		int fullWidth = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
		int fullHeight = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
		DRAWING_WIDTH = fullWidth;
		DRAWING_HEIGHT = fullHeight;
		
		setBounds(fullWidth / 2 - DRAWING_WIDTH / 2, 0, fullWidth, fullHeight);
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    setVisible(false);
	    cardPanel = new JPanel();
	    CardLayout cl = new CardLayout();
	    cardPanel.setLayout(cl);
	    
		MenuPanel panel1 = new MenuPanel(this);    
		PlayerPanel panel2 = new PlayerPanel(this);
		GamePanel panel4 = new GamePanel(this);
		ModePanel panel3 = new ModePanel(this, panel4);
		
	    
	    addKeyListener(panel4);
	
	    cardPanel.add(panel1,"1");
	    cardPanel.add(panel2,"2");
	    cardPanel.add(panel3,"3");
	    cardPanel.add(panel4,"4");
	    
	    add(cardPanel);
	    setVisible(true);
	   // setResizable(false);
	    
	}

	/**
	 * Main Method
	 */
	public static void main(String[] args) {
		Circ w = new Circ("Circ");
	
	}

	/**
	 * Changes to next panel
	 */
	public void changePanel() {
		((CardLayout)cardPanel.getLayout()).next(cardPanel);
		requestFocus();
	}
	
	/**
	 * Changes to specified panel
	 * @param i - Panel to change to
	 */
	public void changePanel(int i) {
		((CardLayout)cardPanel.getLayout()).show(cardPanel, i +"");
		requestFocus();
	}
	
	
}
