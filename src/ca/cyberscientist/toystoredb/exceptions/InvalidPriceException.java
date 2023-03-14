package ca.cyberscientist.toystoredb.exceptions;

/**
 * This class implements an exception to indicate that a string was not of the
 * correct format for conversion to a serial number (a sort of integer).
 */
public class InvalidPriceException extends NumberFormatException {
	public InvalidPriceException() {
		super();
	}

	public InvalidPriceException(String message) {
		super(message);
	}

}
