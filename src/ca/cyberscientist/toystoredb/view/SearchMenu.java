package ca.cyberscientist.toystoredb.view;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**This class extends the view parent class by providing a view for searching the database.
 *
 * Further extensions to this class give more functionality, such as a view for when things go wrong in the searchMenu.
 *
 */
public class SearchMenu extends View {

    public String[] userChoice;

    public SearchMenu() {
        boolean searchAgainFlag = true;
        while(searchAgainFlag) {
            System.out.println("You are in the search menu.\n");

            System.out.println("\tS) Serial number");
            System.out.println("\tN) Name");
            System.out.println("\tT) Type (animal, board game, figure, or puzzle)");
            System.out.println("\tQ) Return to main menu\n");

            char userInput = getValidatedCharInput("Enter your choice: ", {'S', 'N', 'T', 'Q'});

/*          FIXME: move this switch to the controller.
            switch(userInput) {
                case 'S': searchMenuSerialNumber(); break;
                case 'N': searchMenuName();         break;
                case 'T': searcMenuType();          break;
                case 'Q': searchAgainFlag = false;  break;
            }
*/
        }
    }

    private void searchMenuSerialNumber() throws InvalidSerialNumberException {
        final int VALID_SERIAL_NUMBER_LENGTH;

        System.out.println("Enter a serial number, or the letter Q to return to the search menu.");
        String userInput = getValidSerialNumber("Serial number: ", "Q", VALID_SERIAL_NUMBER_LENGTH);

        this.userChoice[0] = userInput;
    }

    private String getValidSerialNumber(String prompt, String alternative, serialNumberLength) throws InvalidSerialNumberException {
        Scanner keyboard = new Scanner(System.in);
        System.out.print(prompt);
        String userInput = keyboard.nextln();

        // If the user's input is incorrect we raise an exception, per the assignment instructions.
        if(userInput.toUpperCase().equals(alternative.toUpperCase())) {
            return alternative;
        } else if(userInput.length != 10) {
            throw new InvalidSerialNumberException();
        } else if(userInput.contains("[![:digit:]]")) {
            throw new InvalidSerialNumberException();
        } else {
            return userInput;
        }
    }
}