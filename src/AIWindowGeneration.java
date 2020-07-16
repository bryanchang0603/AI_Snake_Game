
/**
 * The class for display the AI mode window and initiator of the AI mode
 */
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class AIWindowGeneration {

	private JFrame frmClass;
	public static void NewGame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AIWindowGeneration window = new AIWindowGeneration();
					JFrame frame = new JFrame("test");
					frame.setSize(615, 480);
					frame.setLocationRelativeTo(null);
					frame.setContentPane(new GamePanel());
					frame.setBackground(Color.black);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
      		
	}
}
