//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.IAirVehicle;

public abstract class AirVehicle extends Vehicle implements IAirVehicle{
    private String vehicleUse;

    public String getVehicleUse() {
        return this.vehicleUse;
    }

    private boolean setVehicleUse(String vehicleUse) {
        if (IAirVehicle.checkVehicleUseInput(vehicleUse)) {
            this.vehicleUse = IAirVehicle.possibleVehicleUse.FixCaps(vehicleUse);
            return true;
        }
        System.out.println("\n incorrect 'use' was entered,'Civilian' use set by default.");
        this.vehicleUse = "Civilian";
        return false;
    }

    protected AirVehicle(String model, int seats, float speed, String vehicleUse) {
        super(model, seats, speed);
        setVehicleUse(vehicleUse);
    }

    public String toString() {
        return super.toString() + " " + IAirVehicle.toString(this);
    }

    public boolean equals(Object other) {
    	return super.equals(other) && IAirVehicle.equals(this, other);
    }
}