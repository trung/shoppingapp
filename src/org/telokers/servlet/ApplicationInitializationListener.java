/**
 *
 */
package org.telokers.servlet;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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



		logger.log(Level.INFO, "Application Initialization end");
	}
}
