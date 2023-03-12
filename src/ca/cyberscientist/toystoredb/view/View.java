/**
 * 
 */
package ca.cyberscientist.toystoredb.view;

import java.util.Scanner;

/** This parent class ensures that every child class implements a method to print it's own menu, but provides a helper function to print the prompt and validate input.
 * @author Bryce Carson
 *
 */
public abstract class View {
	
	public Scanner keyboard = new Scanner(System.in);

    /** This function prints the prompt, accepts input, validates it, and asks for corrected input if neccessary.
     *
     * @param message What to prompt the user. This is not the display of input options; merely the prompt before input.
     * @param validInputCharacters A character array which will be used to test for valid input. These should be the same characters that are displayed to the user (if the user is shown capitalized characters, test against capitalized characters).
     * @return the expected valid input from the user.
     */
    public char getValidatedCharInput(String message, char[] validInputCharacters) {
        // Prompt the user for input.
        System.out.print(message);

        String userInput = keyboard.nextLine().toUpperCase();

        // Ask for new input if necessary, redisplaying the options.
        while(!(isValidInput(userInput.charAt(0), validInputCharacters))) {
            System.out.println("The input was invalid. You entered: " + userInput);
            System.out.print("The valid input options are: ");
        	System.out.print(validInputCharacters);
        	System.out.println();
            System.out.print(message);

            userInput = keyboard.nextLine().toUpperCase();
        }

        //keyboard.close();

        return userInput.charAt(0);
    }

    private boolean isValidInput(char input, char[] validInputCharacters) {
        // Return true if the input is any one of the valid input characters.
        for (char validCharacter : validInputCharacters) {
            if(input == validCharacter)
                return true;
        }

        // Only returned if the input was not valid.
        return false;
    }

    public int getValidatedIntInput(String message) {
        final int MINIMUM_AGE = 0;

        // Prompt the user for input.
        System.out.print(message);

        int userInput = keyboard.nextInt();
        keyboard.nextLine(); // Flush the input.

        // Ask for new input if necessary, redisplaying the options.
        while(userInput < MINIMUM_AGE) {
            System.out.println("The input was invalid. You entered: " + userInput);
            System.out.println("The minimum age is " + MINIMUM_AGE);
            System.out.print(message);

            userInput = keyboard.nextInt();
            keyboard.nextLine(); // Flush the input.
        }

        return userInput;
    }
}
