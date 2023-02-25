/**
 * 
 */
package ca.cyberscientist.toystoredb.controller;

import java.io.FileNotFoundException;

import ca.cyberscientist.toystoredb.model.Database;
import ca.cyberscientist.toystoredb.view.View;

/** The toy store class, which instantiates a model of the toy store's inventory, a view for user interaction with the toy store inventory, and throws any pre-defined or custom exceptions.  
 * @author Bryce Carson, Koddy Rae Madriaga
 *
 */
public class ToyStore {
	
	private final View STORE_MENU;
	private final Database DATABASE_HANDLER;
	
	/**
	 * TODO: check this when view is being implemented for name changes of methods
	 * Constructor for the toy store object
	 * @throws FileNotFoundException Exception thrown in case of error when file not found in instantiating db
	 **/
	public ToyStore() throws FileNotFoundException {
		STORE_MENU = new View();
		DATABASE_HANDLER = new Database();
		
		boolean exitCheck = false;
		
		do { //do while because we need to see the menu atleast once
			int mainMenuChoice = 0; //change this later to the proper main menu prompt in view
			
			switch (mainMenuChoice) {
			case 1: 
				searchAndPurchaseMenu();
				break;
			case 2:
				//menu.promptAddNewToy uncomment when implemented, this calls db.addToy
				break;
			case 3:
				//menu.promptRemoveToyFromList(); uncomment when implemented
				//db.removeToyFromList();
				break;
			case 4:
				//menu.exitMenu(); uncomment when implemented
				DATABASE_HANDLER.writeToDisk(); // this can probably be put in view but its here for now
				exitCheck = true;
				break;
			}
			
		} while (!exitCheck); //Checker to exit the loop
	}
	
	/**
	 * Method for the submenu of searching and purchasing a toy
	 * 
	 * TODO: check this when view is being implemented for name changes of methods
	 */
	private void searchAndPurchaseMenu() {
		int searchMenuChoice = 0; //switch when method for searchMenu is implemented
		
		switch(searchMenuChoice) {
		case 1: //case 1 will most likely change depending on how you want to do this (we should probably talk about it)
			//STORE_MENU.promptSerialNumber(); uncomment when implemented
			//STORE_MENU.promptPurchaseChoice(); //method for purchase menu
			//STORE_MENU.promptEnterToContinue();
			break;
		case 2:
			//STORE_MENU.promptToyName();
			//STORE_MENU.promptEnterToContinue();
			break;
		case 3:
			//STORE_MENU.promptToyType();
			//STORE_MENU.promptEnterToContinue();
			break;
		case 4:
			//STORE_MENU.promptEnterToContinue();
			break;
		}
	}
}
