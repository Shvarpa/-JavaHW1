package gui.form;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import classes.Jeep;
import classes.Vehicle;
import gui.ImageText;
import gui.ImageTextRenderer;

public class JeepForm extends Form {
	private static String modelText = "model:";
	private static String speedText = "speed:";
	private static String avgFuelConsumptionText = "avgFuelConsumption:";
	private static String avgMotorLifespanText = "avgMotorLifespan:";
	private static String imagesComboBox = "imagesComboBox";
	
	
			
			
	public JeepForm() {
		super(Arrays.asList(modelText,speedText,avgFuelConsumptionText,avgMotorLifespanText));
		addComponent("imagesComboBox", createImagesComboBox(new Dimension(100, 75)));
	}

	@Override
	Vehicle createVehicle() throws NumberFormatException,NullPointerException{
		String model = getInput(modelText);
		float speed = Float.parseFloat(getInput(speedText));
		double avgFuelConsumption = Double.parseDouble(getInput(avgFuelConsumptionText));
		double avgMotorLifespan = Double.parseDouble(getInput(avgMotorLifespanText));
		Jeep result = new Jeep(model, speed, avgFuelConsumption, avgMotorLifespan);
		result.setImagePath(((JComboBox<ImageText>)getComponent(imagesComboBox)).getSelectedItem().toString());
		return result;
	}
	
	public static JComboBox<ImageText> createImagesComboBox(Dimension preferredSize) {
		List<String> imagePaths = Arrays.asList("Jeep1.jpg","Jeep2.jpg","Jeep3.jpg");
		String basePath = "VehicleTypes\\Jeep\\";
				
		DefaultComboBoxModel<ImageText> model= new DefaultComboBoxModel<ImageText>();
		for(String imagePath: imagePaths) {
			imagePath=basePath + imagePath;
			model.addElement(new ImageText(imagePath, imagePath, preferredSize));
		}
		
		JComboBox<ImageText> combo = new JComboBox<ImageText>(model);
		
		ImageTextRenderer renderer= new ImageTextRenderer();
		renderer.setTextVisable(false);
		combo.setRenderer(renderer);
		
		return combo;
	}
}
