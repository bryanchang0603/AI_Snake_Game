import java.awt.Point;
import java.util.LinkedList;

/**
 * The class responsible for storing information of snake's body and is method
 * 
 * @author Bryan
 *
 */
public class Snake {
	private Integer x, y;
	private Item.item Body;
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int UP = -GamePanel.WIDTH;
	public static final int DOWN = GamePanel.WIDTH;

	public LinkedList<Point> snakeBody = new LinkedList<Point>();

	/**
	 * Init for snake when coordinate is given
	 * 
	 * @param xIn      the input x coordinate
	 * @param yIn      the input y coordinate
	 * @param bodyType the input body type
	 */
	public Snake(int xIn, int yIn, Item.item bodyType) {

		this.x = xIn;
		this.y = yIn;
		this.Body = bodyType;
	}

	/**
	 * Init for snake when coordinate is not given
	 */
	public Snake() {
		snakeBody.add(new Point(0, 0));
	}

	/**
	 * return the length of snakeBody
	 * 
	 * @return length of snakeBody
	 */
	public int size() {
		return snakeBody.size();
	}

	/**
	 * update the coordinate of to the new given coordinate
	 * 
	 * @param xIn the input x coordinate
	 * @param yIn the input y coordinate
	 */
	public void updateCoordinate(int xIn, int yIn) {
		this.x = xIn;
		this.y = yIn;
	}

	/**
	 * only update the x coordinate of the snake
	 * 
	 * @param xIn the input x coordinate
	 */
	public void updateX(int xIn) {
		this.x = xIn;
	}

	/**
	 * only update the x coordinate of the snake
	 * 
	 * @param yIn the input y coordinate
	 */
	public void updateY(int yIn) {
		this.y = yIn;
	}

	/**
	 * getter function for x coordinate
	 * 
	 * @return the x coordinate
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * getter function for y coordinate
	 * 
	 * @return the x coordinate
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * getter function for checking if the current snake part is head
	 * 
	 * @return assertion if the current snake is Head
	 */
	public boolean isHead() {
		return (Body == Item.item.head);
	}

	private class SankeDirver implements Runnable {

		@Override
		public void run() {

			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
