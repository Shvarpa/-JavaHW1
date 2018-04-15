package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenu {
    static final StringRange possibleVehicleTypes = new StringRange(Arrays.asList("Jeep", "Frigate", "SpyDrone", "PlayDrone"));

    private List<Vehicle> vehicleDatabase;
    private List<SeaVehicle> seaVehicleDatabase;
    private List<AirVehicle> airVehicleDatabase;
    private List<LandVehicle> landVehicleDatabase;

    public MainMenu() {
        vehicleDatabase = new ArrayList<>();
        seaVehicleDatabase = new ArrayList<>();
        airVehicleDatabase = new ArrayList<>();
        landVehicleDatabase = new ArrayList<>();
    }

    public static void main(String[] args) {
        MainMenu main = new MainMenu();
        boolean retry = false;
        do {
            retry = main.selectOption();
        } while (retry);
    }

    private void printDatabase() {
        for (Vehicle v : vehicleDatabase) {
            System.out.println("index:" + vehicleDatabase.indexOf(v) + ", " + v.toString());
        }
    }

    private boolean selectOption() {
        if (!vehicleDatabase.isEmpty()) {
            printDatabase();
        }
        System.out.println(
                "\n"
                        + "Select form the following options:\n"
                        + "1)add vehicle\n"
                        + "2)buy vehicle\n"
                        + "3)take a vehicle for a test-drive\n"
                        + "4)reset all vehicle distances\n"
                        + "5)change flags\n"
                        + "6)exit\n\n");
        int option = Inputable.in.nextInt();
        switch (option) {
            case 1:
                this.addVehicle();
                break;
            case 2:
                this.buyVehicle();
                break;
            case 3:
                testDriveVehicle();
                break;
            case 4:
                resetDistances();
                break;
            case 5:
                changeFlags();
                break;
            case 6:
                return false;
        }
        return true;
    }

    private Vehicle inputVehicle() {
        String type;
        type = Inputable.input("what is the vehicle's type?");
        type = possibleVehicleTypes.FixCaps(type);
        switch (type) {
            case "Jeep":
                return Jeep.inputJeep();
            case "Frigate":
                return Frigate.inputFrigate();
            case "SpyDrone":
                return SpyDrone.inputSpyDrone();
            case "PlayDrone":
                return PlayDrone.inputPlayDrone();
            default:
                System.out.println("this type is undefined. (" + possibleVehicleTypes.toString() + ")");
                return null;
        }
    }

    private boolean isEmpty() {
        if (vehicleDatabase.isEmpty()) {
            System.out.println("no vehicles in database, returning\n");
            return true;
        }
        return false;
    }

    private Vehicle selectVehicle() {
        int index = Inputable.inputIntger("enter vehicle index:");
        if (index < 0 || index >= vehicleDatabase.size()) {
            System.out.println("the index " + index + " is out of bounds, returning\n");
            return null;
        }
        return vehicleDatabase.get(index);
    }

    private Vehicle selectOrInputVehicle() {
        if (!isEmpty()) {
            boolean byindex = Inputable.inputBoolean("would you like to select a vehicle by index?");
            if (byindex) {
                return selectVehicle();
            }
        }
        return inputVehicle();
    }

    private boolean addVehicle() {
        Vehicle nVehicle = inputVehicle();
        if (nVehicle != null) {
            this.vehicleDatabase.add(nVehicle);
            if (nVehicle instanceof SeaVehicle) {
                this.seaVehicleDatabase.add((SeaVehicle) nVehicle);
            } else if (nVehicle instanceof LandVehicle) {
                this.landVehicleDatabase.add((LandVehicle) nVehicle);
            } else if (nVehicle instanceof AirVehicle) {
                this.airVehicleDatabase.add((AirVehicle) nVehicle);
            }
            System.out.println("the vehicle: " + nVehicle.toString() + " was added succesfully, returning\n");
            return true;
        }
        return false;
    }

    private boolean removeVehicle(Vehicle nVehicle) {
        if (nVehicle != null) {
            if (vehicleDatabase.contains(nVehicle)) {
                this.vehicleDatabase.remove(nVehicle);
                if (nVehicle instanceof SeaVehicle) {
                    this.seaVehicleDatabase.remove((SeaVehicle) nVehicle);
                } else if (nVehicle instanceof LandVehicle) {
                    this.landVehicleDatabase.remove((LandVehicle) nVehicle);
                } else if (nVehicle instanceof AirVehicle) {
                    this.airVehicleDatabase.remove((AirVehicle) nVehicle);
                } else ;
                return true;
            }
        }
        System.out.println("vehicle doesnt exist, returning\n");
        return false;
    }

    private boolean buyVehicle() {
        if (!isEmpty()) {
            Vehicle currVehicle = selectOrInputVehicle();
            if (currVehicle != null) {
                if (this.removeVehicle(currVehicle)) {
                    System.out.println("the vehicle: " + currVehicle.toString() + " was bought succesfully, returning\n");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean driveVehicle(Vehicle inputedVehicle, double distance) {
        if (vehicleDatabase.contains(inputedVehicle)) {
            vehicleDatabase.get(vehicleDatabase.indexOf(inputedVehicle)).moveDistance(distance);
            return true;
        }
        return false;
    }

    private boolean testDriveVehicle() {
        if (!isEmpty()) {
            Vehicle currVehicle = selectOrInputVehicle();
            if (currVehicle == null) {
                return false;
            }
            try {
                double distance = Inputable.inputDouble("enter test drive distance:");
                if (driveVehicle(currVehicle, distance)) {
                    System.out.println("the vehicle: " + currVehicle.toString() + " was taken for a " + distance + "km test-drive succesfully, returning\n");
                    return true;
                }
                return false;
            } catch (NumberFormatException e) {
                System.out.println("bad input " + e.getLocalizedMessage() + ", returning");
                return false;
            }
        }
        return false;

    }

    private boolean resetDistances() {
        if (isEmpty()) {
            return false;
        }
        for (Vehicle v : vehicleDatabase) {
            v.resetTotalDistance();
        }
        System.out.println("all vehicle distances were reset succesfully, returning\n");
        return true;
    }

    private boolean changeFlags() {
        if (isEmpty()) {
            return false;
        }
        String flag = Inputable.input("Enter new flag name:");
        for (SeaVehicle sV : seaVehicleDatabase) {
            sV.setFlag(flag);
        }
        System.out.println("all vehicle flags were changed to " + flag + " succesfully, returning\n");
        return true;
    }

}
