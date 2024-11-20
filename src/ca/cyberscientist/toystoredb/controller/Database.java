/**
 *
 */
package ca.cyberscientist.toystoredb.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

// Import all of the toy stuff
import ca.cyberscientist.toystoredb.model.Toy;
import ca.cyberscientist.toystoredb.model.Animal;
import ca.cyberscientist.toystoredb.model.BoardGame;
import ca.cyberscientist.toystoredb.model.Figure;
import ca.cyberscientist.toystoredb.model.Puzzle;

/**
 * @author Bryce Carson, Koddy Rae Madriaga
 *
 */
public class Database {
    /**A magic character used to determine which branch of the program to execute. Using this char causes one otherwise identically named constructors to be used.
     * It could be anything, as long as the logic checks for whatever value is assigned to it. 
     */
    public final char SEARCH_BY_SERIAL_NUMBER = 's';
    
    /**A magic character used to determine which branch of the program to execute. Using this char causes one otherwise identically named constructors to be used.
     * It could be anything, as long as the logic checks for whatever value is assigned to it. 
     */
    public final char SEARCH_BY_TOY_NAME = 'n';

    /**The default filename to use. This filename is used when the application is running inside the Eclipse console, and when it has not been exported to a runnable JAR.
     * 
     */
    public final String DEFAULT_FILENAME = "res/toys.txt";
    // NOTE: this is the filename we use when we are exporting a runnable JAR file
    // instead of the local filesystem path above.
    // private final String FILENAME = "/toys.txt";

    /**The toylist which will hold the toys for the whole program. This is the in-memory database.
     * 
     */
    private ArrayList<Toy> records = new ArrayList<Toy>();

    /**
     * Instantiate the database by reading it from disk.
     *
     */
    public Database() {
        this.records = toyListFromFile(this.DEFAULT_FILENAME);
    }


    /**Instatiate a database from an arbitrary file form disk.
     * 
     * @param filename The path and name of the filename to create a database from.
     */
    public Database(String filename) {
        this.records = toyListFromFile(filename);
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
     * @return searchResult An arraylist of toys that include the toys following the
     *         provided search method
     */
    public ArrayList<Toy> searchRecords(String query, char searchMethod) {

        ArrayList<Toy> searchResults = new ArrayList<Toy>();

        iterateOverRecords: for (Toy toy : this.records) {
            // Dispatch based on the method of the search (by name, type, or serial number).
            switch (searchMethod) {
                // Searching by serial number; utilizes a named loop to break from the loop
                // cleanly rather than using an early return.
                case 's':
                    if (toy.getSerialNumber().equals(query)) {
                        searchResults.add(toy);
                        break iterateOverRecords;
                    }

                    break;

                // Searching by name: remove the toys that don't contain the search query from
                // the list of results we will be returning.
                case 'n':
                    if (toy.getName().toUpperCase().contains(query.toUpperCase()))
                        searchResults.add(toy);

                    break;
            }
        }

        return searchResults;
    }

    /**
     * Search the database records for toys with a given minimum age recommendation.
     *
     * @param age The minimum age requirement of the toy, in years.
     * @return searchResult The arraylist of toys that are searched through the
     *         appropriate age field
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
     * @return toylist The toy list containing all the toys that came from the
     *         search method
     */
    public ArrayList<Toy> searchRecords(char query) {

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

    /**
     * Removes an existing toy from the database
     *
     * @param existingToy a toy from the list
     */
    public void removeRecord(Toy existingToy) {
        this.records.remove(existingToy);
    }

    /**
     * Purchases a toy from the database, decrementing it's available count if
     * the count is one or more.
     *
     * @param toy The toy that we are trying to buy
     */
    public void purchaseToy(Toy toy) {
        /*
         * If the toy exists in the database (it should always exist in the
         * database if we are purchasing, given the program logic) retrieve its
         * index in the records array list, obtain the toy itself, although the
         * parameter TOY should already be the same object (in memory). Change its
         * count on the shelf.
         */
        if (this.records.contains(toy)) {
            int indexOfToy = this.records.indexOf(toy);
            Toy record = this.records.get(indexOfToy);
            record.setAvailableCount(record.getAvailableCount() - 1);
        }
    }

    /**
     * Method to create and return an arraylist from a file
     *
     * @param filename The input file containing the toylist on disk
     * @return toyList The arraylist of toys
     *
     */
    private ArrayList<Toy> toyListFromFile(String filename) {
        // NOTE: when exporting as a runnable JAR, we must consume the file as a
        // streaming resource, not a file on the file system.
        File toys = new File(filename);
        ArrayList<Toy> toyList = new ArrayList<>();
        // InputStream toysResourceStream = getClass().getResourceAsStream(filename);
        // InputStreamReader toys = new InputStreamReader(toysResourceStream);

        try (Scanner reader = new Scanner(toys)) {

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            System.out.println("The file is not writable or readable," +
                    "and the program likely doesn't have the ncessary " +
                    "access rights on the system.\n" +
                    "Abnormal exit.");
            System.exit(1);
        }

        return toyList;

    }

    /**
     * Method to append the toylist to the file
     *
     * @param toyList  The toy list that is being added to the file
     * @param filename The file that the toy list is being added to
     */
    private void toyListToFile(ArrayList<Toy> toyList, String filename) {
        File toys = new File(filename);

        try {
            PrintWriter writer = new PrintWriter(toys);

            for (Toy toy : toyList) {
                writer.append(toy.format() + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Database not updated on disk.");
            e.printStackTrace();
        } catch (SecurityException e) {
            System.out.println("The file is not writable or readable," +
                    "and the program likely doesn't have the ncessary " +
                    "access rights on the system.\n" +
                    "Abnormal exit.");
            System.exit(1);
        }

    }

    /**
     * Write the in-memory database back to disk, overwriting any database file that
     * exists (as on-disk data would be out of date).
     *
     * @param filename The filename that the database will be written to.
     */
    public void writeToDisk(String filename) {
        toyListToFile(this.records, filename);
    }
}
