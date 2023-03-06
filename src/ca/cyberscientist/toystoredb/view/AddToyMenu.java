package ca.cyberscientist.toystoredb.view;

import java.util.Scanner;

/**
 * 
 * Primary subclass to handle with the logic dealing with the addToy and its menu
 * 
 * @author koddy
 *
 */
public class AddToyMenu extends View{
	Scanner input = new Scanner(System.in);
	
	/**
	 * 
	 * Method to prompt the user to add a serial number, it will check if it is in the list and validate it
	 * 
	 * @return serialNumber the serial number of the toy that wants to be added
	 */
	private String promptAddSerialNumber() {
		String serialNumber = "";
		do {
			System.out.println("Enter a Serial Number: ");
			serialNumber = input.nextLine();
			
//			this is the checker if the serial number is in the list (i still need to think and read the project a bit more)			
//			if (serialNumber is in list) {
//				throw new InvalidSerialNumberException("The Serial Number is invalid");	
//			}
//			else {
//				brains dead right now hol up, think i need more exceptions or something idk
//			}
			
		} while (serialNumber.length() != 10);
		
		return serialNumber;
	}
	
	
}
