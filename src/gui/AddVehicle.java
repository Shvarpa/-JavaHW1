package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import classes.AmphibiousVehicle;
import classes.Bike;
import classes.CruiseShip;
import classes.Frigate;
import classes.Input;
import classes.Jeep;
import classes.PlayDrone;
import classes.SpyDrone;
import classes.Vehicle;
import gui.Images.ImageOpener;
import interfaces.Commercial;
import interfaces.ILandVehicle;

public class AddVehicle extends JFrame {
	JComboBox<ImageText> typesChoice;
	JPanel mainPanel, selectPanel, buttonPanel , statusPanel;
	JScrollPane fieldPanel;
	
	private AddVehicleForm form;
	private AddVehicleImages imagePanel;
	private JLabel statusLabel;

	// Constructor
	public AddVehicle() {
		setTitle("Add Vehcile");
		// Create the main panel to contain the four sub panels.
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(mainPanel);

		///////////////////////////////////////////////// selectPanel/////////////
		// Create combo box with vehicles types choices.
		selectPanel = new JPanel();
		typesChoice = ComboBoxesCreator.createVehiclesComboBox(new Dimension(60, 60));
		typesChoice.setSelectedIndex(1);
		// Add border around the select panel.
		selectPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Select vehicles"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// Add vehicles types combo box to select panel and image label to selectPanel.
		selectPanel.setLayout(new BorderLayout());
		selectPanel.add(typesChoice, BorderLayout.CENTER);

		// Listen to events from combo box.
		typesChoice.addActionListener((event)->{
			form.show(typesChoice.getSelectedItem().toString());
			imagePanel.show(typesChoice.getSelectedItem().toString());
			pack();
		});

		/////////////////////////////////////////////// fieldpanel/////////////
		fieldPanel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		form = new AddVehicleForm(typesChoice.getSelectedItem().toString());
		fieldPanel.setViewportView(form);
		fieldPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Input Vehicle"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//////////////////////////////// imagepanel///////////////////////	
		imagePanel = new AddVehicleImages(typesChoice.getSelectedItem().toString());
		// Add border around the image panel.
		imagePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Image"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// Add vehicles types combo box to select panel and image label to selectPanel.


		//////////////////////////////// buttonpanel///////////////////////
		buttonPanel = new JPanel();
		// Add border around the button panel.
		buttonPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("action"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// Add vehicles to db
		buttonPanel.setLayout(new GridLayout(1, 3, 5, 5));
		JButton addButton = new JButton("Add");
		addButton.addActionListener((event) -> {
			Vehicle curr = null;
			try {
				curr = createVehicle(typesChoice.getSelectedItem().toString());
			}
			catch (NumberFormatException e) {
				System.err.println(e.getMessage());
				statusLabel.setText(e.getMessage());
				pack();
				return;
			}
			if (curr==null) return;
			DBConnect.getConnection().addVehicle(curr);
			dispose();
		});
		JButton backButton = new JButton("Back");
		backButton.addActionListener((event) -> {
			dispose();
		});
		buttonPanel.add(addButton);
		buttonPanel.add(backButton);
		
		//////////////////////////////// status panel ///////////////////////
		statusPanel = new JPanel();
		statusLabel = new JLabel();
		statusLabel.setForeground(Color.RED);
		statusPanel.add(statusLabel);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridheight = 1;
		mainPanel.add(selectPanel, gbc);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridheight = 1;
		mainPanel.add(fieldPanel, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridheight = 1;
		mainPanel.add(imagePanel, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridheight = 1;
		mainPanel.add(buttonPanel, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridheight = 1;
		mainPanel.add(statusPanel, gbc);
		
		pack();
	}

	private Vehicle createVehicle(String choice) throws NumberFormatException{
		Vehicle result = form.createVehicle();
		if (result != null)
			result.setImagePath(imagePanel.getSelectedImage());
		return result;
	}

}
