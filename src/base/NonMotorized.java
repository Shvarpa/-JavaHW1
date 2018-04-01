package base;

import java.util.Arrays;

public interface NonMotorized {
	static final String[] possibleEnergyRating= {"a","b","c"};
	
	static boolean checkEnergyRatingInput(String energyRating) {
		return Arrays.asList(NonMotorized.possibleEnergyRating).contains(energyRating);
	}
	
	public String getEnergyRating();
}
