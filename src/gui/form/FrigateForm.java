//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui.form;

import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import classes.Frigate;
import classes.Vehicle;
import gui.ComboBoxesCreator;

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

	@Override
	public Vehicle createVehicle() throws NumberFormatException,NullPointerException{
		String model = getInput(modelText);
		int seats = Integer.parseInt(getInput(seatsText));
		float speed = Float.parseFloat(getInput(speedText));
		boolean withWindDiraction = ((JRadioButton)getComponent(withWindDiractionText)).isSelected();
		Frigate result = new Frigate(model, seats , speed, withWindDiraction);
		
		JComponent comboImage = getComponent(imagesComboBoxText);
		if (comboImage instanceof JComboBox<?>) {
			result.setImagePath(((JComboBox<?>)comboImage).getSelectedItem().toString());
		}
		return result;
	}


}
