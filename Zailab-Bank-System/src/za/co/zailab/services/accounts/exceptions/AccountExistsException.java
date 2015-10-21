/**
 * 
 */
package za.co.zailab.services.accounts.exceptions;

/**
 * @author Lawrence
 *
 */
public class AccountExistsException extends Exception {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 1058352995371279653L;

	/**
	 * Message to the shown to the user if the account is not found.
	 * 
	 * @param message
	 */
	public AccountExistsException(String message) {
		super(message);
	}
}
