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

	public String getSelectedString() {
		Object selected = getSelectedItem();
		return (selected != null && selected instanceof ImageAndText ? selected.toString() : null);
	}

	public FlagsComboBox(Dimension d) {
		super(populateComboBoxModel(d));
		setRenderer(new ImageTextRenderer());
		setSelectedIndex(0);
	}
}
