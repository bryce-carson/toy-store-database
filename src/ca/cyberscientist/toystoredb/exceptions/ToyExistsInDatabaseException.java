/**
 * 
 */
package ca.cyberscientist.toystoredb.exceptions;

/**
 * @author Bryce Carson
 *
 */
public class ToyExistsInDatabaseException extends RuntimeException {

	private static final long serialVersionUID = 4494733024682303464L;

	/**
	 * 
	 */
	public ToyExistsInDatabaseException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ToyExistsInDatabaseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ToyExistsInDatabaseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ToyExistsInDatabaseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ToyExistsInDatabaseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
