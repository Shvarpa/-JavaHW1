package gui;

import gui.form.JeepForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AddVehicle extends JDialog {
	
	private CardLayout cl = new CardLayout(10,15);
	private JPanel formPanel;
	
	private static String[] types = {"Jeep", "Frigate","SpyDrone","PlayDrone","AmphibiousVehicle","Bike","CruiseShip"};
	
	private static DefaultComboBoxModel<ImageText> populateComboBoxModel(Dimension imageSize) {
		DefaultComboBoxModel<ImageText> model = new DefaultComboBoxModel<ImageText>();
		
		//empty selection addition:		
		model.addElement(new ImageText());
		
		String basePath = "Icons\\";
		for (String type:types) {
			ImageText curr = new ImageText(basePath+type+".png",type,imageSize);
			model.addElement(curr);
		}
		return model;
	}

	public AddVehicle() {
		setTitle("Add Vehicle");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());	
		
		///select panel
		JPanel selectPanel = new JPanel();
		getContentPane().add(selectPanel, BorderLayout.NORTH);
		FlowLayout fl_selectPanel = new FlowLayout(FlowLayout.LEFT, 20, 5);
		fl_selectPanel.setAlignOnBaseline(true);
		selectPanel.setLayout(fl_selectPanel);

		JLabel selectLabel = new JLabel("Select Type:");
		selectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectPanel.add(selectLabel);

		final JComboBox<ImageText> typesComboBox = new JComboBox<ImageText>();
		typesComboBox.setPreferredSize(new Dimension(200,30));
		typesComboBox.setModel(populateComboBoxModel(new Dimension(30,30)));
		typesComboBox.setRenderer(new ImageTextRenderer());
		typesComboBox.addActionListener((event)->{
			if (typesComboBox.getSelectedItem().toString().equals("Jeep")){
				cl.show(formPanel, "Jeep");
			}
			else cl.show(formPanel, "Empty");
		});
		
		selectPanel.add(typesComboBox);
		///form panel
		
		formPanel = new JPanel();
		getContentPane().add(formPanel, BorderLayout.CENTER);
		
		///added form types
		formPanel.setLayout(cl);
		JPanel emptyForm=new JPanel();
		JeepForm jeepForm = new JeepForm();
		
		formPanel.add("Empty",emptyForm);
		formPanel.add("Jeep", jeepForm);
	
		
		///bottom panel
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton addButton = new JButton("Add");
		addButton.setActionCommand("Add");
		buttonPane.add(addButton);
		getRootPane().setDefaultButton(addButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener((event)->{dispose();});
		buttonPane.add(cancelButton);
		
		pack();		
	}


}