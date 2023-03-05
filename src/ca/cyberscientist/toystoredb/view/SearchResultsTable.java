package ca.cyberscientist.toystoredb.view;

import java.util.ArrayList;
import java.util.Scanner;

import ca.cyberscientist.toystoredb.model.*;

public class SearchResultsTable extends View {

    // Set by the constructor and only used privately.
    private final int NUMBER_PRINTABLE_FIELDS = 7;
    private int[] minimumFieldWidths = new int[NUMBER_PRINTABLE_FIELDS];

    private final ArrayList<Toy> searchResultsToyList;

    private void ascertainFieldWidths(ArrayList<Toy> toyList) {
        // The array is holds, at most, seven fields worth of data; the -1th (or
        // 8th, both nonexistent) field is the length of all the serial numbers,
        // which will always be ten. Therefore there is no point allocating
        // memory for that, or using cycles ensuring this fact when it is
        // already accounted for upon loading the database or updating it.
        int[][] workingArray = new int[7][toyList.size()];

        // Leverage polymorphic references and type coercion to our advantage to reduce
        // code duplication.
        for (Toy toy : toyList) {
            int arrayIndex = toyList.indexOf(toy);

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

                workingArray[5][arrayIndex] = boardGameDesignerLength;
                workingArray[6][arrayIndex] = numberOfPlayersLength;
            }
        }

        // Initialize an integer array of counters.
        int[] fieldWidths = new int[6];

        // Determine the maximum through simple iteration. There's a better way, of
        // course, but this is dead simple.
        for (int i = 0; i < workingArray.length; i++) {
            for (int j = 0; j < toyList.size(); j++) {
                fieldWidths[i] = (workingArray[i][j] > fieldWidths[i]) ? workingArray[i][j] : fieldWidths[i];
            }
        }

        // Overwrite the object's array with the local data.
        minimumFieldWidths = fieldWidths;

        //      ╒════════════╤═══╤═══╤═══╤═══╤═══╤═══╤═══╕      printTableHeader: 1_ ; 2 + xᵢ.length() ; _1_ ; ... ; _1
        //   01═╪ 4831187177 │ b │ c │ d │ e │ f │ g │ h │      printTableRow: 
        //      ╞════════════╪═══╪═══╪═══╪═══╪═══╪═══╪═══╡      printTableRowSeparator: 1_ ; 2 + xᵢ.length() ; _1_ ; ... ; _1
        //   02═╪ 9336740135 │ j │ k │ l │ m │ n │ o │ p │      printTableRow:
        //      ╘════════════╧═══╧═══╧═══╧═══╧═══╧═══╧═══╛      printTableFooter: 1_ ; 2 + xᵢ.length() ; _1_ ; ... ; _1
        // The width of a row is 1 + 7 * (3 + xᵢ.length()), however, that is not
        // a direct translation to algorithm because different parts of the
        // table have different printing needs; some Toys must be printed with
        // double height rows, or algorithmically determined row heights to fit
        // in designers. There is also the issue of needing to print serial
        // numbers and then also the selection for that row.
    }

    private void printSearchResultsTable() {
        // Acquire the information needed for table display.
        ascertainFieldWidths(this.searchResultsToyList);

        // Remove the last toy from the list so that each toy can be printed by
        // the toy row printer, then a separator printed. The final toy is
        // printed manually followed by an end of table separator.
        Toy finalToy = toyList.remove(this.searchResultsToyList.size());

        printTableHeader();

        for (Toy toy : this.searchResultsToyList) {
            printTableRow(toy); // TODO: implement method.
            printTableRowSeparator(); // TODO: implement method.
        }

        printTableRow(finalToy);
        printTableFooter();
    }

    // FIXME: this won't be correct if I add padding in the implementation of
    // printTableRow, which I am doing.
    private void printTableHeader() {
        System.out.print("\u2554");
        // System.out.print("╒")

        System.out.print("\u2557");
    };

    private void printTableRow(Toy toy) {
        System.out.print("\u2551"); // Left-most separator w/out padding
        // This works well for every toy except board games, which need a
        // comma-separated list of designers printed pretty, as well as the
        // number of players printed well.
        for(String s : toy.toString().split(";")) {
            System.out.print("  " + s + "  \u2551"); // Padding left, element, padding right, separator
        }
    };

    private void printTableRowSeparator() {
        
    };

    private void printTableFooter() {

    };

    /**
     * This method prints the search results from the search method performed
     * in a pretty table. The 0th option (which is actually taken in as Q from the
     * user [digits are for purchasing a toy, and zero is for canceling a
     * "transaction" or lookup.])
     * 
     * @author: Bryce Carson
     * @param toyList An array list of Toys to be printed in table format.
     * @return An integer indicating which toy in the array list the user wishes
     *         to "purchase". A return value of 0 is reserved for voiding/canceling
     *         the
     *         in-progress transaction.
     */
    public int promptPurchaseToyOrQuit() {
        printSearchResultsTable();

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Enter the digits corresponding to a row in the above table to finalize a purchase choice of one unit.");

        int userSelection = 0;
        // No input and incorrect input are both invalid, so we continue _until_ we have
        // valid input!
        boolean acquireInput = true;
        while (acquireInput) {
            System.out.print("Integer selection (enter Q to cancel/void the transaction): ");
            String input = keyboard.nextLine();

            // Only positive integers and lower or uppercase Q are valid input.
            if (input.toUpperCase().charAt(0) == 'Q' || (input.matches("\\d*") && Integer.parseInt(input) > 0)) {
                if (input.matches("\\d*")) {
                    userSelection = Integer.parseInt(input);
                } else {
                    userSelection = 0; // Let Q be 0.
                }

                acquireInput = false;
            } else {
                System.out.println("Input was invalid. Please ensure input matches the prompt or query. Your input was: " + input);
            }
        }

        keyboard.close();

        return userSelection;

    }

    public SearchResultsTable(ArrayList<Toy> toyList) {
        this.searchResultsToyList = toyList;
    }
}
