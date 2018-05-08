package gui.form;

import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import classes.AmphibiousVehicle;
import classes.Vehicle;
import gui.ComboBoxesCreator;
import gui.ImageText;

public class AmphibiousVehicleForm extends Form {

	private static String modelText = "model:";
	private static String seatsText = "seats:";
	private static String speedText = "speed:";
	private static String wheelsText = "wheels:";
	private static String withWindDiractionText = "withWindDiraction:";
	private static String flagText = "flag:";
	private static String avgFuelConsumptionText = "avg fuel consumption:";
	private static String avgMotorLifespanText = "avg motor lifespan:";
	private static String imagesComboBoxText = "image:";	
			
	public AmphibiousVehicleForm() {
		super(Arrays.asList(modelText,seatsText,speedText,wheelsText));
		addComponent(withWindDiractionText, new JRadioButton());
		addComponent(flagText,ComboBoxesCreator.createFlagsComboBox(preferredImageSize));
		addComponent(avgFuelConsumptionText, new JTextField(textColumns));
		addComponent(avgMotorLifespanText, new JTextField(textColumns));
		addComponent(imagesComboBoxText, ComboBoxesCreator.createAmphibiousVehiclesComboBox(preferredImageSize));
	}

	@SuppressWarnings("unchecked")
	@Override
	Vehicle createVehicle() throws NumberFormatException,NullPointerException{
		String model = getInput(modelText);
		int seats = Integer.parseInt(getInput(seatsText));
		float speed = Float.parseFloat(getInput(speedText));
		int wheels = Integer.parseInt(getInput(wheelsText));
		boolean withWindDiraction = ((JRadioButton)getComponent(withWindDiractionText)).isSelected();
		String flag = ((JComboBox<ImageText>)getComponent(flagText)).getSelectedItem().toString();
		double avgFuelConsumption = Double.parseDouble(getInput(avgFuelConsumptionText));
		double avgMotorLifespan = Double.parseDouble(getInput(avgMotorLifespanText));
		AmphibiousVehicle result = new AmphibiousVehicle(model, seats , speed, wheels, withWindDiraction, flag, avgFuelConsumption, avgMotorLifespan);
		result.setImagePath(((JComboBox<ImageText>)getComponent(imagesComboBoxText)).getSelectedItem().toString());
		return result;
	}

}
