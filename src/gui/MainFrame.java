package gui;

import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import classes.Database;
import classes.Vehicle;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JButton addVehicleButton;
	private JButton buyVehicleButton;
	private JButton testDriveButton;
	private JButton resetDistancesButton;
	private JButton changeFlagsButton;
	private DataPanel dataPanel;
	private JLabel status;
	private String defaultStatus = "current status:";
	private JButton refreshButton;
	private Database db = Database.getInstance();

	public MainFrame() {
		setTitle("Car Agency");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 345);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel rightPanel = new JPanel();
		contentPane.add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new GridLayout(6, 1, 0, 0));

		/// buttons
		addVehicleButton = new JButton("Add Vehicle");
		addVehicleButton.setEnabled(true);

		buyVehicleButton = new JButton("Buy Vehicle");
		buyVehicleButton.setEnabled(false);

		testDriveButton = new JButton("Test Drive");
		testDriveButton.setEnabled(false);

		resetDistancesButton = new JButton("Reset Distances");
		resetDistancesButton.setEnabled(false);

		changeFlagsButton = new JButton("Change Flags");
		changeFlagsButton.setEnabled(false);

		refreshButton = new JButton("refresh");
		refreshButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				dataPanel.refresh();
			}
		});

		rightPanel.add(addVehicleButton);
		rightPanel.add(buyVehicleButton);
		rightPanel.add(testDriveButton);
		rightPanel.add(resetDistancesButton);
		rightPanel.add(changeFlagsButton);
		rightPanel.add(refreshButton);

		// actions
		addVehicleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						AddVehicle addVehicleWindow = new AddVehicle();
						addVehicleWindow.setVisible(true);
						addVehicleWindow.setLocationRelativeTo(null);
					}
				});

			}
		});
		
		buyVehicleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				VehicleSelectButton vS=dataPanel.getSelectedVehicle();
				if (vS==null) return;
				vS.setSelected(false);
				db.buyVehicle(vS.getVehicle());
				dataPanel.refresh();
			}
		});
		
		testDriveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				Vehicle currVehicle= dataPanel.getSelectedVehicle().getVehicle();
				if(currVehicle==null) return;
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						TestDrive testDriveWindow = new TestDrive(currVehicle);
						testDriveWindow.setVisible(true);
						testDriveWindow.setLocationRelativeTo(null);
					}
				});
			}
		});

		dataPanel = new DataPanel();		
		dataPanel.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String eventName = event.getPropertyName();
				if (eventName.equals("isEmpty")) {
					if(event.getNewValue().equals(true)) {
						resetDistancesButton.setEnabled(false);
						changeFlagsButton.setEnabled(false);
						buyVehicleButton.setEnabled(false);
						testDriveButton.setEnabled(false);
					}
					else {
						resetDistancesButton.setEnabled(true);
						changeFlagsButton.setEnabled(true);
					}
				}
				else if(eventName.equals("isSelected")) {
					if (event.getNewValue().equals(true)) {
						buyVehicleButton.setEnabled(true);
						testDriveButton.setEnabled(true);
					}
					else {
						buyVehicleButton.setEnabled(false);
						testDriveButton.setEnabled(false);
					}
				}
			}
		});
				
		
		contentPane.add(dataPanel, BorderLayout.CENTER);

		JPanel toStringPanel = new JPanel();
		contentPane.add(toStringPanel, BorderLayout.SOUTH);
		toStringPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

		// labels
		status = new JLabel(defaultStatus);
		status.setForeground(Color.GRAY);
		toStringPanel.add(status);

	}
}
