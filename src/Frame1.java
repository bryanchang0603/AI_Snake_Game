import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

/**
 * The class for displaying the main menu of the game
 * 
 * @author Josh
 *
 */
public class Frame1 {

	JFrame frame;
	private JButton btnQuitGame;
	private JButton btnDifficulty;
	public static int difficulty = 5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application and initialize the game.
	 */
	public Frame1() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(167, 71, 117, 29);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(difficulty);
				Display nw = new Display(difficulty);
				nw.run(difficulty);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnNewGame);

		JButton btnNewButton = new JButton("AI Mode");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AIWindowGeneration nw = new AIWindowGeneration();
				nw.NewGame();
			}
		});
		btnNewButton.setBounds(167, 107, 117, 29);
		frame.getContentPane().add(btnNewButton);

		btnQuitGame = new JButton("Quit Game");
		btnQuitGame.addActionListener(new CloseListener());
		btnQuitGame.setBounds(167, 182, 117, 29);
		frame.getContentPane().add(btnQuitGame);

		btnDifficulty = new JButton("Difficulty");
		btnDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Difficulty nw = new Difficulty();
				nw.NewGame();
				// System.out.println(difficulty);
			}
		});
		btnDifficulty.setBounds(167, 148, 117, 29);
		frame.getContentPane().add(btnDifficulty);

		JLabel btnMySnake = new JLabel("My Snake");
		btnMySnake.setBounds(200, 21, 148, 38);
		frame.getContentPane().add(btnMySnake);
	}

	private class CloseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// DO SOMETHING
			System.exit(0);
		}
	}
}
