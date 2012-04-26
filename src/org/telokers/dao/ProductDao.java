/**
 *
 */
package org.telokers.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.telokers.model.Product;
import org.telokers.model.Product.ProductProperty;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

/**
 * @author trung
 *
 */
public class ProductDao {

	private static final Logger logger = Logger.getLogger(ProductDao.class.getName());

	/**
	 * @param userId
	 * @return
	 */
	public static List<Product> findByUserId(String userId) {
		List<Product> list = new ArrayList<Product>();
		try {
			Query query = new Query(Product.class.getSimpleName());
			query.addFilter(ProductProperty.seller.toString(), FilterOperator.EQUAL, userId);
			List<Entity> listE = DatastoreServiceFactory.getDatastoreService().prepare(query).asList(FetchOptions.Builder.withDefaults());
			if (listE != null && listE.size() > 0) {
				for (Entity e : listE) {
					list.add(new Product(e));
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to get list of products by user id [" + userId + "]", e);
		}
		return list;
	}

	/**
	 * @param productId
	 * @return
	 */
	public static Product findById(String productId) {
		if (productId == null || productId.length() == 0) {
			return null;
		}
		try {
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Entity e = ds.get(Product.getKey(productId));
			return new Product(e);
		} catch (Exception e) {
			logger.log(Level.FINE, "[" + productId + "] not found due to " + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @param p
	 */
	public static void persist(Product p) {
		DatastoreServiceFactory.getDatastoreService().put(p.getEntity());
	}

	/**
	 * @param productId
	 */
	public static void deleteById(Product p) {
		DatastoreServiceFactory.getDatastoreService().delete(p.getEntity().getKey());
	}

	/**
	 * @return
	 */
	public static List<Product> findAll() {
		List<Product> list = new ArrayList<Product>();
		try {
			Query query = new Query(Product.class.getSimpleName());
			List<Entity> listE = DatastoreServiceFactory.getDatastoreService().prepare(query).asList(FetchOptions.Builder.withDefaults());
			if (listE != null && listE.size() > 0) {
				for (Entity e : listE) {
					list.add(new Product(e));
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to get list of products", e);
		}
		return list;
	}

	/**
	 * @param q
	 * @return
	 */
	public static List<Product> findByKeyword(String q) {

		return null;
	}

}
