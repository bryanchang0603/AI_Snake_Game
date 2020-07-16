
/**
 * The class responsible for AI behaviour in AI mode
 */
import java.awt.Point;
import java.awt.print.Printable;
import java.util.LinkedList;

public class Ai {

	private int[] mov = { Snake.LEFT, Snake.RIGHT, Snake.UP, Snake.DOWN };
	private int[][] mov2 = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

	private Snake tmpSnake = new Snake();

	private Board tmpBoard = new Board();

	private Food food;

	/**
	 * init function of AI class
	 */
	public Ai() {
	}

	/**
	 * The class to assert if the cell contains no snake
	 * 
	 * @param idx   the element on the board that need to be asserted
	 * @param snake the snake that will be asserted for
	 * @return true if the cell is empty, and false if it is not
	 */
	public static boolean is_cell_free(Point idx, Snake snake) {
		for (int i = 0; i < snake.snakeBody.size(); i++) {
			Point p = new Point(snake.snakeBody.get(i).x, snake.snakeBody.get(i).y);
			if (p.x == idx.x && p.y == idx.y) {
				return false;
			}
		}
		return true;
	}

	/**
	 * check if the given move is feasible
	 * 
	 * @param idx  the point where the snake will move to
	 * @param move the next move of the snake as an array of int
	 * @return false if the snake cannot move, and true if it is not
	 */
	public boolean is_move_possible(Point idx, int[] move) {

		Point temp = new Point(idx.x, idx.y);

		temp.x = temp.x + move[0];
		temp.y = temp.y + move[1];
		if (temp.x < 0 || temp.x > GamePanel.WIDTH - 1) {
			return false;
		}
		if (temp.y < 0 || temp.y > GamePanel.HEIGHT - 1) {
			return false;
		}
		return true;
	}

	/**
	 * reset the game board based on the current board's status
	 * 
	 * @param snake the snake on the game board
	 * @param food  the food on the game board
	 * @param board the game board
	 */
	public static void board_reset(Snake snake, Food food, Board board) {
		for (int i = 0; i < GamePanel.WIDTH; i++) {
			for (int j = 0; j < GamePanel.HEIGHT; j++) {
				if (i == food.food.x && j == food.food.y) {
					board.board[i][j] = GamePanel.FOOD;
				} else if (is_cell_free(new Point(i, j), snake)) {
					board.board[i][j] = GamePanel.UNDEFINE;
				} else {
					board.board[i][j] = GamePanel.SNAKE;
				}
			}
		}
	}

	/**
	 * check if food is being eaten by the snake
	 * 
	 * @param food  the food on the game board
	 * @param snake the snake on the game board
	 * @param board board the game board
	 * @return true if the snake eats the food, and false if it is not
	 */
	public boolean board_refresh(Food food, Snake snake, Board board) {

		LinkedList<Point> queue = new LinkedList<Point>();

		queue.add(food.food);

		boolean[][] vis = new boolean[GamePanel.WIDTH + 1][GamePanel.HEIGHT + 1];

		boolean found = false;

		while (queue.size() > 0) {

			Point idx = queue.pop();

			if (vis[idx.x][idx.y]) {
				continue;
			}
			vis[idx.x][idx.y] = true;

			for (int i = 0; i < 4; i++) {
				if (is_move_possible(idx, mov2[i])) {

					Point temp = new Point(idx.x, idx.y);
					temp.x = temp.x + mov2[i][0];
					temp.y = temp.y + mov2[i][1];

					Point head = snake.snakeBody.getFirst();

					if (temp.x == head.x && temp.y == head.y) {
						found = true;
					}

					if (board.board[temp.x][temp.y] < GamePanel.SNAKE) {

						if (board.board[temp.x][temp.y] > board.board[idx.x][idx.y] + 1) {
							board.board[temp.x][temp.y] = board.board[idx.x][idx.y] + 1;
						}
						if (!vis[temp.x][temp.y]) {
							queue.add(temp);
						}
					}
				}
			}

		}
		return found;
	}

