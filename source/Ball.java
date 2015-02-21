
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 * This class represents a ball controlled by the player that can move freely.
 * @author Yuta Arai
 *
 */
public class Ball implements KeyListener {

	private double acc = 0.10;
	private double dec = 0.05;
	private double maxVel = 7;
	private final static double ballSize = 40;

	private String name;
	
	private double xVel, yVel;

	private double radius;
	protected double x, y;
	private Image[] ballImages;

	private boolean isMarked;
	private boolean isDead;
	private boolean canMove;
	private boolean isAI;

	private boolean isImmune;
	private boolean isJukeJuiced;

	private boolean[] keys = new boolean[1000];

	private int[] control = new int[4];

	private Timer immunTimer;
	private Timer powerUpTimer;

	private Ball(boolean mark, double x, double y, double d) {
		ballImages = new Image[] {(new ImageIcon("BallGreen.png")).getImage() ,(new ImageIcon("BallBlack.png")).getImage() };
		isMarked = mark;
		canMove = true;
		xVel = 0;
		yVel = 0;
		radius = d/2;
		this.x = x;
		this.y = y;
		immunTimer = new Timer(500, new ImmunityTimer());
		powerUpTimer = new Timer(8000, new PowerUpTimer());
	}

	/**
	 * Creates a default instance of a new player Ball with the x-value of the 
	 * top left point of the ball at x and the y-value of the top left
	 * point of the ball at y. The control of the ball is determined by
	 * the string controls and the state (mark or nonmarked) is determined
	 * by the boolean mark.
	 * @param mark - determines if the ball will be marked or not
	 * @param x - the x-value of the top left point of the ball
	 * @param y - the y-value of the top left point of the ball
	 * @param controls - the keys used to control the ball
	 */
	public Ball(boolean mark, double x, double y, String controls) {
		this(mark, x, y, ballSize);
		if (controls == "ARROWS") {
			control[0] = KeyEvent.VK_UP;
			control[1] = KeyEvent.VK_RIGHT;
			control[2] = KeyEvent.VK_DOWN;
			control[3] = KeyEvent.VK_LEFT;
		}
		if (controls == "WASD") {
			control[0] = KeyEvent.VK_W;
			control[1] = KeyEvent.VK_D;
			control[2] = KeyEvent.VK_S;
			control[3] = KeyEvent.VK_A;
		}
		if (controls == "IJKL") {
			control[0] = KeyEvent.VK_I;
			control[1] = KeyEvent.VK_L;
			control[2] = KeyEvent.VK_K;
			control[3] = KeyEvent.VK_J;
		}
		if (controls == "TFGH") {
			control[0] = KeyEvent.VK_T;
			control[1] = KeyEvent.VK_H;
			control[2] = KeyEvent.VK_G;
			control[3] = KeyEvent.VK_F;
		}
		isAI = false;
	}

	/**
	 * Creates a default instance of a new AI Ball with the x-value of the 
	 * top left point of the ball at x and the y-value of the top left
	 * point of the ball at y. The control of the ball is determined by
	 * the string controls and the state (mark or nonmarked) is determined
	 * by the boolean mark.
	 * @param mark - determines if the ball will be marked or not
	 * @param x - the x-value of the top left point of the ball
	 * @param y - the y-value of the top left point of the ball
	 * @param AIKind - determines the smartness of the AI ball
	 * @param b - the surrounding balls
	 */
	public Ball(boolean mark, double x, double y, int AIKind, Ball[] b) {
		this(mark, x, y, ballSize);
		isAI = true;
		new BallAI(this, AIKind, b);
	}

	public boolean[] getKeys() {
		return keys;
	}

	public void setKeys(boolean[] keys) {
		this.keys = keys;
	}

	public boolean isAI() {
		return isAI;
	}

	public void setX(double x) {
		this.x = x;
		xVel = 0;
	}

