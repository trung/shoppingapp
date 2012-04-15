/**
 *
 */
package org.telokers.service;

/**
 * Singleton to provide reference to all services.
 *
 * Be careful of cyclic references when implementing a factory method
 *
 * @author trung
 *
 */
public class AppContext {
	private static AppContext singleton = null;

	public static AppContext instance() {
		if (singleton == null) {
			singleton = new AppContext();
		}
		return singleton;
	}

	private AppContext() {

	}
}
