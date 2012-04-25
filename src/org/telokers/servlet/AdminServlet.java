/**
 *
 */
package org.telokers.servlet;

import java.io.IOException;
import java.util.List;

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

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<User> users = UserDao.findAll();
		req.setAttribute(MiscConstants.KEY_USERS, users);
		RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
		rp.forward(req, resp);
	}

}
