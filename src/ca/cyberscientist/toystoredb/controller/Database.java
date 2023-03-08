/**
 *
 */
package ca.cyberscientist.toystoredb.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import ca.cyberscientist.toystoredb.exceptions.InvalidSearchMethodException;
import ca.cyberscientist.toystoredb.exceptions.InvalidToyTypeQueryException;
import ca.cyberscientist.toystoredb.exceptions.ToyExistsInDatabaseException;
// Import all of the toy stuff
import ca.cyberscientist.toystoredb.model.Toy;
import ca.cyberscientist.toystoredb.model.Animal;
import ca.cyberscientist.toystoredb.model.BoardGame;
import ca.cyberscientist.toystoredb.model.Figure;
import ca.cyberscientist.toystoredb.model.Puzzle;

/**
 * @author Bryce Carson
 *
 */
public class Database {
    public final char SEARCH_BY_SERIAL_NUMBER = 's';
    public final char SEARCH_BY_TOY_NAME = 'n';

    private final String FILENAME = "res/toys.txt";

    private ArrayList<Toy> records = new ArrayList<Toy>();

    /**
     * Instantiate the database by reading it from disk.
     *
     * @throws FileNotFoundException if the database is not found on-disk we must
     *                               throw an exception.
     */
    public Database() throws FileNotFoundException {
        this.records = toyListFromFile(this.FILENAME);
    }

    /**
     * Search the database records for a name matching the given string (using the
     * String.contains() method) or an exact serial number.
     *
     * @param query        The query for the database, a String, which is either
     *                     toy's serial number or string to be matched against the
     *                     toy's name.
     * @param searchMethod a character indicating whether the method to use is the
     *                     serial number or name search methodology, either 's' or
     *                     'n'.
     * @return
     */
    public ArrayList<Toy> searchRecords(String query, char searchMethod) throws InvalidSearchMethodException {

        ArrayList<Toy> searchResults = new ArrayList<Toy>();

        iterateOverRecords: for (Toy toy : this.records) {
            // Dispatch based on the method of the search (by name, type, or serial number).
            switch (searchMethod) {
                case 's': // Searching by serial number; utilizes an early return.
                    if (toy.getSerialNumber().equals(query)) {
                        searchResults.add(toy);
                        break iterateOverRecords;
                    }

                    break;

                case 'n': // Searching by name: remove the toys that don't contain the search query from
                          // the list of results we will be returning.
                    if (toy.getName().toUpperCase().contains(query.toUpperCase()))
                        searchResults.add(toy);

                    break;

                default:
                    throw new InvalidSearchMethodException();
            }
        }

        return searchResults;
    }

    /**
     * Search the database records for toys with a given minimum age recommendation.
     *
     * @param query The query for the database, an integer, which is the toy's
     *              minimum age recommendation.
     * @return
     */
    public ArrayList<Toy> searchRecords(int age) {

        ArrayList<Toy> searchResults = new ArrayList<Toy>();

        for (Toy toy : this.records) {
            if (toy.getAppropriateAge() >= age)
                searchResults.add(toy);
        }

        return searchResults;
    }

    /**
     * Search the database records for toys of a particular type.
     *
     * @param query The type of toy to search for, one of 'a', 'b', 'f', or 'p'.
     * @return
     */
    public ArrayList<Toy> searchRecords(char query) throws InvalidToyTypeQueryException {

        ArrayList<Toy> searchResults = new ArrayList<Toy>();

        for (Toy toy : this.records) {
            switch (query) {
                case 'A':
                    if (toy instanceof Animal)
                        searchResults.add(toy);
                    break;
                case 'B':
                    if (toy instanceof BoardGame)
                        searchResults.add(toy);
                    break;
                case 'F':
                    if (toy instanceof Figure)
                        searchResults.add(toy);
                    break;
                case 'P':
                    if (toy instanceof Puzzle)
                        searchResults.add(toy);
                    break;
                default: // The view validated the input passed to this function, but if an exception to
                         // that occurs, throw an exception!
                    throw new InvalidToyTypeQueryException();
            }
        }

        return searchResults;
    }

    /**
     * Adds a new toy to the database.
     * 
     * @param newToy a toy of any type.
     */
    public void addRecord(Toy newToy) {
        this.records.add(newToy);
    }

    // TODO: Write a test for this functionality!
    public void removeRecord(Toy existingToy) {
        this.records.remove(existingToy);
    }

    public void purchaseToy(Toy toy) {
        /* If the toy exists in the database (it should always exist in the
        database if we are purchasing, given the program logic) retrieve its
        index in the records array list, obtain the toy itself, althought the
        parameter TOY should already be the same object (in memory). Change its
        count on the shelf. */
        if (this.records.contains(toy)) {
            int indexOfToy = this.records.indexOf(toy);
            Toy record = this.records.get(indexOfToy);
            record.setAvailableCount(record.getAvailableCount() - 1);
        } else {
            throw new ToyExistsInDatabaseException(); // Indicate that the toy does not exist in the database.
        }
    }

    private ArrayList<Toy> toyListFromFile(String filename) throws FileNotFoundException {
        File toys = new File(filename);
        Scanner reader = new Scanner(toys);

        ArrayList<Toy> toyList = new ArrayList<Toy>();

        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] fields;

            int toyType = Integer.parseInt(line.substring(0, 1));

            Toy currentToy = new Animal();

            switch (toyType) {
                case 0:
                case 1:
                    fields = line.split(";", 7);
                    currentToy = new Figure(fields);
                    break;

                case 2:
                case 3:
                    fields = line.split(";", 8);
                    currentToy = new Animal(fields);
                    break;

                case 4:
                case 5:
                case 6:
                    fields = line.split(";", 7);
                    currentToy = new Puzzle(fields);
                    break;

                case 7:
                case 8:
                case 9:
                    fields = line.split(";", 8);
                    currentToy = new BoardGame(fields);
                    break;
            }

            toyList.add(currentToy);
        }

        reader.close();

        return toyList;
    }

    private void toyListToFile(ArrayList<Toy> toyList, String filename) throws FileNotFoundException {
        File toys = new File(filename);
        PrintWriter writer = new PrintWriter(toys); // Helpful, automatic overwriting.

        for (Toy toy : toyList) {
            writer.append(toy.format() + "\n");
        }

        writer.close();
    }

    /**
     * Write the in-memory database back to disk, overwriting any database file that
     * exists (as on-disk data would be out of date).
     *
     * @throws FileNotFoundException if the database is not found on-disk we must
     *                               throw an exception.
     */
    public void writeToDisk() throws FileNotFoundException {
        toyListToFile(this.records, this.FILENAME);
    }
}
