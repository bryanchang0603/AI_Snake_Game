
/**
 * The General controller of AI mode of the game
 * @author Eric
 */
import java.awt.*;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	public static final int HEIGHT = 20;
	public static final int WIDTH = 40;

	public static final int FOOD = 0;
	public static final int UNDEFINE = (HEIGHT + 1) * (WIDTH + 1);
	public static final int SNAKE = 2 * UNDEFINE;

	public static Snake snake = new Snake();

	public static Board board = new Board();

	public static Food food = new Food(2, 2);

	private Ai ai = new Ai();

	/**
	 * the init function of GamePanel
	 */
	public GamePanel() {
		this.setBackground(Color.BLACK);
		new Thread(this).start();
	}

	/**
	 * the function for draw the game board
	 */
	@Override
	public void paint(Graphics g) {
		board.drawMe(g);
	}

	/**
	 * the general controller for AI mode
	 */
	@Override
	public void run() {

		int[] best_move;

		while (true) {
			ai.board_reset(snake, food, board);
			if (ai.board_refresh(food, snake, board)) {
				best_move = ai.find_safe_way();
			} else {
				best_move = ai.follow_tail();
			}
			if (best_move[0] == -1111 && best_move[1] == -1111) {
				best_move = ai.any_possible_move();
			}
			if (best_move[0] != -1111 && best_move[1] != -1111) {
				ai.makeMove(best_move);
				repaint();
			} else {
				break;
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (GamePanel.snake.size() >= WIDTH * HEIGHT) {
				break;
			}
		}
	}

}
