//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017


package interfaces;

import java.util.Scanner;

public interface Inputable {
	static Scanner in=new Scanner(System.in);
	static String input(String msg) {
		System.out.println(msg);
		return in.next();
	}
	
	public static Integer inputInteger(String msg) throws NumberFormatException{
		return Integer.parseInt(input(msg+" (int)"));
	}
	
	public static Float inputFloat(String msg) throws NumberFormatException {
		return Float.parseFloat(input(msg+" (float)"));
	}
	
	public static Double inputDouble(String msg) throws NumberFormatException{
		return Double.parseDouble(input(msg+" (double)"));
	}
	
	public static Boolean inputBoolean(String msg){
		String inputStr=input(msg+" (boolean)");
		return Boolean.parseBoolean(inputStr)||"yes".equalsIgnoreCase(inputStr)||"1".equalsIgnoreCase(inputStr)||"on".equalsIgnoreCase(inputStr)||"y".equalsIgnoreCase(inputStr);
	}
	
	public static boolean isNumeric(String input) {
		try {
			Double.parseDouble(input);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
