/**
 *
 */
package org.telokers.model;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 *
 * Base model
 *
 * @author trung
 *
 */

public abstract class AbstractModel implements Serializable {
	private Entity entity;
	
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
