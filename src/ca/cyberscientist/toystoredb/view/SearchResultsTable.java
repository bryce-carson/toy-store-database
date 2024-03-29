package ca.cyberscientist.toystoredb.view;

import java.util.ArrayList;

import ca.cyberscientist.toystoredb.model.*;

/**
 * This large class is used to construct a table of toys, pretty printed for the user, with indexes for user selection or differnetiation.
 * @author Bryce Carson
 *
 */
public class SearchResultsTable extends View {

	/**The length of the longest digits, zero-padded, which are used to select a row and which are printed to the left of a row.
	 * 
	 */
	private int rowSelectorDigitLength;

	/**The minimum width of the field widths, if the content of the fields are never longer than the headers. That is, the width of the headers in the field.
	 * 
	 */
	private int[] minimumFieldWidths = {
			4, 5, 5, 13,
			11, 10, 10
	};

	/**The toylist which this search results table will display.
	 * 
	 */
	private final ArrayList<Toy> searchResultsToyList;

	/**
	 * Method to check the data lengths
	 */
	private void ascertainFieldWidths() {
		// The array is holds, at most, seven fields worth of data; the -1th (or
		// 8th, both nonexistent) field is the length of all the serial numbers,
		// which will always be ten. Therefore there is no point allocating
		// memory for that, or using cycles ensuring this fact when it is
		// already accounted for upon loading the database or updating it.
		int[][] workingArray = new int[7][this.searchResultsToyList.size()];

		// Leverage polymorphic references and type coercion to our advantage to reduce
		// code duplication.
		for (Toy toy : this.searchResultsToyList) {
			int arrayIndex = this.searchResultsToyList.indexOf(toy);

			int nameLength = toy.getName().length();
			int brandLength = toy.getBrand().length();

			// + $ ; math is more efficient than calculating the length of the string after
			// concatenation, ie, concatenate later when we print. Not when we care about
			// math only.
			int priceLength = Double.toString(toy.getPrice()).length() + 1;

			int availableCountLength = Integer.toString(toy.getAvailableCount()).length();
			int appropriateAgeLength = Integer.toString(toy.getAppropriateAge()).length();

			workingArray[0][arrayIndex] = nameLength;
			workingArray[1][arrayIndex] = brandLength;
			workingArray[2][arrayIndex] = priceLength;
			workingArray[3][arrayIndex] = availableCountLength;
			workingArray[4][arrayIndex] = appropriateAgeLength;

			if (toy instanceof Animal) {
				Animal a = (Animal) toy;
				int materialLength = a.getMaterial().length();
				int sizeLength = a.getSize().length();

				workingArray[5][arrayIndex] = materialLength;
				workingArray[6][arrayIndex] = sizeLength;
			} else if (toy instanceof Figure) {
				Figure f = (Figure) toy;
				int classificationLength = f.getClassification().length();

				workingArray[5][arrayIndex] = classificationLength;
			} else if (toy instanceof Puzzle) {
				Puzzle p = (Puzzle) toy;
				int puzzleTypeLength = p.getPuzzleType().length();

				workingArray[5][arrayIndex] = puzzleTypeLength;
			} else if (toy instanceof BoardGame) {
				BoardGame b = (BoardGame) toy;

				int boardGameDesignerLength = 0;
				String[] designers = b.getDesigner().split(",");
				for (String designer : designers) {
					boardGameDesignerLength = (designer.length() > boardGameDesignerLength) ? designer.length()
							: boardGameDesignerLength;
				}

				// Number of players
				// minimumNumber + " - " + maximumNumber
				int numberOfPlayersLength = Integer.toString(b.getMinNumPlayers()).length()
						+ Integer.toString(b.getMaxNumPlayers()).length() + 3;

				workingArray[5][arrayIndex] = numberOfPlayersLength;
				workingArray[6][arrayIndex] = boardGameDesignerLength;
			}
		}

		// Initialize an integer array of counters.
		int[] fieldWidths = new int[7];

		// Determine the maximum through simple iteration. There's a better way, of
		// course, but this is dead simple.
		for (int i = 0; i < workingArray.length; i++) {
			for (int j = 0; j < this.searchResultsToyList.size(); j++) {
				fieldWidths[i] = (workingArray[i][j] > fieldWidths[i]) ? workingArray[i][j] : fieldWidths[i];
			}
		}

		// Overwrite the object's array with the local data.
		for (int i = 0; i < fieldWidths.length; i++) {
			minimumFieldWidths[i] = (fieldWidths[i] > minimumFieldWidths[i]) ? fieldWidths[i] : minimumFieldWidths[i];
		}
	}

