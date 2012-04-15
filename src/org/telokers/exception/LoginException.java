/**
 *
 */
package org.telokers.exception;

/**
 * @author trung
 *
 */
public class LoginException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 248062987748054838L;

	public LoginException() {
		super();
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginException(String message) {
		super(message);
	}

	public LoginException(Throwable cause) {
		super(cause);
	}

}
