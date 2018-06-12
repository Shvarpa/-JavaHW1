//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.JComboBox;
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
import classes.Jeep;
import classes.PlayDrone;
import classes.SpyDrone;
import gui.FormParameter;
import gui.FormParameter.Parameter;
import interfaces.Commercial;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.IVehicle;
import interfaces.NonMotorized;

public class AddVehicleForm extends JPanel {
	private static final long serialVersionUID = 1L;
	///the addVehicleForm contains all the possible vehicle inputs and their GUI representation, and selects to show the required fields according to a vehicle type specified at the vehicle type comboBox.
	///the form attempts to create the require vehicle type with the vehicle inputs given, and if unsuccessful throws a numberFormateException detailing the input failure.
		
	private FormParameter<String> model = new FormParameter<String>(Parameter.model,String.class);
	private FormParameter<String> energySource = new FormParameter<String>(Parameter.energySource,String.class);
	private FormParameter<String> vehicleUse = new FormParameter<String>(Parameter.vehicleUse,String.class);
	private FormParameter<String> roadType = new FormParameter<String>(Parameter.roadType,String.class);
	private FormParameter<String> licence = new FormParameter<String>(Parameter.licence,String.class);
	private FormParameter<String> energyRating = new FormParameter<String>(Parameter.energyRating,String.class);
	private FormParameter<String> flag = new FormParameter<String>(Parameter.flag,String.class);


	private FormParameter<Integer> seats = new FormParameter<Integer>(Parameter.seats,Integer.class);
	private FormParameter<Integer> wheels = new FormParameter<Integer>(Parameter.wheels,Integer.class);

	private FormParameter<Float> speed = new FormParameter<Float>(Parameter.speed,Float.class);
	
	private FormParameter<Double> avgMotorLifespan = new FormParameter<Double>(Parameter.avgMotorLifespan,Double.class);
	private FormParameter<Double> avgFuelConsumption = new FormParameter<Double>(Parameter.avgFuelConsumption,Double.class);

	private FormParameter<Boolean> withWindDiraction = new FormParameter<Boolean>(Parameter.withWindDiraction,Boolean.class);

	private List<FormParameter<?>> shown = new ArrayList<FormParameter<?>>();
	private String vehicleType;
	private int currentRow = 0;
	
	public AddVehicleForm(String vehicleType) {
		setLayout(new GridBagLayout());
		show(vehicleType);
	}
	private void remove(FormParameter<?> p) {
		this.remove(p.getKey());
		this.remove(p.getVal());
	}
	private void clear() {
		for (FormParameter<?> p : shown) {
			p.restoreDefault();
			this.remove(p);
		}
		this.currentRow = 0;
		shown.clear();
	}
	private void initiate(FormParameter<?> p) {
		if(!p.initialized()) {
			switch(p.getParameter()){
			case model: 
				this.model.initialize(new JLabel("model"), new JTextField());
				break;
			case seats: 
				this.seats.initialize(new JLabel("seats"), new JTextField());
				break;
			case speed:
				this.speed.initialize(new JLabel("speed"), new JTextField());
				break;
			case wheels: 
				this.wheels.initialize(new JLabel("wheels"), new JTextField());
				break;
			case avgMotorLifespan: 
				this.avgMotorLifespan.initialize(new JLabel("avg motor lifespan"), new JTextField());
				break;
			case avgFuelConsumption: 
				this.avgFuelConsumption.initialize(new JLabel("avg fuel consumption"), new JTextField());
				break;
			case energySource: 
				this.energySource.initialize(new JLabel("energy source"), new JTextField());
				break;
			case withWindDiraction:
				this.withWindDiraction.initialize(new JLabel("with wind diraction?"),new JRadioButton());
				break;
			case vehicleUse:
				this.vehicleUse.initialize(new JLabel("vehicle use"),new JComboBox<String>(IAirVehicle.possibleVehicleUse.toStringArray()));
				break;
			case roadType:
				this.roadType.initialize(new JLabel("road type"),new JComboBox<String>(ILandVehicle.possibleRoadType.toStringArray()));
				break;
			case licence:
				this.licence.initialize(new JLabel("licence"),new JComboBox<String>(Commercial.possibleLicences.toStringArray()));
				break;
			case energyRating: 
				this.energyRating.initialize(new JLabel("energy rating"),new JComboBox<String>(NonMotorized.possibleEnergyRating.toStringArray()));
				break;
			case flag: 
				this.flag.initialize(new JLabel("flag"),Utilities.createFlagsComboBox(new Dimension(70, 50)));
				break;
			default:
				break;
			}
		}
		
	}
	private void initiate(Collection<FormParameter<?>>params) {
		for(FormParameter<?> p : params)
			initiate(p);
	}

