//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import classes.Vehicle;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;

import java.awt.Color;

public class TestDrive extends JDialog {

	private Vehicle currVehicle;
	private final JPanel contentPanel = new JPanel();
	private JTextField distanceField;
	JLabel statusLabel;
	private DBConnect db = DBConnect.getConnection();

	private void clearStatus() {
		statusLabel.setText("");
	}

	static private HashMap<String, Semaphore> testDrivers;
	static {
		testDrivers = new HashMap<String, Semaphore>(3);
		for (String s : Arrays.asList(ILandVehicle.class.getSimpleName(), ISeaVehicle.class.getSimpleName(),
				IAirVehicle.class.getSimpleName()))
			testDrivers.put(s, new Semaphore(1));
	}

	public TestDrive(Vehicle curr) {
		this.currVehicle = curr;
		setTitle("Test Drive");
		setBounds(100, 100, 350, 150);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JLabel distanceLabel = new JLabel("Enter Distance:");
		distanceField = new JTextField(" ");

		inputPanel.add(distanceLabel);
		inputPanel.add(distanceField);
		distanceField.setColumns(10);

		contentPanel.add(inputPanel, BorderLayout.CENTER);

		JPanel statusPanel = new JPanel();
		contentPanel.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		statusLabel = new JLabel(" ");
		statusLabel.setForeground(Color.RED);
		statusPanel.add(statusLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		getRootPane().setDefaultButton(okButton);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		okButton.addActionListener((event)->{
			String input = distanceField.getText();
			Scanner inputScanner = new Scanner(input);
			if (!inputScanner.hasNextDouble()) {
				statusLabel.setText("distance must be a number!!");
				inputScanner.close();
				return;
			}
			clearStatus();
			Double distance = inputScanner.nextDouble();
			inputScanner.close();
			if(distance<=0) {
				statusLabel.setText("distance must be a positive!!");
				return;
			}
			SwingUtilities.invokeLater(()->{
				List<String> busyTestDrivers = new ArrayList<String>();
				if (currVehicle instanceof ILandVehicle) {
					busyTestDrivers.add(ILandVehicle.class.getSimpleName());
				}
				if (currVehicle instanceof ISeaVehicle) {
					busyTestDrivers.add(ISeaVehicle.class.getSimpleName());
				}
				if (currVehicle instanceof IAirVehicle) {
					busyTestDrivers.add(IAirVehicle.class.getSimpleName());
				}
				Utilities.invokeInBackground(
					() -> {// background					
						try {	
							for (String driver : busyTestDrivers) testDrivers.get(driver).acquire();	
							Thread.sleep((long)(distance*100));
						} catch (InterruptedException e) {
							for (String driver : busyTestDrivers) testDrivers.get(driver).release();
						}
					}, 
					()->{
						for (String driver : busyTestDrivers) testDrivers.get(driver).release();
						db.testDriveVehicle(currVehicle, distance);
						ConfirmationDialog confirmation = new ConfirmationDialog("the vehicle "+ currVehicle.toString() +" was taken for a test drive of " + distance + "km succesfully!");
						confirmation.addWindowListener(new WindowListener() {
							@Override
							public void windowClosed(WindowEvent arg0) {
								dispose();
							}
							@Override //unimplemented
							public void windowOpened(WindowEvent arg0) {} public void windowIconified(WindowEvent arg0) {} public void windowDeiconified(WindowEvent arg0) {}
							public void windowDeactivated(WindowEvent arg0) {} public void windowClosing(WindowEvent arg0) {} public void windowActivated(WindowEvent arg0) {}
						});
						Utilities.showDialog(confirmation);
					});
			});
		});
		cancelButton.addActionListener((event)->{dispose();});
	}
}








