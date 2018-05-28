//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import classes.Jeep;
import classes.PlayDrone;

public class App {
	///creates and shows the application
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame app = new MainFrame();
				app.setSize(500, 300);
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				app.setLocationRelativeTo(null);;
				app.setVisible(true);				
			}
		});

	}
}