	private void show(FormParameter<?> param) {
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridy=this.currentRow;
		gbc.gridx=0;
		gbc.ipadx=5;
		gbc.ipady=5;
		gbc.weightx=0;
		gbc.weighty=0;
		gbc.anchor=GridBagConstraints.EAST;
		this.add(param.getKey(),gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridy=this.currentRow;
		gbc.gridx=1;
		gbc.ipadx=5;
		gbc.ipady=5;
		gbc.weightx=1;
		gbc.weighty=0;
		gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		this.add(param.getVal(),gbc);
		
		shown.add(param);
		this.currentRow++;
	}

	private void show(List<FormParameter<?>> list) {
		initiate(list);
		for(FormParameter<?> fP:list) {
			show(fP);
		}
	}

	public void show(String type) {
		this.vehicleType = type;
		clear();
		switch (this.vehicleType) {
			case "Jeep":
				initiate(Arrays.asList(seats,wheels,roadType,licence));
				seats.setDefault(Integer.toString(Jeep.defaultSeats));
				wheels.setDefault(Integer.toString(Jeep.defaultWheels));
				roadType.setDefault(Jeep.defaultRoadType);
				licence.setDefault(Jeep.defaultLicence);
				show(Arrays.asList(model,seats,speed,wheels,roadType,avgFuelConsumption,avgMotorLifespan,licence));
				break;
			case "Frigate":
				initiate(Arrays.asList(avgFuelConsumption,avgMotorLifespan,flag));
				avgFuelConsumption.setDefault(Double.toString(Frigate.defaultAvgFuelConsumption));
				avgMotorLifespan.setDefault(Double.toString(Frigate.defaultAvgMotorLifespan));
				flag.setDefault(Frigate.defaultFlag);
				show(Arrays.asList(model,seats,speed,withWindDiraction,avgFuelConsumption,avgMotorLifespan,flag));	
				break;
			case "SpyDrone":
				initiate(Arrays.asList(model,seats,speed,vehicleUse,energyRating));
				model.setDefault(SpyDrone.defaultModel);
				seats.setDefault(Integer.toString(SpyDrone.defaultSeats));
				speed.setDefault(Float.toString(SpyDrone.defaultSpeed));
				vehicleUse.setDefault(SpyDrone.defaultVehicleUse);
				energyRating.setDefault(SpyDrone.defaultEnergyRating);
				show(Arrays.asList(model,seats,speed,vehicleUse,energySource,energyRating));	
				break;
			case "PlayDrone":
				initiate(Arrays.asList(model,seats,speed,vehicleUse,energySource,energyRating));
				model.setDefault(PlayDrone.defaultModel);
				seats.setDefault(Integer.toString(PlayDrone.defaultSeats));
				speed.setDefault(Float.toString(PlayDrone.defaultSpeed));
				vehicleUse.setDefault(PlayDrone.defaultVehicleUse);
				energySource.setDefault(PlayDrone.defaultEnergySource);
				energyRating.setDefault(PlayDrone.defaultEnergyRating);
				show(Arrays.asList(model,seats,speed,vehicleUse,energySource,energyRating));	
				break;
			case "AmphibiousVehicle":
				initiate(Arrays.asList(roadType));
				roadType.setDefault(AmphibiousVehicle.defaultRoadType);
				show(Arrays.asList(model,seats,speed,wheels,roadType,avgFuelConsumption,avgMotorLifespan,withWindDiraction,flag));
				break;
			case "Bike":
				initiate(Arrays.asList(wheels,energySource,energyRating));
				wheels.setDefault(Integer.toString(Bike.defaultWheels));
				energyRating.setDefault(Bike.defaultEnergyRating);
				energySource.setDefault(Bike.defaultEnergySource);
				show(Arrays.asList(model,seats,speed,wheels,roadType,energySource,energyRating));
				break;
			case "CruiseShip":
				initiate(Arrays.asList(withWindDiraction,licence));
				licence.setDefault(CruiseShip.defaultLicence);
				withWindDiraction.setDefault(CruiseShip.defaultWithWindDiraction);
				show(Arrays.asList(model,seats,speed,withWindDiraction,avgFuelConsumption,avgMotorLifespan,licence,flag));
				break;
			case "HybridPlane":
				initiate(Arrays.asList(vehicleUse,roadType));
				vehicleUse.setDefault(HybridPlane.defaultVehicleUse);
				roadType.setDefault(HybridPlane.defaultRoadType);
				show(Arrays.asList(model,seats,speed,wheels,withWindDiraction,avgFuelConsumption,avgMotorLifespan,vehicleUse,roadType,flag));
				break;
			case "ElectricBike":
				initiate(Arrays.asList(wheels,avgMotorLifespan));
				wheels.setDefault(Integer.toString(ElectricBike.defaultWheels));
				avgFuelConsumption.setDefault(Double.toString(ElectricBike.defaultAvgFuelConsumption));
				show(Arrays.asList(model,seats,speed,wheels,avgFuelConsumption,avgMotorLifespan,roadType));
				break;
		}
		revalidate();
	}
	
	public IVehicle createVehicle() throws NumberFormatException{
		switch (this.vehicleType) {
		case "Jeep":
			return new Jeep(model.get(), speed.get(), avgFuelConsumption.get(), avgMotorLifespan.get());
		case "Frigate":
			return new Frigate(model.get(), seats.get(), speed.get(), withWindDiraction.get());
		case "SpyDrone":
			return new SpyDrone(energySource.get());
		case "PlayDrone":
			return new PlayDrone();
		case "AmphibiousVehicle":
			return new AmphibiousVehicle(model.get(), seats.get(), speed.get(), wheels.get(), withWindDiraction.get(), flag.get(), avgFuelConsumption.get(), avgMotorLifespan.get());
		case "Bike":
			return new Bike(model.get(), seats.get(), speed.get(), roadType.get());
		case "CruiseShip":
			return new CruiseShip(model.get(), seats.get(), speed.get(), flag.get(), avgFuelConsumption.get(), avgMotorLifespan.get());
		case "HybridPlane":
			return new HybridPlane(model.get(), seats.get(), speed.get(), wheels.get(), withWindDiraction.get(), flag.get(), avgFuelConsumption.get(), avgMotorLifespan.get());
		case "ElectricBike":
			return new ElectricBike(model.get(), seats.get(), speed.get(), roadType.get(), avgMotorLifespan.get());
		}
		return null;
	}	
}
