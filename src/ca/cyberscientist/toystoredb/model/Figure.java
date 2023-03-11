/**
 * 
 */
package ca.cyberscientist.toystoredb.model;

/**
 * A class which models figures, such as action figures, dolls, or historical
 * figures.
 * 
 * @author Bryce Carson
 *
 */
public class Figure extends Toy {

	String classification;

	/**
	 * Acquire the classification of the figurine, such as action figure, doll, or
	 * historical figure.
	 * 
	 * @return the classification of the figure.
	 */
	public String getClassification() {
		return this.classification;
	}

	/**
	 * @param serialNumber   The serial number to associate with the figure.
	 * @param name           The name to associate with the figure.
	 * @param brand          The brand name (manufacturer) to associate with the
	 *                       figure.
	 * @param price          The cost of the figure in dollars and cents.
	 * @param availableCount The number of this figure in inventory.
	 * @param appropriateAge The minimum recommended age for this figure.
	 * @param classification The sort of figurine this is.
	 */
	public Figure(String serialNumber, String name, String brand, double price, int availableCount, int appropriateAge,
			String classification) {
		super(serialNumber, name, brand, price, availableCount, appropriateAge);
		this.classification = classification;
	}

	/**
	 * Create a figurine from a string array. Wraps the more complex constructor.
	 * 
	 * @param fields The record from the on-disk "database" to create a figure from.
	 */
	public Figure(String[] fields) {
		this(fields[0],
				fields[1],
				fields[2],
				Double.parseDouble(fields[3]),
				Integer.parseInt(fields[4]),
				Integer.parseInt(fields[5]),
				fields[6]);
	}

	@Override
	public String toString() {
		return super.toString() + getClassification();
	}

	@Override
	public String format() {
		return super.toString() + getClassification();
	}
}
