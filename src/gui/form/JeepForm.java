//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui.form;

import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import classes.Input;
import classes.Jeep;
import classes.Vehicle;
import gui.ComboBoxesCreator;

public class JeepForm extends Form {
	private static String modelText = "model:";
	private static String speedText = "speed:";
	private static String avgFuelConsumptionText = "avg fuel consumption:";
	private static String avgMotorLifespanText = "avg motor lifespan:";
	private static String imagesComboBoxText = "image:";

	public JeepForm() {
		super(Arrays.asList(modelText, speedText, avgFuelConsumptionText, avgMotorLifespanText));
		addComponent(imagesComboBoxText, ComboBoxesCreator.createJeepsComboBox(preferredImageSize));
	}

	@Override
	public Vehicle createVehicle() throws NumberFormatException {
		String model = getInput(modelText);
		float speed = Input.parseFloat(speedText,getInput(speedText));
		double avgFuelConsumption = Input.parseDouble(avgFuelConsumptionText,getInput(avgFuelConsumptionText));
		double avgMotorLifespan = Input.parseDouble(avgMotorLifespanText,getInput(avgMotorLifespanText));
		Jeep result = new Jeep(model, speed, avgFuelConsumption, avgMotorLifespan);

		JComponent comboImage = getComponent(imagesComboBoxText);
		if (comboImage instanceof JComboBox<?>) {
			result.setImagePath(((JComboBox<?>) comboImage).getSelectedItem().toString());
		}
		return result;

	}
}
