import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.awt.geom.AffineTransform;

/**
 * This class the starting menu panel
 * @author Vinh Doan
 *
 */
public class ModePanel extends JPanel implements ActionListener {

	/**
	 * Width of panel
	 */
	public static final int DRAWING_WIDTH = Circ.DRAWING_WIDTH;
	/**
	 * Height of panels
	 */
	public static final int DRAWING_HEIGHT = Circ.DRAWING_HEIGHT;
	Circ w;
	GamePanel gp;
	private JButton backButton;
	private JButton nextButton;
	private MiniModePanel onePanel, twoPanel;
	private int gameTime;

	private int selectedPanel;

	/**
	 * Creates mode panel
	 * @param circ - the main Circ
	 * @param gp - the Game Panel to start
	 */
	public ModePanel(Circ w, GamePanel gp) {
		this.w = w;
		this.gp = gp;
		this.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		//topPanel.setLayout(new GridLayout(0,1, 0, 0));
		topPanel.setLayout(new BorderLayout());
		Font f = new Font("SansSerif", Font.BOLD, 30);
		JLabel info = new JLabel("CHOOSE GAME MODE:");
		info.setForeground(Color.WHITE);
		info.setFont(f);
		topPanel.add(info, BorderLayout.CENTER);
		//topPanel.add(info);
		topPanel.setBackground(Color.DARK_GRAY);
		this.add(topPanel,BorderLayout.NORTH);

		JPanel twoSplitPanels = new JPanel();
		twoSplitPanels.setLayout(new GridLayout(0,2, 10, 0));
		onePanel = new MiniModePanel(1, true);
		twoPanel = new MiniModePanel(2, false);
		twoSplitPanels.add(onePanel);
		twoSplitPanels.add(twoPanel);
		twoSplitPanels.setBackground(Color.DARK_GRAY);
		onePanel.addMouseListener(new MouseHandler());
		twoPanel.addMouseListener(new MouseHandler());
		add(twoSplitPanels,BorderLayout.CENTER);
		
		gameTime = 30;
		
		JLabel sprLabel = new JLabel(" Choose Seconds Per Round");
		JSlider timeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 60, gameTime);
		timeSlider.setMajorTickSpacing(10);
		timeSlider.setMinorTickSpacing(5);
		timeSlider.setPaintTicks(true);
		timeSlider.setPaintLabels(true);
		timeSlider.setSnapToTicks(true);
		timeSlider.addChangeListener(new SliderHandler());
		timeSlider.setPreferredSize(new Dimension((int)(DRAWING_WIDTH * .75), 45));
		JPanel bottomUpper = new JPanel();
		JPanel bottomButtons = new JPanel();

		bottomUpper.add(sprLabel);
		bottomUpper.add(timeSlider);
		
		backButton = new JButton("Back");
		backButton.addActionListener(this);
		bottomButtons.add(backButton,BorderLayout.WEST);

		nextButton = new JButton("Next");
		nextButton.addActionListener(this);
		bottomButtons.add(nextButton,BorderLayout.EAST);

		backButton.addActionListener(new ButtonHandler());
		nextButton.addActionListener(new ButtonHandler());
		JPanel lowerSplit = new JPanel(new GridLayout(2,1, 0, 0));
		lowerSplit.add(bottomUpper);
		lowerSplit.add(bottomButtons);
		this.add(lowerSplit,BorderLayout.SOUTH);

		selectedPanel = 1;

	}

	class SliderHandler implements ChangeListener
	{
		public void stateChanged(ChangeEvent arg0) {
			JSlider js = (JSlider) arg0.getSource();
			gameTime = js.getValue();
			repaint();
		}

	}

	class MouseHandler implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			MiniModePanel s = (MiniModePanel) arg0.getSource();
			if (s == onePanel)
			{
				onePanel.setSelected(true);
				twoPanel.setSelected(false);
				selectedPanel = 1;
				repaint();
			}
			else if  (s == twoPanel)
			{
				twoPanel.setSelected(true);
				onePanel.setSelected(false);
				selectedPanel = 2;
				repaint();
			}

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



	public void actionPerformed(ActionEvent e) {
		//w.changePanel();
	}

	class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JButton sourceButton = (JButton)arg0.getSource();
			if (sourceButton == backButton)
			{
				GamePanel.setGameType(selectedPanel);
				w.changePanel(2);
			}
			else if (sourceButton == nextButton)
			{
				GamePanel.setGameType(selectedPanel);
				GamePanel.setGameTime(gameTime);
				w.changePanel();
				gp.startGame();
			}
		}

	}

}