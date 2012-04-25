
/**
 @author tommyquangqthinh
*/

package org.telokers.servlet.api;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.telokers.model.User;
import org.telokers.model.dao.UserDao;
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
		HttpSession session = req.getSession(true);

		User user = UserDao.findbyUserId(userId);
		logger.log(Level.FINE, "user [" + userId + "] logging in");
		if(user != null && user.getPassword().equals(password)){
			logger.log(Level.FINE, "user [" + userId + "] login successfully");
			String key = UUID.randomUUID().toString();
			user.setUserSessionId(key);
			UserDao.persistUser(user);
			session.setAttribute(MiscConstants.USER_SESSION_KEY, key);
			RequestDispatcher rp = getServletContext().getRequestDispatcher("/home.jsp");
			rp.forward(req, resp);
		}
		else {
			logger.log(Level.FINE, "user [" + userId + "] login failed");
			req.setAttribute(MiscConstants.ERROR_MESSAGE, errorMsg);
			RequestDispatcher rp = getServletContext().getRequestDispatcher("/index.jsp");
			rp.forward(req, resp);
		}
	}
}

