/**
 *
 */
package ca.cyberscientist.toystoredb.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

// The model is imported in the Database class, moreover, the Database is in the
// same package and doesn't need to be imported.
import ca.cyberscientist.toystoredb.view.*;
import ca.cyberscientist.toystoredb.exceptions.IncorrectPlaceholderConstructorUsageException;
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
		STORE_MENU = new Menu(); // Instantiate a menu system for issuing prompts to the user and acquiring user
		// input.
		DATABASE_HANDLER = new Database(); // Instantiate the database.

		boolean continueLooping = true;

		do { // do while because we need to see the menu atleast once
			char mainMenuChoice = STORE_MENU.promptMainMenu();
			// view
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
					DATABASE_HANDLER.writeToDisk();
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
		try {
			searchResultsTable = new SearchResultsTable(searchResultsToyList, PLACEHOLDER_CONSTRUCTOR);
		} catch (IncorrectPlaceholderConstructorUsageException e) {
			System.out.println(
					"[DEBUG] This exception should never be thrown during production. You introduced a regression by modifying the intializtion of the search results table in this method."
							+ e.getMessage());
			e.printStackTrace();
		}
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
				String toyName = STORE_MENU.promptToyName();
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
		if (purchaseIndex >= 0) {
			DATABASE_HANDLER.purchaseToy(searchResultsToyList.get(purchaseIndex));
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
				DATABASE_HANDLER.addRecord(STORE_MENU.promptAddFigure(serialNumber));
				STORE_MENU.promptEnterToContinue();
				break;
			case 2:
			case 3:
				DATABASE_HANDLER.addRecord(STORE_MENU.promptAddAnimal(serialNumber));
				STORE_MENU.promptEnterToContinue();
				break;
			case 4:
			case 5:
			case 6:
				DATABASE_HANDLER.addRecord(STORE_MENU.promptAddPuzzles(serialNumber));
				STORE_MENU.promptEnterToContinue();
				break;
			case 7:
			case 8:
			case 9:
				DATABASE_HANDLER.addRecord(STORE_MENU.promptAddBoardGames(serialNumber));
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
