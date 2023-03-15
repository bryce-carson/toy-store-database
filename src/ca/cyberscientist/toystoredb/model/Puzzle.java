/**
 * 
 */
package ca.cyberscientist.toystoredb.model;

/**
 * Class dealing with subclass Puzzle
 * @author Bryce Carson
 *
 */
public class Puzzle extends Toy {

	private String puzzleType;

	/**
	 * The puzzle type (such as a mechanical or logical puzzle).
	 * 
	 * @return the sort of puzzle this is.
	 *
	 */
	public String getPuzzleType() {
		return this.puzzleType;
	}

	/**
	 * @param serialNumber   The serial number to associate with the puzzle.
	 * @param name           The name to associate with the puzzle.
	 * @param brand          The brand name (manufacturer) to associate with the
	 *                       puzzle.
	 * @param price          The cost of the puzzle in dollars and cents.
	 * @param availableCount The number of this puzzle in inventory.
	 * @param appropriateAge The minimum recommended age for this puzzle.
	 * @param puzzleType     The sort of puzzle that this is.
	 */
	public Puzzle(String serialNumber, String name, String brand, double price, int availableCount, int appropriateAge,
			String puzzleType) {
		super(serialNumber, name, brand, price, availableCount, appropriateAge);
		this.puzzleType = puzzleType;
	}

	/**
	 * Create a puzzle from a string array. Wraps the more complex constructor.
	 * 
	 * @param fields The record from the on-disk "database" to create a puzzle from.
	 */
	public Puzzle(String[] fields) {
		this(fields[0], fields[1], fields[2], Double.parseDouble(fields[3]), Integer.parseInt(fields[4]),
				Integer.parseInt(fields[5]), fields[6]);
	}

	/**
	 * @return the string format of the puzzle suitable for logic purposes
	 */
	@Override
	public String toString() {
		return super.toString() + getPuzzleType();
	}

	/**
	 * @return the string format of the puzzle suitable for on-disk storage.
	 */
	@Override
	public String format() {
		return super.toString() + getPuzzleType();
	}
}
