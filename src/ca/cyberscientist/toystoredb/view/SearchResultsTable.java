package ca.cyberscientist.toystoredb.view;

import java.util.ArrayList;

import ca.cyberscientist.toystoredb.model.*;

public class SearchResultsTable extends View {

    // Set by the constructor and only used privately.
    private final int NUMBER_PRINTABLE_FIELDS = 7;
    private int[] field_widths = new int[NUMBER_PRINTABLE_FIELDS];

    private int[] ascertainFieldWidths(ArrayList<Toy> toyList) {

        // The array is holds, at most, seven fields worth of data; the -1th
        // field is the length of all the serial numbers, which will always be
        // ten. Therefore there is no point allocating memory for that, or using
        // cycles ensuring this fact when it is already accounted for upon
        // loading the database or updating it.
        int[][] workingArray = new int[7][toyList.size()]; // NOTE: helpfully, filled with zeros on initialization.

        // Leverage polymorphic references and type coersion to our advantage to reduce
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

                // FIXME: TODO: the designer needs to be parsed as a comma separated list of designer(s), and the longest name determined.
                int boardGameDesignerLength = b.getDesigner().length();

                // minimum + " - " + maximum
                int numberOfPlayersLength = Integer.toString( b.getMinNumPlayers() ).length() + Integer.toString( b.getMaxNumPlayers() ).length() + 3;

                workingArray[5][arrayIndex] = boardGameDesignerLength;
                workingArray[6][arrayIndex] = numberOfPlayersLength;
            }
        }

        // Initialize an integer array of counters.
        int[] fieldWidths = new int[6];

        // Determine the maximum through simple iteration. There's a better way, of course, but this is dead simple.
        for(int i = 0 ; i < workingArray.length ; i++) {
            for(int j = 0 ; j < toyList.size() ; j++) {
                fieldWidths[i] = ( workingArray[i][j] > fieldWidths[i] ) ? workingArray[i][j] : fieldWidths[i];
            }
        }

        return fieldWidths;
    }

    private void printSearchResults(ArrayList<Toy> toyList) {
        // Remove the last toy from the list so that each toy can be printed by
        // the toy row printer, then a separator printed. The final toy is
        // printed manually followed by an end of table separator.
        Toy finalToy = toyList.remove(toyList.size());

        printTableHeader(searchResultsLength);

        for (Toy toy : toyList) {
            // FIXME: the current toString method is a plain stringification of the object.
            // TODO: the toy's printable/accessible fields need to be semi-colon separated
            // for better string interpolation.
            this.printRow(toy, columnWidths); // TODO: implement method.
            this.printRowSeparator(columnWidths);
        }

        printRow(finalToy);
        printTableFooter();
    }

    public int promptPurchaseToyOrQuit(ArrayList<Toy> toyList) {

        ascertainFieldWidths(toyList);
        printSearchResults(toyList);

    }

    public SearchResultsTable(ArrayList<Toy> toyList) {

    }
}
