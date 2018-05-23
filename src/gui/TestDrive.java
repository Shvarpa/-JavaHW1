//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

	private void updateStatusLabel(String text, Color c) {
		if (c==null) c= Color.RED;
		statusLabel.setText(text);
		statusLabel.setForeground(c);
		revalidate();
	}
	
	public TestDrive(Vehicle curr) {
		this.currVehicle = curr;
		setTitle("Test Drive");
		setBounds(100, 100, 350, 150);
		setLayout(new BorderLayout());
		add(contentPanel, BorderLayout.CENTER);
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
		statusLabel = new JLabel();
		updateStatusLabel(" ", null);
		statusPanel.add(statusLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPanel, BorderLayout.SOUTH);
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
			updateStatusLabel(" ", null);
			Double distance = inputScanner.nextDouble();
			inputScanner.close();
			if(distance<=0) {
				statusLabel.setText("distance must be a positive!!");
				return;
			}
			SwingUtilities.invokeLater(()->{
				updateStatusLabel("Started test drive, please wait", Color.BLACK);
				okButton.setEnabled(false);
				Utilities.invokeInBackground(
					() -> {// background					
						try {
							boolean status = aquireTestRide(currVehicle);
							if(!status) {
								this.stop=true;
								JOptionPane.showMessageDialog(null, "vehicle test drive already in progress");
								return;
							}
							Thread.sleep((long)(distance*100));
						} catch (InterruptedException e) {
							Utilities.log("thread sleep interrupted");
							releaseTestRide(currVehicle);
						}
					}, 
					()->{
						if(stop) {dispose();stop=false;return;}
						releaseTestRide(currVehicle);
						db.testDriveVehicle(currVehicle, distance);
						updateStatusLabel(" ", null);
						JOptionPane.showMessageDialog(null, "the vehicle \n"+ currVehicle.toString() +"\nwas taken for a test drive of " + distance + "km succesfully!");
						okButton.setEnabled(true);
						dispose();
					});
			});
		});
		cancelButton.addActionListener((event)->{dispose();});
	}
	private boolean stop = false;

	
	///// locking vehicle test riding
		static private HashMap<String, Semaphore> testDrivers;
		private static List<String> reqTestRiders(Vehicle v){
			List<String> reqTestRiders = new ArrayList<String>();
			if (v instanceof ILandVehicle) {
				reqTestRiders.add(ILandVehicle.class.getSimpleName());
			}
			if (v instanceof ISeaVehicle) {
				reqTestRiders.add(ISeaVehicle.class.getSimpleName());
			}
			if (v instanceof IAirVehicle) {
				reqTestRiders.add(IAirVehicle.class.getSimpleName());
			}
			return reqTestRiders;
		}
		private boolean aquireTestRide(Vehicle vehicle) {
			if (db.duringTestDriveContains(vehicle)) return false;
			try {
			for(String s:TestDrive.reqTestRiders(vehicle)) testDrivers.get(s).acquire();
			} catch (InterruptedException e) {
				for(String s:TestDrive.reqTestRiders(vehicle)) testDrivers.get(s).release();
				return false;
			}
			return db.duringTestDriveAdd(vehicle);
		}
		private void releaseTestRide(Vehicle vehicle) {
			db.duringTestDriveRemove(vehicle);
			for(String s:TestDrive.reqTestRiders(vehicle)) testDrivers.get(s).release();
		}
		static {
			testDrivers = new HashMap<String, Semaphore>(3);
			for (String s : Arrays.asList(ILandVehicle.class.getSimpleName(), ISeaVehicle.class.getSimpleName(),
					IAirVehicle.class.getSimpleName()))
				testDrivers.put(s, new Semaphore(1));
		}	
		
}








