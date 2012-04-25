/**
 *
 */
package org.telokers.model;



import com.google.appengine.api.datastore.Entity;


/**
 * @author trung
 *
 */

public class User extends AbstractModel{

	public static enum UserProperty {
		name,
		email,
		userId,
		password,
		sessionId
	}

	public User(String userId) {
		super(userId);
		entity = new Entity(User.class.getSimpleName(), userId);
		setUserId(userId);
	}

	public User(Entity entity) throws Exception{
		super(entity);
	}


	public void setProperty(UserProperty propertyName, Object value) {
		entity.setProperty(propertyName.toString(), value);
	}

	public Object getProperty(UserProperty propertyName) {
		return entity.getProperty(propertyName.toString());
	}

	public void setPassword(String password) {
		setProperty(UserProperty.password, password);
	}

	public String getPassword() {
		return (String) getProperty(UserProperty.password);
	}

	public void setUserSessionId(String sid) {
		setProperty(UserProperty.sessionId, sid);
	}

	public void getUserSessionId(String sid) {
		setProperty(UserProperty.sessionId, sid);
	}

	public String getName() {
		return (String) getProperty(UserProperty.name);
	}

	public void setName(String name) {
		setProperty(UserProperty.name, name);
	}

	public String getUserId() {
		return (String) getProperty(UserProperty.userId);
	}

	private void setUserId(String userId) {
		setProperty(UserProperty.userId, userId);
	}

	public String getEmail() {
		return (String) getProperty(UserProperty.email);
	}

	public void setEmail(String email) {
		setProperty(UserProperty.email, email);
	}

	@Override
	public Entity getEntity(){
		return this.entity;
	}

}
