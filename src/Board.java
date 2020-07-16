
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The class responsible for the functionalities of board in the game
 */
public class Board extends JPanel implements ActionListener {

	public int[][] board = new int[GamePanel.WIDTH][GamePanel.HEIGHT];
	private int boardWidthPixel;
	private int boardHeightPixel;
	private int dotSize = 10;
	private int boardWidth;
	private int boardHeight;
	private int gameCycle = 100;
	private int speed = 3;
	private ArrayList<Snake> snake = new ArrayList<Snake>();

	private int bodyLength;
	private int appleX;
	private int appleY;

	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;
	private boolean pause = false;

	private Timer timer;
	private Image ball;
	private Image apple;
	private Image head;
	Toolkit toolkit = Toolkit.getDefaultToolkit();

	/**
	 * Init function of Board when difficulty level is given
	 * 
	 * @param diff the given difficulty
	 */
	public Board(int diff) {
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		boardWidthPixel = 400;
		boardHeightPixel = 400;
		boardWidth = 40 - 1;
		boardHeight = 40 - 1;
		speed = 11 - diff;

		setPreferredSize(new Dimension(boardWidthPixel, boardHeightPixel));
		loadImages();
		initGame();
	}

	/**
	 * init function of Board when difficulty level is not given
	 */
	public Board() {
		for (int i = 0; i < GamePanel.WIDTH; i++) {
			for (int j = 0; j < GamePanel.HEIGHT; j++) {
				this.board[i][j] = GamePanel.UNDEFINE;
			}
		}
		loadImages();
	}

	/**
	 * load the image of ball, apple, and head from the folder resource
	 */
	private void loadImages() {
		ImageIcon iid = new ImageIcon("src/resource/dot.png");
		ball = iid.getImage();

		ImageIcon iia = new ImageIcon("src/resource/apple.png");
		apple = iia.getImage();

		ImageIcon iih = new ImageIcon("src/resource/head.png");
		head = iih.getImage();
	}

	/**
	 * Initialize the game so that game becomes ready to run.
	 */
	private void initGame() {

		bodyLength = 4;
		snake.add(new Snake(50, 50, Item.item.head));
		for (int x = 1; x < bodyLength; x++) {
			snake.add(new Snake(50 - x * 10, 50, Item.item.body));
		}

		locateApple();

		timer = new Timer(gameCycle * speed, this);
		timer.start();
	}

	/**
	 * function responsible for drawing the apple and the snake onto the board when
	 * board is not initialized
	 * 
	 * @param gameBoardWindow the window where the image will be drawn
	 */
	@Override
	public void paintComponent(Graphics gameBoardWindow) {
		super.paintComponent(gameBoardWindow);

		if (inGame) {

			gameBoardWindow.drawImage(apple, appleX, appleY, this);

			for (int i = 0; i < bodyLength; i++) {
				if (snake.get(i).isHead()) {
					gameBoardWindow.drawImage(head, snake.get(i).getX(), snake.get(i).getY(), this);
				} else {
					gameBoardWindow.drawImage(ball, snake.get(i).getX(), snake.get(i).getY(), this);
				}
			}

			Toolkit.getDefaultToolkit().sync();

		} else {

			gameOver(gameBoardWindow);
		}
	}

	/**
	 * function responsible for drawing the apple and the snake onto the board when
	 * board is initialized
	 * 
	 * @param gameBoardWindow the window where the image will be drawn
	 */
	public void drawMe(Graphics gameBoardWindow) {
		for (int i = 0; i < GamePanel.WIDTH; i++) {
			for (int j = 0; j < GamePanel.HEIGHT; j++) {
				if (board[i][j] == GamePanel.FOOD) {
					gameBoardWindow.drawImage(apple, i * 10, j * 10, this);
				} else if (board[i][j] == GamePanel.SNAKE) {
					gameBoardWindow.drawImage(ball, i * 10, j * 10, this);
				} else {
					gameBoardWindow.setColor(Color.BLACK);
					gameBoardWindow.fillRect(i * 10, j * 10, 29, 29);
				}
			}
		}
		Point headSnake = GamePanel.snake.snakeBody.getFirst();
		gameBoardWindow.drawImage(head, headSnake.x * 10, headSnake.y * 10, this);
	}

