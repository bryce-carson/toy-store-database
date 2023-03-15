package ca.cyberscientist.toystoredb.exceptions;

/**
 * This class implements an exception to indicate that a string was not of the
 * correct format for conversion to a serial number (a sort of integer).
 */
public class InvalidPriceException extends NumberFormatException {
	private static final long serialVersionUID = 6729263749787865861L;
	/**
	 * Constructor for exception
	 */
	public InvalidPriceException() {
		super();
	}

	/**
	 * Constructor for exception
	 */
	public InvalidPriceException(String message) {
		super(message);
	}

}
