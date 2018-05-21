package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.Images.ImageOpener;

public class AddVehicleImages extends JPanel {

	private String selectedImagePath, filePath;
	private JComboBox<ImageText> imagesCombo;
	private JRadioButton imagesRadio,fileRadio;
	private JPanel imagesPanel = new JPanel();
	
	public void show(String type) {
		if (ComboBoxesCreator.vehicleTypes.contains(type)) {
			if(imagesCombo != null) imagesPanel.remove(imagesCombo);
			imagesCombo = ComboBoxesCreator.createTypesComboBox(type, "jpg", 3, new Dimension(80, 60));
			imagesPanel.add(imagesCombo);
			imagesPanel.revalidate();
			selectedImagePath = imagesCombo.getSelectedItem().toString();
		}
	}
	
	public AddVehicleImages(String type) {
		setLayout(new GridBagLayout());

		// Create the radio buttons.
		imagesRadio = new JRadioButton();
		fileRadio = new JRadioButton();
		ButtonGroup group = new ButtonGroup();
		group.add(imagesRadio);
		group.add(fileRadio);
		
		JButton fileButton = new JButton("Browse");

		JFileChooser browser = new JFileChooser();
		browser.setCurrentDirectory(new java.io.File("."));
		browser.setDialogTitle("Choose a picture to upload");
		browser.addChoosableFileFilter(new FileNameExtensionFilter("image", "jpg","png","gif"));
		browser.setAcceptAllFileFilterUsed(false);
		
		imagesRadio.addActionListener((event)->{
			selectedImagePath = imagesCombo.getSelectedItem().toString();
		});
		imagesRadio.setSelected(true);
		fileRadio.addActionListener((event)->{
			selectedImagePath = filePath;
		});

		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (browser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File uploadedPictureFile = null;
					uploadedPictureFile = browser.getSelectedFile();
					if (uploadedPictureFile != null) // user actually chose a file
					{
						filePath = uploadedPictureFile.getAbsolutePath();
						ImageIcon fileImage = ImageOpener.scaleImg(ImageOpener.createImageIcon(filePath), new Dimension(80, 60));
						fileButton.setIcon(fileImage);
						fileButton.setText("");
						revalidate();
					}

				}
			}
		});
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		add(imagesRadio,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(imagesPanel,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		add(fileRadio,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(fileButton,gbc);
		
		show(type);
	}
	
	
	public String getSelectedImage() {
		return selectedImagePath;
	}
}
