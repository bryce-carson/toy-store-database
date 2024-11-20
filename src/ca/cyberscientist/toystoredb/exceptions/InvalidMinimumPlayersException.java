package ca.cyberscientist.toystoredb.exceptions;

/**
 * This class implements an exception to indicate that a string was not of the
 * correct format for conversion to a serial number (a sort of integer).
 */
public class InvalidMinimumPlayersException extends NumberFormatException {
	/**The universal identification for the serializable form of this exception.
	 * 
	 */
    private static final long serialVersionUID = 4484804861673876932L;
    
    /**
	 * Constructor for exception
	 */
	public InvalidMinimumPlayersException() {
        super();
    }
	
	/**Constructor for exception with a message which can be fetched using the standard .getMessage method of the parent class.
	 * 
	 * @param message The message to display to the user.
	 */
    public InvalidMinimumPlayersException(String message) {
        super(message);
    }

}
