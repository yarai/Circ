import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


/**
 * This class represents Shark Mode, a certain game mode.
 * @author Vinh Doan
 *
 */


public class SharkMode extends GameMode implements ActionListener{

	private ArrayList<Rectangle2D.Double> zoneOne;
	private ArrayList<Rectangle2D.Double> zoneTwo;

	private int zoneOn;

	/**
	 * Shark Mode constructor
	 * @param timePerRound - how many seconds per round
	 * @param b - Balls to act on
	 * @param zone1 - the first zone
	 * @param zone2 - the second zone
	 */
	public SharkMode(int timePerRound, Ball[] b,ArrayList<Rectangle2D.Double> zone1, ArrayList<Rectangle2D.Double> zone2)
	{
		super(timePerRound, b);
		zoneOne = zone1;
		zoneTwo = zone2;
		zoneOn = 1;
	}


	public ArrayList<Rectangle2D.Double> getZone()
	{
		if (zoneOn == 1)
		{
			return zoneOne;
		}
		else
			return zoneTwo;

	}

	/**
	 * Change the zones to the other
	 */
	public void switchZones()
	{
		if (zoneOn == 1)
		{
			zoneOn = 2;
		}
		else
			zoneOn = 1;
	}

	/**
	 * Begin the game mode
	 */
	public void startGameMode() {
		super.startGameMode();
		switchZones();
		ArrayList<Ball> balls = getBalls();
		int ballNum = balls.size();
		int chosenShark = (int) (Math.random() * ballNum);
		Ball toShark = balls.get(chosenShark);
		toShark.setMarked(true);
		for (Ball b: balls)
		{	if(b.isMarked())
				GamePanel.spawnBall(b, 5);
			else
				GamePanel.zoneSpawn(b);
			
		}
		GameUI.setPlayers(ballNum - 1);
		

	}

	/**
	 * Begin the respective game mode's round
	 */
	public void beginRound() {
		super.beginRound();
		switchZones();
		ArrayList<Ball> balls = getBalls();
		for (Ball allBall: balls)
		{
			allBall.setCanMove(true);
		}
		//open keylisteners
		
		for (Ball b : balls)
		{
			if (b.isDead())
			{
				b.setMarked(true);
				b.setDead(false);
			}
		}
		int deadCount = 0;
		for (Ball b : balls)
		{
			if (b.isMarked())
			{
				GamePanel.spawnBall(b, 5);	
				deadCount++;
			}
		}
		GameUI.setPlayers(balls.size() - deadCount);
		 

	}

	/**
	 * Act on the balls at the end of the round
	 * @return whether the game is over
	 */
	public boolean endRound() {
		super.endRound();
		
		int marked = 0;
		ArrayList<Ball> balls = getBalls();
		for (Ball allBall: balls)
		{
			allBall.setCanMove(true);
		}
		for (Ball b : balls)
		{
			if (!b.isMarked())
			{
				if (!b.isFullyInRect( getZone()))
				{
					b.setDead(true);
				}
			}
			
			if (b.isDead() || b.isMarked())
				marked++;


		}

		if (balls.size() - marked == 1)
		{
			for(int i = 0; i < balls.size(); i ++)
			{
				if (!balls.get(i).isMarked() && !balls.get(i).isDead())
				{
					GamePanel.setWinnerIndici(i);
					return true;
				}
			}
			
		}
		else if (balls.size() - marked <= 0)
		{
			GamePanel.setWinnerIndici(-2);
			return true;
		}

		return false;

	}

}
