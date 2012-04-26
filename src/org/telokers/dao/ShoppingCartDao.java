/**
 *
 */
package org.telokers.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.telokers.model.ShoppingCart;
import org.telokers.model.ShoppingCart.ShoppingCartProperty;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

/**
 * @author trung
 *
 */
public class ShoppingCartDao {


	private static final Logger logger = Logger.getLogger(ShoppingCartDao.class.getName());
	/**
	 * @param userId
	 * @return
	 */
	public static ShoppingCart findByUserId(String userId) {
		try {
			Query q = new Query(ShoppingCart.getKind());
			q.addFilter(ShoppingCartProperty.userId.toString(), FilterOperator.EQUAL, userId);
			Entity e = DatastoreServiceFactory.getDatastoreService().prepare(q).asSingleEntity();
			if (e == null) {
				return null;
			}
			return new ShoppingCart(e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Can't get cart", e);
			return null;
		}
	}
	/**
	 * @param cart
	 */
	public static void persist(ShoppingCart cart) {
		DatastoreServiceFactory.getDatastoreService().put(cart.getEntity());
	}

}
