package org.telokers.model.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.telokers.dao.EMF;
import org.telokers.model.User;
import org.telokers.servlet.security.SecurityFilter;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class JpaUserDao {
	private static final Logger logger = Logger.getLogger(JpaUserDao.class.getName());
	
	private final String queryByUserKey = "Select from User where userKey = ?1";
	private final String queryByUserName = "Select from User where email = ?1";
	
	private static class Holder {
		static JpaUserDao instance = new JpaUserDao();
	}
	
	public static JpaUserDao instance(){
		return Holder.instance;
	}
	
	public User findByKey(String userKey){
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query query = em.createQuery(queryByUserKey);
			query.setParameter(1, userKey);
			return (User)query.getSingleResult();
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Error retreiving single result");
		}
		finally {
			em.close();
		}
		return null;
	}
	
	public void persistUser(User user){
		EntityManager em = EMF.get().createEntityManager();
		try {
			em.persist(user);
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Error persisting User");
		}
		finally {
			em.close();
		}
	}
	
	public User findByUserName(String userName){
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query query = em.createQuery(queryByUserName);
			User ret = (User) query.setParameter(1, userName).getSingleResult();
			return ret;
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Error retreiving single result");
		}
		finally {
			em.close();
		}
		return null;
	}
}
