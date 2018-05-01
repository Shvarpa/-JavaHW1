package gui;

import classes.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AddVehicle extends JDialog {
	
	private Database data = Database.getInstance();
	private CardLayout cl = new CardLayout();
	private JPanel formPanel;
	
	private static String[] types = {"Jeep", "Frigate","SpyDrone","PlayDrone","AmphibiousVehicle","Bike","CruiseShip"};
	
	private static DefaultComboBoxModel<ImageAndText> populateComboBoxModel(Dimension imageSize) {
		String basePath = "Icons\\";
		DefaultComboBoxModel<ImageAndText> model = new DefaultComboBoxModel<ImageAndText>();
		for (String type:types) {
			ImageAndText curr = new ImageAndText(basePath+type+".png",type);
			curr.scaleImg(imageSize);
			model.addElement(curr);
		}
		return model;
	}

	public AddVehicle() {
		setTitle("Add Vehicle");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			
			///select panel
			JPanel selectPanel = new JPanel();
			getContentPane().add(selectPanel, BorderLayout.NORTH);
			FlowLayout fl_selectPanel = new FlowLayout(FlowLayout.LEFT, 20, 5);
			fl_selectPanel.setAlignOnBaseline(true);
			selectPanel.setLayout(fl_selectPanel);
			{
				JLabel selectLabel = new JLabel("Select Type:");
				selectLabel.setHorizontalAlignment(SwingConstants.CENTER);
				selectPanel.add(selectLabel);
			}
			{
				final JComboBox<ImageAndText> typesComboBox = new JComboBox<ImageAndText>();
				typesComboBox.setPreferredSize(new Dimension(200,30));
				typesComboBox.setModel(populateComboBoxModel(new Dimension(30,30)));
				typesComboBox.setRenderer(new ImageTextRenderer());
				typesComboBox.addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (typesComboBox.getSelectedItem().toString().equals("Jeep")){
							cl.show(formPanel, "Jeep");
						}
						else cl.show(formPanel, "Empty");
					}
				});
				
				selectPanel.add(typesComboBox);
			}
		}
		
		
		///form panel
		{
			formPanel = new JPanel();
			getContentPane().add(formPanel, BorderLayout.CENTER);
			cl.setVgap(10);
			cl.setHgap(15);
			
			///added form types
			formPanel.setLayout(cl);
			JeepForm jeepForm = new JeepForm();
			formPanel.add("Jeep", jeepForm);
		}
		
		///bottom panel
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton addButton = new JButton("Add");
				addButton.setActionCommand("Add");
				buttonPane.add(addButton);
				getRootPane().setDefaultButton(addButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}


}