package input;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;

public class InputHelper {
	/**
	 * gets the string input form user
	 * @param prompt the raw string input
	 * @return the line of input read in
	 */
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
	/**
	 * changes format to double format 
	 * @param prompt gets the string input
	 * @return returns if double
	 * @throws NumberFormatException throws if not double
	 */
	public static double getDoubleInput(String prompt) throws NumberFormatException {
		String input = getInput(prompt);
		return Double.parseDouble(input);

	}
	/**
	 * changes format in Integer format
	 * @param prompt gets the string input
	 * @return returns if Integer
	 * @throws NumberFormatException throws if not Integer
	 */
	public static int getIntegerInput(String prompt) throws NumberFormatException {
		String input = getInput(prompt);
		if(input != null && !("".equals(input))) {
			return Integer.parseInt(input);	
		}else {
			return 0;
		}
	}
	/**
	 * changes the input in boolean format
	 * @param prompt string input
	 * @return boolean or invalid statemnt
	 */
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
	/**
	 * gets the current time stamp
	 * @return current timestamp
	 */
	public static Timestamp getTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
}
