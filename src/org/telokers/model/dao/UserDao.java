
/**
 @author tommyquangqthinh
*/

package org.telokers.model.dao;

import java.util.Iterator;

import org.telokers.dao.DataStoreManager;
import org.telokers.model.User;

import com.google.appengine.api.datastore.Entity;

public class UserDao {
	private static final User tempUser = new User("dummy");
	
	public static void persistUser(User user){
		DataStoreManager.instance().persistEntity(user);
	}
	
	public static User findbyUserId(String userId) throws Exception{
		Iterator<Entity> userIterator = DataStoreManager.instance()
									.findEntityByField(tempUser, User.UserProperty.userId.toString(), userId).iterator();
		if(userIterator.hasNext()){
			return new User(userIterator.next());
		}
		else {
			return null;
		}
	}
}

