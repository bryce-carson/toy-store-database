/**
 * 
 */
package ca.cyberscientist.toystoredb.exceptions;

/**TODO: write the exception to describe the problem encountered by the program.
 * @author Bryce Carson
 *
 */
public class InvalidToyTypeQueryException extends IllegalArgumentException {

	private static final long serialVersionUID = -5362925029673584095L;

	/**
	 * 
	 */
	public InvalidToyTypeQueryException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 */
	public InvalidToyTypeQueryException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public InvalidToyTypeQueryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidToyTypeQueryException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
