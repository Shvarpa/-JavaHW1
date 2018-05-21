//Pavel Shvarchov - 319270583, Mordy Dabah - 203507017

package classes;

import java.util.Scanner;

public class Input {
	static Scanner in = new Scanner(System.in);

	static String input(String msg) {
		System.out.println(msg);
		return in.next();
	}

	public static Integer inputInteger(String msg) throws NumberFormatException {
		return Integer.parseInt(input(msg + " (int)"));
	}

	public static Float inputFloat(String msg) throws NumberFormatException {
		return Float.parseFloat(input(msg + " (float)"));
	}

	public static Double inputDouble(String msg) throws NumberFormatException {
		return Double.parseDouble(input(msg + " (double)"));
	}

	public static Boolean inputBoolean(String msg) {
		String inputStr = input(msg + " (boolean)");
		return Boolean.parseBoolean(inputStr) || "yes".equalsIgnoreCase(inputStr) || "1".equalsIgnoreCase(inputStr)|| "on".equalsIgnoreCase(inputStr) || "y".equalsIgnoreCase(inputStr);
	}

	public static int parseInt(String target, String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(target + " must be Integer");
		}
	}

	public static double parseDouble(String target, String input) {
		try {
			return Double.parseDouble(input);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(target + " must be Double");
		}
	}

	public static float parseFloat(String target, String input) {
		try {
			return Float.parseFloat(input);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(target + " must be Float");
		}
	}

	public static boolean parseBoolean(String target, String input) {
		if ("true".equalsIgnoreCase(input) || "yes".equalsIgnoreCase(input) || "1".equalsIgnoreCase(input)
				|| "on".equalsIgnoreCase(input) || "y".equalsIgnoreCase(input)) {
			return true;
		} else if ("false".equalsIgnoreCase(input) || "no".equalsIgnoreCase(input) || "0".equalsIgnoreCase(input)
				|| "off".equalsIgnoreCase(input) || "n".equalsIgnoreCase(input)) {
			return false;
		} else {
			throw new NumberFormatException(target + " must be Boolean (y/n)");
		}
	}

	public static void parsePositive(String target, double input) {
		if (input < 0)
			throw new NumberFormatException(target + " must be positive");
	}

}
