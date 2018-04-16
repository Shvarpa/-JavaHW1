//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017


package base;

import java.util.Arrays;

public interface Commercial {
	static final StringRange possibleLicences= new StringRange(Arrays.asList("MINI","LIMIT","UNLIMIT"));
	
	static String toString(Commercial self) {return "has "+self.getLicence()+" licence.";}
	static boolean equals(Commercial self,Object other) {
		if (other instanceof Commercial) {
			return self.getLicence().equals(((Commercial)other).getLicence());
		}
		return false;
	}
	
	
	static boolean checkLicenseInput(String Licence) {
		return Commercial.possibleLicences.containsIgnoreCaps(Licence);
	}
	
	public String getLicence();
}
