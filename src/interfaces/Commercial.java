//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017


package interfaces;

import java.util.Arrays;

import classes.StringRange;

public interface Commercial {
	static final StringRange possibleLicences= new StringRange(Arrays.asList("MINI","LIMIT","UNLIMIT"));
	
	static String toString(Commercial self) {return "has "+self.getLicence()+" licence.";}
	static Boolean equals(Commercial self,Object other) {
		if (other instanceof Commercial) {
			return self.getLicence().equals(((Commercial)other).getLicence());
		}
		return false;
	}
	
	
	static Boolean checkLicenseInput(String Licence) {
		return Commercial.possibleLicences.containsIgnoreCaps(Licence);
	}
	
	public String getLicence();
}
