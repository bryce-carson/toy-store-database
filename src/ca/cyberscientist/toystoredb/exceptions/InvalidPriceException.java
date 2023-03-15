package ca.cyberscientist.toystoredb.exceptions;

/**
 * This class implements an exception to indicate that a string was not of the
 * correct format for conversion to a serial number (a sort of integer).
 */
public class InvalidPriceException extends NumberFormatException {
	/**The universal identification for the serializable form of this exception.
	 * 
	 */
	private static final long serialVersionUID = 6729263749787865861L;
	
	/**
	 * Constructor for exception
	 */
	public InvalidPriceException() {
		super();
	}

	/**Constructor for exception with a message which can be fetched using the standard .getMessage method of the parent class.
	 * 
	 * @param message The message to display to the user.
	 */
	public InvalidPriceException(String message) {
		super(message);
	}

}
