//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
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
import javax.swing.SwingWorker;

import classes.Vehicle;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;
import javafx.scene.media.MediaPlayer.Status;

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
				db.new testDriveVehicle (currVehicle, distance) {
					@Override
					protected void done() {
						super.done();
						switch(getStatus()) {
						case STOP: default:
							JOptionPane.showMessageDialog(null,"The vehicle was bought already, closing...");
							dispose();
							return;
						case RETRY:
							JOptionPane.showMessageDialog(null,DBConnect.duringTransactionMessege);
							updateStatusLabel(" ", null);
							okButton.setEnabled(true);
							return;
						case CONTINUE:
							JOptionPane.showMessageDialog(null, "the vehicle \n"+ currVehicle.toString() +"\nwas taken for a test drive of " + distance + "km succesfully!");
							okButton.setEnabled(true);
							dispose();
							return;
						}
					}
				}.execute();
			});
		});
		cancelButton.addActionListener((event)->{dispose();});
	}		
}








