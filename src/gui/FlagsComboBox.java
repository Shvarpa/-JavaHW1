package gui;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import interfaces.ISeaVehicle;

public class FlagsComboBox extends JComboBox<ImageAndText> {

	private static DefaultComboBoxModel<ImageAndText> populateComboBoxModel(Dimension imageSize) {
		String basePath = "Flags\\";
		DefaultComboBoxModel<ImageAndText> model = new DefaultComboBoxModel<ImageAndText>();
		for (String type : ISeaVehicle.getPossibleFlags()) {
			ImageAndText curr = new ImageAndText(basePath + type + ".png", type, imageSize);
			model.addElement(curr);
		}
		return model;
	}

	public FlagsComboBox(Dimension d) {
		setRenderer(new ImageTextRenderer());
		setModel(populateComboBoxModel(d));
		setSelectedIndex(0);
	}
}
