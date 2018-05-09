//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui.form;

import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import classes.AmphibiousVehicle;
import classes.Input;
import classes.Vehicle;
import gui.ComboBoxesCreator;

public class AmphibiousVehicleForm extends Form {

	private static String modelText = "model:";
	private static String seatsText = "seats:";
	private static String speedText = "speed:";
	private static String wheelsText = "wheels:";
	private static String withWindDiractionText = "with wind diraction?:";
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

	@Override
	public Vehicle createVehicle() throws NumberFormatException,NullPointerException{
		String model = getInput(modelText);
		int seats = Input.parseInt(seatsText,getInput(seatsText));
		float speed = Input.parseFloat(speedText,getInput(speedText));
		int wheels = Input.parseInt(wheelsText,getInput(wheelsText));
		
		Boolean withWindDiraction=null;
		JComponent radioWind = getComponent(withWindDiractionText);
		if(radioWind instanceof JRadioButton) {
			withWindDiraction = ((JRadioButton)getComponent(withWindDiractionText)).isSelected();
		}
		
		String flag = null;
		JComponent comboFlag = getComponent(flagText);
		if (comboFlag instanceof JComboBox<?>) {
			flag = (((JComboBox<?>)comboFlag).getSelectedItem().toString());
		}
		
		double avgFuelConsumption = Input.parseDouble(avgFuelConsumptionText,getInput(avgFuelConsumptionText));
		double avgMotorLifespan = Input.parseDouble(avgMotorLifespanText,getInput(avgMotorLifespanText));
		
		AmphibiousVehicle result = new AmphibiousVehicle(model, seats , speed, wheels, withWindDiraction, flag, avgFuelConsumption, avgMotorLifespan);
		
		JComponent comboImage = getComponent(imagesComboBoxText);
		if (comboImage instanceof JComboBox<?>) {
			result.setImagePath(((JComboBox<?>)comboImage).getSelectedItem().toString());
		}
		return result;
	}

}
