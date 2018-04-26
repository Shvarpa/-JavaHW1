//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import interfaces.ILandVehicle;
import interfaces.ISeaVehicle;

public class AmphibiousVehicle extends Vehicle implements ILandVehicle,ISeaVehicle{
	
	LandVehicle LandPart;
	SeaVehicle SeaPart;
	
	protected AmphibiousVehicle(String model, int seats, float speed,int wheels,String roadType,boolean withWindDiraction,String flag) {
		super(model, seats, speed);
//		LandPart=new LandVehicle(model,seats,speed,wheels,roadType);
//		SeaPart=new SeaVehicle(model,seats,speed,withWindDiraction,flag);
	}

	@Override
	public boolean getWithWindDiraction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getFlag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWheels() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getRoadType() {
		// TODO Auto-generated method stub
		return null;
	}

}
