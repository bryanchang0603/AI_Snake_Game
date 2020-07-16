import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 * the class responsible to generate the window of Difficulty and contains
 * functions supporting user input Difficulty
 */
public class Difficulty {

	private JFrame frame;
	private JTextField textField;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void NewGame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Difficulty window = new Difficulty();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// return a;
	}

	/**
	 * Create the application.
	 */
	public Difficulty() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Enter Difficulty Level(1-10):");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(116, 71, 219, 29);
		frame.getContentPane().add(btnNewButton);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textField.setBounds(200, 112, 64, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().length() != 0)
					Frame1.difficulty = Integer.parseInt(textField.getText());
				if (Frame1.difficulty > 10)
					Frame1.difficulty = 10;
				else if (Frame1.difficulty < 1)
					Frame1.difficulty = 1;
				// System.out.println(a);
				Frame1 window = new Frame1();
				window.frame.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(0, 210, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
	}
}