	public void setY(double y) {
		this.y = y;
		yVel = 0;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public boolean isImmune() {
		return isImmune;
	}
	public Timer getImmunTimer() {
		return immunTimer;
	}

	public void setImmune(boolean isImmune) {
		this.isImmune = isImmune;
	}

	public boolean isJukeJuiced() {
		return isJukeJuiced;
	}

	public void setJukeJuiced(boolean isJukeJuiced) {
		this.isJukeJuiced = isJukeJuiced;
	}

	public String getName() {
		return name;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public double getxVel() {
		return xVel;
	}

	public double getyVel() {
		return yVel;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public double getXVel(){
		return xVel;
	}

	public double getYVel(){
		return yVel;
	}

	public void setXVel(double xv){
		xVel = xv;
	}

	public void setYVel(double yv){
		yVel = yv;
	}

	/**
	 * Makes the ball bounce to the opposite direction of its
	 * movement when it hits a Bomb powerup.
	 * @param b - the Bomb powerup that the ball hits
	 */
	public void hitBomb(Bomb b){
		Rectangle2D.Double r = (Rectangle2D.Double) b.getBounds2D();
		if(collideWith(r)){
			double angle = Math.atan2((x-r.x)/Point2D.distance(r.x,r.y,x,y),(y-r.y)/Point2D.distance(r.x,r.y,x,y));
			bounce(-Math.tan(angle),3);
		}
	}

	/**
	 * Makes the ball accelerate greatly towards the direction
	 * of its movement when it hits a BoostPad powerup
	 * @param b - the BoostPad powerup that the ball hits
	 */
	public void hitBoost(BoostPad b){
		Rectangle2D.Double r = (Rectangle2D.Double) b.getBounds2D();
		if(collideWith(r)){
			xVel *= 3;
			yVel *= 3;
		}
	}

	/**
	 * Turns all powerups affecting the ball off
	 */
	public void unPowerup(){
		isJukeJuiced = false;
		isImmune = false;
	}

	/**
	 * Checks to see if the ball collides with any of
	 * the powerups on the grid. If it does, checks the
	 * type of the powerup and calls the methods that makes
	 * the ball act the way the powerup specifies
	 * @param pu - the list of powerups that are on the grid
	 */
	public void powerupCollide(ArrayList<Powerup> pu){
		for(Powerup p : pu){
			if(p.isOn() && collideWith((Rectangle2D.Double) p.getBounds2D())){
				if(p instanceof JukeJuice){
					isJukeJuiced = true;
					powerUpTimer.start();
					p.setOn(false);
				} else if(p instanceof Immunity){
					if(!isMarked)
					{
						isImmune = true;
						powerUpTimer.start();
						p.setOn(false);
					}
					p.setOn(false);
				} else if(p instanceof Bomb){
					hitBomb((Bomb)p);
					StillPowerUp sp = (StillPowerUp)p;
					sp.setOn(false);
				} else if(p instanceof BoostPad){
					hitBoost((BoostPad)p);
					StillPowerUp sp = (StillPowerUp)p;
					sp.setOn(false);
				}

			}
		}
	}

	/**
	 * Increases the x or y velocity of the ball 
	 * based on the key that is pressed. Increases
	 * acceleration and decceleration if JukeJuice 
	 * powerup is on and disables the keyboard control
	 * if the ball is an AI. Updates the location of the
	 * ball based on the velocity and puts a cap on how fast
	 * the ball can move.
	 */
	public void move() {
		if(isJukeJuiced){
			acc = 0.15;
			dec = 0.08;
		} else{
			acc = 0.10;
			dec = 0.05;
		}
		if(isAI)
		{
			if (keys[0]) {
				yVel -= acc;
			}
			if (keys[1]) {
				yVel += acc;
			}
			if (keys[2]) {
				xVel -= acc;
			}
			if (keys[3]) {
				xVel += acc;
			}
		}
		else
		{
			if (keys[control[0]]) {
				yVel -= acc;
			}
			if (keys[control[2]]) {
				yVel += acc;
			}
			if (keys[control[3]]) {
				xVel -= acc;
			}
			if (keys[control[1]]) {
				xVel += acc;
			}
		}

		x += xVel;
		y += yVel;
		if(maxVel < Math.abs(xVel)){
			if(xVel > 0)
				xVel = maxVel;
			if(xVel < 0)
				xVel = -maxVel;
		}
		if(maxVel < Math.abs(yVel)){
			if(yVel > 0)
				yVel = maxVel;
			if(yVel < 0)
				yVel = -maxVel;
		}

		if (xVel > 0) {
			xVel -= dec;
		} else if (xVel < 0) {
			xVel += dec;
		}
		if (yVel > 0) {
			yVel -= dec;
		} else if (yVel < 0) {
			yVel += dec;
		}
	}

	/**
	 * Bounds the ball within the area of the window.
	 */
	public void constrain() {
		if (x <= 0) {
			this.xVel = -this.xVel / 3;
			x = 0;
		}
		if (x >= 800) {
			this.xVel = -this.xVel / 3;
			x = 800;
		}
		if (y <= 0) {
			this.yVel = -this.yVel / 3;
			this.y = 0;
		}
		if (y >= 600) {
			this.yVel = -this.yVel / 3;
			y = 600;
		}
	}

	/**
	 * Checks to see if the ball is fully inside any of the rectangles
	 * in the aArrayList. Returns true if it is fully inside and returns false
	 * if any of the part of the ball is outside all the rectangles
	 * @param zone - the rectangular area that is being checked for a ball
	 * @return true if the ball is inside the rectangles
	 */
	public boolean isFullyInRect(ArrayList<Rectangle2D.Double> zone){

		for(Rectangle2D.Double rectB : zone){
			Point a, b, c, d;
			a = new Point((int)x,(int)y);
			b = new Point((int)(x+radius*2),(int)(y+radius*2));
			c = new Point((int)(x+radius*2),(int)(y));
			d = new Point((int)(x),(int)(y+radius*2));
			if(rectB.contains(a) || rectB.contains(b) || rectB.contains(c) || rectB.contains(d)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks to see if the ball collides with a Rectangle.
	 * @param rB - the Rectangle that is checked for a collision.
	 * @return true if there is a collision
	 */
	public boolean collideWith(Rectangle2D.Double rB){
		Rectangle2D.Double rA = new Rectangle2D.Double(x,y,radius*2,radius*2);

		double leftA, leftB;
		double rightA, rightB;
		double topA, topB;
		double bottomA, bottomB;

		leftA = rA.x;
		rightA = rA.x + rA.width;
		topA = rA.y;
		bottomA = rA.y + rA.height;

		leftB = rB.x;
		rightB = rB.x + rB.width;
		topB = rB.y;
		bottomB = rB.y + rB.height;

		if( bottomA <= topB ) {
			return false;
		}
		if( topA >= bottomB ) {
			return false;
		}
		if( rightA <= leftB ){
			return false;
		}
		if( leftA >= rightB ){
			return false;
		}
		return true;
	}

	/**
	 * Checks and handles collision for two rectangles
	 * @param rectangle1 - the rectangle that is in the collision
	 * @param rectangle2 - the other rectangle that is in the collision
	 */
	private void rectangleCollision(Rectangle2D.Double rectangle1, Rectangle2D.Double rectangle2){
		if(rectangle1.intersects(rectangle2)){
			double leftOverlap=rectangle1.x+rectangle1.width-rectangle2.x;
			double rightOverlap=rectangle2.x+rectangle2.width-rectangle1.x;
			double topOverlap=rectangle1.y+rectangle1.height-rectangle2.y;
			double bottomOverlap=rectangle2.y+rectangle2.height-rectangle1.y;

			double smallestOverlap=Double.MAX_VALUE; 
			double moveX=0;
			double moveY=0;

			if(leftOverlap<smallestOverlap){
				smallestOverlap=leftOverlap;
				moveX=-leftOverlap;
				moveY=0;
				if(!(topOverlap<smallestOverlap) && !(bottomOverlap<smallestOverlap)){
					
					xVel = -xVel/3;
				}

			}
			if(rightOverlap<smallestOverlap){
				smallestOverlap=rightOverlap;
				moveX=rightOverlap;
				moveY=0;
				if(!(topOverlap<smallestOverlap) && !(bottomOverlap<smallestOverlap)){
					xVel = -xVel/3;
				}
			}
			if(topOverlap<smallestOverlap){
				smallestOverlap=topOverlap;
				moveX=0;
				moveY=-topOverlap;
				if(!(rightOverlap<smallestOverlap) && !(leftOverlap<smallestOverlap))
					yVel = -yVel/3;

			}
			if(bottomOverlap<smallestOverlap){
				smallestOverlap=bottomOverlap;
				moveX=0;
				moveY=bottomOverlap;
				if(!(rightOverlap<smallestOverlap) && !(leftOverlap<smallestOverlap))
					yVel = -yVel/3;
			}
			rectangle1.x+=moveX;
			rectangle1.y+=moveY;
		}
	}

	/**
	 * Detects and handles collisions between two circles.
	 * Moves the circle out of each so they would not intersect.
	 * @param circle1 - the circle that is in the collision
	 * @param circle2 - the other circle that is in the collision
	 */
	private void circleCollision(Ellipse2D.Double circle1, Ellipse2D.Double circle2){
		double distance = Point2D.distance(circle1.x+circle1.width/2, circle1.y+circle1.height/2, circle2.x+circle2.width/2, circle2.y+circle2.height/2);;

		if((distance>0)&&circle1.getBounds2D().intersects(circle2.getBounds2D())){
			double normalX=((circle2.x+circle2.width/2)-(circle1.x+circle1.width/2))/distance;
			double normalY=((circle2.y+circle2.height/2)-(circle1.y+circle1.height/2))/distance;

			x=(circle2.x+circle2.width/2)-(normalX*(circle1.width/2+circle2.width/2))-(circle1.width/2);
			y=(circle2.y+circle2.height/2)-(normalY*(circle1.height/2+circle2.height/2))-(circle1.height/2);
			
		}
	}

	/**
	 * Detects and handles the collisions between a circle and a rectangle.
	 * Moves the circle out of the rectangle.
	 * @param circle - the circle involved in the collision
	 * @param rectangle - the rectangle involved in the collision
	 */
	private void circleRectangleCollision(Ellipse2D.Double circle, Rectangle2D.Double rectangle)
	{
		if(circle.getBounds2D().intersects(rectangle)){
			if(((circle.y+circle.height/2)>rectangle.y && (circle.y+circle.height/2)<(rectangle.y+rectangle.height)) || 
				((circle.x+circle.width/2)>rectangle.x && (circle.x+circle.width/2)<(rectangle.x+rectangle.width))){
				Rectangle2D.Double tempRectangle=new Rectangle2D.Double();
				tempRectangle.setFrame(circle.getBounds2D());
				rectangleCollision(tempRectangle, rectangle);
				x = tempRectangle.x;
				y = tempRectangle.y;
			}else {
				int corner=cornerCollision(circle, rectangle);
				if(corner>0) {
					Ellipse2D.Double tempCircle = new Ellipse2D.Double(0, 0, Double.MIN_VALUE, Double.MIN_VALUE);

					if(corner == 1){
						tempCircle.x=rectangle.x;
						tempCircle.y=rectangle.y;
					} else if(corner ==2){
						tempCircle.x=rectangle.x+rectangle.width;
						tempCircle.y=rectangle.y;
					} else if(corner == 3){
						tempCircle.x=rectangle.x;
						tempCircle.y=rectangle.y+rectangle.height;
					} else if(corner == 4){
						tempCircle.x=rectangle.x+rectangle.width;
						tempCircle.y=rectangle.y+rectangle.height;
					}

					circleCollision(circle, tempCircle );
				}
			}
		}
	}

	private int cornerCollision(Ellipse2D.Double circle, Rectangle2D.Double rectangle)
	{
		Ellipse2D.Double tempCircle=new Ellipse2D.Double(0, 0, Double.MIN_VALUE, Double.MIN_VALUE);
		tempCircle.x=rectangle.x;
		tempCircle.y=rectangle.y;
		if(circleCollide(tempCircle, circle)>0)return 1;

		tempCircle.x=rectangle.x+rectangle.width;
		tempCircle.y=rectangle.y;
		if(circleCollide(tempCircle, circle)>0)return 2;

		tempCircle.x=rectangle.x;
		tempCircle.y=rectangle.y+rectangle.height;
		if(circleCollide(tempCircle, circle)>0)return 3;

		tempCircle.x=rectangle.x+rectangle.width;
		tempCircle.y=rectangle.y+rectangle.height;
		if(circleCollide(tempCircle, circle)>0)return 4;

		return -1;
	}

	private double circleCollide(Ellipse2D.Double circ1, Ellipse2D.Double circ2)
	{
		if(circ1.getBounds2D().intersects(circ2.getBounds2D()))
		{
			double distance=Point2D.distance(circ1.x+circ1.width/2, circ1.y+circ1.height/2, circ2.x+circ2.width/2, circ2.y+circ2.height/2);
			if(distance<circ1.width/2+circ2.width/2)
			{
				return distance;
			}
		}
		return -1;
	}

	/**
	 * Changes the direction and slows down the ball
	 * when it collides with any side of the obstacle
	 * in the ArrayList obstacles
	 * @param obstacles - the array with the obstacle that the ball can collide with
	 */
	public void wallCollide(ArrayList<Rectangle2D.Double> obstacles) {

		for(Rectangle2D.Double r : obstacles){
			Ellipse2D.Double circle = new Ellipse2D.Double((int)x,(int)y,(int)radius*2,(int)radius*2);
			circleRectangleCollision(circle,r);
		}
	}


	/**
	 * Draws the ball onto the Graphics class.
	 * Does not draw the ball if the ball is in a Dead state.
	 * Draws different balls based on the Marked state.
	 * @param g - the Graphics class that the ball will be drawn onto
	 * @param io - the class that assists the drawing of the ball
	 */
	public void draw(Graphics g, ImageObserver io) {
		if (!isDead)
		{
			g.setColor(Color.BLACK);
			if(isMarked)
				g.drawImage(ballImages[0], (int) x, (int) y, (int) radius*2, (int) radius*2, io);
			else
				g.drawImage(ballImages[1], (int) x, (int) y, (int) radius*2, (int) radius*2, io);
			g.drawString(name, (int) x, (int) y);
		}
		else
		{
			g.drawArc((int)x, (int)y, 40, 40, 0, 360);
			g.drawString(name, (int) x, (int) y);
		}
		if(isImmune)
		{
			g.setColor(new Color(255,255,255,125));
			g.fillArc((int)x, (int)y, 40, 40, 0, 360);
		}
		if(isJukeJuiced)
		{
			g.setColor(new Color(0, 255, 0, 120));
			Polygon tri = new Polygon();
			tri.addPoint((int)x + 20, (int)y);
			tri.addPoint((int)x, (int)y + 20);
			tri.addPoint((int)x + 40, (int)y + 20);
			g.fillRect((int)x + 15, (int)y + 20, 10, 20);
			g.fillPolygon(tri);
		}

	}


	private double vSign(double var){
		if(var <= 0) return -1;
		else return 1;
	}

	
	private void bounce(double slope, double bouncyness){
		if(Math.abs(xVel) < Double.MIN_VALUE) xVel = Double.MIN_VALUE;
		if(Math.abs(yVel) < Double.MIN_VALUE) yVel = Double.MIN_VALUE;
		if(!(slope >= Double.MAX_VALUE)){
			double bounce_angle = Math.atan(getYVel()/getXVel()) - 2 * Math.atan(slope);
			double velocity = Math.sqrt(Math.pow(Math.abs(getXVel()),2) + Math.pow(getYVel(),2));
			double xv = getXVel();
			setXVel(velocity * vSign(getXVel()) * Math.cos(bounce_angle) * bouncyness);
			setYVel(-velocity * vSign(xv) * Math.sin(bounce_angle) * bouncyness);
		} else{
			setXVel(-1*getXVel()*bouncyness);
			setYVel(getYVel()*bouncyness);
		}
		if(Math.abs(xVel) < Double.MIN_VALUE) xVel = 0;
		if(Math.abs(yVel) < Double.MIN_VALUE) yVel = 0;
	}
	

	/**
	 * Checks to see if there is a collision between two balls.
	 * If a collision occurs, calculates the trajectory of
	 * each ball so it will bounce off each other.
	 * @param balls - the Array of balls that this Ball can collide with
	 */
	public void crashBalls(Ball[] balls){
		double x1 = x;
		double y1 = y;
		for(Ball ball : balls){
			if(this != ball && !ball.isDead()){
				if(Point2D.distance(x,y,ball.x,ball.y)<ballSize){
					if(ball.isMarked != isMarked && (!isImmune && !ball.isImmune())){
						if(GamePanel.getGameType()==1){
							isMarked = !isMarked;
							ball.isMarked = !isMarked;
							if(!isMarked){
								isImmune = true;
								immunTimer.start();
							}
							else{
								ball.setImmune(true);
								ball.getImmunTimer().start();
							}
						}
						else{
							if(!isMarked){
								isDead = true;
							}
							else if (!ball.isMarked()){
								ball.setDead(true);
							}
						}
					}
					double angle = Math.atan2((x-ball.x)/Point2D.distance(ball.x,ball.y,x,y),(y-ball.y)/Point2D.distance(ball.x,ball.y,x,y));
					bounce(-Math.tan(angle),0.8);
				}
			}


		}
		x = x1;
		y = y1;
	}


	/**
	 * Runs the ball.
	 * @param obstacles - the array with the obstacle that the ball can collide with
	 * @param balls - the Array of balls that this Ball can collide with
	 */
	public void run(ArrayList<Rectangle2D.Double> obstacles, ArrayList<Powerup> p1, ArrayList<Powerup> p2, Ball[] balls) {
		if(canMove){
			move();
			wallCollide(obstacles);

			if(!isDead){
				crashBalls(balls);
				powerupCollide(p1);
				powerupCollide(p2);
				//constrain();
			}

		}
	}

	/**
	 * Runs the ball.
	 * @param obstacles - the array with the obstacle that the ball can collide with
	 * @param balls - the Array of balls that this Ball can collide with
	 */
	public void run(ArrayList<Rectangle2D.Double> obstacles, ArrayList<Rectangle2D.Double> z1, ArrayList<Rectangle2D.Double> z2, ArrayList<Powerup> p1, ArrayList<Powerup> p2, Ball[] balls) {
		if(canMove){
			move();
			wallCollide(z1);
			wallCollide(z2);
			wallCollide(obstacles);

			if(!isDead){
				crashBalls(balls);
				powerupCollide(p1);
				powerupCollide(p2);
				//constrain();
			}
		}
	}



	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!isAI)
			keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!isAI)
			keys[e.getKeyCode()] = false;
	}

	public void setMarked(boolean b){
		isMarked = b;
	}

	public boolean isMarked(){
		return isMarked;
	}

	public void setDead(boolean b){
		isDead = b;

	}

	public boolean isDead(){
		return isDead;
	}

	class ImmunityTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			isImmune  = false;
			immunTimer.stop();
		}
	}

	class PowerUpTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			isImmune = false;
			isJukeJuiced = false;//add code to end all powerups booleans
			powerUpTimer.stop();
		}
	}



}
