package base;

public interface Commercial {
	static final String[] possibleLicenses= {"MINI","LIMIT","UNLIMIT"};
	
	static boolean checkLicenseInput(String license) {
		for(int i=0;i<possibleLicenses.length;i++) {
			if (license.equals(possibleLicenses[i])){
				return true;
			}
		}
		return false;
	}
	
	public String getLicenses();
}
