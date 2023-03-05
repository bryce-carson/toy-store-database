package ca.cyberscientist.toystoredb.exceptions;

/**
 * This class implements an exception to indicate that a string was not of the
 * correct format for conversion to a serial number (a sort of integer).
 */
public class InvalidSerialNumberException extends NumberFormatException {
    public InvalidSerialNumberException() {
        // Empty, no-args constructor.
    }

    // TODO: implement a proper exception constructor.
    public InvalidSerialNumberException(String message) {
    }

}
