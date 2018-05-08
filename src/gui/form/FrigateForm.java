package gui.form;

import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import classes.Frigate;
import classes.Vehicle;
import gui.ComboBoxesCreator;
import gui.ImageText;

public class FrigateForm extends Form{

	private static String modelText = "model:";
	private static String seatsText = "seats:";
	private static String speedText = "speed:";
	private static String withWindDiractionText = "with wind diraction?:";
	private static String imagesComboBoxText = "image:";	
			
	public FrigateForm() {
		super(Arrays.asList(modelText,seatsText,speedText));
		addComponent(withWindDiractionText, new JRadioButton());
		addComponent(imagesComboBoxText, ComboBoxesCreator.createFrigatesComboBox(preferredImageSize));
	}

	@SuppressWarnings("unchecked")
	@Override
	Vehicle createVehicle() throws NumberFormatException,NullPointerException{
		String model = getInput(modelText);
		int seats = Integer.parseInt(getInput(seatsText));
		float speed = Float.parseFloat(getInput(speedText));
		boolean withWindDiraction = ((JRadioButton)getComponent(withWindDiractionText)).isSelected();
		Frigate result = new Frigate(model, seats , speed, withWindDiraction);
		result.setImagePath(((JComboBox<ImageText>)getComponent(imagesComboBoxText)).getSelectedItem().toString());
		return result;
	}


}
