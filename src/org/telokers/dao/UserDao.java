
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

public class UserDao {

	private static final Logger logger = Logger.getLogger(UserDao.class.getName());

	public static void persistUser(User user){
		DatastoreServiceFactory.getDatastoreService().put(user.getEntity());
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
			return new User(e);
		} catch (Exception e) {
			logger.log(Level.FINE, "[" + sessionId + "] not found due to " + e.getMessage(), e);
			return null;
		}
	}
}

