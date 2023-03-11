package ca.cyberscientist.toystoredb.view;

import java.util.Scanner;

import ca.cyberscientist.toystoredb.controller.Database;
import ca.cyberscientist.toystoredb.exceptions.*;
import ca.cyberscientist.toystoredb.model.Animal;
import ca.cyberscientist.toystoredb.model.BoardGame;
import ca.cyberscientist.toystoredb.model.Figure;
import ca.cyberscientist.toystoredb.model.Puzzle;

public class Menu extends View {
	
	/**
	 * Common Variables used by majority of addToy prompt methods
	 * 
	 */
	Database db;
	Scanner keyboard;
	private String toyName;
	private String toyBrand;
	private double toyPrice;
	private int availableCount;
	private int appropriateAge;

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
	private String getSerialNumber(String prompt) throws InvalidSerialNumberException {
		keyboard = new Scanner(System.in);
		System.out.print(prompt);
		String serialNumber = keyboard.nextLine();

		boolean isDigits = (serialNumber.matches("\\d*")) ? true : false;

		//keyboard.close();

		// If the user's input is incorrect we raise an exception, per the assignment
		// instructions.
		if (serialNumber.length() != 10 || !isDigits) {
			throw new InvalidSerialNumberException("Serial numbers must be ten digits.");
		} else {
			return serialNumber;
		}
	}

    public String promptToyName() {
        keyboard = new Scanner(System.in);

        System.out.print("Enter a toy's name to search for it: ");
        String toyName = keyboard.nextLine();

        keyboard.close();
        
        return toyName;
    }

	public char promptToyType() {
		final char[] VALID_CHARACTERS = { 'A', 'B', 'F', 'P' };
		char toyType = getValidatedCharInput("\nInput one of the following letters for a given toy type:\n\n\tA) Animal\n\tB) Board Game\n\tF) Figurine\n\tP) Puzzle\n\nToy type: ", VALID_CHARACTERS);

		return toyType;
	}

	public void recommendationMenu() {
		System.out.println("You are in the recommendation menu.\n");

		System.out.println("\tA) Age");
		System.out.println("\tT) Type (animal, board game, figure, or puzzle)");
		System.out.println("\tP) Price range ($0.00 - $XX.XX)");
		System.out.println("\tS) Search using criteria");
		System.out.println("\tC) Cancel search\n");

		char[] validCharacterInput = { 'S', 'N', 'T', 'Q' };
		char userInput = getValidatedCharInput("Enter your choice: ", validCharacterInput);

		// boolean searchAgainFlag = true;
		// while (searchAgainFlag) {
		// 	switch (userInput) {
		// 		case 'A': getValidatedIntInput("Enter an age (minimum age is zero): "); break;
		// 		case 'T': getValidatedToyTypeInput("Enter a toy type: ", VALID_TOY_TYPES); break;
		// 		case 'P': getValidatedPriceRangeInput("Enter a price range (0.00 XX.XX): "); break;
		// 		case 'S': getRecommendations(); break;
		// 		case 'C': searchAgainFlag = false; break; // Cancel?
		// 	}
		// }

	}
	
	/**
	 * Method to print the add toy menu, it will later display the proper toy prompts that follow the serial numbers
	 */
	public void printAddToyMenu(){
		String serialNumber = promptSerialNumber();
		
		switch (serialNumber.charAt(0)) {
			case 0: 
			case 1:
				promptAddFigure(serialNumber);
				promptEnterToContinue();
				break;
			case 2: 
			case 3:
				promptAddAnimal(serialNumber);
				promptEnterToContinue();
				break;
			case 4: 
			case 5: 
			case 6:
				promptAddPuzzles(serialNumber);
				promptEnterToContinue();
				break;
			case 7: 
			case 8: 
			case 9:
				promptAddBoardGames(serialNumber);
				promptEnterToContinue();
				break;
		}
		
	}
	
	/**
	 * Method to prompt user to add the properties of a puzzle to be added to the toy arraylist
	 * 
	 * @param serialNumber the serial number of that was already validated and checked
	 */
	public void promptAddPuzzles(String serialNumber) {
		keyboard = new Scanner(System.in);
		String puzzleType;
		
		System.out.print("\nEnter Toy Name: ");
		toyName = keyboard.nextLine();
		System.out.print("\nEnter Toy Brand: ");
		toyBrand = keyboard.nextLine();
		System.out.print("\nEnter Toy Price: ");
		toyPrice = keyboard.nextDouble();
		System.out.print("\nEnter Available Counts: ");
		availableCount = keyboard.nextInt();
		System.out.print("\nEnter Appropiate Age: ");
		appropriateAge = keyboard.nextInt();
		System.out.print("\nEnter Puzzle Type: ");
		puzzleType = keyboard.nextLine();
		keyboard.nextLine();
		
		Puzzle newPuzzle = new Puzzle(serialNumber, toyName, toyBrand, toyPrice, availableCount, appropriateAge, puzzleType);
		db.addRecord(newPuzzle);
		
		System.out.println("New Toy Added!");
	}
	
