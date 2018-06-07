//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import classes.AmphibiousVehicle;
import classes.Bike;
import classes.CruiseShip;
import classes.ElectricBike;
import classes.Frigate;
import classes.HybridPlane;
import classes.Input;
import classes.Jeep;
import classes.PlayDrone;
import classes.SpyDrone;
import classes.Vehicle;
import interfaces.Commercial;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.IVehicle;
import interfaces.NonMotorized;
import javafx.util.Pair;

public class AddVehicleForm extends JPanel {
	private static final long serialVersionUID = 1L;
	///the addVehicleForm contains all the possible vehicle inputs and their GUI representation, and selects to show the required fields according to a vehicle type specified at the vehicle type comboBox.
	///the form attempts to create the require vehicle type with the vehicle inputs given, and if unsuccessful throws a numberFormateException detailing the input failure.
	
	private Pair<JLabel, JTextField> model = new Pair<JLabel, JTextField>(new JLabel("model"), new JTextField());
	private Pair<JLabel, JTextField> seats = new Pair<JLabel, JTextField>(new JLabel("seats"), new JTextField());
	private Pair<JLabel, JTextField> speed = new Pair<JLabel, JTextField>(new JLabel("speed"), new JTextField());
	private Pair<JLabel, JTextField> wheels = new Pair<JLabel, JTextField>(new JLabel("wheels"), new JTextField());

	private Pair<JLabel, JTextField> avgMotorLifespan = new Pair<JLabel, JTextField>(new JLabel("avg motor lifespan"), new JTextField());
	private Pair<JLabel, JTextField> avgFuelConsumption = new Pair<JLabel, JTextField>(new JLabel("avg fuel consumption"), new JTextField());
	private Pair<JLabel, JTextField> energySource = new Pair<JLabel, JTextField>(new JLabel("energy source"), new JTextField());

	private Pair<JLabel, JRadioButton> withWindDiraction = new Pair<JLabel, JRadioButton>(new JLabel("with wind diraction?"),
			new JRadioButton());

	private Pair<JLabel, JComboBox<String>> vehicleUse = new Pair<JLabel, JComboBox<String>>(new JLabel("vehicle use"),
			new JComboBox<String>(IAirVehicle.possibleVehicleUse.toStringArray()));
	private Pair<JLabel, JComboBox<String>> roadType = new Pair<JLabel, JComboBox<String>>(new JLabel("road type"),
			new JComboBox<String>(ILandVehicle.possibleRoadType.toStringArray()));
	private Pair<JLabel, JComboBox<String>> licence = new Pair<JLabel, JComboBox<String>>(new JLabel("licence"),
			new JComboBox<String>(Commercial.possibleLicences.toStringArray()));
	private Pair<JLabel, JComboBox<String>> energyRating = new Pair<JLabel, JComboBox<String>>(new JLabel("energy rating"),
			new JComboBox<String>(NonMotorized.possibleEnergyRating.toStringArray()));
	
	private Pair<JLabel, JComboBox<ImageText>> flag = new Pair<JLabel, JComboBox<ImageText>>(new JLabel("flag"),
			Utilities.createFlagsComboBox(new Dimension(70, 50)));

	private List<Pair<JLabel,?>> shown = new ArrayList<Pair<JLabel,?>>();
	private String vehicleType;
	private int currentRow = 0;
	
	public AddVehicleForm(String vehicleType) {
		setLayout(new GridBagLayout());
		show(vehicleType);
	}
	
	private void clear(Pair<JLabel,?> p) {
		this.remove(p.getKey());
		Object v = p.getValue();
		if (v instanceof JComponent) this.remove((JComponent) v);
	}
	
	private void clear() {
		for (Pair<JLabel,?> p : shown) {
			Object val = p.getValue();
			if (val instanceof JComponent) ((JComponent)val).setEnabled(true);
			if(val instanceof JTextField) {((JTextField) val).setText("");}
			clear(p);
		}
		this.currentRow = 0;
		shown.clear();
	}
	
