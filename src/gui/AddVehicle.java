//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import classes.ColoredBorder;
import classes.IconDraw;
import classes.StatusReporter;
import classes.StatusReporter.VehicleStatus;
import interfaces.IVehicle;


public class AddVehicle extends JDialog {
	private static final long serialVersionUID = 1L;
	JComboBox<ImageText> typesChoice;
	JPanel mainPanel, selectPanel, buttonPanel , statusPanel;
	JScrollPane fieldPanel;
	
	private AddVehicleForm form;
	private AddVehicleImages imagePanel;
	private JLabel statusLabel;
	private IVehicle currVehicle;
	public AddVehicle() {
		///the addVehicle dialog lets you input a vehicle into the database.
		///the addVehicle is constructed out of a vehicle type selection(comboBox), the vehicle form input, and vehicle image input
		///and there is a status bar showing the cause of the input malfunction. 
		setTitle("Add Vehcile");
		// Create the main panel to contain the four sub panels.
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(mainPanel);

		///////////////////////////////////////////////// selectPanel/////////////
		// Create combo box with vehicles types choices.
		selectPanel = new JPanel();
		typesChoice = Utilities.createVehiclesComboBox(new Dimension(60, 60));
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
		getRootPane().setDefaultButton(addButton);
		addButton.addActionListener((event) -> {
			try {
				currVehicle = createVehicle(typesChoice.getSelectedItem().toString());
			}
			catch (NumberFormatException e) {
				System.err.println(e.getMessage());
				statusLabel.setText(e.getMessage());
				pack();
				return;
			}
			if (currVehicle==null) return;
			DBConnect.getConnection().addVehicle(currVehicle);
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
		
		_add(selectPanel);
		_addColapsable(fieldPanel);
		_add(imagePanel);
		_add(buttonPanel);
		_add(statusPanel);
		
		pack();
	}
	
	private Dimension nextPlace = new Dimension(0, 0);
	private void _add(JComponent a) {
		mainPanel.add(a, new GridBagConstraints(0, nextPlace.height++, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
	}
	private void _addColapsable(JComponent a) {
		mainPanel.add(a, new GridBagConstraints(0, nextPlace.height++, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}

	private IVehicle createVehicle(String choice) throws NumberFormatException{
		IVehicle result = form.createVehicle();
		if (result != null) {
			result = new IconDraw(result,(imagePanel.getSelectedImage()));
			Color color = imagePanel.getSelectedColor();
			result = new ColoredBorder(result, color);
			result = new StatusReporter(result, VehicleStatus.STANDBY);
		}
		return result;
	}

}
