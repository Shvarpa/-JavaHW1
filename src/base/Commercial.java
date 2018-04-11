package base;

import java.util.Arrays;

public interface Commercial {
	static final String[] possibleLicences= {"MINI","LIMIT","UNLIMIT"};
	
	static String toString(Commercial self) {return "has "+self.getLicence()+" licence.";}
	static boolean equals(Commercial self,Commercial other) {return self.getLicence().equals(other.getLicence());}
	
	
	static boolean checkLicenseInput(String Licence) {
		return Arrays.asList(Commercial.possibleLicences).contains(Licence);
	}
	
	public String getLicence();
}
