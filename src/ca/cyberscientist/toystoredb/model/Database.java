/**
 * 
 */
package ca.cyberscientist.toystoredb.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Bryce Carson
 *
 */
public class Database {
	
	private final String FILENAME = "res/toys.txt";
	
	private ArrayList<Toy> records = new ArrayList<Toy>();

	public Database() throws FileNotFoundException {
		this.records = toyListFromFile(FILENAME);
	}
	
	/**A simple getter for the array list "records".
	 * @return the array list holding all the in-memory representations of the records on-disk.
	 */
	public ArrayList<Toy> getRecords() {
		return this.records;
	}
	
	private ArrayList<Toy> toyListFromFile(String filename) throws FileNotFoundException {
		File toys = new File(filename);
		Scanner reader = new Scanner(toys);
		
		ArrayList<Toy> toyList = new ArrayList<Toy>();
		
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] fields;
			
			int toyType = Integer.parseInt(line.substring(0, 0));
			
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
	
	public void writeToDisk() throws FileNotFoundException {
		this.toyListToFile(records, FILENAME);
	}
}
