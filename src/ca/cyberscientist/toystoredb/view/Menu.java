package ca.cyberscientist.toystoredb.view;

import ca.cyberscientist.toystoredb.exceptions.*;
import ca.cyberscientist.toystoredb.model.Animal;
import ca.cyberscientist.toystoredb.model.BoardGame;
import ca.cyberscientist.toystoredb.model.Figure;
import ca.cyberscientist.toystoredb.model.Puzzle;

/**
 * Class to deal with all the menus
 *
 * @author Koddy Rae Madriaga, Bryce Carson
 *
 */
public class Menu extends View {

	/**
	 * Method to print the main menu and prompt for user input
	 *
	 * @return char user choice for where to go in menu
	 */
	public char promptMainMenu() {
		System.out.println("You are in the main menu.\n");

		System.out.println("\tS) Search and purchase a toy.");
		System.out.println("\tA) Add a toy to the database.");
		System.out.println("\tR) Remove a toy from the database.");
		System.out.println("\tG) Generate a toy suggestion for a customer.");
		System.out.println("\tQ) Save the database to disk and quit. [End of shift]\n");

		char[] validCharacterInput = { 'S', 'A', 'R', 'G', 'Q' };
		char userInput = getValidatedCharInput("Enter your choice: ", validCharacterInput);

		return userInput;
	}

	/**
	 * Method to print the search menu and prompt for user input
	 *
	 * @return char user choice for where to go in menu
	 */
	public char promptSearchMenu() {
		System.out.println("\nYou are in the search menu.\n");

		System.out.println("\tS) Serial number");
		System.out.println("\tN) Name");
		System.out.println("\tT) Type (animal, board game, figure, or puzzle)");
		System.out.println("\tQ) Return to main menu\n");

		final char[] VALID_CHARACTERS = { 'S', 'N', 'T', 'Q' };
		char searchMethodOrQuitFlag = getValidatedCharInput("Enter your choice: ", VALID_CHARACTERS);

		return searchMethodOrQuitFlag;
	}

	/**
	 * Method to prompt user for the serial number
	 *
	 * @return serial number The serial number given by the user
	 */
	public String promptSerialNumber() {
		boolean exceptionOccured; // Did an exception occur while getting user input?

		String userInput = "0123456789";

		do {
			try {
				System.out.println("Enter a ten digit serial number.");
				userInput = getSerialNumber("Serial number: ");
				exceptionOccured = false; // Not reached if an exception occurs.
			} catch (InvalidSerialNumberException e) {
				System.out.println("[EXCEPTION] " + e.getMessage());
				exceptionOccured = true;
			}
		} while (exceptionOccured);

		return userInput;
	}

	// The only case in which we are logically allowed to, and necessarily must,
	// indicate a method throws an exception in a method header in this
	// assignment.
	/**
	 * Method to prompt the user for the serial number
	 *
	 * @param prompt The prompt message
	 * @return serialnumber The serial number from the user
	 * @throws InvalidSerialNumberException Exception thrown if serial number is
	 *                                      invalid
	 */
	private String getSerialNumber(String prompt) throws InvalidSerialNumberException {
		System.out.print(prompt);
		String serialNumber = keyboard.nextLine();

		boolean isDigits = (serialNumber.matches("\\d*")) ? true : false;

		// If the user's input is incorrect we raise an exception, per the assignment
		// instructions.
		if (serialNumber.length() != 10 || !isDigits) {
			throw new InvalidSerialNumberException("Serial numbers must be ten digits.");
		} else {
			return serialNumber;
		}
	}

	/**
	 * Method to prompt user for the toy name
	 *
	 * @return name The name of the toy
	 */
	public String promptToyNameForSearching() {

		System.out.print("Enter a toy's name to search for it: ");
		String toyName = keyboard.nextLine();

		return toyName;
	}