	/**
	 * Method to print the search results
	 */
	private void printSearchResultsTable() {
		// Acquire the information needed for table display.
		ascertainFieldWidths();

		// Remove the last toy from the list so that each toy can be printed by
		// the toy row printer, then a separator printed. The final toy is
		// printed manually followed by an end of table separator.
		Toy finalToy;
		if (this.searchResultsToyList.size() > 1) {
			finalToy = this.searchResultsToyList.remove(this.searchResultsToyList.size() - 1);
		} else {
			// .remove is not appropriate because it throws an IndexOutOfBoundsException
			finalToy = this.searchResultsToyList.remove(0);
		}

		printTableHeader();
		printTableRowSeparator();

		if (searchResultsToyList.size() >= 1) {
			for (Toy toy : this.searchResultsToyList) {
				printTableRow(toy);
				printTableRowSeparator();
			}
		}

		searchResultsToyList.add(finalToy); // Add the toy back to the list so the index works.
		printTableRow(finalToy);

		printTableFooter();
	}

	/**
	 * Method to print the table header
	 */
	private void printTableHeader() {
		System.out.println(); // Ensure vertical padding.
		
		// Part One: printing the header separator (top border)
		// Left padding and the length of the array (e.g. 001 or 999).
		System.out.print(" ".repeat(rowSelectorDigitLength + 2) + "\u2552"); // â•’

		System.out.print("\u2550".repeat(12)); // â•� end of field 0: Serial No.

		// Algorithm for printing the header according to field widths 1:7
		for (int i = 0; i < minimumFieldWidths.length - 1; i++) {
			System.out.print("\u2564"); // â•¤

			System.out.print("\u2550".repeat(minimumFieldWidths[i] + 2)); // â•�
		}

		System.out.print("\u2564"); // â•¤

		System.out.print("\u2550".repeat(minimumFieldWidths[6] + 2)); // â•�

		System.out.println("\u2555"); // â••

		// Part Two: printing the header row table cells
		// â”‚ Serial No. â”‚ NameÎ†â”‚ BrandÎ’â”‚ PriceÎ“â”‚ AvailableÎ”â”‚ Minimum AgeÆ�â”‚ Misc. infoÎ–â”‚
		// Misc. infoÎ—â”‚
		System.out.print(" ".repeat(rowSelectorDigitLength + 2) + "\u2502 Serial No. "); // â”‚â� Serial No.â� 

		// Field separator and padding, â”‚â� , field name, padding
		for (int i = 0; i < minimumFieldWidths.length; i++) {
			String headerFieldString = "";

			// Which string to use
			switch (i) {
				case 0:
					headerFieldString = "Name";
					break;
				case 1:
					headerFieldString = "Brand";
					break;
				case 2:
					headerFieldString = "Price";
					break;
				case 3:
					headerFieldString = "No. Available";
					break;
				case 4:
					headerFieldString = "Minimum age";
					break;
				case 5:
				case 6:
					headerFieldString = "Misc. info";
					break;
			}

			System.out.print("\u2502 "); // Table cell separator: â”‚â� 
			System.out.print(headerFieldString + " ".repeat(minimumFieldWidths[i] - headerFieldString.length() + 1)); // field
																														// //
																														// padding
		}

		System.out.println("\u2502 "); // Terminate the line with the right-side table border.
	};