	/**
	 * find the possible move for the snake with an element with lowest value
	 * 
	 * @param snake the snake on the game board
	 * @param board board the game board
	 * @return the element with the lowest value that the snake's head can move to
	 */
	public int[] choose_shortest_safe_move(Snake snake, Board board) {
		int[] best_move = { -1111, -1111 };
		int min = GamePanel.SNAKE;
		for (int i = 0; i < 4; i++) {
			Point head = snake.snakeBody.getFirst();
			Point temp = new Point(head.x, head.y);
			temp.x = temp.x + mov2[i][0];
			temp.y = temp.y + mov2[i][1];
			if (is_move_possible(head, mov2[i]) && board.board[temp.x][temp.y] < min) {
				min = board.board[temp.x][temp.y];
				best_move = mov2[i];
			}
		}
		return best_move;
	}

	/**
	 * find the possible move for the snake with an element with highest value
	 * 
	 * @param snake the snake on the game board
	 * @param board board the game board
	 * @return the element with the highest value that the snake's head can move to
	 */
	public int[] choose_longest_safe_move(Snake snake, Board board) {

		int[] best_move = { -1111, -1111 };
		int max = -1;
		for (int i = 0; i < 4; i++) {
			Point head = snake.snakeBody.getFirst();
			Point temp = new Point(head.x, head.y);
			temp.x = temp.x + mov2[i][0];
			temp.y = temp.y + mov2[i][1];
			if (is_move_possible(head, mov2[i]) && board.board[temp.x][temp.y] < GamePanel.UNDEFINE
					&& board.board[temp.x][temp.y] > max) {
				max = board.board[temp.x][temp.y];
				best_move = mov2[i];
			}
		}
		return best_move;
	}

	/**
	 * check if the head of the snake can move to the tail's coordinate in the next
	 * gameCycle
	 * 
	 * @return true if tail is reachable, false if it is not
	 */
	public boolean is_tail_inside() {

		Point tail = new Point(tmpSnake.snakeBody.getLast().x, tmpSnake.snakeBody.getLast().y);
		Point food = new Point(GamePanel.food.food.x, GamePanel.food.food.y);

		tmpBoard.board[tail.x][tail.y] = 0;

		tmpBoard.board[food.x][food.y] = GamePanel.SNAKE;

		Food tmpfood = new Food(tail.x, tail.y);

		boolean result = board_refresh(tmpfood, tmpSnake, tmpBoard);

		for (int i = 0; i < 4; i++) {
			Point head = new Point(tmpSnake.snakeBody.getFirst().x, tmpSnake.snakeBody.getFirst().y);
			Point temp = new Point(head.x, head.y);
			temp.x = temp.x + mov2[i][0];
			temp.y = temp.y + mov2[i][1];
			if (is_move_possible(head, mov2[i]) && temp.x == tail.x && temp.y == tail.y
					&& tmpSnake.snakeBody.size() > 3) {
				return false;
			}
		}

		return result;
	}

	/**
	 * set the path to go to the tail's coordinate
	 * 
	 * @return the coordinate that the snake need to move to in the next gameCycle
	 */
	public int[] follow_tail() {
		tmpSnake.snakeBody.clear();
		for (int i = 0; i < GamePanel.snake.snakeBody.size(); i++) {

			Point p = new Point(GamePanel.snake.snakeBody.get(i).x, GamePanel.snake.snakeBody.get(i).y);

			tmpSnake.snakeBody.add(p);
		}
		board_reset(tmpSnake, GamePanel.food, tmpBoard);

		Point tail = new Point(tmpSnake.snakeBody.getLast().x, tmpSnake.snakeBody.getLast().y);
		Point food = new Point(GamePanel.food.food.x, GamePanel.food.food.y);

		tmpBoard.board[tail.x][tail.y] = GamePanel.FOOD;
		tmpBoard.board[food.x][food.y] = GamePanel.SNAKE;

		Food tmpfood = new Food(tmpSnake.snakeBody.getLast().x, tmpSnake.snakeBody.getLast().y);

		board_refresh(tmpfood, tmpSnake, tmpBoard);

		tmpBoard.board[tail.x][tail.y] = GamePanel.SNAKE;

		return choose_longest_safe_move(tmpSnake, tmpBoard);
	}

