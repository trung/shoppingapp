/**
 *
 */
package org.telokers.exception;

/**
 * @author trung
 *
 */
public class ValidationException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 248062987748054838L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

}
