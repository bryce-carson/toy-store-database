/**
 *
 */
package ca.cyberscientist.toystoredb.controller;

import java.util.ArrayList;

// The model is imported in the Database class, moreover, the Database is in the
// same package and doesn't need to be imported.
import ca.cyberscientist.toystoredb.view.*;
import ca.cyberscientist.toystoredb.model.*;

/**
 * The toy store class, which instantiates a model of the toy store's inventory,
 * a view for user interaction with the toy store inventory, and throws any
 * pre-defined or custom exceptions.
 *
 * @author Bryce Carson, Koddy Rae Madriaga
 *
 */
public class ToyStore {

	private final Menu STORE_MENU;
	private final Database DATABASE_HANDLER;
	public final boolean PLACEHOLDER_CONSTRUCTOR = true;

	/**
	 * Constructor for the toystore
	 */
	public ToyStore() {
		// Instantiate a menu system for issuing prompts to the user and acquiring user
		// input.
		STORE_MENU = new Menu();

		// Instantiate the database with the default filename.
		DATABASE_HANDLER = new Database();

		boolean continueLooping = true;

		// Loop, displaying the main menu while we should continue looping and at
		// least once.
		do {
			char mainMenuChoice = STORE_MENU.promptMainMenu();

			// Call other ToyStore methods, which have their own looping logic and
			// call Menu class methods.
			switch (mainMenuChoice) {
				case 'S':
					searchAndPurchaseMenu();
					break;

				case 'A':
					addToyMenu();
					break;

				case 'R':
					removeToyMaybeMenu();
					break;

				case 'Q':
					DATABASE_HANDLER.writeToDisk(DATABASE_HANDLER.DEFAULT_FILENAME);
					continueLooping = false;
					break;
			}
		} while (continueLooping); // Semantic flag for whether or not to continue looping.

		// Exiting the program. Close the keyboard!
		STORE_MENU.keyboard.close();
	}

	/**
	 * Method for the submenu of searching and purchasing a toy
	 *
	 */
	private void searchAndPurchaseMenu() {
		char searchMethod = STORE_MENU.promptSearchMenu();

		// Initialized by one of the search methodologies; declared here because we
		// must have one.
		ArrayList<Toy> searchResultsToyList = new ArrayList<Toy>();
		SearchResultsTable searchResultsTable = null;
		searchResultsTable = new SearchResultsTable(searchResultsToyList, PLACEHOLDER_CONSTRUCTOR);
		int purchaseIndex = 0;

		// Search by serial number, name, or type.
		switch (searchMethod) {
			case 'S':
				String serialNumber = STORE_MENU.promptSerialNumber();
				searchResultsToyList = DATABASE_HANDLER.searchRecords(serialNumber,
						DATABASE_HANDLER.SEARCH_BY_SERIAL_NUMBER);
				searchResultsTable = new SearchResultsTable(searchResultsToyList);

				break;

			case 'N':
				String toyName = STORE_MENU.promptToyNameForSearching();
				searchResultsToyList = DATABASE_HANDLER.searchRecords(toyName, DATABASE_HANDLER.SEARCH_BY_TOY_NAME);
				searchResultsTable = new SearchResultsTable(searchResultsToyList);

				break;

			case 'T':
				char toyType = STORE_MENU.promptToyType();
				searchResultsToyList = DATABASE_HANDLER.searchRecords(toyType);
				searchResultsTable = new SearchResultsTable(searchResultsToyList);

				break;

			case 'Q':
				break;
		}

		// Less one because the integer returned is the user selection; to convert to an
		// index usable with the search results, it must be restored to zero-indexing.
		purchaseIndex = searchResultsTable.promptPurchaseToyOrQuit() - 1;
		if (purchaseIndex >= 0 && searchResultsToyList.get(purchaseIndex).getAvailableCount() > 0) {
			DATABASE_HANDLER.purchaseToy(searchResultsToyList.get(purchaseIndex));

			String toyName = searchResultsToyList.get(purchaseIndex).getName();
			System.out.println("\n1 " + toyName + " purchases successfully.\n");

		} else if (purchaseIndex < 0) {
			System.out.println("The input was invalid. The input must be a positive integer.");
		} else {
			System.out.println("There are no units available for purchase.");
		}
	}

	/**
	 * Method to print the add toy menu, it will later display the proper toy
	 * prompts that follow the serial numbers
	 */
	public void addToyMenu() {
		String serialNumber = STORE_MENU.promptSerialNumber();

		switch (serialNumber.charAt(0)) {
			case 0:
			case 1:
				DATABASE_HANDLER.addRecord(STORE_MENU.promptAddFigure());
				STORE_MENU.promptEnterToContinue();
				break;
			case 2:
			case 3:
				DATABASE_HANDLER.addRecord(STORE_MENU.promptAddAnimal());
				STORE_MENU.promptEnterToContinue();
				break;
			case 4:
			case 5:
			case 6:
				DATABASE_HANDLER.addRecord(STORE_MENU.promptAddPuzzles());
				STORE_MENU.promptEnterToContinue();
				break;
			case 7:
			case 8:
			case 9:
				DATABASE_HANDLER.addRecord(STORE_MENU.promptAddBoardGames());
				STORE_MENU.promptEnterToContinue();
				break;
		}
	}

	/**
	 * Method to prompt the user and print the remove toy menu, confirming that the
	 * user wants to remove the toy
	 */
	public void removeToyMaybeMenu() {
		// Ask the user for the serial number of the toy they wish to remove,
		// create an array list of toys to contain the toy, and then display the
		// toy to the user so they can have as much information as possible to
		// inform their choice of whether, maybe, the will remove the toy.
		String serialNumberOfToyToRemove = STORE_MENU.promptSerialNumber();
		Toy toyMaybeRemove = DATABASE_HANDLER
				.searchRecords(serialNumberOfToyToRemove,
						DATABASE_HANDLER.SEARCH_BY_SERIAL_NUMBER)
				.get(0);

		ArrayList<Toy> toyDisplay = new ArrayList<Toy>();
		// Add the toy to the database because there's no constructor macro for
		// arbitrary types.
		toyDisplay.add(toyMaybeRemove);

		// We only need to call the constructor. A local variable is unnecessary and may
		// generate IDE warnings.
		new SearchResultsTable(toyDisplay);

		char maybe = STORE_MENU.promptYesOrNo();
		if (maybe == 'Y') {
			DATABASE_HANDLER.removeRecord(toyMaybeRemove);
		} else {
			System.out.println("Toy '" + toyMaybeRemove.getName() + "' not removed from database.");
		}
	}
}
