import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;


/**
 * This class represents a game mode, which dictates the rules of a game.
 * @author Vinh Doan
 *
 */

public abstract class GameMode implements ActionListener{
	private int timePerRound = 30;
	private ArrayList<Ball> balls;
	private Timer pauseTimer;

	

	private int secondsLeft;
	private final int pauseTime = 3;

	private Timer timer;
	
	/**
	 * Creates GameMode
	 * @param timerPerRound - The amount of timer per round
	 * @param b - The balls that will be for the game mode
	 */
	public GameMode(int timePerRound, Ball[] b)
	{
		this.timePerRound = timePerRound;
		balls = new ArrayList<Ball>();
		for (Ball bs: b)
		{
			balls.add(bs);
		}
		
	}
	
	public ArrayList<Ball> getBalls() {
		return balls;
	}
	
	public void setBalls(ArrayList<Ball> balls) {
		this.balls = balls;
	}
	
	
	
	/**
	 * Begin the game mode
	 */
	public void startGameMode()
	{
		timer =  new Timer(1000, this);
		timer.start();
		secondsLeft = timePerRound;
		GameUI.setTime(secondsLeft);
		GameUI.setPlayers(balls.size());
		for(int i = 0; i < 5; i++)
			GamePanel.spawnPowerUp();
	}
	
	
	/**
	 * Begin the respective game mode's round
	 */
	public  void beginRound()
	{
		timer =  new Timer(1000, this);
		timer.start();
		secondsLeft = timePerRound;
		GameUI.setTime(secondsLeft);
		GameUI.setPlayers(balls.size());
		for(int i = 0; i < 5; i++)
			GamePanel.spawnPowerUp();
	}
	
	/**
	 * Act on the balls at the end of the round
	 * @return whether the game is over
	 */
	public boolean endRound()
	{
		for (Ball bs: balls)
		{
			bs.unPowerup();
		}
		GamePanel.clearPowerUps();
		return false;
	}
	
	public void setTimePerRound(int timePerRound) {
		this.timePerRound = timePerRound;
	}

	public int getTimePerRound() {
		return timePerRound;
	}
	
	public void actionPerformed(ActionEvent e) {
		secondsLeft--;
		GameUI.setTime(secondsLeft);
		if(secondsLeft <= 0)
		{
			GameUI.setTime(0);
			boolean b = endRound();
			timer.stop();
			if(!b)
			{
				pauseTimer = new Timer(1000 * pauseTime, new PauseActionHandler());
				pauseTimer.start();
			}	
			else
			{
				GamePanel.setGameOver(true);
			}
		}
		
	}
	
	class PauseActionHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			pauseTimer.stop();
			beginRound();
		}
	}
}