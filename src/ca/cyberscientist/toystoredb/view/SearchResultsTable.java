package ca.cyberscientist.toystoredb.view;

import java.util.ArrayList;
import java.util.Scanner;

import ca.cyberscientist.toystoredb.model.*;

public class SearchResultsTable extends View {

    // Set by the constructor and only used privately.
    private final int NUMBER_PRINTABLE_FIELDS = 7;
    private int[] minimumFieldWidths = new int[NUMBER_PRINTABLE_FIELDS]; // FIXME: this needs to be initialized as the widths of the header strings.

    private final ArrayList<Toy> searchResultsToyList;

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

                workingArray[5][arrayIndex] = boardGameDesignerLength;
                workingArray[6][arrayIndex] = numberOfPlayersLength;
            }
        }

        // Initialize an integer array of counters.
        int[] fieldWidths = new int[6];

        // Determine the maximum through simple iteration. There's a better way, of
        // course, but this is dead simple.
        for (int i = 0; i < workingArray.length; i++) {
            for (int j = 0; j < this.searchResultsToyList.size(); j++) {
                fieldWidths[i] = (workingArray[i][j] > fieldWidths[i]) ? workingArray[i][j] : fieldWidths[i];
            }
        }

        // Overwrite the object's array with the local data.
        minimumFieldWidths = fieldWidths;

        // Headers define the actual minimum size of the table, but the cell content can expand a given field's width.
        //␠␠␠␠╒════════════╤═════Ά╤══════Β╤══════Γ╤══════════Δ╤════════════Ɛ╤═══════════Ζ╤═══════════Η╕                printTableHeader: 1_ ; 2 + xᵢ.length() ; _1_ ; ... ; _1
        //␠␠␠␠│ Serial No. │ NameΆ│ BrandΒ│ PriceΓ│ AvailableΔ│ Minimum AgeƐ│ Misc. infoΖ│ Misc. infoΗ│                ....
        //␠␠␠␠╞════════════╪═════Ά╪══════Β╪══════Γ╪══════════Δ╪════════════Ɛ╪═══════════Ζ╪═══════════Η╡                printTableRowSeparator
        //␠01═╪ 0113513686 │ Toy soldier │ Gamen │ $ 14.06 │ 2 │ 5 │ Action figure │  │                                              printTableRow:
        //    ╞════════════╪═══════════════════════════════╪═══════════╪══════════╪═══╪═══╪═════╪═════════════════╡      printTableRowSeparator: 1_ ; 2 + xᵢ.length() ; _1_ ; ... ; _1
        // 02═╪ 8685655988 │ Betrayal at House on the Hill │ Gamegenix │ $ 131.52 │ 9 │ 6 │ 2-6 │ 1. Barney Lugo  │      printTableRow:
        //    │            │                               │           │          │   │   │     │ 2. Yu Zimmerman │      printTableRow:
        //    ╘════════════╧═══════════════════════════════╧═══════════╧══════════╧═══╧═══╧═════╧═════════════════╛      printTableFooter: 1_ ; 2 + xᵢ.length() ; _1_ ; ... ; _1
        // The width of a row is 1 + 7 * (3 + xᵢ.length()), however, that is not
        // a direct translation to algorithm because different parts of the
        // table have different printing needs; some Toys must be printed with
        // double height rows, or algorithmically determined row heights to fit
        // in designers. There is also the issue of needing to print serial
        // numbers and then also the selection for that row.
        // Seven spacing variables, alpha, beta, gamma, delta, epsilon, zeta, eta; the first field, containing serial number, always has the same width. All variables begin with a value of three (padding for an empty value, printed as " ").
        // The name column (ones are padding around data):
        // 1 + fieldWidth[0] + 1 = Ά
        // " " + cellData + " ".repeat(Ά - cellData.length() - 1)
    }

    private void printSearchResultsTable() {
        // Acquire the information needed for table display.
        ascertainFieldWidths();

        // Remove the last toy from the list so that each toy can be printed by
        // the toy row printer, then a separator printed. The final toy is
        // printed manually followed by an end of table separator.
        Toy finalToy = this.searchResultsToyList.remove(this.searchResultsToyList.size());

        printTableHeader();

        for (Toy toy : this.searchResultsToyList) {
            printTableRow(toy); // TODO: implement method.
            printTableRowSeparator(); // TODO: implement method.
        }

        printTableRow(finalToy);

        printTableFooter();
    }

    private void printTableHeader() {
        // Part One: printing the header separator (top border)
        System.out.print("\u2554");
        // ╔
        System.out.print("\u2554".repeat(12));
        // ═
        System.out.print("\u2564");
        // ╤ end of field 0: Serial No.

        // Algorithm for printing the header according to field widths 1:7
        for(int fieldWidth : minimumFieldWidths) {
            System.out.print("\u2554".repeat(fieldWidth));
            // ═

            System.out.print("\u2564");
            // ╤ end of i field
        }

        System.out.print("\u2557");
        // ╗

        // Part Two: printing the header row table cells
        // │ Serial No. │ NameΆ│ BrandΒ│ PriceΓ│ AvailableΔ│ Minimum AgeƐ│ Misc. infoΖ│ Misc. infoΗ│
        System.out.print("\u2502 Serial No. "); // │␠Serial No.␠

        // Field separator and padding, │␠, field name, padding
        for(int i = 0 ; i < minimumFieldWidths.length ; i++ ) {
            String headerFieldString = "";

            // Which string to use
            switch(i) {
            case 0: headerFieldString = "Name";
            case 1: headerFieldString = "Brand";
            case 2: headerFieldString = "Price";
            case 3: headerFieldString = "Available";
            case 4: headerFieldString = "Minimum age";
            case 5:
            case 6: headerFieldString = "Misc. info";
            }

            System.out.print("\u2502 "); // Table cell separator: │␠
            // FIXME: think hard about the calculation. It shouldn't be less the padding, the padding actually needs to be added to the length of the cell content.
            System.out.print(headerFieldString + " ".repeat(minimumFieldWidths[i] - headerFieldString.length() - 1)); // field width, less the field string length, less the left padding
        }
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
        // Part one: print the Serial No. field
        System.out.print("\u255e" + "\u2550".repeat(12)); // ╞════════════

        // Part two: variable field widths
        for (int i = 0; i < minimumFieldWidths.length; i++) {
            System.out.print("\u256a"); // ╪
            System.out.print("\u2550".repeat(minimumFieldWidths[i] + 2)); // Minimum plus padding
        }

        // Part three: table end
        System.out.println("\u2551") // ╡
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
