package base;

import java.util.Arrays;

public abstract class AirVehicle extends Vehicle {
    private String vehicleUse;
    private static final StringRange possibleVehicleUse = new StringRange(Arrays.asList("Army", "Civilian"));

    private String getVehicleUse() {
        return this.vehicleUse;
    }

    private static boolean checkVehicleUseInput(String vehicleUse) {
        return AirVehicle.possibleVehicleUse.containsIgnoreCaps(vehicleUse);
    }

    private boolean setVehicleUse(String vehicleUse) {
        if (AirVehicle.checkVehicleUseInput(vehicleUse)) {
            this.vehicleUse = AirVehicle.possibleVehicleUse.FixCaps(vehicleUse);
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
        return super.toString() + " intended for " + this.vehicleUse + " use.";
    }

    public boolean equals(Object other) {
        if (other instanceof AirVehicle) {
            return super.equals(other) && this.vehicleUse.equals(((AirVehicle) other).getVehicleUse());
        }
        return false;
    }
}