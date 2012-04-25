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
}
