package gui.form;

import java.util.Arrays;

import javax.swing.JComboBox;
import classes.SpyDrone;
import classes.Vehicle;
import gui.ComboBoxesCreator;
import gui.ImageText;

public class SpyDroneForm extends Form {

	private static String energySourceText = "energy source:";
	private static String imagesComboBoxText = "image:";

			
	public SpyDroneForm() {
		super(Arrays.asList(energySourceText));
		addComponent(imagesComboBoxText, ComboBoxesCreator.createSpyDronesComboBox(preferredImageSize));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vehicle createVehicle() throws NumberFormatException,NullPointerException{
		String energySource = getInput(energySourceText);
		SpyDrone result = new SpyDrone(energySource);
		result.setImagePath(((JComboBox<ImageText>)getComponent(imagesComboBoxText)).getSelectedItem().toString());
		return result;
	}

}
