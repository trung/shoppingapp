/**
 *
 */
package org.telokers.dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Persistent Manager Factory
 *
 * @author trung
 *
 */
public class PMF {
	private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");


	public static PersistenceManagerFactory instance() {
		return pmfInstance;
	}
}
