package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import classes.Database;
import classes.Vehicle;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class MainFrame extends JFrame implements ActionListener,FocusListener{

	private JPanel contentPane;
	private JButton addVehicleButton;;
	private JButton buyVehicleButton;
	private JButton testDriveButton;
	private JButton resetDistancesButton;
	private JButton changeFlagsButton;
	private DataPanel dataPanel;
	private JDialog addVehicleWindow;
	private JLabel status;
	private String defaultStatus = "current status:";
	private JButton btnRefresh;

	public MainFrame() {
		setTitle("Car Agency");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel rightPanel = new JPanel();
		contentPane.add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new GridLayout(6, 1, 0, 0));

		/// buttons
		addVehicleButton = new JButton("Add Vehicle");

		rightPanel.add(addVehicleButton);

		buyVehicleButton = new JButton("Buy Vehicle");
		buyVehicleButton.setEnabled(false);
		buyVehicleButton.addFocusListener(this);

		rightPanel.add(buyVehicleButton);

		testDriveButton = new JButton("Test Drive");
		testDriveButton.setEnabled(false);

		rightPanel.add(testDriveButton);

		resetDistancesButton = new JButton("Reset Distances");
		resetDistancesButton.setEnabled(false);

		rightPanel.add(resetDistancesButton);

		changeFlagsButton = new JButton("Change Flags");
		changeFlagsButton.setEnabled(false);

		rightPanel.add(changeFlagsButton);

		btnRefresh = new JButton("refresh");
		btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				dataPanel.refresh();
			}
		});
		
		btnRefresh.addActionListener(dataPanel);
		rightPanel.add(btnRefresh);


		// actions
		addVehicleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						addVehicleWindow = new AddVehicle();
						addVehicleWindow.setVisible(true);
						addVehicleWindow.setLocationRelativeTo(null);
						// dispose();
					}
				});

				// rightPanel.remove(buyVehicleButton);
				// revalidate();
				// buyVehicleButton.setEnabled(true);
			}
		});

		/// panels
		dataPanel = new DataPanel();
		contentPane.add(dataPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		FlowLayout fl_bottomPanel = new FlowLayout(FlowLayout.LEFT, 10, 5);
		bottomPanel.setLayout(fl_bottomPanel);

		// labels
		status = new JLabel(defaultStatus);
		status.setForeground(Color.GRAY);
		bottomPanel.add(status);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("vehicle selected")) {
			System.out.println("vehicle selected");
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
