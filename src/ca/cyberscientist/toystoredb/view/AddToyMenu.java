package ca.cyberscientist.toystoredb.view;

import java.util.Scanner;

/**
 * 
 * @author koddy
 *
 */
public class AddToyMenu extends View{
	Scanner input = new Scanner(System.in);
	
	private String promptAddSerialNumber() {
		String serialNumber = "";
		do {
			System.out.println("Enter a Serial Number: ");
			serialNumber = input.nextLine();
			
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
