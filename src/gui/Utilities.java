//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SwingWorker;

import interfaces.ISeaVehicle;

public class Utilities {
	
	public static List<String> vehicleTypes = Arrays.asList("Jeep", "Frigate", "SpyDrone", "PlayDrone", "AmphibiousVehicle", "Bike", "CruiseShip",
			"HybridPlane", "ElectricBike");
	
	/// Vehicles
	private static DefaultComboBoxModel<ImageText> populateVehicles(Dimension imageSize) {
		DefaultComboBoxModel<ImageText> model = new DefaultComboBoxModel<ImageText>();

		// empty selection addition:
		model.addElement(new ImageText());
		String basePath = "Icons\\";

		for (String type : vehicleTypes) {
			ImageText curr = new ImageText(basePath + type + ".png", type, imageSize);
			model.addElement(curr);
		}
		return model;
	}

	static public JComboBox<ImageText> createVehiclesComboBox(Dimension imageSize) {
		JComboBox<ImageText> combo = new JComboBox<ImageText>(populateVehicles(imageSize));
		combo.setRenderer(new ImageTextRenderer());
		return combo;
	}

	/// Flags
	private static DefaultComboBoxModel<ImageText> populateFlags(Dimension imageSize) {
		String basePath = "Flags\\";
		DefaultComboBoxModel<ImageText> model = new DefaultComboBoxModel<ImageText>();
		for (String type : ISeaVehicle.getPossibleFlags()) {
			ImageText curr = new ImageText(basePath + type + ".png", type, imageSize);
			model.addElement(curr);
		}
		return model;
	}

	static public JComboBox<ImageText> createFlagsComboBox(Dimension d) {
		JComboBox<ImageText> combo = new JComboBox<ImageText>(populateFlags(d));
		combo.setRenderer(new ImageTextRenderer());
		combo.setSelectedIndex(0);
		return combo;
	}

	private static DefaultComboBoxModel<ImageText> populateTypes(String className, String fileType, int imageCount,
			Dimension d) {

		// this method creates an image combo box that has the images who's names are
		// represented by the strings
		// "<className>1.<fileType>" till "<className><imageCount>.<fileType>"
		// located in the "src\gui\images\VehicleTypes\<className>" folder

		String basePath = "VehicleTypes\\" + className + "\\";
		String imagePath;
		DefaultComboBoxModel<ImageText> model = new DefaultComboBoxModel<ImageText>();
		for (int i = 1; i <= imageCount; i++) {
			imagePath = basePath + className + i + "." + fileType;
			model.addElement(new ImageText(imagePath, imagePath, d));
		}

		return model;
	}

	public static JComboBox<ImageText> createTypesComboBox(String className, String fileType, int imageCount,
			Dimension d) {
		JComboBox<ImageText> combo = new JComboBox<ImageText>(populateTypes(className, fileType, imageCount, d));
		ImageTextRenderer renderer = new ImageTextRenderer();
		renderer.setTextVisable(false);
		combo.setRenderer(renderer);
		return combo;
	}

//	public static JComboBox<ImageText> createJeepsComboBox(Dimension d) {
//		return createTypesComboBox("Jeep", "jpg", 3, d);
//	}
//
//	public static JComboBox<ImageText> createFrigatesComboBox(Dimension d) {
//		return createTypesComboBox("Frigate", "jpg", 3, d);
//	}
//
//	public static JComboBox<ImageText> createSpyDronesComboBox(Dimension d) {
//		return createTypesComboBox("SpyDrone", "jpg", 3, d);
//	}
//
//	public static JComboBox<ImageText> createPlayDronesComboBox(Dimension d) {
//		return createTypesComboBox("PlayDrone", "jpg", 3, d);
//	}
//
//	public static JComboBox<ImageText> createAmphibiousVehiclesComboBox(Dimension d) {
//		return createTypesComboBox("AmphibiousVehicle", "jpg", 3, d);
//	}
//
//	public static JComboBox<ImageText> createBikesComboBox(Dimension d) {
//		return createTypesComboBox("Bike", "jpg", 3, d);
//	}
//
//	public static JComboBox<ImageText> createCruiseShipsComboBox(Dimension d) {
//		return createTypesComboBox("CruiseShip", "jpg", 3, d);
//	}
//	
//	public static JComboBox<ImageText> createHybridPlaneComboBox(Dimension d) {
//		return createTypesComboBox("HybridPlane", "jpg", 3, d);
//	}
//
//	public static JComboBox<ImageText> createElectricBikeComboBox(Dimension d) {
//		return createTypesComboBox("ElectricBike", "jpg", 3, d);
//	}
	
	public static void invokeAfter(long millis, Runnable code) {
		new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				Thread.sleep(millis);
				return null;
			}

			@Override
			protected void done() {
				code.run();
			}
		}.execute();
	}

	public static void invokeInBackground(Runnable background, Runnable after) {
		new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				background.run();
				return null;
			}

			@Override
			protected void done() {
				after.run();
			}
		}.execute();
	}
}
