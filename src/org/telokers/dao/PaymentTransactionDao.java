/**
 *
 */
package org.telokers.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.telokers.model.PaymentTransaction;
import org.telokers.model.PaymentTransaction.PaymentTransactionProperty;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

/**
 * @author trung
 *
 */
public class PaymentTransactionDao {

	private static final Logger logger = Logger.getLogger(PaymentTransactionDao.class.getName());

	/**
	 * @param pt
	 */
	public static void persis(PaymentTransaction pt) {
		DatastoreServiceFactory.getDatastoreService().put(pt.getEntity());
	}

	/**
	 * @param userId
	 * @return
	 */
	public static List<PaymentTransaction> findByUserId(String userId) {
		List<PaymentTransaction> list = new ArrayList<PaymentTransaction>();
		try {
			Query query = new Query(PaymentTransaction.getKind());
			query.addFilter(PaymentTransactionProperty.userId.toString(), FilterOperator.EQUAL, userId);
			List<Entity> listE = DatastoreServiceFactory.getDatastoreService().prepare(query).asList(FetchOptions.Builder.withDefaults());
			if (listE != null && listE.size() > 0) {
				for (Entity e : listE) {
					list.add(new PaymentTransaction(e));
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to get list of products by user id [" + userId + "]", e);
		}
		return list;
	}

}
