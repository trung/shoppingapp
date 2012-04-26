/**
 *
 */
package org.telokers.model;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * @author trung
 *
 */
public class ProductKeywords extends AbstractModel {
	/**
	 * @param key
	 */
	public ProductKeywords(Key parentKey, String key) {
		super(key);
		entity = new Entity(getKind(), key, parentKey);
		setId(key);
	}

	/**
	 * @return
	 */
	public static String getKind() {
		return ProductKeywords.class.getSimpleName();
	}

	public void setKeywords(List<String> keywords) {
		setProperty(ProductKeywordsProperty.keywords, keywords);
	}

	public Key getProduct() {
		return entity.getParent();
	}

	/**
	 * @param key
	 */
	private void setId(String key) {
		setProperty(ProductKeywordsProperty.id, key);
	}

	/**
	 * @throws Exception
	 *
	 */
	public ProductKeywords(Entity e) throws Exception {
		super(e);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 4775212815450681721L;

	public static enum ProductKeywordsProperty {
		id,
		products,
		keywords
	}

	/* (non-Javadoc)
	 * @see org.telokers.model.AbstractModel#getEntity()
	 */
	@Override
	public Entity getEntity() {
		return entity;
	}


}
