package org.telokers.servlet.api;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.UserDao;
import org.telokers.model.User;

public class LogoutServlet extends HttpServlet{

	/**
	 *
	 */
	private static final long serialVersionUID = -6889944754374771505L;

	private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		User user = UserDao.findBySession(req.getSession().getId());
		if (user != null) {
			user.setUserSessionId("");
			UserDao.persistUser(user);
		}
		resp.sendRedirect("/login");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
