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
	final static String FLAGS_PATH = "flags\\";
	final static String IMAGES_SUFIX = ".jpg";
	final static String ICONS_SUFIX = ".png";
	final static String IMAGES_PATH = "VehicleTypes\\";
	final static int NUMBERFLAG = 7;
	final static String ICONS_PATH = "Icons\\";
	final static int NUM_IMAGES = 8;
	final static int NUMBERFIELD = 9;
	final static String COMBO_BOX_CHANGED_COMMAND = "comboBoxChanged";
	
	
	final JComboBox<ImageText> flagsComboBox;
	JComboBox<ImageText> typschoices;
	JPanel mainPanel, selectPanel, imagePanel, fieldPanel, buttonPanel , statusPanel;
	private JFileChooser fileChooser = new JFileChooser();
	
	// fieldpanel
	private JLabel[] jbl;
	private JTextField[] jtf;
	private JLabel[] jl;
	private String picpath = null;
	private String fpicpath = null;
	
	//imagepanel
	JRadioButton jrb0,jrb1,jrb2,jrb3;
	
	//status panel
	private JLabel statusLabel;

	// Constructor
	public AddVehicle() {
		setTitle("Add Vehcile");
		// Create the main panel to contain the four sub panels.
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(mainPanel);

		// Create the phase selection and display panels.
		selectPanel = new JPanel();
		imagePanel = new JPanel();
		fieldPanel = new JPanel();
		buttonPanel = new JPanel();
		statusPanel = new JPanel();
		
		// Add the select and display panels to the main panel.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1;
		mainPanel.add(selectPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 1;
		mainPanel.add(fieldPanel, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		mainPanel.add(imagePanel, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		mainPanel.add(buttonPanel, c);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		mainPanel.add(statusPanel, c);

		///////////////////////////////////////////////// selectPanel/////////////
		// Create combo box with vehicles types choices.
		typschoices = ComboBoxesCreator.createVehiclesComboBox(new Dimension(60, 60));
		typschoices.setSelectedIndex(1);
		// Add border around the select panel.
		selectPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Select vehicles"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// Add vehicles types combo box to select panel and image label to selectPanel.
		selectPanel.setLayout(new BorderLayout());
		selectPanel.add(typschoices, BorderLayout.CENTER);

		// Listen to events from combo box.
		typschoices.addActionListener((event)->{
			updatefield(typschoices.getSelectedItem().toString());
		});

		/////////////////////////////////////////////// fieldpanel/////////////
		fileChooser.setCurrentDirectory(new java.io.File("."));
		fileChooser.setDialogTitle("Choose a picture to upload");
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG", "png"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPG", "jpg"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("GIF", "gif"));
		fileChooser.setAcceptAllFileFilterUsed(false);

		// Add border around the field panel.
		flagsComboBox = ComboBoxesCreator.createFlagsComboBox(new Dimension(30, 20));
		fieldPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Input Vehicle"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// Add labels and textfields to field panel.
		fieldPanel.setLayout(new GridLayout(NUMBERFIELD + 1, 2, 1, 1));
		jbl = new JLabel[NUMBERFIELD];
		jtf = new JTextField[NUMBERFIELD];
		for (int i = 0; i < NUMBERFIELD; i++) {
			jbl[i] = new JLabel();
			jtf[i] = new JTextField();
		}
		for (int i = 0; i < NUMBERFIELD; i++) {
			fieldPanel.add(jbl[i]);
			fieldPanel.add(jtf[i]);
		}
		fieldPanel.add(flagsComboBox);

		//////////////////////////////// imagepanel///////////////////////
		// Add border around the image panel.
		imagePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Image"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// Add vehicles types combo box to select panel and image label to selectPanel.
		imagePanel.setLayout(new GridLayout(2, 4, 5, 5));

		// Create the radio buttons.
		jrb0 = new JRadioButton();
		jrb1 = new JRadioButton();
		jrb2 = new JRadioButton();
		jrb3 = new JRadioButton();
		JButton picselect = new JButton("browse");
		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(jrb0);
		group.add(jrb1);
		group.add(jrb2);
		group.add(jrb3);
		// Register a listener for the radio buttons.
		jrb0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picpath = IMAGES_PATH + typschoices.getSelectedItem().toString() + "\\"
						+ typschoices.getSelectedItem().toString() + 1 + IMAGES_SUFIX;
			}
		});
		jrb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picpath = IMAGES_PATH + typschoices.getSelectedItem().toString() + "\\"
						+ typschoices.getSelectedItem().toString() + 2 + IMAGES_SUFIX;
			}
		});
		jrb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picpath = IMAGES_PATH + typschoices.getSelectedItem().toString() + "\\"
						+ typschoices.getSelectedItem().toString() + 3 + IMAGES_SUFIX;
			}
		});
		jrb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picpath = fpicpath;
			}
		});

		imagePanel.add(jrb0);
		imagePanel.add(jrb1);
		imagePanel.add(jrb2);
		imagePanel.add(jrb3);

		jl = new JLabel[3];
		for (int i = 0; i < 3; i++)
			imagePanel.add(jl[i] = new JLabel());
		imagePanel.add(picselect);
		picselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File uploadedPictureFile = null;
					uploadedPictureFile = fileChooser.getSelectedFile();
					if (uploadedPictureFile != null) // user actually chose a file
					{
						fpicpath = uploadedPictureFile.getAbsolutePath();
						picselect.setIcon(new ImageIcon(new ImageIcon(uploadedPictureFile.getAbsolutePath()).getImage()
								.getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
						picselect.setText("");
						pack();

					}

				}
			}
		});

		//////////////////////////////// buttonpanel///////////////////////
		// Add border around the button panel.
		buttonPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("action"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// Add vehicles to db
		buttonPanel.setLayout(new GridLayout(1, 3, 5, 5));
		JButton addButton = new JButton("Add");
		addButton.addActionListener((event) -> {
			Vehicle curr = null;
			try {
				curr = createVehicle(typschoices.getSelectedItem().toString());
			}
			catch (NumberFormatException e) {
				System.err.println(e.getMessage());
				statusLabel.setText(e.getMessage());
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
		
		statusLabel = new JLabel();
		statusLabel.setForeground(Color.RED);
		statusPanel.add(statusLabel);
		
		
		updatefield(typschoices.getSelectedItem().toString());
		pack();
	}

	private Vehicle createVehicle(String choice) throws NumberFormatException{
		Vehicle result = null;
		String model;
		int seats;
		float speed;
		int wheels;
		boolean withWindDiraction;
		String flag;
		String energySource;
		String roadType;
		double avgFuelConsumption;
		double avgMotorLifespan;
		String licence;
		flagsComboBox.setVisible(false);
		switch (choice) {
		case "Jeep":// Jeep(String model, float speed, double avgFuelConsumption, double
					// avgMotorLifespan)
			model = jtf[0].getText();
			speed = Input.parseFloat("Speed", jtf[2].getText());
			Input.parsePositive("Speed", speed);
			
			avgFuelConsumption = Input.parseDouble("Avg Fuel Consumption", jtf[5].getText());
			Input.parsePositive("Avg Fuel Consumption", avgFuelConsumption);
			
			avgMotorLifespan = Input.parseDouble("Avg Motor Lifespan", jtf[6].getText());
			Input.parsePositive("Avg Motor Lifespan", avgMotorLifespan);
			
			result = new Jeep(model, speed, avgFuelConsumption, avgMotorLifespan);
			flagsComboBox.setVisible(false);
			break;
		case "Frigate":// Frigate(String model, int seats, float speed, boolean withWindDiraction)
			model = jtf[0].getText();
			
			seats = Input.parseInt("Seats", jtf[1].getText());
			Input.parsePositive("Seats", seats);

			speed = Input.parseFloat("Speed", jtf[2].getText());
			Input.parsePositive("Speed", speed);

			withWindDiraction = Input.parseBoolean(jtf[3].getText());
			result = new Frigate(model, seats, speed, withWindDiraction);
			break;
		case "SpyDrone":// SpyDrone(String energySource)
			energySource = jtf[4].getText();
			result = new SpyDrone(energySource);
			break;
		case "PlayDrone":// PlayDrone()
			result = new PlayDrone();
			break;
		case "AmphibiousVehicle":// AmphibiousVehicle(String model, int seats, float speed,int wheels,boolean
									// withWindDiraction,String flag,double avgFuelConsumption,double
									// avgMotorLifespan) {
			model = jtf[0].getText();
			
			seats = Input.parseInt("Seats", jtf[1].getText());
			Input.parsePositive("Seats", seats);

			speed = Input.parseFloat("Speed", jtf[2].getText());
			Input.parsePositive("Speed", speed);

			wheels = Input.parseInt("Wheels", jtf[3].getText());
			Input.parsePositive("Wheels", wheels);

			avgFuelConsumption = Input.parseDouble("Avg Fuel Consumption", jtf[5].getText());
			Input.parsePositive("Avg Fuel Consumption", avgFuelConsumption);

			avgMotorLifespan = Input.parseDouble("Avg Motor Lifespan", jtf[6].getText());
			Input.parsePositive("Avg Motor Lifespan", avgMotorLifespan);

			licence = jtf[7].getText();
			if (!Commercial.checkLicenseInput(licence)) {
				throw new NumberFormatException("License must be in " + Commercial.possibleLicences.toString());
			}
			withWindDiraction = Input.parseBoolean(jtf[8].getText());
			flag = flagsComboBox.getSelectedItem().toString();
			result = new AmphibiousVehicle(model, seats, speed, wheels, withWindDiraction, flag, avgFuelConsumption, avgMotorLifespan);
			break;
		case "Bike":// Bike(String model, int seats, float speed, String roadType)
			model = jtf[0].getText();
			seats = Input.parseInt("Seats", jtf[1].getText());
			Input.parsePositive("Seats", seats);

			speed = Input.parseFloat("Speed", jtf[2].getText());
			Input.parsePositive("Speed", speed);

			roadType = jtf[4].getText();
			if (!ILandVehicle.checkRoadTypeInput(roadType)) {
				throw new NumberFormatException("License must be in " + ILandVehicle.possibleRoadType.toString());
			}
			result = new Bike(model, seats, speed, roadType);
			break;
		case "CruiseShip":// CruiseShip(String model, int seats, float speed, String flag,double
							// avgFuelConsumption, double avgMotorLifespan) {
			model = jtf[0].getText();
			seats = Input.parseInt("Seats", jtf[1].getText());
			Input.parsePositive("Seats", seats);

			speed = Input.parseFloat("Speed", jtf[2].getText());
			Input.parsePositive("Speed", speed);

			avgFuelConsumption = Input.parseDouble("Avg Fuel Consumption", jtf[4].getText());
			Input.parsePositive("Avg Fuel Consumption", avgFuelConsumption);

			avgMotorLifespan = Input.parseDouble("Avg Motor Lifespan", jtf[5].getText());
			Input.parsePositive("Avg Motor Lifespan", avgMotorLifespan);

			flag = flagsComboBox.getSelectedItem().toString();
			result = new CruiseShip(model, seats, speed, flag, avgFuelConsumption, avgMotorLifespan);
			break;
		}
		if (result != null)
			result.setImagePath(picpath);
		return result;
	}

	protected void updatefield(String choice) {

		switch (choice) {
		case "Jeep":// Jeep(String model, float speed, double avgFuelConsumption, double
					// avgMotorLifespan)
			updatetext(0, true, "Model", "");
			updatetext(1, false, "Seats", "5");
			updatetext(2, true, "Speed", "");
			updatetext(3, false, "Wheels", "4");
			updatetext(4, false, "RoadType", "Dirt");
			updatetext(5, true, "Avg Fuel Consumption", "");
			updatetext(6, true, "Avg Motor Lifespan", "");
			updatetext(7, false, "licence", "MINI");
			updatetext(8, false, "", "");
			flagsComboBox.setVisible(false);
			break;
		case "Frigate":// Frigate(String model, int seats, float speed, boolean withWindDiraction)
			updatetext(0, true, "Model", "");
			updatetext(1, true, "Seats", "");
			updatetext(2, true, "Speed", "");
			updatetext(3, true, "With Wind Direction", "");
			updatetext(5, false, "Avg Fuel Consumption", "500");
			updatetext(6, false, "Avg Motor Lifespan", "4");
			updatetext(4, false, "Flag", "Israel");
			updatetext(7, false, "", "");
			updatetext(8, false, "", "");
			flagsComboBox.setVisible(false);

			break;
		case "SpyDrone":// SpyDrone(String energySource)
			updatetext(0, false, "Model", "Classified");
			updatetext(1, false, "Seats", "1");
			updatetext(2, false, "speed", "50");
			updatetext(3, false, "vehicle Use", "Army");
			updatetext(4, true, "Energy Source", "");
			updatetext(5, false, "Energy Rating", "C");
			updatetext(6, false, "", "");
			updatetext(7, false, "", "");
			updatetext(8, false, "", "");
			flagsComboBox.setVisible(false);
			break;
		case "PlayDrone":// PlayDrone()
			updatetext(0, false, "Model", "Toy");
			updatetext(1, false, "Seats", "0");
			updatetext(2, false, "speed", "10");
			updatetext(3, false, "vehicle Use", "Civillian");
			updatetext(4, false, "Energy Source", "Manual");
			updatetext(5, false, "Energy Rating", "A");
			updatetext(6, false, "", "");
			updatetext(7, false, "", "");
			updatetext(8, false, "", "");
			flagsComboBox.setVisible(false);
			break;
		case "AmphibiousVehicle":// AmphibiousVehicle(String model, int seats, float speed,int wheels,boolean
									// withWindDiraction,String flag,double avgFuelConsumption,double
									// avgMotorLifespan) {
			updatetext(0, true, "Model", "");
			updatetext(1, true, "Seats", "");
			updatetext(2, true, "Speed", "");
			updatetext(3, true, "Wheels", "");
			updatetext(4, false, "RoadType", "constructed");
			updatetext(5, true, "Avg Fuel Consumption", "");
			updatetext(6, true, "Avg Motor Lifespan", "");
			updatetext(7, true, "licence", "");
			updatetext(8, true, "With Wind Direction", "");
			flagsComboBox.setVisible(true);
			break;
		case "Bike":// Bike(String model, int seats, float speed, String roadType)
			updatetext(0, true, "Model", "");
			updatetext(1, true, "Seats", "");
			updatetext(2, true, "speed", "");
			updatetext(3, false, "Wheels", "2");
			updatetext(4, true, "RoadType", "");
			updatetext(5, false, "Energy Source", "Manual");
			updatetext(6, false, "Energy Rating", "A");
			updatetext(7, false, "", "");
			updatetext(8, false, "", "");
			flagsComboBox.setVisible(false);
			break;
		case "CruiseShip":// CruiseShip(String model, int seats, float speed, String flag,double
							// avgFuelConsumption, double avgMotorLifespan) {
			updatetext(0, true, "Model", "");
			updatetext(1, true, "Seats", "");
			updatetext(2, true, "Speed", "");
			updatetext(3, false, "With Wind Direction", "true");
			updatetext(4, true, "Avg Fuel Consumption", "");
			updatetext(5, true, "Avg Motor Lifespan", "");
			updatetext(6, false, "licence", "Unlimited");
			updatetext(7, false, "", "");
			updatetext(8, false, "", "");
			flagsComboBox.setVisible(true);
			break;

		}
		for (int i = 0; i < 3; i++)
			updateLabel(jl[i], IMAGES_PATH + typschoices.getSelectedItem().toString() + "\\"
					+ typschoices.getSelectedItem().toString() + (i + 1) + IMAGES_SUFIX, 100, 100);
		
		
		statusLabel.setText("");
		
		///////////////////////////////////////////////////////////////
		picpath = IMAGES_PATH + typschoices.getSelectedItem().toString() + "\\"+ typschoices.getSelectedItem().toString() + 1 + IMAGES_SUFIX;
		jrb0.setSelected(true);
		///////////////////////////////////////////////////////////////

		this.pack();

	}

	protected void updateLabel(JLabel pic, String name, int width, int height) {
		ImageIcon icon = ImageOpener.scaleImg(ImageOpener.createImageIcon(name), new Dimension(width, height));
		pic.setToolTipText("A drawing of a " + name.toLowerCase());
		pic.setSize(width, height);
		if (icon != null) {
			pic.setIcon(icon);
			pic.setText(null);
		} else {
			pic.setText("Image not found");
		}
	}

	protected void updatetext(int num, boolean editable, String labelt, String textt) {
		jbl[num].setText(labelt);
		jtf[num].setText(textt);
		jtf[num].setEditable(editable);
	}

}
