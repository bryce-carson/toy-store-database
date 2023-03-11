/**
 * 
 */
package ca.cyberscientist.toystoredb.model;

/**
 * @author Bryce Carson
 *
 */
public class Animal extends Toy {

	private String material;
	private String size;

	/**
	 * Acquire the size of the animal.
	 * 
	 * @return the size (small, medium, large, etc.) of the animal.
	 */
	public String getSize() {
		return this.size;
	}

	/**
	 * Acquire the material the animal is made out of.
	 * 
	 * @return the text description of the material (such as wood, or fabric) the
	 *         animal is made from.
	 */
	public String getMaterial() {
		return this.material;
	}

	/**
	 * @param serialNumber   The serial number to associate with the animal.
	 * @param name           The name to associate with the animal.
	 * @param brand          The brand name (manufacturer) to associate with the
	 *                       animal.
	 * @param price          The cost of the animal in dollars and cents.
	 * @param availableCount The number of this animal in inventory.
	 * @param appropriateAge The minimum recommended age for this animal.
	 * @param material       A text description of the animal's fabrication
	 *                       material.
	 * @param size           The size of the animal.
	 */
	public Animal(String serialNumber, String name, String brand, double price, int availableCount, int appropriateAge,
			String material, String size) {
		super(serialNumber, name, brand, price, availableCount, appropriateAge);
		this.material = material;
		this.size = size;
	}

	/**
	 * Create an animal from a string array. Wraps the more complex constructor.
	 * 
	 * @param fields The record from the on-disk "database" to create an animal
	 *               from.
	 */
	public Animal(String[] fields) {
		this(fields[0],
				fields[1],
				fields[2],
				Double.parseDouble(fields[3]),
				Integer.parseInt(fields[4]),
				Integer.parseInt(fields[5]),
				fields[6],
				fields[7]);
	}

	/**
	 * A constructor used to create a place-holding animal, which avoids
	 * initializing the currentToy with null.
	 * 
	 */
	public Animal() {
		super();
		// The constructor doesn't need to do anything, helpfully.
	}

	@Override
	public String toString() {
		return super.toString() + getMaterial() + ";" + getSize();
	}

	@Override
	public String format() {
		return super.toString() + getMaterial() + ";" + getSize();
	}
}
