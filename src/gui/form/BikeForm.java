package gui.form;

import java.util.Arrays;

import javax.swing.JComboBox;

import classes.Bike;
import classes.Vehicle;
import gui.ComboBoxesCreator;
import gui.ImageText;
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
		int seats = Integer.parseInt(getInput(seatsText));
		float speed = Float.parseFloat(getInput(speedText));
		String roadType = ((JComboBox<String>)getComponent(roadTypeText)).getSelectedItem().toString();
		Bike result = new Bike(model, seats , speed, roadType);
		result.setImagePath(((JComboBox<ImageText>)getComponent(imagesComboBoxText)).getSelectedItem().toString());
		return result;
	}

}
