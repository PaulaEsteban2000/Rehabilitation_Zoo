package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
	
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

   public static int readInt() throws IOException, NumberFormatException {
        String text = "";
        int number = 0;

        while (true) {
            try {
                BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));
                text = consola.readLine();
                number = Integer.parseInt(text);
                return number;
            } catch (IOException ioe) {
                System.out.println("Error in keyboard reading. Please enter an input again.");
            } catch (NumberFormatException nfe) {
                System.out.println("The input was not an integer. Please try again.");
            }
        }

    }

    public static String readLine() throws IOException {
        String text = "";

        while (true) {
            try {
                BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));
                text = consola.readLine();
                return text;
            } catch (IOException ioe) {
                System.out.println("Error in keyboard reading. Please enter an input again.");
            }
        }

    }
}
