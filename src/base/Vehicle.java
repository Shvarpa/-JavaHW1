//Pavel Shvarchov - 319270583
//Mordy Dabah - 203507017

package base;

public abstract class Vehicle {
    private double totalDistance;
    private String model;
    private int seats;
    private float speed;


    protected Vehicle(String model, int seats, float speed) {
        setModel(model);
        setTotalDistance(0);
        setSeats(seats);
        setSpeed(speed);
    }

    private String getModel() {
        return this.model;
    }

    private void setModel(String model) {
        this.model = model;
    }

    private double getTotalDistance() {
        return this.totalDistance;
    }

    private void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    private int getSeats() {
        return this.seats;
    }

    private void setSeats(int seats) {
        if (seats < 0) {
            System.out.println("\n incorrect number of seats , 0 was set to default");
            seats = 0;
        }
        this.seats = seats;
    }

    private float getSpeed() {
        return this.speed;
    }

    private void setSpeed(float speed) {
        if (speed < 0) {
            System.out.println("\n incorrect maximum speed , 0 was set to default");
            speed = 0;
        }
        this.speed = speed;
    }

    public void resetTotalDistance() {
        this.setTotalDistance(0);
    }

    protected boolean moveDistance(double distance) {
        if (distance > 0) {
            totalDistance += distance;
            return true;
        } else return false;
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