	/**
	 * Method to prompt user for the toy type
	 *
	 * @return char The character that correlates to the toy type
	 */
	public char promptToyType() {
		final char[] VALID_CHARACTERS = { 'A', 'B', 'F', 'P' };
		char toyType = getValidatedCharInput(
				"\nInput one of the following letters for a given toy type:\n\n\tA) Animal\n\tB) Board Game\n\tF) Figurine\n\tP) Puzzle\n\nToy type: ",
				VALID_CHARACTERS);

		return toyType;
	}

	/**
	 * Method to prompt user to add the properties of a puzzle to be added to the
	 * toy arraylist
	 *
	 * @param serialNumber the serial number of that was already validated and
	 *                     checked
	 */
	public Puzzle promptAddPuzzles(String serialNumber) {
		String puzzleType;
		String toyName;
		String toyBrand;
		double toyPrice;
		int availableCount;
		int appropriateAge;

		System.out.print("\nEnter Toy Name: ");
		toyName = keyboard.nextLine();
		System.out.print("\nEnter Toy Brand: ");
		toyBrand = keyboard.nextLine();

		toyPrice = promptPrice();

		System.out.print("\nEnter Available Counts: ");
		availableCount = keyboard.nextInt();
		System.out.print("\nEnter Appropiate Age: ");
		appropriateAge = keyboard.nextInt();
		System.out.print("\nEnter Puzzle Type: ");
		puzzleType = keyboard.nextLine();
		keyboard.nextLine();

		Puzzle newPuzzle = new Puzzle(serialNumber, toyName, toyBrand, toyPrice, availableCount, appropriateAge,
				puzzleType);

		System.out.println("New Toy Added!");

		return newPuzzle;
	}

	/**
	 * Method to prompt user to add the properties of a figure to be added to the
	 * toy arraylist
	 *
	 * @param serialNumber the serial number of that was already validated and
	 *                     checked
	 */
	public Figure promptAddFigure(String serialNumber) {
		String classification;
		String toyName;
		String toyBrand;
		double toyPrice;
		int availableCount;
		int appropriateAge;

		System.out.print("\nEnter Toy Name: ");
		toyName = keyboard.nextLine();
		System.out.print("\nEnter Toy Brand: ");
		toyBrand = keyboard.nextLine();

		toyPrice = promptPrice();

		System.out.print("\nEnter Available Counts: ");
		availableCount = keyboard.nextInt();
		System.out.print("\nEnter Appropiate Age: ");
		appropriateAge = keyboard.nextInt();
		System.out.print("\nEnter Classification:");
		classification = keyboard.nextLine();
		keyboard.nextLine();

		Figure newFigure = new Figure(serialNumber, toyName, toyBrand, toyPrice, availableCount, appropriateAge,
				classification);
		System.out.println("New Toy Added!");
		return newFigure;

	}

	/**
	 * Method to prompt user to add the properties of a board game to be added to
	 * the toy arraylist
	 *
	 * @param serialNumber the serial number of that was already validated and
	 *                     checked
	 */
	public BoardGame promptAddBoardGames(String serialNumber) {
		String designers;
		int minPlayers;
		int maxPlayers;
		String numOfPlayers;
		String toyName;
		String toyBrand;
		double toyPrice;
		int availableCount;
		int appropriateAge;

		System.out.print("\nEnter Toy Name: ");
		toyName = keyboard.nextLine();
		System.out.print("\nEnter Toy Brand: ");
		toyBrand = keyboard.nextLine();

		// Loop until a valid price is entered.
		toyPrice = promptPrice();

		System.out.print("\nEnter Available Counts: ");
		availableCount = keyboard.nextInt();
		System.out.print("\nEnter Appropiate Age: ");
		appropriateAge = keyboard.nextInt();
		System.out.print("\nEnter Designer Names (Use ',' to separate the names if there is more than one name): ");
		designers = keyboard.nextLine();
		System.out.print("\nEnter Minimum Number of Players: ");
		minPlayers = keyboard.nextInt();
		System.out.print("\nEnter Maximum Number of Players: ");
		maxPlayers = keyboard.nextInt();
		keyboard.nextLine();

		numOfPlayers = minPlayers + "-" + maxPlayers;

		BoardGame newBoardGame = new BoardGame(serialNumber, toyName, toyBrand, toyPrice, availableCount,
				appropriateAge, numOfPlayers, designers);

		System.out.println("New Toy Added!");
		return newBoardGame;
	}

