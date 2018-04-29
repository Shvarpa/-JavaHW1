package gui;

import java.awt.EventQueue;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JButton;

public class VehiclesDisplay extends JScrollPane {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VehiclesDisplay frame = new VehiclesDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VehiclesDisplay() {
		setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	}

}
