//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui.form;

import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import classes.SpyDrone;
import classes.Vehicle;
import gui.ComboBoxesCreator;

public class SpyDroneForm extends Form {

	private static String energySourceText = "energy source:";
	private static String imagesComboBoxText = "image:";

			
	public SpyDroneForm() {
		super(Arrays.asList(energySourceText));
		addComponent(imagesComboBoxText, ComboBoxesCreator.createSpyDronesComboBox(preferredImageSize));
	}

	@Override
	public Vehicle createVehicle() throws NumberFormatException,NullPointerException{
		String energySource = getInput(energySourceText);
		SpyDrone result = new SpyDrone(energySource);
		
		JComponent comboImage = getComponent(imagesComboBoxText);
		if (comboImage instanceof JComboBox<?>) {
			result.setImagePath(((JComboBox<?>)comboImage).getSelectedItem().toString());
		}
		return result;
	}

}
