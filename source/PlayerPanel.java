import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.Arrays;

/**
 * This class the starting menu panel
 * @author Vinh Doan
 *
 */
public class PlayerPanel extends JPanel implements ActionListener {

	Circ w;
	
	/**
	 * Width of panel
	 */
	public static final int DRAWING_WIDTH = Circ.DRAWING_WIDTH;
	/**
	 * Height of panels
	 */
	public static final int DRAWING_HEIGHT = Circ.DRAWING_HEIGHT;
	
	private JButton backButton, nextButton;
	private MiniPlayerPanel[] mpp;
	private JSlider[] sliders;
	
	private int aiNum, aiLevel;

	/**
	 * Creates player panel
	 * @param circ - the main Circ
	 */
	public PlayerPanel(Circ w) {
		this.w = w;

		this.setLayout(new BorderLayout());
		
		sliders = new JSlider[2];
		
		JPanel twoSplitPanelsC = new JPanel(new GridLayout(1,2, 10, 0));
		
		JPanel centerLeft = new JPanel(new GridLayout(1,2, 0, 0));
		JPanel centerRight = new JPanel(new GridLayout(1,2, 0, 0));
		aiNum = 4;
		aiLevel = 5;
		JLabel ainum = new JLabel(" Additional Computers:");
		JLabel ailevel = new JLabel(" Choose Computer Level: (1 = Random, 10 = Smart)");
		for(int i = 0; i < 2; i++)
		{
			if(i == 0)
				sliders[i] = new JSlider(SwingConstants.HORIZONTAL, 0, 4, aiNum);
			else
				sliders[i] = new JSlider(SwingConstants.HORIZONTAL, 1, 10, aiLevel);
			sliders[i].setMajorTickSpacing(1);
			sliders[i].setMinorTickSpacing(1);
			sliders[i].setPaintTicks(true);
			sliders[i].setPaintLabels(true);
			sliders[i].setSnapToTicks(true);
			sliders[i].addChangeListener(new SliderHandler());
		}
		centerLeft.add(ainum);
		centerLeft.add(sliders[0]);
		centerRight.add(ailevel);
		centerRight.add(sliders[1]);
		twoSplitPanelsC.add(centerLeft);
		twoSplitPanelsC.add(centerRight);
		JPanel fourSplitPanels = new JPanel();
		fourSplitPanels.setLayout(new GridLayout(2,2, 10, 10));
		mpp = new MiniPlayerPanel[4];
		mpp[0] = new MiniPlayerPanel(1);
		mpp[1] = new MiniPlayerPanel(2);
		mpp[2] = new MiniPlayerPanel(3);
		mpp[3] = new MiniPlayerPanel(4);
		
		fourSplitPanels.add(mpp[0]);
		fourSplitPanels.add(mpp[1]);
		fourSplitPanels.add(mpp[2]);
		fourSplitPanels.add(mpp[3]);
		
		//masterSplit.add(box);
		this.add(fourSplitPanels, BorderLayout.CENTER);
		JPanel bottomButtons = new JPanel();
		
		 backButton = new JButton("Back");
		backButton.addActionListener(this);
		bottomButtons.add(backButton,BorderLayout.WEST);

		 nextButton = new JButton("Next");
		nextButton.addActionListener(this);
		bottomButtons.add(nextButton,BorderLayout.EAST);

		backButton.addActionListener(new ButtonHandler());
		nextButton.addActionListener(new ButtonHandler());
		JPanel lowerSplit = new JPanel(new GridLayout(2,1, 0, 0));
		lowerSplit.add(twoSplitPanelsC);
		lowerSplit.add(bottomButtons);
		this.add(lowerSplit,BorderLayout.SOUTH);


	}

	class SliderHandler implements ChangeListener
	{
		public void stateChanged(ChangeEvent arg0) {
			JSlider js = (JSlider) arg0.getSource();
			if(js == sliders[0])
			{
				aiNum = js.getValue();
			}
			else if (js == sliders[1])
			{
				aiLevel = js.getValue();
			}
			repaint();
		}
		
	}

	
	
	class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JButton sourceButton = (JButton)arg0.getSource();
			if (sourceButton == backButton)
			{
				w.changePanel(1);
			}
			else if (sourceButton == nextButton)
			{
				String[] playerNames = new String[4];
				for(int i = 0; i < 4; i++)
				{
					playerNames[i] = mpp[i].getName();
					if (playerNames[i].length() > 7)
					{
						playerNames[i] = playerNames[i].substring(0, 8);
					}
				}
				
				int[] playerTypes = new int[4];
				for(int i = 0; i < 4; i++)
				{
					playerTypes[i] = mpp[i].getPlayerType();
				
				}

				GamePanel.setPlayerTypes(playerTypes);
				GamePanel.setPlayerNames(playerNames);
				GamePanel.setAILevel(aiLevel);
				GamePanel.setExtraAI(aiNum);
				w.changePanel();
			}
		}

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}