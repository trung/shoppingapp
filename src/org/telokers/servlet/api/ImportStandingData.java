/**
 *
 */
package org.telokers.servlet.api;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.UserDao;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;
import org.telokers.service.utils.SecurityUtils;

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

	private static final Logger logger = Logger.getLogger(ImportStandingData.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// all application initialization comes here
		User user = new User("admin");
		user.setEmail("admin@telokers.org");
		user.setPassword(SecurityUtils.hashPassword(MiscConstants.SHARE_SECRET));
		user.setName("Super Admin");
		user.setRole(MiscConstants.ROLE_ADMIN);
		user.setStatus(MiscConstants.STATUS_APPROVED);
		UserDao.persistUser(user);

		user = new User("user");
		user.setEmail("user@telokers.org");
		user.setPassword(SecurityUtils.hashPassword(MiscConstants.SHARE_SECRET));
		user.setName("Telokers");
		user.setRole(MiscConstants.ROLE_USER);
		user.setStatus(MiscConstants.STATUS_APPROVED);
		UserDao.persistUser(user);

		resp.sendRedirect("/login");

	}

	/**
	 * @param importedCategories
	 * @param count
	 * @return
	 */
	/*private Set<Key> randomCategorySelection(
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
	}*/
}
