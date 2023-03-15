package ca.cyberscientist.toystoredb.exceptions;

/**
 * This class implements an exception to indicate that a string was not of the
 * correct format for conversion to a serial number (a sort of integer).
 */
public class InvalidSerialNumberException extends NumberFormatException {
    private static final long serialVersionUID = 6638557996982295242L;

	/**
	 * Constructor for exception
	 */
	public InvalidSerialNumberException() {
        // Empty, no-args constructor.
    	super();
    }

	/**
	 * Constructor for exception
	 */
    public InvalidSerialNumberException(String message) {
    	super(message);
    }
    
}
