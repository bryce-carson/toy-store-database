/**
 * 
 */
package ca.cyberscientist.toystoredb.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import ca.cyberscientist.toystoredb.exceptions.InvalidToyTypeQueryException;
import ca.cyberscientist.toystoredb.exceptions.InvalidSearchMethodException;

/**
 * @author Bryce Carson
 *
 */
public class Database {
	
	private final String FILENAME = "res/toys.txt";
	
	private ArrayList<Toy> records = new ArrayList<Toy>();

	/**Instantiate the database by reading it from disk.
	 * 
	 * @throws FileNotFoundException if the database is not found on-disk we must throw an exception.
	 */
	public Database() throws FileNotFoundException {
		this.records = toyListFromFile(this.FILENAME);
	}
	
	/**Search the database records for a name matching the given string (using the String.contains() method) or an exact serial number.
	 * 
	 * @param query The query for the database, a String, which is either toy's serial number or string to be matched against the toy's name.  
	 * @param searchMethod a character indicating whether the method to use is the serial number or name search methodology, either 's' or 'n'.
	 * @return
	 */
	public ArrayList<Toy> searchRecords(String query, char searchMethod) throws InvalidSearchMethodException {
		
		ArrayList<Toy> searchResults = new ArrayList<Toy>();
		
		iterateOverRecords: for(Toy toy : this.records) {
			// Dispatch based on the method of the search (by name, type, or serial number).
			switch(searchMethod) {
			case 's': // Searching by serial number; utilizes an early return.
				if(toy.getSerialNumber().equals(query)) {
					searchResults.add(toy);
					break iterateOverRecords;
				}
				
				break;
				
			case 'n': // Searching by name: remove the toys that don't contain the search query from the list of results we will be returning.
				if(toy.getName().toUpperCase().contains(query.toUpperCase()))
					searchResults.add(toy);
				
				break;
			
			default:
				throw new InvalidSearchMethodException();
			}
		}
		
		return searchResults;
	}
	
	/**Search the database records for toys of a particular type.
	 * 
	 * @param query The type of toy to search for, one of 'a', 'b', 'f', or 'p'.
	 * @return
	 */
	public ArrayList<Toy> searchRecords(char query) throws InvalidToyTypeQueryException {
		
		ArrayList<Toy> searchResults = new ArrayList<Toy>();
		
		for(Toy toy : this.records) {
			switch(query) {
			case 'a':
				if(toy instanceof Animal)
					searchResults.add(toy);
				break;
			case 'b':
				if(toy instanceof BoardGame)
					searchResults.add(toy);
				break;
			case 'f':
				if(toy instanceof Figure)
					searchResults.add(toy);
				break;
			case 'p':
				if(toy instanceof Puzzle)
					searchResults.add(toy);
				break;
			default: // The view validated the input passed to this function, but if an exception to that occurs, throw an exception!
				throw new InvalidToyTypeQueryException();
			}
		}
		
		return searchResults;
	}
	
	/**Adds a new toy to the database.
	 * @param newToy a toy of any type.
	 */
	public void addRecord(Toy newToy) {
		this.records.add(newToy);
	}
	
	private ArrayList<Toy> toyListFromFile(String filename) throws FileNotFoundException {
		File toys = new File(filename);
		Scanner reader = new Scanner(toys);
		
		ArrayList<Toy> toyList = new ArrayList<Toy>();
		
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] fields;
			
			int toyType = Integer.parseInt(line.substring(0, 1));
			
			Toy currentToy = new Animal();
			
			switch(toyType) {
			case 0: case 1:
				fields = line.split(";", 7);
				currentToy = new Figure(fields);
				break;
				
			case 2: case 3:
				fields = line.split(";", 8);
				currentToy = new Animal(fields);
				break;
				
			case 4: case 5: case 6:
				fields = line.split(";", 7);
				currentToy = new Puzzle(fields);
				break;
				
			case 7: case 8: case 9:
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
		
		for(Toy toy : toyList) {
			writer.append(toy.format() + "\n");
		}
		
		writer.close();
	}
	
	/**Write the in-memory database back to disk, overwriting any database file that exists (as on-disk data would be out of date).
	 * 
	 * @throws FileNotFoundException if the database is not found on-disk we must throw an exception.
	 */
	public void writeToDisk() throws FileNotFoundException {
		toyListToFile(this.records, this.FILENAME);
	}
}
