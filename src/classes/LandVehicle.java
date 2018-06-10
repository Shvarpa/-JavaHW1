//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;


import interfaces.ILandVehicle;
public abstract class LandVehicle extends Vehicle implements ILandVehicle{
    private int wheels;
    private String roadType;

    protected LandVehicle(String model, int seats, float speed, int wheels, String roadType) {
        super(model, seats, speed);
        setWheels(wheels);
        setRoadType(roadType);
    }
    
    public String getRoadType() {
        return this.roadType;
    }

    private boolean setRoadType(String roadType) {
        if (ILandVehicle.checkRoadTypeInput(roadType)) {
            this.roadType = ILandVehicle.possibleRoadType.FixCaps(roadType);
            return true;
        }
        System.out.println("\n incorrect Road Type was entered,'constructed' use set by default.");
        this.roadType = "constructed";
        return false;
    }

    public int getWheels() {
        return this.wheels;
    }

    private void setWheels(int wheels) {
        if (wheels < 0) {
            System.out.println("\n incorrect number of wheels , 0 was set to default");
            wheels = 0;
        }
        this.wheels = wheels;
    }

    public String toString() {
        return super.toString() + " " +ILandVehicle.toString(this);
    }

    public boolean equals(Object other) {
    	return super.equals(other) && ILandVehicle.equals(this, other);
    }
    
    public LandVehicle (LandVehicle toCopy) {
    	super(toCopy);
    	setRoadType(toCopy.getRoadType());
    	setWheels(toCopy.getWheels());
    }
}
