//Pavel Shvarchov - 319270583
//Mordy Dabah - 203507017

package base;

import java.util.Arrays;

public interface NonMotorized {
	static final StringRange possibleEnergyRating=new StringRange(Arrays.asList("a","b","c"));
		
	static boolean checkEnergyRatingInput(String energyRating) {
		return NonMotorized.possibleEnergyRating.containsIgnoreCaps(energyRating);
	}
	
	static String toString(NonMotorized self) {return "Energy source:"+self.getEnergySource()+", Energy rating:"+self.getEnergyRating()+".";}
	static boolean equals(NonMotorized self,Object other) {
		if (other instanceof NonMotorized) {
			return self.getEnergyRating().equals(((NonMotorized)other).getEnergyRating());
		}
		return false;
	}
	
	public String getEnergyRating();
	public String getEnergySource();
}
