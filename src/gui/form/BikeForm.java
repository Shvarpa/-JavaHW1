//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui.form;

import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import classes.Bike;
import classes.Input;
import classes.Vehicle;
import gui.ComboBoxesCreator;
import interfaces.ILandVehicle;

public class BikeForm extends Form{

	private static String modelText = "model:";
	private static String seatsText = "seats:";
	private static String speedText = "speed:";
	private static String roadTypeText = "road type:";
	private static String imagesComboBoxText = "image:";
			
	public BikeForm() {
		super(Arrays.asList(modelText,seatsText,speedText));
		JComboBox<String> c = new JComboBox<String>(ILandVehicle.possibleRoadType.toStringArray());
		c.setSelectedIndex(0);
		addComponent(roadTypeText,c);
		addComponent(imagesComboBoxText, ComboBoxesCreator.createBikesComboBox(preferredImageSize));
	}

	@Override
	public Vehicle createVehicle() throws NumberFormatException,NullPointerException{
		String model = getInput(modelText);
		
		int seats = Input.parseInt(seatsText,getInput(seatsText));
		Input.parsePositive(seatsText, seats);

		float speed = Input.parseFloat(speedText,getInput(speedText));
		Input.parsePositive(speedText, speed);

		String roadType = null;		
		JComponent comboRoadType = getComponent(roadTypeText);
		if (comboRoadType instanceof JComboBox<?>) {
			roadType = (((JComboBox<?>)comboRoadType).getSelectedItem().toString());
		}
		
		Bike result = new Bike(model, seats , speed, roadType);
		JComponent comboImage = getComponent(imagesComboBoxText);
		if (comboImage instanceof JComboBox<?>) {
			result.setImagePath(((JComboBox<?>)comboImage).getSelectedItem().toString());
		}
		return result;
	}

}
