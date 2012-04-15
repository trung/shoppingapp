/**
 *
 */
package org.telokers.servlet.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.DataStoreManager;
import org.telokers.model.Category;
import org.telokers.model.Item;
import org.telokers.model.index.ItemIndex;
import org.telokers.service.utils.JSONUtils;
import org.telokers.service.utils.ModelUtils;

import com.google.appengine.api.datastore.Key;

/**
 *
 * Just import some dummy data for testing
 *
 * @author trung
 *
 */
public class ImportStandingData extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = -2488675157052092290L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		int categoryCount = 10;
		int itemCount = 100;
		List<Category> importedCategories = new ArrayList<Category>(categoryCount);
		List<Item> importedItems = new ArrayList<Item>(itemCount);
		// import some categories
		DataStoreManager.instance().removeAll(Category.class);
		DataStoreManager.instance().removeAll(Item.class);
		for (int i = 0; i < categoryCount; i++) {
			Category cat = new Category();
			cat.setCategoryName("Category " + i);
			cat.setCategoryDescription("Description " + i);
			DataStoreManager.instance().save(cat);
			importedCategories.add(cat);
		}
		DataStoreManager.instance().flush();
		Random rnd = new Random();
		for (int i = 0; i < itemCount; i++) {
			Item item = new Item();
			item.setItemName("Item Name " + i);
			item.setItemDescription("Description " + i);
			item.setQuantity(10L);
			item.setCategoryIds(randomCategorySelection(importedCategories, rnd.nextInt(3)));
			try {
				DataStoreManager.instance().beginTransaction();
				ItemIndex itemIndex = new ItemIndex(item);
				itemIndex.addTag(item.getItemName());
				itemIndex.addTag(item.getItemDescription());
				itemIndex.addTags(ModelUtils.decompose(item.getItemName()));
				itemIndex.addTags(ModelUtils.decompose(item.getItemDescription()));
				DataStoreManager.instance().save(itemIndex);
				DataStoreManager.instance().commitTransaction();
			} finally {
				DataStoreManager.instance().rollbackIfAlive();
			}
			importedItems.add(item);
		}

		DataStoreManager.instance().flush();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("categories", importedCategories);
		response.put("items", importedItems);

		resp.getWriter().write(JSONUtils.toJSON(response));
	}

	/**
	 * @param importedCategories
	 * @param count
	 * @return
	 */
	private Set<Key> randomCategorySelection(
			List<Category> importedCategories, int count) {
		Set<Key> ids = new HashSet<Key>();
		int i = 0;
		Random rnd = new Random();
		while (i <= count) {
			Key selectedId = null;
			do {
				int idx =  rnd.nextInt(importedCategories.size());
				selectedId = importedCategories.get(idx).getId();
			} while (ids.contains(selectedId));
			ids.add(selectedId);
			i++;
		}
		return ids;
	}
}
