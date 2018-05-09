//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import classes.Vehicle;
import java.awt.Color;

public class TestDrive extends JDialog {

	private Vehicle currVehicle;
	private final JPanel contentPanel = new JPanel();
	private JTextField distanceField;
	JLabel statusLabel;
	private DBConnect db = DBConnect.getConnection();

	private void setBadStatus() {
		String badStatus = "distance must be a number!!";
		String currStatus = statusLabel.getText();
		if (!currStatus.equals(badStatus)) {
			statusLabel.setText(badStatus);
		} else {
			statusLabel.setText("* " + badStatus + " *");
		}
	}
	private void clearStatus() {
		statusLabel.setText("");
	}

	public TestDrive(Vehicle curr) {
		this.currVehicle = curr;
		setTitle("Test Drive");
		setBounds(100, 100, 350, 150);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());
		{
			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
			JLabel distanceLabel = new JLabel("Enter Distance:");
			distanceField = new JTextField(" ");

			inputPanel.add(distanceLabel);
			inputPanel.add(distanceField);
			distanceField.setColumns(10);

			contentPanel.add(inputPanel, BorderLayout.CENTER);
		}
		{
			JPanel statusPanel = new JPanel();
			contentPanel.add(statusPanel, BorderLayout.SOUTH);
			statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			statusLabel = new JLabel(" ");
			statusLabel.setForeground(Color.RED);
			statusPanel.add(statusLabel);
		}
		{
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			JButton okButton = new JButton("OK");
			JButton cancelButton = new JButton("Cancel");
			getRootPane().setDefaultButton(okButton);
			buttonPanel.add(okButton);
			buttonPanel.add(cancelButton);

			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					String input = distanceField.getText();
					Scanner inputScanner = new Scanner(input);
					if (!inputScanner.hasNextDouble()) {
						setBadStatus();
						inputScanner.close();
						return;
					}
					clearStatus();
					Double distance = inputScanner.nextDouble();
					inputScanner.close();
					db.testDriveVehicle(currVehicle, distance);
					SwingUtilities.invokeLater(() -> {
						ConfirmationDialog confirmation = new ConfirmationDialog(
								"  the vehicle was taken for a test drive of " + distance + "km succesfully!  ");
						confirmation.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						confirmation.setVisible(true);
						confirmation.setLocationRelativeTo(null);
						confirmation.addWindowListener(new WindowListener() {

							@Override
							public void windowOpened(WindowEvent arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void windowIconified(WindowEvent arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void windowDeiconified(WindowEvent arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void windowDeactivated(WindowEvent arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void windowClosing(WindowEvent arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void windowClosed(WindowEvent arg0) {
								dispose();
							}

							@Override
							public void windowActivated(WindowEvent arg0) {
								// TODO Auto-generated method stub

							}
						});
					});
				}
			});
			
			cancelButton.addActionListener((event)->{dispose();});
		}
	}

}
