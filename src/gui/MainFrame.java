package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Car Agency");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		///panels
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JPanel rightPanel = new JPanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.gridheight = 3;
		gbc_rightPanel.gridwidth = 0;
		gbc_rightPanel.insets = new Insets(0, 0, 5, 0);
		gbc_rightPanel.gridx = 3;
		gbc_rightPanel.gridy = 0;
		contentPane.add(rightPanel, gbc_rightPanel);
		GridBagLayout gbl_rightPanel = new GridBagLayout();
		gbl_rightPanel.columnWidths = new int[]{89, 0};
		gbl_rightPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 23, 23, 0};
		gbl_rightPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_rightPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		rightPanel.setLayout(gbl_rightPanel);
		
		JPanel bottomPanel = new JPanel();
		GridBagConstraints gbc_bottomPanel = new GridBagConstraints();
		gbc_bottomPanel.gridwidth = 6;
		gbc_bottomPanel.fill = GridBagConstraints.BOTH;
		gbc_bottomPanel.gridx = 0;
		gbc_bottomPanel.gridy = 3;
		contentPane.add(bottomPanel, gbc_bottomPanel);
		FlowLayout fl_bottomPanel = new FlowLayout(FlowLayout.LEFT, 10, 5);
		bottomPanel.setLayout(fl_bottomPanel);
		
		///buttons
		JButton changeFlagsButton = new JButton("Change Flags");
		changeFlagsButton.setEnabled(false);
		changeFlagsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton addVehicleButton = new JButton("Add Vehicle");

		GridBagConstraints gbc_addVehicleButton = new GridBagConstraints();
		gbc_addVehicleButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_addVehicleButton.insets = new Insets(0, 0, 5, 0);
		gbc_addVehicleButton.gridx = 0;
		gbc_addVehicleButton.gridy = 2;
		rightPanel.add(addVehicleButton, gbc_addVehicleButton);
		
		JButton buyVehicleButton = new JButton("Buy Vehicle");
		buyVehicleButton.setEnabled(false);
		GridBagConstraints gbc_buyVehicleButton = new GridBagConstraints();
		gbc_buyVehicleButton.insets = new Insets(0, 0, 5, 0);
		gbc_buyVehicleButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_buyVehicleButton.gridx = 0;
		gbc_buyVehicleButton.gridy = 3;
		rightPanel.add(buyVehicleButton, gbc_buyVehicleButton);
		
		JButton testDriveButton = new JButton("Test Drive");
		testDriveButton.setEnabled(false);
		GridBagConstraints gbc_testDriveButton = new GridBagConstraints();
		gbc_testDriveButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_testDriveButton.insets = new Insets(0, 0, 5, 0);
		gbc_testDriveButton.gridx = 0;
		gbc_testDriveButton.gridy = 4;
		rightPanel.add(testDriveButton, gbc_testDriveButton);
		
		JButton btnResetDistances = new JButton("Reset Distances");
		btnResetDistances.setEnabled(false);
		GridBagConstraints gbc_btnResetDistances = new GridBagConstraints();
		gbc_btnResetDistances.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnResetDistances.insets = new Insets(0, 0, 5, 0);
		gbc_btnResetDistances.gridx = 0;
		gbc_btnResetDistances.gridy = 5;
		rightPanel.add(btnResetDistances, gbc_btnResetDistances);
		GridBagConstraints gbc_changeFlagsButton = new GridBagConstraints();
		gbc_changeFlagsButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_changeFlagsButton.insets = new Insets(0, 0, 5, 0);
		gbc_changeFlagsButton.gridx = 0;
		gbc_changeFlagsButton.gridy = 6;
		rightPanel.add(changeFlagsButton, gbc_changeFlagsButton);
		
		//labels
		JLabel status = new JLabel("current status:");
		status.setForeground(Color.GRAY);
		bottomPanel.add(status);
		
		//actions
		addVehicleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						Form form = new Form();
						form.setVisible(true);
						form.setLocationRelativeTo(null);
						dispose();
					}
				});

//				rightPanel.remove(buyVehicleButton);
//				revalidate();
//				buyVehicleButton.setEnabled(true);
			}
		});
	}

}
