/**
 *
 */
package org.telokers.servlet;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.telokers.model.User;
import org.telokers.model.dao.UserDao;

/**
 * @author trung
 *
 */
public class ApplicationInitializationListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(ApplicationInitializationListener.class.getName());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// any clean up?
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// all application initialization comes here
		User user = new User("bear");
		user.setEmail("bear@grizzly.com");
		user.setPassword("abc123");
		user.setName("Super Bear");

		UserDao.persistUser(user);
		logger.log(Level.INFO, "Application Initialization start");
		logger.log(Level.INFO, "Application Initialization end");
	}
}
