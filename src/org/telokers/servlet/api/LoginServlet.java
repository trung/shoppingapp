
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
import org.telokers.service.utils.SecurityUtils;

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
		String errorMsg = "Invalid user/password or your account has not been approved yet.";
		HttpSession session = req.getSession(true);
		User user = UserDao.findbyUserId(userId);
		logger.log(Level.FINE, "user [" + userId + "] logging in with session id [" + session.getId() + "] where existing session is [" + (user == null ? null : user.getSessionId()) + "]");
		if(user != null
				&& user.getPassword().equals(SecurityUtils.hashPassword(password))
				&& user.isActive()){
			logger.log(Level.FINE, "user [" + userId + "] login successfully on session [" + session.getId() + "]");
			user.setUserSessionId(session.getId());
			user.setLastLogin(new Date());
			user.createCSRFToken();
			UserDao.persistUser(user);
			resp.sendRedirect("/secured/home");
		}
		else {
			logger.log(Level.FINE, "user [" + userId + "] login failed");
			if (userId != null) {
				req.setAttribute(MiscConstants.ERROR_MESSAGE, errorMsg);
			} else {
				User u = UserDao.findBySession(session.getId());
				if (u != null) {
					resp.sendRedirect("/secured/home");
					return;
				}
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

