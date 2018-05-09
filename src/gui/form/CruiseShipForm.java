package gui.form;

import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import classes.AmphibiousVehicle;
import classes.CruiseShip;
import classes.Vehicle;
import gui.ComboBoxesCreator;
import gui.ImageText;

public class CruiseShipForm extends Form {

	private static String modelText = "model:";
	private static String seatsText = "seats:";
	private static String speedText = "speed:";
	private static String flagText = "flag:";
	private static String avgFuelConsumptionText = "avg fuel consumption:";
	private static String avgMotorLifespanText = "avg motor lifespan:";
	private static String imagesComboBoxText = "image:";	
			
	public CruiseShipForm() {
		super(Arrays.asList(modelText,seatsText,speedText));
		addComponent(flagText,ComboBoxesCreator.createFlagsComboBox(preferredImageSize));
		addComponent(avgFuelConsumptionText, new JTextField(textColumns));
		addComponent(avgMotorLifespanText, new JTextField(textColumns));
		addComponent(imagesComboBoxText, ComboBoxesCreator.createCruiseShipsComboBox(preferredImageSize));
	}

	@Override
	public Vehicle createVehicle() throws NumberFormatException,NullPointerException{
		String model = getInput(modelText);
		int seats = Integer.parseInt(getInput(seatsText));
		float speed = Float.parseFloat(getInput(speedText));
		
		String flag =null;
		JComponent comboFlag = getComponent(flagText);
		if (comboFlag instanceof JComboBox<?>) {
			flag = (((JComboBox<?>)comboFlag).getSelectedItem().toString());
		}
		
		double avgFuelConsumption = Double.parseDouble(getInput(avgFuelConsumptionText));
		double avgMotorLifespan = Double.parseDouble(getInput(avgMotorLifespanText));
		CruiseShip result = new CruiseShip(model, seats , speed, flag, avgFuelConsumption, avgMotorLifespan);
		
		JComponent comboImage = getComponent(imagesComboBoxText);
		if (comboImage instanceof JComboBox<?>) {
			result.setImagePath(((JComboBox<?>)comboImage).getSelectedItem().toString());
		}
		return result;
	}

}
