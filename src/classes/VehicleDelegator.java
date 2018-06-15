package classes;

import java.util.Collection;
import gui.VehicleSelectButton;
import interfaces.Commercial;
import interfaces.IAirVehicle;
import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;
import interfaces.IVehicle;
import interfaces.Motorized;
import interfaces.NonMotorized;

public abstract class VehicleDelegator implements IVehicle,ISeaVehicle,IAirVehicle,ILandVehicle,Motorized,NonMotorized,Commercial{
	private IVehicle vehicle;
	public VehicleDelegator(IVehicle origin) {
		setVehicle(origin);
	}
	
	protected void setVehicle(IVehicle v) {
		this.vehicle = v;
	}

	public IVehicle getVehicle() {
		return vehicle;
	}

	@Override
	public VehicleSelectButton draw() {
		return vehicle.draw();
	}

	@Override
	public String getModel() {
		return vehicle.getModel();
	}

	@Override
	public Double getTotalDistance() {
		return vehicle.getTotalDistance();
	}

	@Override
	public Integer getSeats() {
		return vehicle.getSeats();
	}

	@Override
	public Float getSpeed() {
		return vehicle.getSpeed();
	}

	@Override
	public void resetTotalDistance() {
		vehicle.resetTotalDistance();
	}

	@Override
	public Boolean moveDistance(double distance) {
		return vehicle.moveDistance(distance);
	}

	@Override
	public String toString() {
		return vehicle.toString();
	}

	@Override
	public String getUniqueID() {
		return vehicle.getUniqueID();
	}

	@Override
	public Collection<Class<?>> getInterfaces() {
		return vehicle.getInterfaces();
	}

	@Override
	abstract public IVehicle clone();
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String getLicence() {
		if (vehicle instanceof Commercial)
			if (getInterfaces().contains(Commercial.class))
				return ((Commercial) vehicle).getLicence();
		return null;
	}

	@Override
	public String getEnergyRating() {
		if (vehicle instanceof NonMotorized)
			if (getInterfaces().contains(NonMotorized.class))
				return ((NonMotorized) vehicle).getEnergyRating();
		return null;
	}

	@Override
	public String getEnergySource() {
		if (vehicle instanceof NonMotorized)
			if (vehicle.getInterfaces().contains(NonMotorized.class))
				return ((NonMotorized) vehicle).getEnergySource();
		return null;
	}

	@Override
	public Double getAvgFuelConsumption() {
		if (vehicle instanceof Motorized)
			if (getInterfaces().contains(Motorized.class))
				return ((Motorized) vehicle).getAvgFuelConsumption();
		return null;
	}

	@Override
	public Double getAvgMotorLifespan() {
		if (vehicle instanceof Motorized)
			if (getInterfaces().contains(Motorized.class))
				return ((Motorized) vehicle).getAvgMotorLifespan();
		return null;
	}

	@Override
	public Integer getWheels() {
		if (vehicle instanceof ILandVehicle)
			if (vehicle.getInterfaces().contains(ILandVehicle.class))
				return ((ILandVehicle) vehicle).getWheels();
		return null;
	}

	@Override
	public String getRoadType() {
		if (vehicle instanceof ILandVehicle)
			if (getInterfaces().contains(ILandVehicle.class))
				return ((ILandVehicle) vehicle).getRoadType();
		return null;
	}

	@Override
	public String getVehicleUse() {
		if (vehicle instanceof IAirVehicle)
			if (getInterfaces().contains(IAirVehicle.class))
				return ((IAirVehicle) vehicle).getVehicleUse();
		return null;
	}

	@Override
	public Boolean getWithWindDiraction() {
		if (vehicle instanceof ISeaVehicle)
			if (getInterfaces().contains(ISeaVehicle.class))
				return ((ISeaVehicle) vehicle).getWithWindDiraction();
		return null;
	}

	@Override
	public String getFlag() {
		if (vehicle instanceof ISeaVehicle)
			if (getInterfaces().contains(ISeaVehicle.class))
				return ((ISeaVehicle) vehicle).getFlag();
		return null;
	}

	@Override
	public void setFlag(String flag) {
		if (vehicle instanceof ISeaVehicle)
			if (getInterfaces().contains(ISeaVehicle.class))
				((ISeaVehicle) vehicle).setFlag(flag);
	}
}
