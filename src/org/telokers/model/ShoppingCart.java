/**
 *
 */
package org.telokers.model;

import java.util.ArrayList;
import java.util.List;

import org.telokers.service.utils.MiscUtils;

import com.google.appengine.api.datastore.Entity;

/**
 * @author trung
 *
 */
public class ShoppingCart extends AbstractModel {

	public static enum ShoppingCartProperty {
		userId,
		products
	}

	public ShoppingCart(String userId) {
		super(userId);
		entity = new Entity(getKind(), userId);
		setUserId(userId);
	}


	private double totalPrice = 0f;

	/**
	 * @param userId
	 */
	private void setUserId(String userId) {
		setProperty(ShoppingCartProperty.userId, userId);
	}

	public void addProduct(String pid) {
		List<String> list = getProductIds();
		list.add(pid);
		setProperty(ShoppingCartProperty.products, list);
	}

	public List<String> getProductIds() {
		List<String> list = (List<String>) getProperty(ShoppingCartProperty.products);
		if (list == null) {
			list = new ArrayList<String>();
		}
		return list;
	}

	public int countProducts() {
		return getProductIds().size();
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the totalPrice
	 */
	public String getTotalPriceString() {
		return MiscUtils.formatPrice(totalPrice);
	}

	/**
	 * @param entity
	 * @throws Exception
	 */
	public ShoppingCart(Entity entity) throws Exception {
		super(entity);
	}

	/* (non-Javadoc)
	 * @see org.telokers.model.AbstractModel#getEntity()
	 */
	@Override
	public Entity getEntity() {
		return entity;
	}

	public static String getKind() {
		return ShoppingCart.class.getSimpleName();
	}

	/**
	 * @param productId
	 */
	public void removeProduct(String productId) {
		List<String> list = getProductIds();
		list.remove(productId);
		setProperty(ShoppingCartProperty.products, list);
	}

}
