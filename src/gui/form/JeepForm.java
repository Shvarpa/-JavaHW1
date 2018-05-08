package gui.form;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import classes.Jeep;
import classes.Vehicle;
import gui.ComboBoxesCreator;
import gui.ImageText;
import gui.ImageTextRenderer;
import javafx.scene.control.ComboBox;

public class JeepForm extends Form {
	private static String modelText = "model:";
	private static String speedText = "speed:";
	private static String avgFuelConsumptionText = "avg fuel consumption:";
	private static String avgMotorLifespanText = "avg motor lifespan:";
	private static String imagesComboBoxText = "image:";	
			
	public JeepForm() {
		super(Arrays.asList(modelText,speedText,avgFuelConsumptionText,avgMotorLifespanText));
		addComponent(imagesComboBoxText, ComboBoxesCreator.createJeepsComboBox(preferredImageSize));
	}

	@SuppressWarnings("unchecked")
	@Override
	Vehicle createVehicle() throws NumberFormatException,NullPointerException{
		String model = getInput(modelText);
		float speed = Float.parseFloat(getInput(speedText));
		double avgFuelConsumption = Double.parseDouble(getInput(avgFuelConsumptionText));
		double avgMotorLifespan = Double.parseDouble(getInput(avgMotorLifespanText));
		Jeep result = new Jeep(model, speed, avgFuelConsumption, avgMotorLifespan);
		result.setImagePath(((JComboBox<ImageText>)getComponent(imagesComboBoxText)).getSelectedItem().toString());
		return result;
	}
	

}
