package ca.cyberscientist.toystoredb.view;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import ca.cyberscientist.toystoredb.controller.Database;
import ca.cyberscientist.toystoredb.exceptions.*;
import ca.cyberscientist.toystoredb.model.Animal;
import ca.cyberscientist.toystoredb.model.BoardGame;
import ca.cyberscientist.toystoredb.model.Figure;
import ca.cyberscientist.toystoredb.model.Puzzle;

public class Menu extends View {
	
	Scanner keyboard;
	
	Database db;
	
	/**
	 * List of similar attributes of the given toy types
	 */
	String toyName;
	String toyBrand;
	double toyPrice;
	int availableCount;
	int appropriateAge;

	/**
	 * Inteded usage pattern: if the user searches by serial number, get the
	 * toyTypeFilter and pass that to the same method used to search by type. After
	 * search resuolts of that type have been returned, filter by the serial
	 * number by calliing the serial number search method.
	 */
	private int toyTypeFilter;

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
		System.out.println("You are in the search menu.\n");

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

		// The first character of the user's input. This is used to permit filtering the
		// records by type before searching by serial number for efficiency sake.
		this.toyTypeFilter = Integer.parseInt(userInput.substring(0, 1));
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

		keyboard.close();

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
		char filter = 'A';



		switch (this.toyTypeFilter) {
			// FIXME: ensure character is correct for these numbers.
			case 0:
			case 1:
				filter = 'A';
				break;
			case 2:
			case 3:
				filter = 'A';
				break;
			case 4:
			case 5:
			case 6:
				filter = 'A';
				break;
			case 7:
			case 8:
			case 9:
				filter = 'A';
				break;
		}

		return filter;
	}

	public void recommendationMenu() {
		final String[] VALID_TOY_TYPES = { "A", "B", "F", "P", "Animal", "Board Game", "Figure", "Puzzle" };

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
	
	public void printAddToyMenu(){
		
		String serialNumber = promptSerialNumber();
		
		switch (serialNumber.charAt(0)) {
			case 0: 
			case 1:
				promptAddFigure(serialNumber);
				break;
			case 2: 
			case 3:
				promptAddAnimal(serialNumber);
				break;
			case 4: 
			case 5: 
			case 6:
				promptAddPuzzles(serialNumber);
				break;
			case 7: 
			case 8: 
			case 9:
				promptAddBoardGames(serialNumber);
				break;
		}
		
	}
	
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
}