	/**
	 * Method to prompt user to add the properties of an animal to be added to the
	 * toy arraylist
	 *
	 * @param serialNumber the serial number of that was already validated and
	 *                     checked
	 */
	public Animal promptAddAnimal() {
		String[] toyData = promptForToyInformation();

		if (toyData.length != 6) {
			System.out.println("Improper length of prompt add toy argument array.");
			System.exit(1);
		}

		String toySerialNumber = toyData[0];
		String toyName = toyData[1];
		String toyBrand = toyData[2];
		double toyPrice = Double.parseDouble(toyData[3]);
		int toyAvailableCount = Integer.parseInt(toyData[4]);
		int toyAppropriateAge = Integer.parseInt(toyData[5]);

		String animalMaterial;
		System.out.print("\nEnter Material: ");
		animalMaterial = keyboard.nextLine();

		String animalSize;
		System.out.print("\nEnter Size: ");
		animalSize = keyboard.nextLine();

		Animal newAnimal = new Animal(toySerialNumber,
				toyName,
				toyBrand,
				toyPrice,
				toyAvailableCount,
				toyAppropriateAge,

				animalMaterial,
				animalSize);

		System.out.println("\nNew Toy Added!");

		return newAnimal;
	}

	private String[] promptForToyInformation() {
		String[] toyData = new String[6];

		toyData[0] = promptSerialNumber();
		toyData[1] = promptToyNameForNewToy();
		toyData[2] = promptToyBrandForNewToy();
		toyData[3] = Double.toString(promptToyPriceForNewToy());
		toyData[4] = promptToyAvailableCountForNewToy();
		toyData[5] = promptToyAppropriateAgeForNewToy();

		return toyData;
	}

	private String promptToyNameForNewToy() {
		System.out.print("\nEnter Toy Name: ");
		String toyName = keyboard.nextLine();

		return toyName;
	}

	private String promptToyBrandForNewToy() {
		System.out.print("\nEnter Toy Brand: ");
		String toyBrand = keyboard.nextLine();

		return toyBrand;
	}

	/** Acquire a valid price for a toy. */
	private double promptToyPriceForNewToy() throws InvalidPriceException {
		double price = 00.00;
		while (price <= 00.00) {
			System.out.print("Enter a positive price (##.##): ");
			price = keyboard.nextDouble();
			keyboard.nextLine(); // Flush keyboard

			if (price <= 00.00) {
				throw new InvalidPriceException("The price was less than or equal to zero (non-[positive)!");
			}
		}

		return price;
	}

	private String promptToyAvailableCountForNewToy() {
		int availableCount = -1;
		while (availableCount < 0) {
			System.out.print("\nEnter the number of this item in stock: ");
			availableCount = keyboard.nextInt();
			keyboard.nextLine(); // Flush keyboard
		}

		return Integer.toString(availableCount);
	}

	private String promptToyAppropriateAgeForNewToy() {
		int appropriateAge = 0;
		while (appropriateAge < 0) {
			System.out.print("\nEnter an appropiate age for the toy (>= 0): ");
			appropriateAge = keyboard.nextInt();
			keyboard.nextLine(); // Flush keyboard
		}

		return Integer.toString(appropriateAge);
	}

	/**
	 * Method to allow player to press enter to continue
	 *
	 */
	public void promptEnterToContinue() {
		System.out.println("Press \"Enter\" to Continue...");
		keyboard.nextLine();
	}

	/**
	 * Method to prompt the user for yes or no
	 */
	public char promptYesOrNo() {
		char checker;
		System.out.println("Do you want to remove it (Y\\N)");
		checker = keyboard.nextLine().charAt(0);
		keyboard.nextLine();

		return checker;
	}
}
