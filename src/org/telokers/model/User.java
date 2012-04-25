/**
 *
 */
package org.telokers.model;



import com.google.appengine.api.datastore.Entity;


/**
 * @author trung
 *
 */

public class User {

	public static enum UserProperty {
		email,
		userId,
	}

	private Entity entity;

	public User() {
		entity = new Entity(User.class.getSimpleName());
	}

	public void setProperty(UserProperty propertyName, Object value) {
		entity.setProperty(propertyName.toString(), value);
	}

	public Object getProperty(UserProperty propertyName) {
		return entity.getProperty(propertyName.toString());
	}
}