	/**
	 * display the Game Over pop up window
	 * 
	 * @param gameBoardWindow the window where the Game Over Window will pop up
	 */
	private void gameOver(Graphics gameBoardWindow) {

		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		gameBoardWindow.setColor(Color.white);
		gameBoardWindow.setFont(small);
		gameBoardWindow.drawString(msg, (boardWidthPixel - metr.stringWidth(msg)) / 2, boardHeightPixel / 2);
	}

	/**
	 * check if the snake eats the apple. If so, increase the length of the snake by
	 * one and place another apple
	 */
	private void checkApple() {

		if ((snake.get(0).getX() == appleX) && (snake.get(0).getY() == appleY)) {

			bodyLength++;
			snake.add(new Snake(appleX - 10, appleY - 10, Item.item.body));
			locateApple();
		}
	}

	/**
	 * move the snake one block forward based on the current direction
	 */
	private void move() {

		for (int z = bodyLength - 1; z > 0; z--) {
			int xTemp = snake.get(z - 1).getX();
			int yTemp = snake.get(z - 1).getY();
			snake.get(z).updateCoordinate(xTemp, yTemp);
		}
		int xNewHead = snake.get(0).getX();
		int yNewHead = snake.get(0).getY();
		if (leftDirection) {
			snake.get(0).updateX(xNewHead - dotSize);
		}

		if (rightDirection) {
			snake.get(0).updateX(xNewHead + dotSize);
		}

		if (upDirection) {
			snake.get(0).updateY(yNewHead - dotSize);
		}

		if (downDirection) {
			snake.get(0).updateY(yNewHead + dotSize);
		}
	}

	/**
	 * check if the game over condition is met
	 */
	private void checkCollision() {

		for (int z = bodyLength - 1; z > 0; z--) {

			if ((snake.get(0).getX().equals(snake.get(z).getX()))
					&& (snake.get(0).getY().equals(snake.get(z).getY()))) {
				inGame = false;
			}
		}

		if (snake.get(0).getY() >= boardHeightPixel || snake.get(0).getY() < 0 || snake.get(0).getX() >= boardWidthPixel
				|| snake.get(0).getX() < 0) {
			inGame = false;
		}

		if (!inGame) {
			timer.stop();
		}
	}

	/**
	 * randomly generate an apple on the game board
	 */
	private void locateApple() {

		int r = (int) (Math.random() * boardWidth);
		appleX = ((r * dotSize));

		r = (int) (Math.random() * boardHeight);
		appleY = ((r * dotSize));
	}

	/**
	 * change the game speed of the game with the speed in the range of 1 - 10
	 */
	private void changeSpeed(int change) {
		if ((change == -1 && speed == 1) || (change == 1 && speed == 10)) {
			return;
		} else if (change == 1) {
			speed += 1;
		} else if (change == -1) {
			speed -= 1;
		}
	}

	/**
	 * The repeating function for handling general running mechanism of the game
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (inGame) {
			if (!pause) {
				checkApple();
				checkCollision();
				move();
				timer.stop();
				timer = new Timer(gameCycle * speed, this);
				timer.start();
			}

		}

		repaint();
	}

	/**
	 * class for handling the incoming keyboard input
	 *
	 */
	private class TAdapter extends KeyAdapter {
		/**
		 * function for handling the keyboard input
		 */
		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			if (key == KeyEvent.VK_P) {
				pause = !pause;
			}
			if (key == KeyEvent.VK_EQUALS) {
				changeSpeed(-1);
			}
			if (key == KeyEvent.VK_MINUS) {
				changeSpeed(1);
			}
			if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_UP) && (!downDirection)) {
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}

			if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
		}
	}
}
