package base;

import java.util.Arrays;

public interface NonMotorized {
	static final String[] possibleEnergyRating= {"a","b","c"};
	
	static String toString(NonMotorized self) {return "Energy rating:"+self.getEnergyRating()+".";}
	static boolean equals(NonMotorized self,NonMotorized other) {return self.getEnergyRating().equals(other.getEnergyRating());}
	
	static boolean checkEnergyRatingInput(String energyRating) {
		return Arrays.asList(NonMotorized.possibleEnergyRating).contains(energyRating);
	}
	
	public String getEnergyRating();
}
