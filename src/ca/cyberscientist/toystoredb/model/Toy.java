/**
 * 
 */
package ca.cyberscientist.toystoredb.model;

/**
 * Abstract class for Toy objects
 * @author Bryce Carson
 *
 */
public abstract class Toy {

	private String serialNumber;
	private String name;
	private String brand;
	private double price;
	private int availableCount;
	private int appropriateAge;

	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return the availableCount
	 */
	public int getAvailableCount() {
		return availableCount;
	}

	/**
	 * @param availableCount the availableCount to set
	 */
	public void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
	}

	/**
	 * @return the appropriateAge
	 */
	public int getAppropriateAge() {
		return appropriateAge;
	}

	/**
	 * @param serialNumber   The serial number to associate with the toy.
	 * @param name           The name to associate with the toy.
	 * @param brand          The brand name (manufacturer) to associate with the
	 *                       toy.
	 * @param price          The cost of the toy in dollars and cents.
	 * @param availableCount The number of this toy in inventory.
	 * @param appropriateAge The minimum recommended age for this toy.
	 */
	public Toy(String serialNumber, String name, String brand, double price, int availableCount, int appropriateAge) {
		this.serialNumber = serialNumber;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.availableCount = availableCount;
		this.appropriateAge = appropriateAge;
	}

	/**
	 * A constructor to use for generating a place-holder Animal.
	 * 
	 */
	public Toy() {
		// Helpfully, this doesn't need to do anything.
	}

	/**
	 * The toString method of all toys, providing a drier way to format each object
	 * for saving to disk. Ends with a semicolon so each toy type can merely append
	 * the next item.
	 * 
	 * @return String the pretty-printable form of the object.
	 */
	@Override
	public String toString() {
		return getSerialNumber() + ";" + getName() + ";" + getBrand() + ";" + getPrice() + ";" + getAvailableCount()
				+ ";" + getAppropriateAge() + ";";
	}

	/**
	 * Ensure that each subclass implements a format method to call the
	 * super.toString() method and append the subclass-specific information for
	 * saving to disk.
	 * 
	 * @return The formatted string which will be used when saving the object to
	 *         disk.
	 */
	public abstract String format();
}
