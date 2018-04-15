package base;

import java.util.Arrays;

public abstract class LandVehicle extends Vehicle {
    private static StringRange possibleRoadType = new StringRange(Arrays.asList("dirt", "constructed"));
    private int wheels;
    private String roadType;

    protected LandVehicle(String model, int seats, float speed, int wheels, String roadType) {
        super(model, seats, speed);
        setWheels(wheels);
        setRoadType(roadType);
    }

    private static boolean checkRoadTypeInput(String roadType) {
        return LandVehicle.possibleRoadType.containsIgnoreCaps(roadType);
    }

    private boolean setRoadType(String roadType) {
        if (LandVehicle.checkRoadTypeInput(roadType)) {
            this.roadType = LandVehicle.possibleRoadType.FixCaps(roadType);
            return true;
        }
        System.out.println("\n incorrect Road Type was entered,'constructed' use set by default.");
        this.roadType = "constructed";
        return false;
    }

    private int getWheels() {
        return this.wheels;
    }

    private void setWheels(int wheels) {
        if (wheels < 0) {
            System.out.println("\n incorrect number of wheels , 0 was set to default");
            wheels = 0;
        }
        this.wheels = wheels;
    }

    private String getRoadType() {
        return this.roadType;
    }

    public String toString() {
        return super.toString() + " has " + this.wheels + " wheels, can ride on " + this.roadType + " roads.";
    }

    public boolean equals(Object other) {
        if (other instanceof LandVehicle) {
            return super.equals(other) && this.wheels == ((LandVehicle) other).getWheels() && this.roadType.equals(((LandVehicle) other).getRoadType());
        }
        return false;
    }
}
