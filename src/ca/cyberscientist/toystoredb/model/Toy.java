/**
 * 
 */
package ca.cyberscientist.toystoredb.model;

/** 
 * @author Bryce Carson
 *
 */
public abstract class Toy {

	private int serialNumber;
	private String name;
	private String brand;
	private double price;
	private int availableCount;
	private int appropriateAge;
	
	/**
	 * @return the serialNumber
	 */
	
	public int getSerialNumber() {
		return serialNumber;
	}
	
	/**
	 * @param serialNumber the serialNumber to set
	 */
	
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
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
	 * @param appropriateAge the appropriateAge to set
	 */
	public void setAppropriateAge(int appropriateAge) {
		this.appropriateAge = appropriateAge;
	}
	
	// TODO Ensure that every sub-class' constructor uses these fields; this is done with an abstract constructor, right?
}
