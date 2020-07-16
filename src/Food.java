
/**
 * The ADT for the food in the game
 */
import java.awt.Point;

public class Food {

	public Point food;

	/**
	 * the init function of Food
	 * 
	 * @param x the x coordinate of the food
	 * @param y the y coordinate of the food
	 */
	public Food(int x, int y) {

		this.food = new Point(x, y);

	}

	/**
	 * place a food on the game board are a random place
	 */
	public void newfood() {

		boolean cell_free = false;
		while (!cell_free) {
			int x = (int) (Math.random() * GamePanel.WIDTH);
			int y = (int) (Math.random() * GamePanel.HEIGHT);
			food = new Point(x, y);
			cell_free = Ai.is_cell_free(food, GamePanel.snake);
		}
		// food = new Point(2, 0);
	}

}
