/**
 *
 */
package org.telokers.model;

import java.text.DecimalFormat;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author trung
 *
 */
public class Product extends AbstractModel {

	public static enum ProductProperty {
		productId,
		category,
		name,
		picture,
		price,
		comment,
		seller
	}

	public Product(String id) {
		super(id);
		entity = new Entity(getKey(id));
	}

	/**
	 * @param entity
	 * @throws Exception
	 */
	public Product(Entity entity) throws Exception {
		super(entity);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -830557849917908150L;

	/* (non-Javadoc)
	 * @see org.telokers.model.AbstractModel#getEntity()
	 */
	@Override
	public Entity getEntity() {
		return entity;
	}

	public String getProductId() {
		return (String) getProperty(ProductProperty.productId);
	}

	public String getProductName() {
		return (String) getProperty(ProductProperty.name);
	}

	public String getCategory() {
		return (String) getProperty(ProductProperty.category);
	}

	public String getPictureUrl() {
		String keyString = (String) getProperty(ProductProperty.picture);
		return "/picture?key=" + keyString;
	}

	public boolean hasPicture() {
		String keyString = (String) getProperty(ProductProperty.picture);
		return keyString != null && keyString.length() > 0;
	}

	public String getPriceString() {
		Double price = (Double) getProperty(ProductProperty.price);
		if (price == null) {
			return "";
		}
		return String.valueOf(new DecimalFormat("#,###00.##").format(price));
	}

	public String getComment() {
		return (String) getProperty(ProductProperty.comment);
	}

	public String getSeller() {
		return (String) getProperty(ProductProperty.seller);
	}

	public void setProductName(String name) {
		setProperty(ProductProperty.name, name);
	}
	public void setCategory(String name) {
		setProperty(ProductProperty.category, name);
	}
	public void setPrice(double p) {
		setProperty(ProductProperty.price, p);
	}
	public void setComment(String comment) {
		setProperty(ProductProperty.comment, comment);
	}
	public void setSeller(String userId) {
		setProperty(ProductProperty.seller, userId);
	}



	/**
	 * @param productId
	 * @return
	 */
	public static Key getKey(String productId) {
		return KeyFactory.createKey(getKind(), productId);
	}

	/**
	 * @return
	 */
	private static String getKind() {
		return Product.class.getSimpleName();
	}

	/**
	 * @param keyString
	 */
	public void setPictureBlobKey(String keyString) {
		setProperty(ProductProperty.picture, keyString);
	}
}
