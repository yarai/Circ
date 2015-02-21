import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Timer;

/**
 * This class represents Last Man Mode, a certain game mode.
 * @author Vinh Doan
 *
 */

public class LastManMode extends GameMode implements ActionListener{
	private int markedPerRound;
	private int ballsLeft;
	private int timePerRound = 30;
	private Ball[] origBalls;

	/**
	 * Last Man Mode constructor
	 * @param timePerRound - how many seconds per round
	 * @param mpr - balls to be marked per round
	 * @param b - Balls to act on
	 */
	public LastManMode(int timePerRound, Ball[] b)
	{
		super(timePerRound, b);
		origBalls = b;
		markedPerRound = getMarkAmount(b.length);
	}
	
	private int getMarkAmount(int n)
	{
		if (n == 8)
		{
			return 4;
		}
		else if(n == 7)
		{
			return 3;
		}
		else if(n > 4)
		{
			return 2;
		}
		else
			return 1;
	}

	/**
	 * Begin the game mode
	 */
	public void startGameMode() {
		super.startGameMode();
		
		ArrayList<Ball> balls = getBalls();
		for (Ball b: balls)
		{
			GamePanel.spawnBall(b, 5);
		}
		int ballNum = balls.size();

		if(ballNum == 2)
		{
			int toMark = (int)(Math.random() * ballNum);
			
			balls.get(toMark).setMarked(true);

		}
		else
		{
			int[] toMark = new int[markedPerRound];

			for(int i = 0; i < markedPerRound; i++)
			{
				int num = (int)(Math.random() * ballNum);
				for(int j = 1; j < toMark.length; j++)
				{
					if(num == toMark[j])
					{
						num = (int)(Math.random() * ballNum);
						j = -1;
					}
				}
				toMark[i] = num;
			}
			/*
			for(int i : toMark)
			{
				i--;
			}
			*/
			for(int i : toMark)
			{
				balls.get(i).setMarked(true);
			}
		}
		
		// TODO Auto-generated method stub

	}

	/**
	 * Begin the respective game mode's round
	 */
	public void beginRound() {
		super.beginRound();
		for (Ball allBall: origBalls)
		{
			allBall.setCanMove(true);
		}
		ArrayList<Ball> balls = getBalls();
		int ballNum = balls.size();

		if(ballNum == 2)
		{
			int toMark = (int)(Math.random() * ballNum);
			
			balls.get(toMark).setMarked(true);

		}
		else
		{
			markedPerRound = getMarkAmount(ballNum);
			int[] toMark = new int[markedPerRound];

			for(int i = 0; i < markedPerRound; i++)
			{
				int num = (int)(Math.random() * ballNum);
				for(int j = 0; j < toMark.length; j++)
				{
					if(num == toMark[j])
					{
						num = (int)(Math.random() * ballNum);
						j = -1;
					}
				}
				toMark[i] = num;
			}
			for(int i : toMark)
			{
				i--;
			}
			
			for(int i : toMark)
			{
				balls.get(i).setMarked(true);
			}
		}
		
	}

	/**
	 * Act on the balls at the end of the round
	 * @return whether the game is over
	 */
	public boolean endRound() {
		super.endRound();
		for (Ball allBall: origBalls)
		{
			allBall.setCanMove(false);
		}
		ArrayList<Ball> balls = getBalls();
		for(int i = balls.size() - 1; i >= 0; i--)
		{
			Ball thisBall = balls.get(i);
			if(thisBall.isMarked() == true)
			{
				thisBall.setDead(true);
				balls.remove(i);
			}
		}
		setBalls(balls);
		if(balls.size() == 1)
		{
			Ball lastBall = balls.get(0);
			for (int i = 0; i < origBalls.length; i++)
			{
				if (origBalls[i].equals(lastBall))
				{
					GamePanel.setWinnerIndici(i);
					return true;
				}
			}
			return true;
		}
		return false;

	}
	

}
