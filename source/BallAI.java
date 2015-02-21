import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.Timer;


/**
 * This class represents an AI that controls a Ball.
 * @author Vinh Doan and Yuta Arai
 *
 */
public class BallAI{

	private Timer moveTimer;
	private int level;
	private Ball thisBall;
	private boolean isMoving;
	private Ball[] balls;
	
	/**
	 * Creates a default instance of an AI that controls
	 * a specified ball. Sets the level of smart ness of the
	 * ball and takes in the surrounding balls.
	 * @param b - the ball that is controlled by the AI
	 * @param aik - the smartness of the AI
	 * @param bs - the surrounding balls
	 */
	public BallAI(Ball b, int aik, Ball[] bs) {
		thisBall = b;
		level = aik;
		moveTimer = new Timer(1000, new MoveTimer());
		balls = bs;
		moveTimer.start();
	}
	
	private boolean[] getSmartToward(Ball b)
	{
		boolean[] newKeys = new boolean[4];
		if(b.getX()>thisBall.getX()) {
			newKeys[3] = true;					
		}
		else{
			newKeys[2] = true;
		}
		if(b.getY()>thisBall.getY()) {
			newKeys[1] = true;
		}
		else{
			newKeys[0] = true;
		}
		return newKeys;
	}
	
	private boolean[] getSmartAway(Ball b)
	{
		boolean[] newKeys = new boolean[4];
		if(b.getX()>thisBall.getX()) {
			newKeys[2] = true;					
		}
		else{
			newKeys[3] = true;
		}
		if(b.getY()>thisBall.getY()) {
			newKeys[0] = true;
		}
		else{
			newKeys[1] = true;
		}
		return newKeys;
	}
	
	private Ball getClosestBall()
	{
		double closeDistance = Double.MAX_VALUE;
		Ball closeBall = balls[1];
		for(Ball b : balls){
			if(thisBall != b && !b.isDead() && thisBall.isMarked() != b.isMarked() && !b.isImmune()){
				double distance = Point2D.distance(thisBall.getX(), thisBall.getY(), b.getX(), b.getY());
				if(distance < closeDistance){
					closeDistance = distance;
					closeBall = b;
				}
			}
		}
		return closeBall;
	}
	
	class MoveTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			//0 = up, 1 = down, 2 = left, 3 = right
			boolean[] newKeys = new boolean[4];
			
			//@Yuta: edit stuff here
			if((int)(Math.random() * 10) + 1  <= level){
				Ball closestBall = getClosestBall();
				
				if(thisBall.isMarked()){
					newKeys = getSmartToward(closestBall);
				}
				else{
					newKeys = getSmartAway(closestBall);
				}
			}
			else
				newKeys[(int)(Math.random() * 4)] = true;
			boolean[] keys = thisBall.getKeys();
			moveTimer.setDelay((int)(Math.random() * 10 + 1) * 100);
			thisBall.setKeys(newKeys);
			
			
			//moveTimer.stop();
		}
	}

}
