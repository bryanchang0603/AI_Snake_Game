import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * the class for generate the window for manual mode of the game
 */
public class Display extends JFrame {
	/**
	 * the init function of the Display
	 * 
	 * @param diff the difficulty level that need to pass to board
	 */
	public Display(int diff) {
		add(new Board(diff));

		setResizable(false);
		pack();

		setTitle("Snake");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * the function to display the manual mode of the game
	 * 
	 * @param diff the difficulty level that need to pass to board
	 */
	public void run(int diff) {

		EventQueue.invokeLater(() -> {
			JFrame ex = new Display(diff);
			ex.setVisible(true);
		});
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			JFrame ex = new Display(5);
			ex.setVisible(true);
		});
	}
}
