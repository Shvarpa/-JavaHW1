package gui.form;


import javax.swing.JComboBox;
import javax.swing.JComponent;

import classes.PlayDrone;
import classes.Vehicle;
import gui.ComboBoxesCreator;

public class PlayDroneForm extends Form {
	
	private static String imagesComboBoxText = "image:";
	
	public PlayDroneForm() {
		addComponent(imagesComboBoxText, ComboBoxesCreator.createPlayDronesComboBox(preferredImageSize));
	}
	
	@Override
	public Vehicle createVehicle() {
		PlayDrone result = new PlayDrone();
		
		JComponent comboImage = getComponent(imagesComboBoxText);
		if (comboImage instanceof JComboBox<?>) {
			result.setImagePath(((JComboBox<?>)comboImage).getSelectedItem().toString());
		}
		return result;
	}

}
