/**
 * 
 */
package ca.cyberscientist.toystoredb.exceptions;

/**TODO: write the exception to describe the problem encountered by the program.
 * @author Bryce Carson
 *
 */
public class InvalidSearchMethodException extends IllegalArgumentException {

	private static final long serialVersionUID = -5275208059814049166L;

	/**
	 * 
	 */
	public InvalidSearchMethodException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 */
	public InvalidSearchMethodException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public InvalidSearchMethodException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidSearchMethodException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