	// NOTE: this is a necessary and acceptable violation of the MVC pattern. In
	// this case, the controller cannot suitably generate the information to
	// pass to the view without introducing and undue amount of code which would
	// be more difficult to maintain and debug.
	/**
	 * Method to print the table row
	 * @param toy the toy in the row
	 */
	private void printTableRow(Toy toy) {
		// Part one: printing the information common to all toys.
		// Row selector and serial number: â� ###â•�â•ªâ� ##########â� 
		System.out.print(String.format(" %02d", searchResultsToyList.indexOf(toy) + 1) + "\u2550\u256a "); // â� ###â•�â•ª
		System.out.print(toy.getSerialNumber() + " "); // ##########â� .

		// Other common fields
		System.out.print("\u2502 " + toy.getName() + " ".repeat(minimumFieldWidths[0] - toy.getName().length() + 1)); // â”‚â� toy.getName()Î†â� 
		System.out.print("\u2502 " + toy.getBrand() + " ".repeat(minimumFieldWidths[1] - toy.getBrand().length() + 1)); // â”‚â� toy.getBrand()Î’â� 

		// Price should be right-aligned, so it must be wrapped in a helper method to
		// abstract the logic.
		System.out.print("\u2502 " + "$" + " ".repeat(minimumFieldWidths[2] - Double.toString(toy.getPrice()).length() - 1) + toy.getPrice() + " "); // â”‚â� toy.getPrice()Î“â� 

		System.out.print("\u2502 " + toy.getAvailableCount()
				+ " ".repeat(minimumFieldWidths[3] - Integer.toString(toy.getAvailableCount()).length() + 1)); // â”‚â� toy.getAvailable()Î”â� 
		System.out.print("\u2502 " + toy.getAppropriateAge()
				+ " ".repeat(minimumFieldWidths[4] - Integer.toString(toy.getAppropriateAge()).length() + 1)); // â”‚â� toy.getMiniumumAge()Æ�â� 

		// The most rudimentary reflection; equivalent in complexity to instanceof,
		// which was taught in class. Does not require an import.
		// NOTE: Board games are the only class which has a double-height row, so the
		// printing is otherwise the same in each case.
		if (toy instanceof Animal) {
			Animal a = (Animal) toy;
			String material = a.getMaterial();
			String size = a.getSize();
			System.out.print("\u2502 " + material + " ".repeat(minimumFieldWidths[5] - material.length() + 1));
			System.out.println("\u2502 " + size + " ".repeat(minimumFieldWidths[6] - size.length() + 1) + "\u2502");

		} else if (toy instanceof BoardGame) {
			BoardGame b = (BoardGame) toy;
			String players = b.getMinNumPlayers() + "-" + b.getMaxNumPlayers();
			String[] designers = b.getDesigner().split(",");

			for (int i = 0; i < designers.length; i++) {
				designers[i] = designers[i].trim(); // Ensure there are no leading or trailing spaces in the string
													// containing the name.
			}

			// Print the players and the designers. NOTE: the designers, if
			// longer than 1, requires multi-line printing and we must print the
			// rows in a loop.
			System.out.print("\u2502 " + players + " ".repeat(minimumFieldWidths[5] - players.length() + 1));
			System.out.println("\u2502 " + designers[0] + " ".repeat(minimumFieldWidths[6] - designers[0].length() + 1)
					+ "\u2502");

			// In the case there is more than one designer we need to print a
			// row per additional designer, with empty fields, except for the
			// last field.
			if (designers.length > 1) {
				for (int i = 1; i < designers.length; i++) {
					// Left padding
					System.out.print(" ".repeat(rowSelectorDigitLength + 2));

					// Print the empty serial number field
					// Left padding that is always present, the field length of the row selector
					// digit, and then the ten characters that would be the serial number, and the
					// right padding.
					System.out.print("\u2502" + " ".repeat(1 + 10 + 1));

					// Print empty fields for the inner six fields
					for (int field = 0; field < 6; field++) {
						System.out.print("\u2502" + " ".repeat(1 + minimumFieldWidths[field] + 1));
					}

					// Print the designer name.
					System.out.println("\u2502 " + designers[i]
							+ " ".repeat(minimumFieldWidths[6] - designers[i].length() + 1) + "\u2502");
				}
			}
		} else if (toy instanceof Figure) {
			Figure f = (Figure) toy;
			String classification = f.getClassification();

			System.out.print(
					"\u2502 " + classification + " ".repeat(minimumFieldWidths[5] - classification.length() + 1));
			System.out.println("\u2502 " + " ".repeat(minimumFieldWidths[6] + 1) + "\u2502");

		} else if (toy instanceof Puzzle) {
			Puzzle p = (Puzzle) toy;
			String puzzleType = p.getPuzzleType();

			System.out.print("\u2502 " + puzzleType + " ".repeat(minimumFieldWidths[5] - puzzleType.length() + 1));
			System.out.println("\u2502 " + " ".repeat(minimumFieldWidths[6] + 1) + "\u2502");
		}
	};

