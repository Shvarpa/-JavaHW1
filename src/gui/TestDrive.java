//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import classes.Vehicle;

import java.awt.Color;

public class TestDrive extends JDialog {
	private static final long serialVersionUID = 1L;
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
				updateStatusLabel("Preparing test drive, please wait", Color.BLACK);
				okButton.setEnabled(false);
				db.new testDriveVehicleThread (currVehicle, distance) {
					@Override
					protected void done() {
						switch(getStatus()) {
						case STOP:
							JOptionPane.showMessageDialog(null,"The vehicle was bought already, closing...");
							dispose();
							return;
						case RETRY:
							JOptionPane.showMessageDialog(null,"the vehicle:\n" + currVehicle.toString() + "\nis during/awaiting a test drive, please retry later");
							updateStatusLabel(" ", null);
							okButton.setEnabled(true);
							return;
						case DONE:
							JOptionPane.showMessageDialog(null, "the vehicle \n"+ currVehicle.toString() +"\nwas taken for a test drive of " + distance + "km succesfully!");
							okButton.setEnabled(true);
							dispose();
							return;
						case ABORT: default:
							JOptionPane.showMessageDialog(null, "the "+distance+" testdrive with the vehicle \n"+ currVehicle.toString() +"\nwas canceled");
						}
					}
				}.execute();
			});
		});
		cancelButton.addActionListener((event)->{dispose();});
	}		
}








