package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.Images.ImageOpener;

public class AddVehicleImages extends JPanel {
	private static final long serialVersionUID = 1L;
	///the addVehicleImages lets the user select an image corresponding to the vehicle's type or select a photo located on elsewhere on the pc.
	private String imagesPath, filePath;
	private JComboBox<ImageText> imagesCombo;
	private JRadioButton imagesRadio,fileRadio;
	private JPanel imagesPanel = new JPanel(new GridLayout(1,1));
	private Color selectedColor = null;
	
	public void show(String type) {
		if (Utilities.vehicleTypes.contains(type)) {
			if(imagesCombo != null) imagesPanel.remove(imagesCombo);
			imagesCombo = Utilities.createTypesComboBox(type, "jpg", 3, new Dimension(80, 60));
			imagesPanel.add(imagesCombo);
			imagesPanel.revalidate();
			imagesCombo.addActionListener((event)->{
				imagesRadio.setSelected(true);
				imagesPath = imagesCombo.getSelectedItem().toString();
			});
			imagesRadio.setSelected(true);
			imagesPath = imagesCombo.getSelectedItem().toString();
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
		
		show(type);
		JButton fileButton = new JButton("Browse");

		JFileChooser browser = new JFileChooser();
		browser.setCurrentDirectory(new java.io.File("."));
		browser.setDialogTitle("Choose a picture to upload");
		browser.addChoosableFileFilter(new FileNameExtensionFilter("image", "jpg","png","gif"));
		browser.setAcceptAllFileFilterUsed(false);
		
		
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (browser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File uploadedPictureFile = null;
					uploadedPictureFile = browser.getSelectedFile();
					if (uploadedPictureFile != null) // user actually chose a file
					{
						fileRadio.setSelected(true);
						filePath = uploadedPictureFile.getAbsolutePath();
						ImageIcon fileImage = ImageOpener.scaleImg(ImageOpener.createImageIcon(filePath), new Dimension(80, 60));
						fileButton.setIcon(fileImage);
						fileButton.setText("");
						revalidate();
					}
				}
			}
		});
		
		JButton openColorSelectButton = new JButton("choose color");
		openColorSelectButton.addActionListener((event)->{
			selectedColor = JColorChooser.showDialog(null, "Select vehicle color", Color.BLACK);
			if(selectedColor !=null) {
				openColorSelectButton.setForeground(selectedColor);
			}
			else
				openColorSelectButton.setForeground(Color.BLACK);
		});
		_add(imagesRadio);
		_add(imagesPanel);
		_add(fileRadio);
		_add(fileButton);
		_addBoth(openColorSelectButton);	
	}
	
	private Dimension nextPlace = new Dimension(0, 0);
	public void _add(JComponent a) {
		GridBagConstraints gbc;
		if (nextPlace.width == 0) {
			gbc = new GridBagConstraints(nextPlace.width++, nextPlace.height, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
		}
		else {
			gbc = new GridBagConstraints(nextPlace.width--, nextPlace.height++, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0);
		}
		add(a,gbc);
	}
	public void _addBoth(JComponent a) {
		GridBagConstraints gbc;
		nextPlace.width=0;
		gbc = new GridBagConstraints(nextPlace.width, nextPlace.height++, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0);
		add(a,gbc);
	}
	
	public String getSelectedImage() {
		if(imagesRadio.isSelected() || filePath==null)	return imagesPath;
		else if(fileRadio.isSelected()) return filePath;
		else return null;
	}
	public Color getSelectedColor() {
		return selectedColor;
	}
}
