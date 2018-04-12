package base;

import java.util.Arrays;

public interface Commercial {
	static final StringRange possibleLicences= new StringRange(Arrays.asList("MINI","LIMIT","UNLIMIT"));
	
	static String toString(Commercial self) {return "has "+self.getLicence()+" licence.";}
	static boolean equals(Commercial self,Commercial other) {return self.getLicence().equals(other.getLicence());}
	
	
	static boolean checkLicenseInput(String Licence) {
		return Commercial.possibleLicences.containsIgnoreCaps(Licence);
	}
	
	public String getLicence();
}