	private void show(Pair<JLabel, ?> p) {
		Object v = p.getValue();
		if (!(v instanceof JComponent)) return;
		JComponent key = p.getKey();
		JComponent val = (JComponent)v;
		
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridy=this.currentRow;
		gbc.gridx=0;
		gbc.ipadx=5;
		gbc.ipady=5;
		gbc.weightx=0;
		gbc.weighty=0;
		gbc.anchor=GridBagConstraints.EAST;
		this.add(key,gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridy=this.currentRow;
		gbc.gridx=1;
		gbc.ipadx=5;
		gbc.ipady=5;
		gbc.weightx=1;
		gbc.weighty=0;
		gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(val,gbc);
		
		shown.add(p);
		this.currentRow++;
	}

	private void show(List<Pair<JLabel, ?>> list) {
		for(Pair<JLabel, ?> p:list) {
			show(p);
		}
	}
	

	public void show(String type) {
		this.vehicleType = type;
		clear();
		switch (this.vehicleType) {
			case "Jeep":
				seats.getValue().setText(Integer.toString(Jeep.defaultSeats));
				seats.getValue().setEnabled(false);
				wheels.getValue().setText(Integer.toString(Jeep.defaultWheels));
				wheels.getValue().setEnabled(false);
				roadType.getValue().setSelectedItem(Jeep.defaultRoadType);
				roadType.getValue().setEnabled(false);
				licence.getValue().setSelectedItem(Jeep.defaultLicence);
				licence.getValue().setEnabled(false);
				show(Arrays.asList(model,seats,speed,wheels,roadType,avgFuelConsumption,avgMotorLifespan,licence));
				break;
			case "Frigate":
				avgFuelConsumption.getValue().setText(Double.toString(Frigate.defaultAvgFuelConsumption));
				avgFuelConsumption.getValue().setEnabled(false);
				avgMotorLifespan.getValue().setText(Double.toString(Frigate.defaultAvgMotorLifespan));
				avgMotorLifespan.getValue().setEnabled(false);
				flag.getValue().setSelectedItem(Frigate.defaultFlag);
				flag.getValue().setEnabled(false);
				show(Arrays.asList(model,seats,speed,withWindDiraction,avgFuelConsumption,avgMotorLifespan,flag));	
				break;
			case "SpyDrone":
				model.getValue().setText(SpyDrone.defaultModel);
				model.getValue().setEnabled(false);
				seats.getValue().setText(Integer.toString(SpyDrone.defaultSeats));
				seats.getValue().setEnabled(false);
				speed.getValue().setText(Float.toString(SpyDrone.defaultSpeed));
				speed.getValue().setEnabled(false);
				vehicleUse.getValue().setSelectedItem(SpyDrone.defaultVehicleUse);
				vehicleUse.getValue().setEnabled(false);
				energyRating.getValue().setSelectedItem(SpyDrone.defaultEnergyRating);
				energyRating.getValue().setEnabled(false);
				show(Arrays.asList(model,seats,speed,vehicleUse,energySource,energyRating));	
				break;
			case "PlayDrone":
				model.getValue().setText(PlayDrone.defaultModel);
				model.getValue().setEnabled(false);
				seats.getValue().setText(Integer.toString(PlayDrone.defaultSeats));
				seats.getValue().setEnabled(false);
				speed.getValue().setText(Float.toString(PlayDrone.defaultSpeed));
				speed.getValue().setEnabled(false);
				vehicleUse.getValue().setSelectedItem(PlayDrone.defaultVehicleUse);
				vehicleUse.getValue().setEnabled(false);
				energySource.getValue().setText(PlayDrone.defaultEnergySource);
				energySource.getValue().setEnabled(false);
				energyRating.getValue().setSelectedItem(PlayDrone.defaultEnergyRating);
				energyRating.getValue().setEnabled(false);
				show(Arrays.asList(model,seats,speed,vehicleUse,energySource,energyRating));	
				break;
			case "AmphibiousVehicle":
				roadType.getValue().setSelectedItem(AmphibiousVehicle.defaultRoadType);
				roadType.getValue().setEnabled(false);
				show(Arrays.asList(model,seats,speed,wheels,roadType,avgFuelConsumption,avgMotorLifespan,withWindDiraction,flag));
				break;
			case "Bike":
				wheels.getValue().setText(Integer.toString(Bike.defaultWheels));
				wheels.getValue().setEnabled(false);
				energyRating.getValue().setSelectedItem(Bike.defaultEnergyRating);
				energyRating.getValue().setEnabled(false);
				energySource.getValue().setText(Bike.defaultEnergySource);
				energySource.getValue().setEnabled(false);
				show(Arrays.asList(model,seats,speed,wheels,roadType,energySource,energyRating));
				break;
			case "CruiseShip":
				licence.getValue().setSelectedItem(CruiseShip.defaultLicence);
				licence.getValue().setEnabled(false);
				withWindDiraction.getValue().setSelected(CruiseShip.defaultWithWindDiraction);
				withWindDiraction.getValue().setEnabled(false);
				show(Arrays.asList(model,seats,speed,withWindDiraction,avgFuelConsumption,avgMotorLifespan,licence,flag));
				break;
			case "HybridPlane":
				vehicleUse.getValue().setSelectedItem(HybridPlane.defaultVehicleUse);
				vehicleUse.getValue().setEnabled(false);
				roadType.getValue().setSelectedItem(HybridPlane.defaultRoadType);
				roadType.getValue().setEnabled(false);
				show(Arrays.asList(model,seats,speed,wheels,withWindDiraction,avgFuelConsumption,avgMotorLifespan,vehicleUse,roadType,flag));
				break;
			case "ElectricBike":
				wheels.getValue().setText(Integer.toString(ElectricBike.defaultWheels));
				wheels.getValue().setEnabled(false);
				avgFuelConsumption.getValue().setText(Double.toString(ElectricBike.defaultAvgFuelConsumption));
				avgFuelConsumption.getValue().setEnabled(false);
				show(Arrays.asList(model,seats,speed,wheels,avgFuelConsumption,avgMotorLifespan,roadType));
				break;
		}
		revalidate();
	}
	
	public IVehicle createVehicle() throws NumberFormatException{
		switch (this.vehicleType) {
		case "Jeep":
			return new Jeep(getString(model), getFloat(speed), getDouble(avgFuelConsumption), getDouble(avgMotorLifespan));
		case "Frigate":
			return new Frigate(getString(model), getInt(seats), getFloat(speed), getBoolean(withWindDiraction));
		case "SpyDrone":
			return new SpyDrone(getString(energySource));
		case "PlayDrone":
			return new PlayDrone();
		case "AmphibiousVehicle":
			return new AmphibiousVehicle(getString(model), getInt(seats), getFloat(speed), getInt(wheels), getBoolean(withWindDiraction), getString(flag), getDouble(avgFuelConsumption), getDouble(avgMotorLifespan));
		case "Bike":
			return new Bike(getString(model), getInt(seats), getFloat(speed), getString(roadType));
		case "CruiseShip":
			return new CruiseShip(getString(model), getInt(seats), getFloat(speed), getString(flag), getDouble(avgFuelConsumption), getDouble(avgMotorLifespan));
		case "HybridPlane":
			return new HybridPlane(getString(model), getInt(seats), getFloat(speed), getInt(wheels), getBoolean(withWindDiraction), getString(flag), getDouble(avgFuelConsumption), getDouble(avgMotorLifespan));
		case "ElectricBike":
			return new ElectricBike(getString(model), getInt(seats), getFloat(speed), getString(roadType), getDouble(avgMotorLifespan));
	}
		return null;
}
	
	private Integer getInt(Pair<JLabel, ?> p) throws NumberFormatException {
		Object val = p.getValue();
		if(!(val instanceof JComponent)) return null;
		String target = p.getKey().getText().toString();
		if (val instanceof JTextField) return (int) Input.parsePositive(target, Input.parseInt(target, ((JTextField) val).getText().toString()));
		return null;
	}
	
	private Float getFloat(Pair<JLabel, ?> p) throws NumberFormatException {
		Object val = p.getValue();
		if(!(val instanceof JComponent)) return null;
		String target = p.getKey().getText().toString();
		if (val instanceof JTextField) return (float) Input.parsePositive(target, Input.parseFloat(target, ((JTextField) val).getText().toString()));
		return null;
	}
	
	private Double getDouble(Pair<JLabel, ?> p) throws NumberFormatException {
		Object val = p.getValue();
		if(!(val instanceof JComponent)) return null;
		String target = p.getKey().getText().toString();
		if (val instanceof JTextField) return (double) Input.parsePositive(target, Input.parseDouble(target, ((JTextField) val).getText()));
		else return null;
	}
	
	private String getString(Pair<JLabel, ?> p) {
		Object val = p.getValue();
		if(!(val instanceof JComponent)) return null;
		if (val instanceof JTextField)	return ((JTextField)val).getText().toString();
		else if (val instanceof JComboBox<?>) return ((JComboBox<?>)val).getSelectedItem().toString();
		else return null;
	}
	
	private Boolean getBoolean(Pair<JLabel, ?> p) {
		Object val = p.getValue();
		if(!(val instanceof JComponent)) return null;
		if (val instanceof JRadioButton) return ((JRadioButton)val).isSelected();
		else return null;
	}
	
}