	/**
	 * choose a possible move for the snake
	 * 
	 * @return the coordinate that the snake need to move to in the next gameCycle
	 */
	public int[] any_possible_move() {

		int[] best_move = { -1111, -1111 };

		board_reset(GamePanel.snake, GamePanel.food, GamePanel.board);

		board_refresh(GamePanel.food, GamePanel.snake, GamePanel.board);

		int min = GamePanel.SNAKE;

		for (int i = 0; i < 4; i++) {
			Point head = GamePanel.snake.snakeBody.getFirst();
			Point temp = new Point(head.x, head.y);
			temp.x = temp.x + mov2[i][0];
			temp.y = temp.y + mov2[i][1];

			if (is_move_possible(head, mov2[i]) && GamePanel.board.board[temp.x][temp.y] < min) {
				min = GamePanel.board.board[temp.x][temp.y];
				best_move = mov2[i];
			}
		}
		return best_move;

	}

	/**
	 * set the shortest path between the snkae's head and the food on the board
	 */
	public void virtual_shortest_move() {
		tmpSnake.snakeBody.clear();
		for (int i = 0; i < GamePanel.snake.snakeBody.size(); i++) {

			Point p = new Point(GamePanel.snake.snakeBody.get(i).x, GamePanel.snake.snakeBody.get(i).y);

			tmpSnake.snakeBody.add(p);
		}
		for (int i = 0; i < GamePanel.WIDTH; i++) {
			for (int j = 0; j < GamePanel.HEIGHT; j++) {
				tmpBoard.board[i][j] = GamePanel.board.board[i][j];
			}
		}
		board_reset(tmpSnake, GamePanel.food, tmpBoard);

		boolean food_eated = false;

		while (!food_eated) {
			board_refresh(GamePanel.food, tmpSnake, tmpBoard);
			int[] move = choose_shortest_safe_move(tmpSnake, tmpBoard);
			Point oldhead = tmpSnake.snakeBody.getFirst();
			tmpSnake.snakeBody.addFirst(new Point(oldhead.x + move[0], oldhead.y + move[1]));
			Point newhead = tmpSnake.snakeBody.getFirst();
			if (newhead.x == GamePanel.food.food.x && newhead.y == GamePanel.food.food.y) {
				board_reset(tmpSnake, GamePanel.food, tmpBoard);
				tmpBoard.board[GamePanel.food.food.x][GamePanel.food.food.y] = GamePanel.SNAKE;
				food_eated = true;
			} else {
				tmpBoard.board[newhead.x][newhead.y] = GamePanel.SNAKE;
				tmpBoard.board[tmpSnake.snakeBody.getLast().x][tmpSnake.snakeBody.getLast().y] = GamePanel.UNDEFINE;
				tmpSnake.snakeBody.removeLast();
			}
		}
	}

	/**
	 * find a safe path for the snake to move
	 * 
	 * @return the coordinate that the snake need to move to in the next gameCycle
	 */
	public int[] find_safe_way() {
		int[] safe_move = { -1111, -1111 };
		virtual_shortest_move();
		if (is_tail_inside()) {
			return choose_shortest_safe_move(GamePanel.snake, GamePanel.board);
		}
		safe_move = follow_tail();
		return safe_move;
	}

	/**
	 * make the snake move for the next gameCycle
	 * 
	 * @param pbestmove the coordinate where the snake will be moved
	 */
	public void makeMove(int[] pbestmove) {
		Point oldhead = GamePanel.snake.snakeBody.getFirst();
		GamePanel.snake.snakeBody.addFirst(new Point(oldhead.x + pbestmove[0], oldhead.y + pbestmove[1]));
		Point newhead = GamePanel.snake.snakeBody.getFirst();
		if (newhead.x == GamePanel.food.food.x && newhead.y == GamePanel.food.food.y) {

			System.out.println(GamePanel.snake.snakeBody.size());
			if (GamePanel.snake.snakeBody.size() == GamePanel.WIDTH * GamePanel.HEIGHT) {
				// System.out.println();
			}
			GamePanel.board.board[GamePanel.food.food.x][GamePanel.food.food.y] = GamePanel.SNAKE;
			System.out.println(GamePanel.food.food.x + " " + GamePanel.food.food.y);
			if (GamePanel.snake.size() < GamePanel.WIDTH * GamePanel.HEIGHT) {
				GamePanel.food.newfood();
			}
		} else {
			GamePanel.board.board[newhead.x][newhead.y] = GamePanel.SNAKE;
			GamePanel.board.board[GamePanel.snake.snakeBody.getLast().x][GamePanel.snake.snakeBody
					.getLast().y] = GamePanel.UNDEFINE;
			GamePanel.snake.snakeBody.removeLast();
		}
	}

}
