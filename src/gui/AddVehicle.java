//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import classes.Vehicle;
import gui.form.AmphibiousVehicleForm;
import gui.form.BikeForm;
import gui.form.CruiseShipForm;
import gui.form.Form;
import gui.form.FrigateForm;
import gui.form.JeepForm;
import gui.form.PlayDroneForm;
import gui.form.SpyDroneForm;

class AddVehicle extends JDialog {

	static private Map<String, Form> forms = new HashMap<String, Form>();

	static private void createForms() {
		forms.put("Jeep", new JeepForm());
		forms.put("Frigate", new FrigateForm());
		forms.put("SpyDrone", new SpyDroneForm());
		forms.put("PlayDrone", new PlayDroneForm());
		forms.put("AmphibiousVehicle", new AmphibiousVehicleForm());
		forms.put("Bike", new BikeForm());
		forms.put("CruiseShip", new CruiseShipForm());
	}
	
//	SwingUtilities.invokeLater(()->{forms.put("Jeep", new JeepForm());});
//	SwingUtilities.invokeLater(()->{forms.put("Frigate", new FrigateForm());});
//	SwingUtilities.invokeLater(()->{forms.put("SpyDrone", new SpyDroneForm());});
//	SwingUtilities.invokeLater(()->{forms.put("PlayDrone", new PlayDroneForm());});
//	SwingUtilities.invokeLater(()->{forms.put("AmphibiousVehicle", new AmphibiousVehicleForm());});
//	SwingUtilities.invokeLater(()->{forms.put("Bike", new BikeForm());});
//	SwingUtilities.invokeLater(()->{forms.put("CruiseShip", new CruiseShipForm());});
	
	static {
		SwingUtilities.invokeLater(()->{createForms();});
	}

	private CardLayout cl = new CardLayout(5, 5);
	private JPanel formPanel;
	private JLabel statusLabel;
	JButton addButton;

	public AddVehicle() {
		setTitle("Add Vehicle");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());

		/// select panel
		JPanel selectPanel = new JPanel();
		getContentPane().add(selectPanel, BorderLayout.NORTH);
		FlowLayout fl_selectPanel = new FlowLayout(FlowLayout.CENTER, 20, 5);
		fl_selectPanel.setAlignOnBaseline(true);
		selectPanel.setLayout(fl_selectPanel);

		JLabel selectLabel = new JLabel("Select Type:");
		selectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectPanel.add(selectLabel);

		final JComboBox<ImageText> vehiclesComboBox = ComboBoxesCreator.createVehiclesComboBox(new Dimension(30, 30));
		vehiclesComboBox.setPreferredSize(new Dimension(200, 30));
		vehiclesComboBox.addActionListener((event) -> {
			
			statusLabel.setText("");
			String name = vehiclesComboBox.getSelectedItem().toString();
			if (forms.keySet().contains(name)) {
				cl.show(formPanel, name);
				addButton.setEnabled(true);
			} else {
				cl.show(formPanel, "Empty");
				addButton.setEnabled(false);
			}

		});

		selectPanel.add(vehiclesComboBox);
		/// form panel

		formPanel = new JPanel();
		getContentPane().add(formPanel, BorderLayout.CENTER);

		/// added form types
		formPanel.setLayout(cl);
		formPanel.add("Empty", new JPanel());
		SwingUtilities.invokeLater(() -> {
			for (String key : forms.keySet()) {
				synchronized (forms) {
					formPanel.add(key, forms.get(key));
					pack();
				}
			}
		});

		/// bottom panel
		JPanel southPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		addButton = new JButton("Add");
		addButton.setEnabled(false);
		addButton.addActionListener((event) -> {
			String selectedType = vehiclesComboBox.getSelectedItem().toString();
			if (!forms.containsKey(selectedType))
				return;
			Vehicle v;
			try {
				v = forms.get(selectedType).createVehicle();
			} catch (NumberFormatException e) {
				// TODO: handle exception better
				statusLabel.setText("cannot create " + selectedType + ": " + e.getMessage());
				pack();
				return;
			}
			DBConnect.getConnection().addVehicle(v);
			statusLabel.setText("");
			SwingUtilities.invokeLater(() -> {
				ConfirmationDialog confirmation = new ConfirmationDialog("The vehicle was created succesfully!");
				confirmation.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				confirmation.setLocationRelativeTo(null);
				confirmation.setVisible(true);
				confirmation.addWindowListener(new WindowListener() {

					@Override
					public void windowOpened(WindowEvent arg0) {
					}

					@Override
					public void windowIconified(WindowEvent arg0) {

					}

					@Override
					public void windowDeiconified(WindowEvent arg0) {
					}

					@Override
					public void windowDeactivated(WindowEvent arg0) {
					}

					@Override
					public void windowClosing(WindowEvent arg0) {
					}

					@Override
					public void windowClosed(WindowEvent arg0) {
						dispose();
					}

					@Override
					public void windowActivated(WindowEvent arg0) {
					}
				});
			});
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((event) -> {
			dispose();
		});

		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		statusLabel = new JLabel("");
		statusLabel.setForeground(Color.RED);

		statusPanel.add(statusLabel);

		getRootPane().setDefaultButton(addButton);
		buttonPanel.add(addButton);
		buttonPanel.add(cancelButton);

		southPanel.add(statusPanel, BorderLayout.CENTER);
		southPanel.add(buttonPanel, BorderLayout.SOUTH);

		getContentPane().add(southPanel, BorderLayout.SOUTH);

		pack();
	}

}