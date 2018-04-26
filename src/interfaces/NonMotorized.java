//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package interfaces;

import java.util.Arrays;

import classes.StringRange;

public interface NonMotorized {
	static StringRange possibleEnergyRating=new StringRange(Arrays.asList("a","b","c"));
		
	static boolean checkEnergyRatingInput(String energyRating) {
		return NonMotorized.possibleEnergyRating.containsIgnoreCaps(energyRating);
	}
	
	static String toString(NonMotorized self) {return "Energy source:"+self.getEnergySource()+", Energy rating:"+self.getEnergyRating()+".";}
	
	static boolean equals(NonMotorized self,Object other) {
		if (other instanceof NonMotorized) {
			return self.getEnergyRating().equals(((NonMotorized)other).getEnergyRating()) && self.getEnergySource().equalsIgnoreCase(((NonMotorized)other).getEnergySource());
		}
		return false;
	}
	
	public String getEnergyRating();
	public String getEnergySource();
}
