//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import classes.Database;
import classes.Jeep;
import classes.Vehicle;

public class App {
	
	private static void test() {
		Vehicle n = new Jeep("a", 5, 5, 5);
		n.setImagePath("images\\Jeep.png");
		DBConnect.getConnection().addVehicle(n);
		DBConnect.getConnection().addVehicle((Vehicle)(new Jeep("b", 5, 5, 5)));
		DBConnect.getConnection().addVehicle((Vehicle)(new Jeep("c", 5, 5, 5)));


	}
	
	public static void main(String[] args) {
		test();
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
