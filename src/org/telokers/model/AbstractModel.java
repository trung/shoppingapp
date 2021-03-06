/**
 *
 */
package org.telokers.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Entity;

/**
 *
 * Base model
 *
 * @author trung
 *
 */

public abstract class AbstractModel implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 4331406434638858032L;

	protected Entity entity;

	public AbstractModel(String key) {

	}

	public AbstractModel(Entity entity) throws Exception{
		if (entity.getKind().equals(this.getClass().getSimpleName())){
			this.entity = entity;
		}
		else throw new Exception ("Incompatible Entity kind");
	}

	public abstract Entity getEntity();

	public void setProperty(Enum<?> propertyName, Object value) {
		entity.setProperty(propertyName.toString(), value);
	}

	public Object getProperty(Enum<?> propertyName) {
		return entity == null ? null : entity.getProperty(propertyName.toString());
	}

}
