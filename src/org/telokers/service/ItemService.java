/**
 *
 */
package org.telokers.service;

/**
 * @author trung
 *
 */
public class ItemService {

	private static ItemService instance = null;

	public static ItemService instance() {
		if (instance == null) {
			instance = new ItemService();
		}
		return instance;
	}

	private ItemService() {

	}

}
