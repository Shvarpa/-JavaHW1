package base;

import java.util.Scanner;

public interface Inputable {
	static Scanner in=new Scanner(System.in);
	static String input(String msg) {
		System.out.println(msg);
		return in.next();
	}
}
