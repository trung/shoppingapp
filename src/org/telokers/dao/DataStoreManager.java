/**
 *
 */
package org.telokers.dao;

import java.util.List;

import javax.jdo.Transaction;

import org.telokers.model.AbstractModel;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * Single util class for all database/transaction related operations
 *
 * @author trung
 *
 */
public class DataStoreManager {


	private static DataStoreManager singleton = null;
	
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	private DataStoreManager() {

	}

	public static DataStoreManager instance() {
		if (singleton == null) {
			singleton = new DataStoreManager();
		}

		return singleton;
	}

	/**
	 * Get object by key
	 * @param <T>
	 * @param clazz
	 * @param key
	 * @return
	 */
	public <T extends AbstractModel> T get(Class<T> clazz, Long id) {
		return PMF.instance().getPersistenceManager().getObjectById(clazz, id);
	}

	/**
	 *
	 * @param <T>
	 * @param object
	 * @return
	 */
	public <T> T save(T object) {
		return PMF.instance().getPersistenceManager().makePersistent(object);
	}

	public void flush() {
		PMF.instance().getPersistenceManager().flush();
	}

	/**
	 * Convienent method to delete records for a particular kind, limit to 1000 records
	 * @param clazz
	 */
	public void removeAll(Class<?> clazz) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(clazz.getSimpleName());
		PreparedQuery pq = ds.prepare(q);
		List<Entity> list = pq.asList(FetchOptions.Builder.withLimit(1000));
		if (list != null) {
			for (Entity e : list) {
				ds.delete(e.getKey());
			}
		}
	}

	/**
	 *
	 */
	public void beginTransaction() {
		PMF.instance().getPersistenceManager().currentTransaction().begin();
	}

	public void rollbackIfAlive() {
		Transaction currentTransaction = PMF.instance().getPersistenceManager().currentTransaction();
		if (currentTransaction.isActive()) {
			currentTransaction.rollback();
		}
	}

	/**
	 *
	 */
	public void commitTransaction() {
		PMF.instance().getPersistenceManager().currentTransaction().commit();
	}
	
	public DatastoreService getDatastore(){
		return datastore;
	}

}
