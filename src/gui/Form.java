package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Form extends JDialog {
	private CardLayout cl = new CardLayout();
	private JPanel formPanel;
	
	private static String[] types = {"Jeep", "Frigate","SpyDrone","PlayDrone","AmphibiousVehicle","Bike","CruiseShip"};
	
	private static DefaultComboBoxModel<ImageAndText> populateComboBoxModel(Dimension imageSize) {
		String basePath= "Icons\\";
		DefaultComboBoxModel<ImageAndText> model = new DefaultComboBoxModel<ImageAndText>();
		for (String type:types) {
			ImageAndText curr = new ImageAndText(basePath+type+".png",type);
			curr.scaleImg(imageSize);
			model.addElement(curr);
		}
		return model;
	}


	private final JPanel contentPanel = new JPanel();

	public Form() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{212, 212, 0};
		gbl_contentPanel.rowHeights = new int[]{218, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			
			///select panel
			JPanel selectPanel = new JPanel();
			GridBagConstraints gbc_selectPanel = new GridBagConstraints();
			gbc_selectPanel.gridwidth = 2;
			gbc_selectPanel.insets = new Insets(0, 0, 5, 0);
			gbc_selectPanel.fill = GridBagConstraints.BOTH;
			gbc_selectPanel.gridx = 0;
			gbc_selectPanel.gridy = 0;
			contentPanel.add(selectPanel, gbc_selectPanel);
			selectPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
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
			GridBagConstraints gbc_formPanel = new GridBagConstraints();
			gbc_formPanel.gridheight = 6;
			gbc_formPanel.gridwidth = 2;
			gbc_formPanel.fill = GridBagConstraints.BOTH;
			gbc_formPanel.gridx = 0;
			gbc_formPanel.gridy = 1;
			contentPanel.add(formPanel, gbc_formPanel);
			
			///added form types
			formPanel.setLayout(cl);
			formPanel.add("Empty", new JPanel());
			formPanel.add("Jeep", new JeepForm());
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
				buttonPane.add(cancelButton);
			}
		}
	}


}