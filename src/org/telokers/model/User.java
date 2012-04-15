/**
 *
 */
package org.telokers.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author trung
 *
 */
@PersistenceCapable
public class User extends AbstractModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 8816546167522794945L;

	@PrimaryKey
	private String email;

	@Persistent
	private String password;

	@Persistent
	private String firstName;

	@Persistent
	private String lastName;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


}
