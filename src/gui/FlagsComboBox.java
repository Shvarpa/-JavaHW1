package gui;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import interfaces.ISeaVehicle;

public class FlagsComboBox extends JComboBox<ImageText> {

	private static DefaultComboBoxModel<ImageText> populateComboBoxModel(Dimension imageSize) {
		String basePath = "Flags\\";
		DefaultComboBoxModel<ImageText> model = new DefaultComboBoxModel<ImageText>();
		for (String type : ISeaVehicle.getPossibleFlags()) {
			ImageText curr = new ImageText(basePath + type + ".png", type, imageSize);
			model.addElement(curr);
		}
		return model;
	}

	public String getSelectedString() {
		Object selected = getSelectedItem();
		return (selected != null && selected instanceof ImageText ? selected.toString() : null);
	}

	public FlagsComboBox(Dimension d) {
		super(populateComboBoxModel(d));
		setRenderer(new ImageTextRenderer());
		setSelectedIndex(0);
	}
}
