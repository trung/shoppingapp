/**
 *
 */
package org.telokers.servlet;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.telokers.dao.UserDao;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;

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
		logger.log(Level.INFO, "Application Initialization start");

		// all application initialization comes here
		User user = new User("admin");
		user.setEmail("admin@telokers.org");
		user.setPassword("admin");
		user.setName("Super Admin");
		user.setRole(MiscConstants.ROLE_ADMIN);
		UserDao.persistUser(user);

		user = new User("user");
		user.setEmail("user@telokers.org");
		user.setPassword("user");
		user.setName("Telokers");
		user.setRole(MiscConstants.ROLE_USER);
		UserDao.persistUser(user);


		logger.log(Level.INFO, "Application Initialization end");
	}
}
