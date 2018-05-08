package gui.form;


import javax.swing.JComboBox;
import classes.PlayDrone;
import classes.Vehicle;
import gui.ComboBoxesCreator;
import gui.ImageText;

public class PlayDroneForm extends Form {
	
	private static String imagesComboBoxText = "image:";
	
	public PlayDroneForm() {
		addComponent(imagesComboBoxText, ComboBoxesCreator.createPlayDronesComboBox(preferredImageSize));
	}
	
	@Override
	public Vehicle createVehicle() {
		PlayDrone result = new PlayDrone();
		result.setImagePath(((JComboBox<ImageText>)getComponent(imagesComboBoxText)).getSelectedItem().toString());
		return result;
	}

}
