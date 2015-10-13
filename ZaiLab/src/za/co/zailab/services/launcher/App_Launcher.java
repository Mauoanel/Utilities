package za.co.lawrence.services.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Launcher {

    static Integer noOfAsterics = null;
    static Integer counter = null;
	
	static {
		noOfAsterics = 0;
		counter = 1;
	}

    /**
     * @param args
     */
    public static void main(String[] args) {
		// Launch the App
        new Launcher();
		//Gets the User Input
       Launcher.getInput();
        
        // Processes the input and prints
        process(getNoOfAsterics());
    }

    /**
     * This method gets the Input from the user by use of a BufferedReader with a
     * InputStreamReader
     */
    private static void getInput() {

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        try {
            System.out.println("Please enter the number of Asterics : ");
            setNoOfAsterics(Integer.parseInt(br.readLine()));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methods prints the Asterics in Forward and Backward order
     * 
     * @param asterics
     */
    private static void process(Integer asterics) {

        // Forward
        for (int i = 1; i <= asterics; i++) {
            for (int j = asterics; j >= i; j--) {
                System.out.print(" ");
            }
            for (int m = 1; m <= i; m++) {
                System.out.print(" *");
            }
            System.out.println();
        }

        // Backward
        for (int i = 1; i <= asterics; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(" ");
            }
            for (int m = asterics; m >= i; m--) {
                System.out.print(" *");
            }
            System.out.println();
        }
    }

    public static Integer getNoOfAsterics() {
        return noOfAsterics;
    }

    public static void setNoOfAsterics(Integer noOfAsterics) {
        Launcher.noOfAsterics = noOfAsterics;
    }

}
