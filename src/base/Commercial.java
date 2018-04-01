package base;

import java.util.Arrays;

public interface Commercial {
	static final String[] possibleLicense= {"MINI","LIMIT","UNLIMIT"};
	
	static boolean checkLicenseInput(String license) {
		return Arrays.asList(Commercial.possibleLicense).contains(license);
	}
	
	public String getLicenses();
}
