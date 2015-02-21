
/**
 * This class represents a wall in the game
 * @author Vinh Doan
 *
 */
public class Wall extends MovingImage{

	/**
	 * Creates wall at x ,y
	 * @param x - x position
	 * @param y - y position
	 */
	public Wall(int x, int y) {
		super("Wall.png", x, y, 40, 40);
	}
	

}