	/**
	 * Method to print the row separators
	 */
	private void printTableRowSeparator() {
		// Part zero: print the left padding
		System.out.print(" ".repeat(rowSelectorDigitLength + 2));

		// Part one: print the Serial No. field
		System.out.print("\u255e" + "\u2550".repeat(12)); // â•žâ•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�

		// Part two: variable field widths
		for (int i = 0; i < minimumFieldWidths.length; i++) {
			System.out.print("\u256a"); // â•ª
			System.out.print("\u2550".repeat(minimumFieldWidths[i] + 2)); // Minimum plus padding
		}

		// Part three: table end
		System.out.println("\u2561"); // â•¡
	};

	/**
	 * Method to print the table footer
	 */
	private void printTableFooter() {
		// Part one: print the Serial No. field
		System.out.print(" ".repeat(rowSelectorDigitLength + 2) + "\u2558" + "\u2550".repeat(12)); // â•˜â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�

		// Part two: variable field widths
		for (int i = 0; i < minimumFieldWidths.length; i++) {
			System.out.print("\u2567"); // â•§
			System.out.print("\u2550".repeat(minimumFieldWidths[i] + 2)); // Minimum plus padding
		}

		// Part three: table end
		System.out.println("\u255b"); // â•›

	};

	/**
	 * This method prints the search results from the search method performed
	 * in a pretty table. The 0th option (which is actually taken in as Q from the
	 * user [digits are for purchasing a toy, and zero is for canceling a
	 * "transaction" or lookup.])
	 *
	 * @author Bryce Carson
	 * @return An integer indicating which toy in the array list the user wishes
	 *         to "purchase". A return value of 0 is reserved for voiding/canceling
	 *         the
	 *         in-progress transaction.
	 */
	public int promptPurchaseToyOrQuit() {
		System.out.println(
				"Enter the digits corresponding to a row in the above table to finalize a purchase choice of one unit.");

		int userSelection = 0;
		// No input and incorrect input are both invalid, so we continue _until_ we have
		// valid input!
		boolean acquireInput = true;
		while (acquireInput) {
			System.out.print("Integer selection (enter Q to cancel/void the transaction): ");
			String input = keyboard.nextLine(); // Keyboard comes from the global scope.

			// Only positive integers and lower or uppercase Q are valid input.
			if (input.toUpperCase().charAt(0) == 'Q' || (input.matches("\\d+") && Integer.parseInt(input) > 0)) {
				if (input.matches("\\d+")) {
					userSelection = Integer.parseInt(input);
				} else {
					userSelection = 0; // Let Q be 0.
				}

				acquireInput = false;
			} else {
				System.out.println(
						"Input was invalid. Please ensure input matches the prompt or query. Your input was: " + input);
			}
		}

		System.out.println();
		
		return userSelection;

	}

    /** Constructs and pretty prints a table of toys.
		 * @param toyList The array list of toys for which to display.
		 */
	public SearchResultsTable(ArrayList<Toy> toyList) {
		this.searchResultsToyList = toyList;
		this.rowSelectorDigitLength = String.format("%02d", searchResultsToyList.size()).length(); // The first element is indexed with
		// Print the table.
		printSearchResultsTable();
	}

	/**
	 * This is a placeholder constructor. It should only be used when declaring a
	 * search results table which is truly initialized within conditional logic, and
	 * which thus causes compiler complaints.
	 * @param placeholderToyList A placeholder toylist which is only used to avoid compiler errors.
	 * @param placeholderConstructorPredicate A boolean value, which can be true or false, it doesn't matter, to force the use of this constructor vs the other constructor.
	 */
	public SearchResultsTable(ArrayList<Toy> placeholderToyList, boolean placeholderConstructorPredicate) {
		this.searchResultsToyList = placeholderToyList;
	}
}
