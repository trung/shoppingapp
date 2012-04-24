/**
 *
 */
package org.telokers.model;


import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * @author trung
 *
 */

@Entity
public class User extends AbstractModel {
	
	@Id
	private String email;


	private String password;

	
	private String firstName;


	private String lastName;
	
	private String userKey;
	
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

	public void setUserKey(String key) {
		this.userKey = key;
	}

	public String getUserKey() {
		return userKey;
	}

}
