/**
 *
 */
package org.telokers.dao;

import org.telokers.model.PaymentTransaction;

import com.google.appengine.api.datastore.DatastoreServiceFactory;

/**
 * @author trung
 *
 */
public class PaymentTransactionDao {

	/**
	 * @param pt
	 */
	public static void persis(PaymentTransaction pt) {
		DatastoreServiceFactory.getDatastoreService().put(pt.getEntity());
	}

}
