
/**
 @author tommyquangqthinh
*/

package org.telokers.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.telokers.model.User;
import org.telokers.model.User.UserProperty;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Transaction;

public class UserDao {

	private static final Logger logger = Logger.getLogger(UserDao.class.getName());

	public static void persistUser(User user){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = ds.beginTransaction();
		try {
			ds.put(user.getEntity());
			tx.commit();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Can't persist user");
			tx.rollback();
		}
	}

	public static User findbyUserId(String userId) {
		if (userId == null || userId.length() == 0) {
			return null;
		}
		try {
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Entity e = ds.get(User.getKey(userId));
			return new User(e);
		} catch (Exception e) {
			logger.log(Level.FINE, "[" + userId + "] not found due to " + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @param key
	 * @return
	 */
	public static User findBySession(String sessionId) {
		try {
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Query query = new Query(User.getKind());
			query.addFilter(UserProperty.sessionId.toString(), FilterOperator.EQUAL, sessionId);
			PreparedQuery pq = ds.prepare(query);
			Entity e = pq.asSingleEntity();
			if (e == null) {
				return null;
			} else {
				return new User(e);
			}
		} catch (Exception e) {
			logger.log(Level.FINE, "[" + sessionId + "] not found due to " + e.getMessage(), e);
			return null;
		}
	}
}

