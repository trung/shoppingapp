/**
 *
 */
package org.telokers.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.telokers.model.Comment;
import org.telokers.model.Comment.CommentProperty;
import org.telokers.model.Product;
import org.telokers.model.Product.ProductProperty;
import org.telokers.model.ProductKeywords;
import org.telokers.model.ProductKeywords.ProductKeywordsProperty;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Transaction;

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

	public static Product findByKey(Key key) {
		try {
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Entity e = ds.get(key);
			return new Product(e);
		} catch (Exception e) {
			logger.log(Level.FINE, "[" + key + "] not found due to " + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @param p
	 */
	public static void persist(Product p) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = ds.beginTransaction();
		try {
			Key k = ds.put(p.getEntity());
			ProductKeywords keywords = new ProductKeywords(k, UUID.randomUUID().toString());
			List<String> kw = new ArrayList<String>();
			parse(kw, p.getProductName().toLowerCase());
			parse(kw, p.getCategory().toLowerCase());
			parse(kw,p.getPriceString());
			parse(kw,p.getSeller().toLowerCase());
			parse(kw, p.getPostedDateString());
			keywords.setKeywords(kw);
			ds.put(keywords.getEntity());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}

	}

	/**
	 * @param kw
	 * @param postedDateString
	 */
	private static void parse(List<String> kw, String data) {
		kw.addAll(Arrays.asList(data.split("\\s")));
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
		Query query = new Query(ProductKeywords.getKind());
		String[] qa = q.split("\\s");
		List<String> queries = new ArrayList<String>();
		for (String qq : qa) {
			if (qq.length() > 0) {
				queries.add(qq.toLowerCase());
			}
			if (queries.size() > 30) {
				break;
			}
		}
		query.addFilter(ProductKeywordsProperty.keywords.toString(), FilterOperator.IN, queries);
		query.setKeysOnly();
		List<Entity> keys = DatastoreServiceFactory.getDatastoreService().prepare(query).asList(FetchOptions.Builder.withDefaults());
		if (keys == null || keys.size()  == 0) {
			return null;
		}
		List<Product> list = new ArrayList<Product>();
		for (Entity e : keys) {
			list.add(findByKey(e.getParent()));
		}
		return list;
	}

	/**
	 * @param productId
	 * @return
	 */
	public static List<Comment> getComments(String productId) {
		try {
			Query query = new Query(Comment.getKind());
			query.addFilter(CommentProperty.productId.toString(), FilterOperator.EQUAL, productId);
			List<Entity> e = DatastoreServiceFactory.getDatastoreService().prepare(query).asList(FetchOptions.Builder.withDefaults());
			if (e == null || e.size() == 0) {
				return new ArrayList<Comment>();
			}
			List<Comment> list = new ArrayList<Comment>();
			for (Entity ee : e) {
				list.add(new Comment(ee));
			}
			Collections.sort(list, new Comparator<Comment>() {
				/* (non-Javadoc)
				 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
				 */
				@Override
				public int compare(Comment o1, Comment o2) {
					return o2.getCreatedDate().compareTo(o1.getCreatedDate());
				}
			});
			return list;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Can't get comments",e);
			return new ArrayList<Comment>();
		}
	}

}
