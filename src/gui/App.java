//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {
	///creates and shows the application
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame app = MainFrame.getInstance();
				app.setSize(500, 300);
				app.setLocationRelativeTo(null);;
				app.setVisible(true);				
			}
		});

	}
}
