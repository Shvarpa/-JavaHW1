package base;

import java.util.Scanner;

public interface Inputable {
	static Scanner in=new Scanner(System.in);
	static String input(String msg) {
		System.out.println(msg);
		return in.next();
	}
	
	static Integer inputIntger(String msg) throws NumberFormatException{
		return Integer.parseInt(input(msg+" (int)"));
	}
	
	static Float inputFloat(String msg) throws NumberFormatException {
		return Float.parseFloat(input(msg+" (float)"));
	}
	
	static Double inputDouble(String msg) throws NumberFormatException{
		return Double.parseDouble(input(msg+" (double)"));
	}
	
	static Boolean inputBoolean(String msg) throws NumberFormatException{
		return Boolean.parseBoolean(input(msg+" (boolean)"));
	}
}
