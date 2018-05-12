//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui.form;

import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import classes.CruiseShip;
import classes.Input;
import classes.Vehicle;
import gui.ComboBoxesCreator;

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
		int seats = Input.parseInt(seatsText,getInput(seatsText));
		Input.parsePositive(seatsText, seats);
		
		float speed = Input.parseFloat(speedText,getInput(speedText));
		Input.parsePositive(speedText, speed);

		String flag =null;
		JComponent comboFlag = getComponent(flagText);
		if (comboFlag instanceof JComboBox<?>) {
			flag = (((JComboBox<?>)comboFlag).getSelectedItem().toString());
		}
		
		double avgFuelConsumption = Input.parseDouble(avgFuelConsumptionText,getInput(avgFuelConsumptionText));
		Input.parsePositive(avgFuelConsumptionText, avgFuelConsumption);
		
		double avgMotorLifespan = Input.parseDouble(avgMotorLifespanText,getInput(avgMotorLifespanText));
		Input.parsePositive(avgMotorLifespanText, avgMotorLifespan);
		
		CruiseShip result = new CruiseShip(model, seats , speed, flag, avgFuelConsumption, avgMotorLifespan);
		
		JComponent comboImage = getComponent(imagesComboBoxText);
		if (comboImage instanceof JComboBox<?>) {
			result.setImagePath(((JComboBox<?>)comboImage).getSelectedItem().toString());
		}
		return result;
	}

}
