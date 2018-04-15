package base;

public abstract class Vehicle {
    private double totalDistance;
    private String model;
    private int seats;
    private float speed;


    public String getModel() {
        return this.model;
    }

    public double getTotalDistance() {
        return this.totalDistance;
    }

    public int getSeats() {
        return this.seats;
    }

    public float getSpeed() {
        return this.speed;
    }

    private void setModel(String model) {
        this.model = model;
    }

    private void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void resetTotalDistance() {
        this.setTotalDistance(0);
    }

    private void setSeats(int seats) {
        if (seats < 0) {
            System.out.println("\n incorrect number of seats , 0 was set to default");
            seats = 0;
        }
        this.seats = seats;
    }

    private void setSpeed(float speed) {
        if (speed < 0) {
            System.out.println("\n incorrect maximum speed , 0 was set to default");
            speed = 0;
        }
        this.speed = speed;
    }

    protected boolean moveDistance(double distance) {
        if (distance > 0) {
            totalDistance += distance;
            return true;
        } else return false;
    }

    protected Vehicle(String model, int seats, float speed) {


        setModel(model);
        setTotalDistance(0);
        setSeats(seats);
        setSpeed(speed);
    }

    public String toString() {
        return this.getClass().getSimpleName() + ":Model: " + this.model + ", traveled:" + this.totalDistance + " Km, Max Speed of " + this.speed + " Mph, can carry max of " + this.seats + " people.";
    }

    public boolean equals(Object other) {
        if (other instanceof Vehicle) {
            return this.model.equals(((Vehicle) other).getModel()) && this.totalDistance == ((Vehicle) other).getTotalDistance() && this.seats == ((Vehicle) other).getSeats() && this.speed == ((Vehicle) other).getSpeed();
        }
        return false;
    }
}