	/**
	 * Method to prompt user to add the properties of a figure to be added to the toy arraylist
	 * 
	 * @param serialNumber the serial number of that was already validated and checked
	 */
	public void promptAddFigure(String serialNumber) {
		keyboard = new Scanner(System.in);
		String classification;
		
		System.out.print("\nEnter Toy Name: ");
		toyName = keyboard.nextLine();
		System.out.print("\nEnter Toy Brand: ");
		toyBrand = keyboard.nextLine();
		System.out.print("\nEnter Toy Price: ");
		toyPrice = keyboard.nextDouble();
		System.out.print("\nEnter Available Counts: ");
		availableCount = keyboard.nextInt();
		System.out.print("\nEnter Appropiate Age: ");
		appropriateAge = keyboard.nextInt();
		System.out.print("\nEnter Classification:");
		classification = keyboard.nextLine();
		keyboard.nextLine();
		
		Figure newFigure = new Figure(serialNumber, toyName, toyBrand, toyPrice, availableCount, appropriateAge, classification);
		db.addRecord(newFigure);
		
		System.out.println("New Toy Added!");
	}
	
	/**
	 * Method to prompt user to add the properties of a board game to be added to the toy arraylist
	 * 
	 * @param serialNumber the serial number of that was already validated and checked
	 */
	public void promptAddBoardGames(String serialNumber) {
		keyboard = new Scanner(System.in);
		String designers;
		int minPlayers;
		int maxPlayers;
		String numOfPlayers;
		
		System.out.print("\nEnter Toy Name: ");
		toyName = keyboard.nextLine();
		System.out.print("\nEnter Toy Brand: ");
		toyBrand = keyboard.nextLine();
		System.out.print("\nEnter Toy Price: ");
		toyPrice = keyboard.nextDouble();
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
		
		BoardGame newBoardGame = new BoardGame(serialNumber, toyName, toyBrand, toyPrice, availableCount, appropriateAge, numOfPlayers, designers);
		db.addRecord(newBoardGame);
		
		System.out.println("New Toy Added!");
	}
	
	/**
	 * Method to prompt user to add the properties of an animal to be added to the toy arraylist
	 * 
	 * @param serialNumber the serial number of that was already validated and checked
	 */
	public void promptAddAnimal(String serialNumber) {
		keyboard = new Scanner(System.in);
		String material;
		String size;
		
		System.out.print("\nEnter Toy Name: ");
		toyName = keyboard.nextLine();
		System.out.println("\nEnter Toy Brand: ");
		toyBrand = keyboard.nextLine();
		System.out.println("\nEnter Toy Price: ");
		toyPrice = keyboard.nextDouble();
		System.out.println("\nEnter Available Counts: ");
		availableCount = keyboard.nextInt();
		System.out.println("\nEnter Appropiate Age: ");
		appropriateAge = keyboard.nextInt();
		System.out.print("\nEnter Material: ");
		material = keyboard.nextLine();
		System.out.print("\nEnter Size: ");
		size = keyboard.nextLine();
		keyboard.nextLine();
		
		Animal newAnimal = new Animal(serialNumber, toyName, toyBrand, toyPrice, availableCount, appropriateAge, material, size);
		db.addRecord(newAnimal);
		
		System.out.println("New Toy Added!");
	}
	
	/**
	 * Method to allow player to return back to main menu
	 * 
	 */
	public void promptEnterToContinue() {
		System.out.println("Press \"Enter\" to Continue...");
		keyboard.nextLine();
	}
	
	/**
	 * Method to print the remove toy menu with the proper prompts to show all items corresponding the serial number and a prompt to make sure you want to remove the toy from the database
	 */
	public void printRemoveToyMenu() {
		String serialNumber = promptSerialNumber();
		
		/*
		 * Steps for this method 
		 * 1) Look through the project to find the proper method to check if the serial number is in the system
		 * 2) Print the corresponding object that contains the serial number in string format
		 * 3) Prompt yes/no to remove the object as confirmation
		 * 4) Print item removed if yes
		 * 5) Prompt enter to continue
		 */
	}
}
