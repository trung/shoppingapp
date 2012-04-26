/**
 *
 */
package org.telokers.dao;

import org.telokers.model.Comment;

import com.google.appengine.api.datastore.DatastoreServiceFactory;

/**
 * @author trung
 *
 */
public class CommentDao {

	/**
	 * @param comment
	 */
	public static void persist(Comment comment) {
		DatastoreServiceFactory.getDatastoreService().put(comment.getEntity());
	}

}
