package ca.cyberscientist.toystoredb.exceptions;

/**
 * This class implements an exception to indicate that a string was not of the
 * correct format for conversion to a serial number (a sort of integer).
 */
public class InvalidMinimumPlayersException extends NumberFormatException {
    private static final long serialVersionUID = 4484804861673876932L;

	public InvalidMinimumPlayersException() {
        super();
    }

    public InvalidMinimumPlayersException(String message) {
        super(message);
    }

}
