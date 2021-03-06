//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package interfaces;

public interface Motorized {

    static String toString(Motorized self) {
        return "Engine: " + self.getAvgFuelConsumption() + "L, lifetime of " + self.getAvgMotorLifespan() + " years.";
    }

    static Boolean equals(Motorized self, Object other) {
        if (other instanceof Motorized) {
            return self.getAvgFuelConsumption() == ((Motorized) other).getAvgFuelConsumption() && self.getAvgMotorLifespan() == ((Motorized) other).getAvgMotorLifespan();
        }
        return false;
    }

    public Double getAvgFuelConsumption();
    public Double getAvgMotorLifespan();
}
