/**
 *
 */
package org.telokers.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.UserDao;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;

/**
 *
 * @author trung
 *
 */
public class AdminServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = -5826144668754372828L;

	private static final Logger logger = Logger.getLogger(AdminServlet.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String approve = req.getParameter("approve");
		String suspend = req.getParameter("suspend");

		if (approve != null) {
			modifyActiveStatus(req, resp, true);
		} else if (suspend != null) {
			modifyActiveStatus(req, resp, false);
		}
		render(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	/**
	 * @param req
	 * @param resp
	 * @param b
	 */
	private void modifyActiveStatus(HttpServletRequest req,
			HttpServletResponse resp, boolean b) {
		String[] userIds = req.getParameterValues("userIds");
		logger.log(Level.FINE, "Modifying active status for users .... set to [" + b + "]");
		if (userIds == null  || userIds.length == 0) {
			req.setAttribute(MiscConstants.ERROR_MESSAGE, "No users were selected");
		} else {
			for (String id : userIds) {
				User u = UserDao.findbyUserId(id);
				if (u != null) {
					u.setActive(b);
				}
				UserDao.persistUser(u);
				logger.fine("User Id [" + id + "] active status has been set to [" + b + "]");
			}
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void render(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> users = UserDao.findAll();
		req.setAttribute(MiscConstants.KEY_USERS, users);
		RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
		rp.forward(req, resp);
	}

}
