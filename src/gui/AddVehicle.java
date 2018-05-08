package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
	
	static private Map<String,Form> forms= new HashMap<String, Form>();
	static private void createForms(){
		forms.put("Jeep", new JeepForm());
		forms.put("Frigate", new FrigateForm());
		forms.put("SpyDrone", new SpyDroneForm());
		forms.put("PlayDrone", new PlayDroneForm());
		forms.put("AmphibiousVehicle", new AmphibiousVehicleForm());
		forms.put("Bike", new BikeForm());
		forms.put("CruiseShip", new CruiseShipForm());
	}
	static {
		createForms();
	}
	
	
	private CardLayout cl = new CardLayout(10,15);
	private JPanel formPanel;
	JButton addButton;
	
	
	public AddVehicle() {
		setTitle("Add Vehicle");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());	
		
		///select panel
		JPanel selectPanel = new JPanel();
		getContentPane().add(selectPanel, BorderLayout.NORTH);
		FlowLayout fl_selectPanel = new FlowLayout(FlowLayout.CENTER, 20, 5);
		fl_selectPanel.setAlignOnBaseline(true);
		selectPanel.setLayout(fl_selectPanel);

		JLabel selectLabel = new JLabel("Select Type:");
		selectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectPanel.add(selectLabel);

		final JComboBox<ImageText> vehiclesComboBox = ComboBoxesCreator.createVehiclesComboBox(new Dimension(30,30));
		vehiclesComboBox.setPreferredSize(new Dimension(200,30));
		vehiclesComboBox.addActionListener((event)->{
			String name =vehiclesComboBox.getSelectedItem().toString();
			if (forms.keySet().contains(name)){
				cl.show(formPanel, name);
				addButton.setEnabled(true);
			}
			else {
				cl.show(formPanel, "Empty");
				addButton.setEnabled(false);
			}

		});
		
		selectPanel.add(vehiclesComboBox);
		///form panel
		
		formPanel = new JPanel();
		getContentPane().add(formPanel, BorderLayout.CENTER);
		
		///added form types
		formPanel.setLayout(cl);
		formPanel.add("Empty",new JPanel());
		for(String key: forms.keySet()) {
			formPanel.add(key,forms.get(key));
		}
	
		
		///bottom panel
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		addButton = new JButton("Add");
		addButton.setEnabled(false);
		addButton.addActionListener((event)->{
			String selectedType = vehiclesComboBox.getSelectedItem().toString();
			if (!forms.containsKey(selectedType)) return;
			Vehicle v;
			try{
				v= forms.get(selectedType).createVehicle();
				DBConnect.getConnection().addVehicle(v);
			}
			catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		});
		buttonPane.add(addButton);
		getRootPane().setDefaultButton(addButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((event)->{dispose();});
		buttonPane.add(cancelButton);
		
		pack();		
	}


}