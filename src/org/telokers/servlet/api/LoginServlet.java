
/**
 @author tommyquangqthinh
*/

package org.telokers.servlet.api;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.telokers.dao.UserDao;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;

public class LoginServlet extends HttpServlet{
	/**
	 *
	 */
	private static final long serialVersionUID = -6300072479769069177L;

	private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String errorMsg = "Invalid user/password";
		HttpSession session = req.getSession();
		User user = UserDao.findbyUserId(userId);
		logger.log(Level.FINE, "user [" + userId + "] logging in with session id [" + session.getId() + "]");
		if(user != null && user.getPassword().equals(password)){
			if (!user.hasExistingSessionId(session.getId())) { // login from other browser?
				req.setAttribute(MiscConstants.ERROR_MESSAGE, "Your session already exists. Please log out from that session and log in again.");
				RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp");
				rp.forward(req, resp);
			}
			logger.log(Level.FINE, "user [" + userId + "] login successfully");
			user.setUserSessionId(session.getId());
			user.setLastLogin(new Date());
			UserDao.persistUser(user);
			resp.sendRedirect("/secured/home");
		}
		else {
			logger.log(Level.FINE, "user [" + userId + "] login failed");
			if (userId != null) {
				req.setAttribute(MiscConstants.ERROR_MESSAGE, errorMsg);
			}
			RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			rp.forward(req, resp);
		}
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

