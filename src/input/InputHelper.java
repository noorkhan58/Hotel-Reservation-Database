package input;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;

public class InputHelper {

	public static String getInput(String prompt) {
		BufferedReader stdin = new BufferedReader(
				new InputStreamReader(System.in));

		System.out.print(prompt);
		System.out.flush();

		try {
			return stdin.readLine();
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}

	public static double getDoubleInput(String prompt) throws NumberFormatException {
		String input = getInput(prompt);
		return Double.parseDouble(input);

	}

	public static int getIntegerInput(String prompt) throws NumberFormatException {
		String input = getInput(prompt);
		if(input != null && !("".equals(input))) {
			return Integer.parseInt(input);	
		}else {
			return 0;
		}
	}
	
	public static boolean getBooleanInput(String prompt) {
		String input = getInput(prompt);
		if(input.toLowerCase().equals("yes")) {
			return true;
		}else if(input.toLowerCase().equals("no")) {
			return false;
		}else{
			System.out.println("Worng input");
			return false;
		}
	}
	
	public static Timestamp getTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
}
